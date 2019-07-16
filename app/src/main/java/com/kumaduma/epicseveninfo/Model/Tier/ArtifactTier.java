package com.kumaduma.epicseveninfo.Model.Tier;

public class ArtifactTier {
    private String nameId;
    private String artifactImageId;
    private String tierEnvironment;
    private String tierPlayer;
    private String description;

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getArtifactImageId() {
        return artifactImageId;
    }

    public void setArtifactImageId(String artifactImageId) {
        this.artifactImageId = artifactImageId;
    }

    public String getTierEnvironment() {
        return tierEnvironment;
    }

    public void setTierEnvironment(String tierEnvironment) {
        this.tierEnvironment = tierEnvironment;
    }

    public String getTierPlayer() {
        return tierPlayer;
    }

    public void setTierPlayer(String tierPlayer) {
        this.tierPlayer = tierPlayer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
