package com.kumaduma.epicseveninfo.Model;

import com.kumaduma.epicseveninfo.Model.Hero.Camping;

public class CampHero {
    private String fileId;
    private String name;
    private Camping camping;

    public CampHero(String fileId, String name, Camping camping){
        this.setFileId(fileId);
        this.setName(name);
        this.setCamping(camping);
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

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }
}
