package com.kumaduma.epicseveninfo.Model;

import java.util.ArrayList;
import java.util.Comparator;

public class SimpleArtifact {
    private String name, fileId;
    private int rarity;
    private ArrayList<String> exclusive;

    public SimpleArtifact(String fileId, String name, int rarity, ArrayList<String> exclusive) {
        this.fileId = fileId;
        this.name = name;
        this.rarity = rarity;
        this.exclusive = exclusive;
    }

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

    public class AlphabeticalComparator implements Comparator<SimpleArtifact> {
        @Override
        public int compare(SimpleArtifact o1, SimpleArtifact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
