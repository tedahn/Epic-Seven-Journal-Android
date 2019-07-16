package com.kumaduma.epicseveninfo.Model.Hero;

import com.google.gson.annotations.JsonAdapter;
import com.kumaduma.epicseveninfo.Model.JsonDeserializer.EmptyArrayAsEmptyStringAdapter;

import java.util.Map;

public class SpecialtySkill {
    private String name;
    private String description;
    @JsonAdapter(EmptyArrayAsEmptyStringAdapter.class)
    private String dispatch;
    @JsonAdapter(EmptyArrayAsEmptyStringAdapter.class)
    private String enhancement;
    private Map<String, Integer> stats;

    public SpecialtySkill(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(String enhancement) {
        this.enhancement = enhancement;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }
}
