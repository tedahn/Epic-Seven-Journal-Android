package com.kumaduma.epicseveninfo.Model.Hero.Skills;

import java.util.List;

public class Skills {
    private boolean isPassive;
    private int soulBurn;
    //@JsonAdapter(EmptyStringAsZeroAdapter.class)
    private int selfSkillBarValue;
    private String soulBurnEffect;
    private boolean awakenUpgrade;
    private int cooldown;
    private String name;
    private int soulAcquire;
    private String description;
    private List<Enhancement> enhancement;
    private List<String> buffs;
    private List<String> debuffs;

    public Skills(){}

    public boolean isPassive() {
        return isPassive;
    }

    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    public int getSoulBurn() {
        return soulBurn;
    }

    public void setSoulBurn(int soulBurn) {
        this.soulBurn = soulBurn;
    }

    public int getSelfSkillBarValue() {
        return selfSkillBarValue;
    }

    public void setSelfSkillBarValue(int selfSkillBarValue) {
        this.selfSkillBarValue = selfSkillBarValue;
    }

    public String getSoulBurnEffect() {
        return soulBurnEffect;
    }

    public void setSoulBurnEffect(String soulBurnEffect) {
        this.soulBurnEffect = soulBurnEffect;
    }

    public boolean isAwakenUpgrade() {
        return awakenUpgrade;
    }

    public void setAwakenUpgrade(boolean awakenUpgrade) {
        this.awakenUpgrade = awakenUpgrade;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoulAcquire() {
        return soulAcquire;
    }

    public void setSoulAcquire(int soulAcquire) {
        this.soulAcquire = soulAcquire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Enhancement> getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(List<Enhancement> enhancement) {
        this.enhancement = enhancement;
    }

    public List<String> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<String> buffs) {
        this.buffs = buffs;
    }

    public List<String> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(List<String> debuffs) {
        this.debuffs = debuffs;
    }
}
