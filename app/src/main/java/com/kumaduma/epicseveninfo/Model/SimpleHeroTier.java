package com.kumaduma.epicseveninfo.Model;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;

public class SimpleHeroTier {
    private String fileId, name, classType, element, zodiac;
    private int rarity;
    private ArrayList<String> buffs, debuffs;
    private String pvpAvg, pveAvg;

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


    public String getPvpAvg() {
        return pvpAvg;
    }

    public void setPvpAvg(String pvpAvg) {
        this.pvpAvg = pvpAvg;
    }

    public String getPveAvg() {
        return pveAvg;
    }

    public void setPveAvg(String pveAvg) {
        this.pveAvg = pveAvg;
    }

    public double getPvpAvgAsDouble() {
        if(NumberUtils.isCreatable(pvpAvg))
            return Double.parseDouble(pvpAvg);
        return 0;
    }

    public double getPveAvgAsDouble() {
        if(NumberUtils.isCreatable(pveAvg))
            return Double.parseDouble(pveAvg);
        return 0;
    }
}
