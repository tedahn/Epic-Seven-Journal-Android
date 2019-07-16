package com.kumaduma.epicseveninfo.Model.Hero.Skills.Modifiers;

public class StatModifier {
    private String name;
    private String description;
    private String section;
    private String stat;
    private String type;
    private String target;
    private float value;
    private float soulburn;

    public StatModifier(){}

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getSoulburn() {
        return soulburn;
    }

    public void setSoulburn(float soulburn) {
        this.soulburn = soulburn;
    }
}
