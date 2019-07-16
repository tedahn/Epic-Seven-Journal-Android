package com.kumaduma.epicseveninfo;

import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiArtifactJsonTest {

    @DisplayName("⌛ Load All Artifacts")
    @Test
    public void loadAllArtifacts(){
        ArtifactManager dataManager = ArtifactManager.getInstance();

        List<SimpleArtifact> aList = dataManager.getArtifactList();
        int c = 0;
        int fc = 0;
        StringBuilder msg = new StringBuilder();
            for (SimpleArtifact a : aList){
            c++;
            Artifact art = dataManager.getArtifactById(a.getFileId());
            if (art == null){
                fc++;
                msg.append(fc+". " + a.getName() + "\n");
                System.out.println("Failed getting " + a.getName() + "\n");
            } else {
                assertEquals(art.getFileId(), a.getFileId());
                System.out.println(c + "/" + aList.size() + ": " + art.getName() + " is Loaded");
            }
        }

        System.out.println("\n" + "Failed getting " + fc + " artifacts:");
        System.out.print(msg.toString());

    }
}
