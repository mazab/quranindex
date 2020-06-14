package org.mazab.quranindex.pojos;

public class EntryWithText extends Entry {
    String arText;
    String enText;

    public String getArText() {
        return arText;
    }

    public void setArText(String arText) {
        this.arText = arText;
    }

    public String getEnText() {
        return enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }

    public EntryWithText(int surah, String ayah, String arText, String enText) {
        super(surah, ayah);
        this.arText = arText;
        this.enText = enText;
    }

    public EntryWithText() {
    }
}
