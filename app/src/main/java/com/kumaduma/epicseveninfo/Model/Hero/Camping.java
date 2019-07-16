package com.kumaduma.epicseveninfo.Model.Hero;

import java.util.List;
import java.util.Map;

public class Camping {
    private List<String> options;
    private Map<String, String> reactions;

    public Camping(){}

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Map<String, String> getReactions() {
        return reactions;
    }

    public void setReactions(Map<String, String> reactions) {
        this.reactions = reactions;
    }

    public Integer getReactionBy(String s){
        if (reactions.containsKey(s)) {
            String r = reactions.get(s);
            if (isInteger(r, 10))
                return Integer.parseInt(reactions.get(s));
            else
                return null;
        } else
            return null;
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
