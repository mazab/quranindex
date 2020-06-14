package org.mazab.quranindex.pojos;

public class BaseTopic {
    protected String id;
    protected String enName;
    protected String arName;
    protected String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseTopic(Topic topic) {
        this(topic.getId(), topic.getEnName(), topic.getArName(), topic.getType());
    }

    public BaseTopic(String id, String enName, String arName, String type) {
        this.id = id;
        this.enName = enName;
        this.arName = arName;
        this.type = type;
    }

    public BaseTopic() {
    }
}
