package org.mazab.common.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

public class Index {
    public static final int MAX_SEARCH_RESULTS = 50;
    public static final String INDEX_LANGUAGE_ARABIC = "ar";
    public static final String INDEX_LANGUAGE_ENGLISH = "en";

    private static final String FIELD_NAME_CONTENT = "content";

    private IndexSearcher searcher;
    private QueryParser queryParser;
    private String[] topicsKeys;

    public Index(String language, Map<String, String> topics) {
        try {
            Analyzer contentAnalyzer;
            switch (language) {
                case INDEX_LANGUAGE_ARABIC:
                    contentAnalyzer = new ArabicAnalyzer();
                    break;
                default:
                    contentAnalyzer = new EnglishAnalyzer();
                    break;
            }
            List<IndexReader> indexReaders = new ArrayList<>();
            List<String> topicsKeys = new ArrayList<>();
            topics.forEach((key, value) -> {
                MemoryIndex index = new MemoryIndex();
                index.addField(FIELD_NAME_CONTENT, value, contentAnalyzer);
                indexReaders.add(index.createSearcher().getIndexReader());
                topicsKeys.add(key);
            });
            MultiReader mReader = new MultiReader(indexReaders.toArray(new IndexReader[indexReaders.size()]), true);
            this.searcher = new IndexSearcher(mReader);
            this.queryParser = new QueryParser(FIELD_NAME_CONTENT, contentAnalyzer);
            this.topicsKeys = topicsKeys.toArray(new String[topicsKeys.size()]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> search(String query, int maxResults) {
        if (maxResults == 0)
            maxResults = MAX_SEARCH_RESULTS;
        List<String> matchedTopics = new ArrayList<>();
        try {
            ScoreDoc[] results = searcher.search(queryParser.parse(query), maxResults).scoreDocs;
            for (ScoreDoc scoreDoc : results) {
                matchedTopics.add(this.topicsKeys[scoreDoc.doc]);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return matchedTopics;
    }
}