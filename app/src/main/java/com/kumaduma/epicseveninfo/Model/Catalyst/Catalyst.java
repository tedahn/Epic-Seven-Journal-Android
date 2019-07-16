package com.kumaduma.epicseveninfo.Model.Catalyst;

import java.util.Comparator;
import java.util.List;

public class Catalyst {
    private String _id, fileId, name, type, description;
    private String rarity;
    private List<Locations> locations;
    private List<ApShop> apShops;

    public Catalyst(String id, String fileId, String name, String rarity, String type, String description, List<Locations> locations) {
        this._id = id;
        this.fileId = fileId;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.description = description;
        this.locations = locations;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public List<ApShop> getApShops() {
        return apShops;
    }

    public void setApShops(List<ApShop> apShops) {
        this.apShops = apShops;
    }

    public class AlphabeticalComparator implements Comparator<Catalyst> {
        @Override
        public int compare(Catalyst o1, Catalyst o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
