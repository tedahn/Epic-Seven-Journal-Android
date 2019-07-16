package com.kumaduma.epicseveninfo.Model.Tier;

import java.util.List;

public class PVETier {
    private String nameId;
    private double hunt;
    private double abyss;
    private double raid;
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

    public double getHunt() {
        return hunt;
    }

    public void setHunt(double hunt) {
        this.hunt = hunt;
    }

    public double getAbyss() {
        return abyss;
    }

    public void setAbyss(double abyss) {
        this.abyss = abyss;
    }

    public double getRaid() {
        return raid;
    }

    public void setRaid(double raid) {
        this.raid = raid;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setAverage(){
        double sum = hunt + abyss + raid;
        average = round((sum/3), 1);
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

    public String getRecommendedArtifactImageId() {
        return recommendedArtifactImageId;
    }

    public void setRecommendedArtifactImageId(String recommendedArtifactId) {
        this.recommendedArtifactImageId = recommendedArtifactId;
    }

    public List<String> getAlternateArtifactImageIdList() {
        return alternateArtifactImageIdList;
    }

    public void setAlternateArtifactImageIdList(List<String> alternateArtifactImageIdList) {
        this.alternateArtifactImageIdList = alternateArtifactImageIdList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
