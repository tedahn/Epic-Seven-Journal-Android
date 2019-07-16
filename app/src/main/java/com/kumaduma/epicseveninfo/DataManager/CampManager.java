package com.kumaduma.epicseveninfo.DataManager;

import com.kumaduma.epicseveninfo.Model.CampHero;
import com.kumaduma.epicseveninfo.Model.CamperReactions;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampManager {

    private static CampManager sCampInstance;
    private List<CampHero> camperList;

    public static CampManager getInstance(){
        if (sCampInstance == null){ //if there is no instance available... create new one
            sCampInstance = new CampManager();

        }

        return sCampInstance;
    }

    public CampManager(){
        camperList = new ArrayList<>();
    }

    public int toggleHero(String fileId){
        int i = 0;
        for (CampHero c : camperList) {
            if (c.getFileId().equals(fileId)) {
                camperList.remove(i);
                return 2;
            }
            i++;
        }

        if (camperList.size() == 4){
            return 0;
        }

        Hero hero = HeroManager.getInstance().getHeroById(fileId);
        if (hero == null) {
            return -1;
        }

        CampHero c = new CampHero(hero.getFileId(), hero.getName(), hero.getCamping());
        camperList.add(c);
        return 1;
    }

    public List<CamperReactions> bestTwo(){
        List<CamperReactions> bestReactions = new ArrayList<>();

        String bestId, secondBestId,bestName,secondBestName,bestOption,secondBestOption;
        bestId = secondBestId=bestName=secondBestName=bestOption=secondBestOption = "";
        int best = -100;
        int secondBest = -101;
        for (CampHero talker: camperList){
            List<String> topics = talker.getCamping().getOptions();
            Map<String, Integer> topicsPoints = new HashMap<>();
            for(CampHero listener: camperList){
                if (!talker.getFileId().equals(listener.getFileId())) {
                    for (String topic: topics){
                        Integer pt = listener.getCamping().getReactionBy(topic);
                        if (pt != null) {
                            if (topicsPoints.containsKey(topic))
                                topicsPoints.put(topic, topicsPoints.get(topic) + pt);
                            else
                                topicsPoints.put(topic, pt);
                        } else
                            return null;
                    }
                }
            }

            for (Map.Entry<String, Integer> entry : topicsPoints.entrySet()) {
                if (entry.getValue() > best) {
                    best = entry.getValue();
                    bestOption = entry.getKey();
                    bestName = talker.getName();
                    bestId = talker.getFileId();
                } else if (entry.getValue() > secondBest) {
                    secondBest = entry.getValue();
                    secondBestOption = entry.getKey();
                    secondBestName = talker.getName();
                    secondBestId = talker.getFileId();
                }
            }
        }

        bestReactions.add(new CamperReactions(bestName, bestOption, bestId,best));
        bestReactions.add(new CamperReactions(secondBestName, secondBestOption, secondBestId,secondBest));

        return bestReactions;
    }

    public int getIndexOf(String fileId){
        int index = 0;
        for (CampHero camper : camperList){
            if (fileId.equals(camper.getFileId()))
                return index;
            index++;
        }
        return -1;
    }

    public List<String> getSelectedHeroIds(){
        List<String> list = new ArrayList<>();
        for(CampHero c : camperList){
            list.add(c.getFileId());
        }
        return list;
    }

    public boolean calculatable(){
        if (camperList.size() == 4)
            return true;

        return false;
    }

    public void resetCampers(){
        camperList = new ArrayList<>();
    }

    public void removeAtIndex(int index){
        if (camperList.size() <= index) return;
        camperList.remove(index);
    }

}
