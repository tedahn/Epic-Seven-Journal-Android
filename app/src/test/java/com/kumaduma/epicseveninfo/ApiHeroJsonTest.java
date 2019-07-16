package com.kumaduma.epicseveninfo;

import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiHeroJsonTest {

    @DisplayName("⌛ Load All Heroes")
    @Test
    public void loadAllHeroes(){
        HeroManager dataManager = HeroManager.getInstance();

        List<SimpleHero> shList = dataManager.getHeroList();
        int c = 0;
        int fc = 0;
        StringBuilder msg = new StringBuilder();
        for (SimpleHero sh : shList){
            c++;
            Hero hero = dataManager.getHeroById(sh.getFileId());
            if (hero == null){
                fc++;
                msg.append(fc+". " + sh.getName() + "\n");
                System.out.println("Failed getting " + sh.getName() + "\n");
            } else {
                assertEquals(hero.getFileId(), sh.getFileId());
                System.out.println(c + "/" + shList.size() + ": " +hero.getName() + " is Loaded");
            }
        }

        System.out.println("\n" + "Failed getting " + fc + " heroes:");
        System.out.print(msg.toString());

    }
}
