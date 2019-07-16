package com.kumaduma.epicseveninfo.Model.Hero.Skills;

import java.util.List;

public class Enhancement {
    private String description;
    private List<Resources> resources;

    public Enhancement(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }
}
