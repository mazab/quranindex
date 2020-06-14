package org.mazab.quranindex.pojos;

public class EntryParent {
    protected String arParentName;
    protected String enParentName;
    protected String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getArParentName() {
        return arParentName;
    }

    public void setArAllParentsNames(String arParentName) {
        this.arParentName = arParentName;
    }

    public String getEnParentName() {
        return enParentName;
    }

    public void setEnParentName(String enParentName) {
        this.enParentName = enParentName;
    }

    public EntryParent() {

    }

    public EntryParent(String arParentName, String enParentName, String parentId) {
        this.enParentName = enParentName;
        this.arParentName = arParentName;
        this.parentId = parentId;
    }
}
