package org.mazab.quranindex.pojos;

public abstract class LongTopic extends BaseTopic {
    protected BaseTopic[] parents;

    public BaseTopic[] getParents() {
        return parents;
    }

    public void setParents(BaseTopic[] parents) {
        this.parents = parents;
    }

    public LongTopic(String id, String enName, String arName, String type, BaseTopic[] parents) {
        super(id, enName, arName, type);
        this.parents = parents;
    }

    public LongTopic() {
    }
}
