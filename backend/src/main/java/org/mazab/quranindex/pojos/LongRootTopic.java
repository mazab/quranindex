package org.mazab.quranindex.pojos;

public class LongRootTopic extends LongTopic {
    protected ShortTopic[] children;

    public ShortTopic[] getChildren() {
        return children;
    }

    public void setChildren(ShortTopic[] children) {
        this.children = children;
    }

    public LongRootTopic(Topic topic) {
        this(topic.getId(), topic.getEnName(), topic.getArName(), topic.getType(), null, null);
    }

    public LongRootTopic(String id, String enName, String arName, String type, BaseTopic[] parents, ShortTopic[] children) {
        super(id, enName, arName, type, parents);
        this.children = children;
    }

    public LongRootTopic() {
    }
}
