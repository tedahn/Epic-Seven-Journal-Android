package com.kumaduma.epicseveninfo.Model;

public class CamperReactions {
    private String heroName;
    private String option;
    private int points;
    private String heroFileId;

    public CamperReactions(String heroName, String option, String fileId, int points){
        this.setHeroName(heroName);
        this.setOption(option);
        this.setPoints(points);
        this.setHeroFileId(fileId);
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String optionToString(){
        String optionStr = option;
        if (optionStr.length() > 1) optionStr = optionStr.substring(0,1).toUpperCase() + optionStr.substring(1);
        while(optionStr.contains("-") && optionStr.indexOf("-")+2 != optionStr.length()){
            int p = optionStr.indexOf("-");
            optionStr = optionStr.substring(0,p) + " " + optionStr.substring(p+1,p+2).toUpperCase() + optionStr.substring(p+2);
        }
        return optionStr;
    }

    public String toString(){
        return heroName + " with " + optionToString() + ": " + points ;
    }

    public String getHeroFileId() {
        return heroFileId;
    }

    public void setHeroFileId(String heroFileId) {
        this.heroFileId = heroFileId;
    }
}
