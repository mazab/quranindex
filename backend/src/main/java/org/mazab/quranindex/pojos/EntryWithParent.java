package org.mazab.quranindex.pojos;

public class EntryWithParent {
    private LongEntry entry;
    private LongTopic parent;

    public LongEntry getEntry() {
        return entry;
    }

    public void setEntry(LongEntry entry) {
        this.entry = entry;
    }

    public LongTopic getParent() {
        return parent;
    }

    public void setParent(LongTopic parent) {
        this.parent = parent;
    }

    public EntryWithParent(LongEntry entry, LongTopic parent) {
        this.entry = entry;
        this.parent = parent;
    }

    public EntryWithParent() {
    }
}