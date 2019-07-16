package com.kumaduma.epicseveninfo.Model.Hero;

public class MemoryImprint {
    private String rank;
    private MemoryImprintStatus status;

    public MemoryImprint(){

    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public MemoryImprintStatus getStatus() {
        return status;
    }

    public void setStatus(MemoryImprintStatus status) {
        this.status = status;
    }
}
