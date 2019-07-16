package com.kumaduma.epicseveninfo.Model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

class ArtifactStats{
    Map<String,Integer> base;
    Map<String,Integer> max;

    public Map<String, Integer> getBase() {
        return base;
    }

    public Map<String, Integer> getMax() {
        return max;
    }
}

public class Artifact {
    private String name, fileId;
    private int rarity;
    private List<String> exclusive;
    private List<String> loreDescription;
    private Map<String, String> skillDescription;
    private ArtifactStats stats;

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

    public List<String> getExclusive() {
        return exclusive;
    }

    public void setExclusive(List<String> exclusive) {
        this.exclusive = exclusive;
    }

    public List<String> getLoreDescription() {
        return loreDescription;
    }

    public void setLoreDescription(List<String> loreDescription) {
        this.loreDescription = loreDescription;
    }

    public Map<String, String> getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(Map<String, String> skillDescription) {
        this.skillDescription = skillDescription;
    }

    public ArtifactStats getStats() {
        return stats;
    }

    public void setStats(ArtifactStats stats) {
        this.stats = stats;
    }

    public Map<String, Integer> getBase() {
        return stats.base;
    }

    public Map<String, Integer> getMax() {
        return stats.max;
    }

    public class AlphabeticalComparator implements Comparator<Artifact> {
        @Override
        public int compare(Artifact o1, Artifact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
