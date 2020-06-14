package org.mazab.quranindex.pojos;

public class SearchResults {
    protected String language;
    protected String searchQuery;
    protected ShortTopic[] matches;
    protected Entry[] documentsMatches;

    public Entry[] getDocumentsMatches() {
        return documentsMatches;
    }

    public void setDocumentsMatches(Entry[] documentsMatches) {
        this.documentsMatches = documentsMatches;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public ShortTopic[] getMatches() {
        return matches;
    }

    public void setMatches(ShortTopic[] matches) {
        this.matches = matches;
    }

    public SearchResults(String language, String searchQuery, ShortTopic[] matches, Entry[] documentsMAtches) {
        this.language = language;
        this.searchQuery = searchQuery;
        this.matches = matches;
        this.documentsMatches = documentsMAtches;
    }

    public SearchResults() {
    }
}