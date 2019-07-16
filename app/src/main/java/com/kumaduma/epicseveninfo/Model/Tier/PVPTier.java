package com.kumaduma.epicseveninfo.Model.Tier;

import java.util.List;

public class PVPTier {
    private String nameId;
    private double arenaOffense;
    private double arenaDefense;
    private double gwOffense;
    private double gwDefense;
    private double average;
    private List<String> recommendedSetList;
    private List<String> recommendedNeckList;
    private List<String> suggestedRoleList;
    private String recommendedArtifactImageId;
    private List<String> alternateArtifactImageIdList;
    private String note;
    
    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setAverage(){
        double sum = arenaDefense + arenaOffense + gwDefense + gwOffense;
        average = round((sum/4), 1);
    }

    public List<String> getRecommendedSetList() {
        return recommendedSetList;
    }

    public void setRecommendedSetList(List<String> recommendedSetList) {
        this.recommendedSetList = recommendedSetList;
    }

    public List<String> getRecommendedNeckList() {
        return recommendedNeckList;
    }

    public void setRecommendedNeckList(List<String> recommendedNeckList) {
        this.recommendedNeckList = recommendedNeckList;
    }

    public List<String> getSuggestedRoleList() {
        return suggestedRoleList;
    }

    public void setSuggestedRoleList(List<String> suggestedRoleList) {
        this.suggestedRoleList = suggestedRoleList;
    }


    public double getArenaOffense() {
        return arenaOffense;
    }

    public void setArenaOffense(double arenaOffense) {
        this.arenaOffense = arenaOffense;
    }

    public double getArenaDefense() {
        return arenaDefense;
    }

    public void setArenaDefense(double arenaDefense) {
        this.arenaDefense = arenaDefense;
    }

    public double getGwOffense() {
        return gwOffense;
    }

    public void setGwOffense(double gwOffense) {
        this.gwOffense = gwOffense;
    }

    public double getGwDefense() {
        return gwDefense;
    }

    public void setGwDefense(double gwDefense) {
        this.gwDefense = gwDefense;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRecommendedArtifactImageId() {
        return recommendedArtifactImageId;
    }

    public void setRecommendedArtifactImageId(String recommendedArtifactImageId) {
        this.recommendedArtifactImageId = recommendedArtifactImageId;
    }

    public List<String> getAlternateArtifactImageIdList() {
        return alternateArtifactImageIdList;
    }

    public void setAlternateArtifactImageIdList(List<String> alternateArtifactImageIdList) {
        this.alternateArtifactImageIdList = alternateArtifactImageIdList;
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
