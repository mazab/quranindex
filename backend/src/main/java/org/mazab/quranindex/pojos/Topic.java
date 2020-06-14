package org.mazab.quranindex.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {
    public static final String TOPIC_TYPE_LEAF = "leaf";
    public static final String TOPIC_TYPE_ROOT = "root";

    private String id;
    private String enName;
    private String arName;
    private String enFullName;
    private String arFullName;
    private List<String> children;
    private List<Entry> entries;
    private String type;

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

    public String getEnFullName() {
        return enFullName;
    }

    public void setEnFullName(String enFullName) {
        this.enFullName = enFullName;
    }

    public String getArFullName() {
        return arFullName;
    }
 
    public void setArFullName(String arFullName) {
        this.arFullName = arFullName;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Topic(String id, String enName, String arName, String enFullName, String arFullName, List<String> children,
            List<Entry> entries, String type) {
        this.id = id;
        this.enName = enName;
        this.arName = arName;
        this.enFullName = enFullName;
        this.arFullName = arFullName;
        this.children = children;
        this.entries = entries;
        this.type = type;
    }

    public Topic() {
    }
}