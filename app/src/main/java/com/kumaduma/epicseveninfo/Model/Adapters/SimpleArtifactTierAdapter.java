package com.kumaduma.epicseveninfo.Model.Adapters;

import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.Model.SimpleHeroTier;

public class SimpleArtifactTierAdapter {

    SimpleArtifactTier artifact;

    public SimpleArtifactTierAdapter(SimpleArtifact artifact, String pve, String pvp){
        SimpleArtifactTier newArtifact = new SimpleArtifactTier();
        newArtifact.setFileId(artifact.getFileId());
        newArtifact.setName(artifact.getName());
        newArtifact.setExclusive(artifact.getExclusive());
        newArtifact.setRarity(artifact.getRarity());

        newArtifact.setPveAvg(pve);
        newArtifact.setPvpAvg(pvp);

        this.artifact = newArtifact;
    }

    public SimpleArtifactTier getArtifact() {
        return artifact;
    }
}
