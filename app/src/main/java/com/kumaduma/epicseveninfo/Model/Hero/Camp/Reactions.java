package com.kumaduma.epicseveninfo.Model.Hero.Camp;

import java.util.Map;

public class Reactions {
    //Any string is considered missing data
    private Map<String, Integer> reactions;

    public Reactions(){

    }

    public Map<String, Integer> getReactions() {
        return reactions;
    }

    public void setReactions(Map<String, Integer> reactions) {
        this.reactions = reactions;
    }

    public Integer getReactionBy(String s){
        if (reactions.containsKey(s))
            return reactions.get(s);
        else
            return null;
    }
}
