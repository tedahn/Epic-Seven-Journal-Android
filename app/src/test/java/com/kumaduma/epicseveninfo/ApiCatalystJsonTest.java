package com.kumaduma.epicseveninfo;

import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiCatalystJsonTest {

    @DisplayName("⌛ Load All Heroes")
    @Test
    public void loadItems(){
        CatalystManager dataManager = CatalystManager.getInstance();

        List<Catalyst> shList = dataManager.getCatalystList();
        int c = 0;
        int fc = 0;
        StringBuilder msg = new StringBuilder();
        for (Catalyst cat : shList){
            c++;
            if (cat.getType().equals("catalyst")){
                System.out.print("\""+cat.getId()+"\"");
                System.out.print(":\n");
            }
        }

        System.out.println("\n" + "Failed getting " + fc + " heroes:");
        System.out.print(msg.toString());

    }
}
