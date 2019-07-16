package com.kumaduma.epicseveninfo.Model.Hero;

import com.google.gson.annotations.JsonAdapter;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Skills;
import com.kumaduma.epicseveninfo.Model.JsonDeserializer.EmptyArrayAsEmptyStringAdapter;
import com.kumaduma.epicseveninfo.Model.JsonDeserializer.EmptyStringAsNullTypeAdapter;

import java.util.List;
import java.util.Map;

public class Hero {
    private String fileId;
    private String name;
    private String classType;
    private String element;
    private String zodiac;
    @JsonAdapter(EmptyStringAsNullTypeAdapter.class)
    private SpecialtyChangeName specialtyChangeName;
    private String selfSkillBarName;
    @JsonAdapter(EmptyArrayAsEmptyStringAdapter.class)
    private String background;
    private int rarity;
    private List<Relations> relations;
    private Map<String, Stats> stats;
    private List<Skills> skills;
    private SpecialtySkill specialtySkill;
    private Camping camping;
    private String memoryImprintAttribute;
    private MemoryImprintFormation memoryImprintFormation;
    private List<MemoryImprint> memoryImprint;
    private List<Awakening> awakening;

    public Hero(){}

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

    public SpecialtyChangeName getSpecialtyChangeName() {
        return specialtyChangeName;
    }

    public void setSpecialtyChangeName(SpecialtyChangeName specialtyChangeName) {
        this.specialtyChangeName = specialtyChangeName;
    }

    public String getSelfSkillBarName() {
        return selfSkillBarName;
    }

    public void setSelfSkillBarName(String selfSkillBarName) {
        this.selfSkillBarName = selfSkillBarName;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public List<Relations> getRelations() {
        return relations;
    }

    public void setRelations(List<Relations> relations) {
        this.relations = relations;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public SpecialtySkill getSpecialtySkill() {
        return specialtySkill;
    }

    public void setSpecialtySkill(SpecialtySkill specialtySkill) {
        this.specialtySkill = specialtySkill;
    }

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }

    public MemoryImprintFormation getMemoryImprintFormation() {
        return memoryImprintFormation;
    }

    public void setMemoryImprintFormation(MemoryImprintFormation memoryImprintFormation) {
        this.memoryImprintFormation = memoryImprintFormation;
    }

    public List<MemoryImprint> getMemoryImprint() {
        return memoryImprint;
    }

    public void setMemoryImprint(List<MemoryImprint> memoryImprint) {
        this.memoryImprint = memoryImprint;
    }

    public Map<String, Stats> getStats() {
        return stats;
    }

    public void setStats(Map<String, Stats> stats) {
        this.stats = stats;
    }

    public List<Awakening> getAwakening() {
        return awakening;
    }

    public Awakening getAwakeningByRank(int rank){
        for (Awakening a : awakening){
            if(a.getRank()==rank) return a;
        }
        return null;
    }

    public void setAwakening(List<Awakening> awakening) {
        this.awakening = awakening;
    }


    public String getMemoryImprintAttribute() {
        return memoryImprintAttribute;
    }

    public void setMemoryImprintAttribute(String memoryImprintAttribute) {
        this.memoryImprintAttribute = memoryImprintAttribute;
    }
}
