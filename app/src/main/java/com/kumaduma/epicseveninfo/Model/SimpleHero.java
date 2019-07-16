package com.kumaduma.epicseveninfo.Model;

import java.util.ArrayList;

public class SimpleHero {
    private String fileId, name, classType, element, zodiac;
    private int rarity;
    private ArrayList<String> buffs, debuffs;

    public SimpleHero(String fileId, String name, int rarity, String classType, String element, String zodiac, ArrayList<String> buffs, ArrayList<String> debuffs) {
        this.fileId = fileId;
        this.name = name;
        this.rarity = rarity;
        this.classType = classType;
        this.element = element;
        this.zodiac = zodiac;
        this.buffs = buffs;
        this.debuffs = debuffs;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public ArrayList<String> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<String> buffs) {
        this.buffs = buffs;
    }

    public ArrayList<String> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(ArrayList<String> debuffs) {
        this.debuffs = debuffs;
    }

}
