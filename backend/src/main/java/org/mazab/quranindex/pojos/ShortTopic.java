package org.mazab.quranindex.pojos;

import java.util.ArrayList;
import java.util.List;

public class ShortTopic extends BaseTopic {
    protected String[] arChildrenNames;
    protected String[] enChildrenNames;
    protected String[] arParentsNames;
    protected String[] enParentsNames;

    public String[] getArChildrenNames() {
        return arChildrenNames;
    }

    public void setArChildrenNames(String[] arChildrenNames) {
        this.arChildrenNames = arChildrenNames;
    }

    public String[] getEnChildrenNames() {
        return enChildrenNames;
    }

    public void setEnChildrenNames(String[] enChildrenNames) {
        this.enChildrenNames = enChildrenNames;
    }

    public String[] getArParentsNames() {
        return arParentsNames;
    }

    public void setArParentsNames(String[] arParentsNames) {
        this.arParentsNames = arParentsNames;
    }

    public String[] getEnParentsNames() {
        return enParentsNames;
    }

    public void setEnParentsNames(String[] enParentsNames) {
        this.enParentsNames = enParentsNames;
    }

    public ShortTopic(Topic topic) {
        this(topic.getId(), topic.getEnName(), topic.getArName(), topic.getType(), null, null, null, null);
        if (Topic.TOPIC_TYPE_LEAF.equals(topic.getType())) {
            List<String> enNames = new ArrayList<>();
            List<String> arNames = new ArrayList<>();
            topic.getEntries().forEach(entry -> {
                enNames.add(entry.enName);
                arNames.add(entry.arName);
            });
            this.enChildrenNames = enNames.toArray(new String[enNames.size()]);
            this.arChildrenNames = arNames.toArray(new String[arNames.size()]);
        }
    }

    public ShortTopic(String id, String enName, String arName, String type, String[] arChildrenNames,
            String[] enChildrenNames, String[] arParentsNames, String[] enParentsNames) {
        super(id, enName, arName, type);
        this.arChildrenNames = arChildrenNames;
        this.enChildrenNames = enChildrenNames;
        this.arParentsNames = arParentsNames;
        this.enParentsNames = enParentsNames;
    }

    public ShortTopic() {
    }
}
