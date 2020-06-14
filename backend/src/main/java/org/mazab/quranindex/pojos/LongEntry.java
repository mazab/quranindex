package org.mazab.quranindex.pojos;

import java.util.*;

public class LongEntry extends Entry {
    protected String arText;
    protected String enText;
    protected List<EntryParent> parents;
    protected String arLinkWWW;
    protected String enLinkWWW;
    protected String arLinkMDOT;
    protected String enLinkMDOT;

    public String getArLinkWWW() {
        return arLinkWWW;
    }

    public void setArLinkWWW(String arLinkWWW) {
        this.arLinkWWW = arLinkWWW;
    }

    public String getEnLinkWWW() {
        return enLinkWWW;
    }

    public void setEnLinkWWW(String enLinkWWW) {
        this.enLinkWWW = enLinkWWW;
    }

    public String getArLinkMDOT() {
        return arLinkMDOT;
    }

    public void setArLinkMDOT(String arLinkMDOT) {
        this.arLinkMDOT = arLinkMDOT;
    }

    public String getEnLinkMDOT() {
        return enLinkMDOT;
    }

    public void setEnLinkMDOT(String enLinkMDOT) {
        this.enLinkMDOT = enLinkMDOT;
    }

    public List<EntryParent> getParents() {
        return this.parents;
    }

    public void setParents(List<EntryParent> parents) {
        this.parents = parents;
    }

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

    public LongEntry(int surah, String ayah, String arText, String enText, List<String> arAllParentsNames, List<String> enAllParentsNames, List<String> allParentsIds) {
        super(surah, ayah);
        this.arText = arText;
        this.enText = enText;
        this.parents = new ArrayList<>();
        for (int i = 0; i < allParentsIds.size(); i++) {
            this.parents.add(new EntryParent(arAllParentsNames.get(i), enAllParentsNames.get(i), allParentsIds.get(i)));
        }

        String varPart = "#aya=" + surah + "_";
        if (ayah.contains(":")) {
            String[] split = ayah.split(":");
            varPart += split[0];
        } else {
            varPart += ayah;
        }
        String baseURL = "https://quran.ksu.edu.sa/";
        String ar = "?l=ar";
        String en = "?l=en";
        String www = "index.php";
        String mdot = "m.php";
        this.arLinkMDOT = baseURL + mdot + ar;
        this.enLinkMDOT = baseURL + mdot + en;
        this.arLinkWWW = baseURL + www + ar;
        this.enLinkWWW = baseURL + www + en;
    }

    public LongEntry() {
    }

    public static LongEntry createOrGetLongEntry(int surahNumber, LongEntry[] surah, String ayah, Map<String, Topic> topics) {
        if (!ayah.contains(":")) {
            return surah[Integer.parseInt(ayah)];
        }

        StringBuffer arText = new StringBuffer();
        StringBuffer enText = new StringBuffer();
        List<String> arParentsNames = new ArrayList<>();
        List<String> enParentsNames = new ArrayList<>();
        List<String> parentsIds = new ArrayList<>();
        Set<String> parentsIdsSet = new HashSet<>();
        String[] split = ayah.split("\\:");
        int start = Integer.parseInt(split[0]);
        int end = Integer.parseInt(split[1]);
        for (int i = start; i <= end; i++) {
            arText.append(surah[i].getArText()).append(" [ " + i + "] ");
            enText.append(surah[i].getEnText()).append("[ " + i + " ] ");
            for (int j = 0; j < surah[i].getParents().size(); j++) {
                String currentId = surah[i].getParents().get(j).getParentId();
                if (!parentsIdsSet.contains(currentId)) {
                    parentsIds.add(currentId);
                    parentsIdsSet.add(currentId);
                    arParentsNames.add(surah[i].getParents().get(j).getArParentName());
                    enParentsNames.add(surah[i].getParents().get(j).getEnParentName());
                }
            }
        }

        return new LongEntry(surahNumber, ayah, arText.toString(), enText.toString(), arParentsNames, enParentsNames, parentsIds);
    }
}
