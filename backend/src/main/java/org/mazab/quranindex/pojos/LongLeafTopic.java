package org.mazab.quranindex.pojos;

public class LongLeafTopic extends LongTopic {
    protected Entry[] entries;

    public Entry[] getEntries() {
        return entries;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;
    }

    public LongLeafTopic(Topic topic) {
        this(topic.getId(), topic.getEnName(), topic.getArName(), topic.getType(), null, topic.getEntries().toArray(new Entry[topic.getEntries().size()]));
    }

    public LongLeafTopic(String id, String enName, String arName, String type, BaseTopic[] parents, Entry[] entries) {
        super(id, enName, arName, type, parents);
        this.entries = entries;
    }

    public LongLeafTopic() {
    }
}
