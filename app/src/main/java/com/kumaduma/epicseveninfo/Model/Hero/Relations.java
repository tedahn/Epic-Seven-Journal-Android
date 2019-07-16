package com.kumaduma.epicseveninfo.Model.Hero;

public class Relations {
    private String relationType;
    private String name;
    private String fileId;
    public Relations(String relationType,String name,String fileId){
        this.relationType = relationType;
        this.name = name;
        this.fileId = fileId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setfileId(String fileId) {
        this.fileId = fileId;
    }
}
