package com.kumaduma.epicseveninfo.Model.Hero.Skills.Modifiers;

public class Pow {
    private String name;
    private String section;
    private int value;
    private int soulburn;
    public Pow(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSoulburn() {
        return soulburn;
    }

    public void setSoulburn(int soulburn) {
        this.soulburn = soulburn;
    }
}
