package org.mazab.quranindex.pojos;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Surah {
    public static class Ayah {
        @JacksonXmlProperty(isAttribute=true)
        private int index;
        @JacksonXmlProperty(isAttribute=true)
        private String text;
        @JacksonXmlProperty(isAttribute=true)
        private String bismillah;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getBismillah() {
            return bismillah;
        }

        public void setBismillah(String bismillah) {
            this.bismillah = bismillah;
        }
    }

    @JacksonXmlElementWrapper(useWrapping=false)
    private List<Ayah> ayah;
    @JacksonXmlProperty(isAttribute=true)
    private String name;
    @JacksonXmlProperty(isAttribute=true)
    private int index;

    public List<Ayah> getAyah() {
        return ayah;
    }

    public void setAyah(List<Ayah> ayahs) {
        this.ayah = ayahs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}