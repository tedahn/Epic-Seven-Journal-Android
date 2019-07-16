package com.kumaduma.epicseveninfo.Model;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Comparator;

public class SimpleArtifactTier {
    private String name, fileId;
    private int rarity;
    private ArrayList<String> exclusive;
    private String pvpAvg;
    private String pveAvg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public ArrayList<String> getExclusive() {
        return exclusive;
    }

    public void setExclusive(ArrayList<String> exclusive) {
        this.exclusive = exclusive;
    }

    public String getPvpAvg() {
        return pvpAvg;
    }

    public void setPvpAvg(String pvpAvg) {
        this.pvpAvg = pvpAvg;
    }

    public String getPveAvg() {
        return pveAvg;
    }

    public void setPveAvg(String pveAvg) {
        this.pveAvg = pveAvg;
    }

    public class AlphabeticalComparator implements Comparator<SimpleArtifactTier> {
        @Override
        public int compare(SimpleArtifactTier o1, SimpleArtifactTier o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
