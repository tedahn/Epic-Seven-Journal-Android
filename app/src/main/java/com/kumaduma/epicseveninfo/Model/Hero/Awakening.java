package com.kumaduma.epicseveninfo.Model.Hero;

import com.kumaduma.epicseveninfo.Model.Hero.Skills.Resources;

import java.util.List;
import java.util.Map;

public class Awakening {
    private int rank;
    private boolean skillUpgrade;
    private List<Map<String, Double>> statsIncrease;
    private List<Resources> resources;

    public Awakening(){}

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isSkillUpgrade() {
        return skillUpgrade;
    }

    public void setSkillUpgrade(boolean skillUpgrade) {
        this.skillUpgrade = skillUpgrade;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    public List<Map<String, Double>> getStatsIncrease() {
        return statsIncrease;
    }

    public void setStatsIncrease(List<Map<String, Double>> statsIncrease) {
        this.statsIncrease = statsIncrease;
    }
}
