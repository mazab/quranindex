package org.mazab.quranindex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mazab.common.index.Index;
import org.mazab.quranindex.pojos.*;
import org.mazab.quranindex.pojos.Surah.Ayah;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    @Value("${topics.file.path}")
    private String topicsFilePath;
    @Value("${en.quran.file.path}")
    private String enQuranFilePath;
    @Value("${ar.quran.file.path}")
    private String arQuranFilePath;

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<String, LongTopic> topicsById = new HashMap<>();
    private Map<String, ShortTopic> shortTopicsById = new HashMap<>();
    private Map<String, LongEntry> entriesById = new HashMap<>();
    private List<ShortTopic> topicsRoot = new ArrayList<>();
    private LongEntry[][] quran = new LongEntry[115][];
    private Index arIndex;
    private Index enIndex;
    private Index arQuranIndex;
    private Index enQuranIndex;

    public LongTopic[] getAllTopics() {
        return topicsById.values().toArray(new LongTopic[topicsById.values().size()]);
    }

    public ShortTopic[] getRootTopics() {
        return topicsRoot.toArray(new ShortTopic[topicsRoot.size()]);
    }

    public LongTopic getTopicById(String id) {
        if (topicsById.containsKey(id)) {
            return topicsById.get(id);
        }
        return null;
    }

    public EntryWithParent getEntry(String parent, String surah, String ayah) {
        String id = parent + "_" + surah + "." + ayah;
        if (entriesById.containsKey(id)) {
            return new EntryWithParent(entriesById.get(id), topicsById.get(parent));
        }
        return null;
    }

    public LongEntry getEntry(int surah, int ayah) {
        if (surah < quran.length && ayah < quran[surah].length) {
            return quran[surah][ayah];
        }
        return null;
    }

    public SearchResults searchTopicsNames(String searchString, String language) {
        List<ShortTopic> matchedTopics = new ArrayList<>();
        List<EntryWithText> matchedDocuments = new ArrayList<>();
        List<String> matches;
        List<String> documentsMatches;
        
        if (Index.INDEX_LANGUAGE_ARABIC.equals(language)) {
            matches = arIndex.search(searchString, 0);
            documentsMatches = arQuranIndex.search(searchString, 0);
        } else {
            matches = enIndex.search(searchString, 0);
            documentsMatches = enQuranIndex.search(searchString, 0);
        }

        matches.forEach(k -> {
            matchedTopics.add(shortTopicsById.get(k));
        });

        documentsMatches.forEach(k -> {
            String[] split = k.split("_");
            int surah = Integer.parseInt(split[0]);
            int ayah = Integer.parseInt(split[1]);
            LongEntry fullEntry = quran[surah][ayah];
            matchedDocuments.add(new EntryWithText(surah, "" + ayah, fullEntry.getArText(), fullEntry.getEnText()));
        });

        return new SearchResults(language, searchString, matchedTopics.toArray(new ShortTopic[matchedTopics.size()]), matchedDocuments.toArray(new Entry[matchedDocuments.size()]));
    }

    @PostConstruct
    public void init() {
        // Initialize index...
        try {
            Map<String, String> enContent = new HashMap<>();
            Map<String, String> arContent = new HashMap<>();
            Map<String, BaseTopic> baseTopics = new HashMap<>();
            Map<String, String> enQuran = new HashMap<>();
            Map<String, String> arQuran = new HashMap<>();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Topic> topics = objectMapper.readValue(
                    resourceLoader.getResource("classpath:" + topicsFilePath).getInputStream(),
                    new TypeReference<>() {
                    }
            );

            class SimpleTopics {
                List<String> ids = new ArrayList<>();
                List<String> arNames = new ArrayList<>();
                List<String> enNames = new ArrayList<>();
            }
            Map<String, SimpleTopics> inverseIndex = new HashMap<>();
            topics.forEach((key, value) -> {
                if (value.getType().equals("leaf")) {
                    value.getEntries().forEach(entry -> {
                        if (entry.getAyah().contains(":")) {
                            String[] split = entry.getAyah().split("\\:");
                            int start = Integer.parseInt(split[0]);
                            int end = Integer.parseInt(split[1]);
                            for (int i = start; i <= end; i++) {
                                String id = "" + entry.getSurah() + "_" + i;
                                SimpleTopics simpleTopics;
                                if (!inverseIndex.containsKey(id)) {
                                    simpleTopics = new SimpleTopics();
                                    inverseIndex.put(id, simpleTopics);
                                } else {
                                    simpleTopics = inverseIndex.get(id);
                                }
                                simpleTopics.arNames.add(value.getArFullName());
                                simpleTopics.enNames.add(value.getEnFullName());
                                simpleTopics.ids.add(value.getId());
                            }
                        } else {
                            String id = "" + entry.getSurah() + "_" + entry.getAyah();
                            SimpleTopics simpleTopics;
                            if (!inverseIndex.containsKey(id)) {
                                simpleTopics = new SimpleTopics();
                                inverseIndex.put(id, simpleTopics);
                            } else {
                                simpleTopics = inverseIndex.get(id);
                            }
                            simpleTopics.arNames.add(value.getArFullName());
                            simpleTopics.enNames.add(value.getEnFullName());
                            simpleTopics.ids.add(value.getId());
                        }
                    });
                }
            });

            XmlMapper xmlMapper = new XmlMapper();
            List<Surah> arQuranList = xmlMapper.readValue(resourceLoader.getResource("classpath:" + arQuranFilePath).getInputStream(),
                    new TypeReference<>() {
                    });
            List<Surah> enQuranList = xmlMapper.readValue(resourceLoader.getResource("classpath:" + enQuranFilePath).getInputStream(), new TypeReference<List<Surah>>(){});

            for (int i = 1; i < quran.length; i++) {
                Surah arSurah = arQuranList.get(i - 1);
                Surah enSurah = enQuranList.get(i - 1);
                int surahSize = arSurah.getAyah().size();
                quran[i] = new LongEntry[surahSize + 1];
                for (int j = 1; j <= surahSize; j++) {
                    Ayah arAyah = arSurah.getAyah().get(j - 1);
                    Ayah enAyah = enSurah.getAyah().get(j - 1);
                    String key = "" + i + "_" + j;
                    SimpleTopics simpleTopics = inverseIndex.get(key);
                    if (simpleTopics == null) {
                        simpleTopics = new SimpleTopics();
                    }
                    quran[i][j] = new LongEntry(i, "" + j, arAyah.getText(), enAyah.getText(), simpleTopics.arNames, simpleTopics.enNames, simpleTopics.ids);
                    enQuran.put(key, enAyah.getText());
                    arQuran.put(key, arAyah.getText());
                }
            }
            this.enQuranIndex = new Index(Index.INDEX_LANGUAGE_ENGLISH, enQuran);
            this.arQuranIndex = new Index(Index.INDEX_LANGUAGE_ARABIC, arQuran);

            topics.forEach((key, topic) -> {
                baseTopics.put(topic.getId(), new BaseTopic(topic));

                LongTopic longTopic;
                if (Topic.TOPIC_TYPE_ROOT.equals(topic.getType())) {
                    longTopic = new LongRootTopic(topic);
                } else {
                    longTopic = new LongLeafTopic(topic);
                    topic.getEntries().forEach(entry -> {
                        String entryId = entry.getSurah() + "." + entry.getAyah();
                        String fullEntryId = topic.getId() + "_" + entryId;
                        LongEntry longEntry = LongEntry.createOrGetLongEntry(entry.getSurah(), quran[entry.getSurah()], entry.getAyah(), topics);
                        entriesById.put(fullEntryId, longEntry);
                    });
                }
                this.topicsById.put(key, longTopic);

                ShortTopic shortTopic = new ShortTopic(topic);
                this.shortTopicsById.put(topic.getId(), shortTopic);
                if (topic.getId().indexOf(".") < 0) {
                    this.topicsRoot.add(shortTopic);
                }

                enContent.put(key, topic.getEnName());
                arContent.put(key, topic.getArName());
            });
            this.enIndex = new Index(Index.INDEX_LANGUAGE_ENGLISH, enContent);
            this.arIndex = new Index(Index.INDEX_LANGUAGE_ARABIC, arContent);

            // Now we itirate again over our class variables to initialize missing properties...
            topics.forEach((key, topic) -> {
                LongTopic longTopic = topicsById.get(topic.getId());
                ShortTopic shortTopic = shortTopicsById.get(topic.getId());

                if (Topic.TOPIC_TYPE_ROOT.equals(topic.getType())) {
                    List<ShortTopic> children = new ArrayList<>();
                    List<String> childrenEnNames = new ArrayList<>();
                    List<String> childrenArNames = new ArrayList<>();
                    topic.getChildren().forEach(childId -> {
                        ShortTopic child = shortTopicsById.get(childId);
                        children.add(child);
                        childrenArNames.add(child.getArName());
                        childrenEnNames.add(child.getEnName());
                    });
                    ((LongRootTopic) longTopic).setChildren(children.toArray(new ShortTopic[children.size()]));
                    shortTopic.setArChildrenNames(childrenArNames.toArray(new String[childrenArNames.size()]));
                    shortTopic.setEnChildrenNames(childrenEnNames.toArray(new String[childrenEnNames.size()]));
                }

                List<BaseTopic> parents = new ArrayList<>();
                List<String> parentsEnNames = new ArrayList<>();
                List<String> parentsArNames = new ArrayList<>();
                String[] idParts = topic.getId().split("\\.");
                if (idParts.length > 1) {
                    String currentParentId = idParts[0];
                    for (int i = 1; i < idParts.length; i++) {
                        BaseTopic parent = baseTopics.get(currentParentId);
                        parents.add(parent);
                        parentsEnNames.add(parent.getEnName());
                        parentsArNames.add(parent.getArName());
                        currentParentId = currentParentId + "." + idParts[i];
                    }
                }
                longTopic.setParents(parents.toArray(new BaseTopic[parents.size()]));
                shortTopic.setEnParentsNames(parentsEnNames.toArray(new String[parentsEnNames.size()]));
                shortTopic.setArParentsNames(parentsArNames.toArray(new String[parentsArNames.size()]));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}