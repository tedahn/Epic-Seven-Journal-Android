package com.kumaduma.epicseveninfo.Model.Hero;

public class MemoryImprintStatus {
    private String type;
    private Double increase;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getIncrease() {
        return increase;
    }

    public void setIncrease(Double increase) {
        this.increase = increase;
    }

    public String toString(){
        String str = (increase*100) + "%";
        return str;
    }
}
