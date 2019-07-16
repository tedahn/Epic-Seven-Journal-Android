package com.kumaduma.epicseveninfo;

import com.kumaduma.epicseveninfo.DataManager.CampManager;
import com.kumaduma.epicseveninfo.Model.CamperReactions;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LabyrinthUnitTest {
    @Test
    public void labyrinthTest() {
        CampManager manager = CampManager.getInstance();
        manager.toggleHero("adlay");
        manager.toggleHero("aramintha");
        manager.toggleHero("lots");
        manager.toggleHero("achates");

        List<CamperReactions> results = manager.bestTwo();

        for (CamperReactions r: results){
            System.out.print(r.getHeroName() + " with " + r.getOption() + ": " + r.getPoints() + "\n");
        }
    }

}