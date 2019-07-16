package com.kumaduma.epicseveninfo.Model.Adapters;

import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.Model.SimpleHeroTier;

public class SimpleHeroTierAdapter{

    SimpleHeroTier hero;

    public SimpleHeroTierAdapter (SimpleHero hero, String pve, String pvp){
        SimpleHeroTier newHero = new SimpleHeroTier();
        newHero.setBuffs(hero.getBuffs());
        newHero.setClassType(hero.getClassType());
        newHero.setDebuffs(hero.getDebuffs());
        newHero.setElement(hero.getElement());
        newHero.setFileId(hero.getFileId());
        newHero.setName(hero.getName());
        newHero.setRarity(hero.getRarity());
        newHero.setZodiac(hero.getZodiac());

        newHero.setPveAvg(pve);
        newHero.setPvpAvg(pvp);

        this.hero = newHero;
    }

    public SimpleHeroTier getHero() {
        return hero;
    }
}
