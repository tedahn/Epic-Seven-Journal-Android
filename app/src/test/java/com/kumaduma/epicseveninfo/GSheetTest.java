package com.kumaduma.epicseveninfo;

import android.content.Context;
import android.content.res.Resources;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.kumaduma.epicseveninfo.DataManager.SheetsCredential;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;
import com.kumaduma.epicseveninfo.Model.Tier.PVETier;
import com.kumaduma.epicseveninfo.Model.Tier.PVPTier;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GSheetTest {

/*
    @DisplayName("⌛ Load All Tiers")
    public void loadAllTiers() throws IOException, GeneralSecurityException, URISyntaxException {
        //SheetsCredential sc = new SheetsCredential();

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "11mwHFlxPpIFu24sRxvcK1ZktkjvBQg1oTv035OSp_OI";
        final String valueRenderOption = "FORMULA";
        final List<String> ranges = Arrays.asList(
            "PvP", "PvE", "Artifacts"
        );
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, sc.getJsonFactory(), sc.getServiceCredentials())
                .setApplicationName(sc.getApplicationName())
                .build();
        BatchGetValuesResponse results = service.spreadsheets().values()
                .batchGet(spreadsheetId)
                .setRanges(ranges)
                .setValueRenderOption(valueRenderOption)
                .execute();

        System.out.printf("%d ranges retrieved. \n", results.getValueRanges().size());

        //PVPTier List from Tier list
        List<List<Object>> values = results.getValueRanges().get(0).getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No PVPTier data found.");
        } else {
            List<Object> header = values.get(0);
            int nameIndex, arenaOffenseIndex, arenaDefenseIndex, gwOffenseIndex, gwDefenseIndex, averageIndex, recSetIndex, recNeckIndex, suggestedRoleIndex, recArtIndex, altArtOneIndex, altArtTwoIndex, noteIndex;
            nameIndex = arenaOffenseIndex = arenaDefenseIndex = gwOffenseIndex = gwDefenseIndex = averageIndex = recSetIndex = recNeckIndex = suggestedRoleIndex = recArtIndex = altArtOneIndex = altArtTwoIndex = noteIndex = -1;
            int c = 0;

            //Set all header index
            for (Object h : header){
                switch ( h.toString().replaceAll("\\s+","").toLowerCase()) {
                    case "name":
                        nameIndex = c;

                    case "arenaoffense":
                        arenaOffenseIndex = c;

                    case "arenadefense":
                        arenaDefenseIndex = c;

                    case "gwoffense":
                        gwOffenseIndex = c;

                    case "gwdefense":
                        gwDefenseIndex = c;

                    case "pvpaverage":
                        averageIndex = c;

                    case "recommendedsets":
                        recSetIndex = c;

                    case "neck":
                        recNeckIndex = c;

                    case "suggestedroles":
                        suggestedRoleIndex = c;

                    case "recommendedartifact":
                        recArtIndex = c;

                    case "alternateartifact":
                        if (altArtOneIndex < 0)
                            altArtOneIndex = c;
                        else
                            altArtTwoIndex = c;

                    case "note":
                        noteIndex = c;
                }
                c++;
            }
            values.remove(0);

            Map<String, PVPTier> heroPvpRatingMap = new HashMap<>();
            for (List row : values) {
                PVPTier tempPvp = new PVPTier();

                if (row.size() > nameIndex){
                    tempPvp.setNameId(row.get(nameIndex).toString().replaceAll("\n","").replaceAll(" & "," ").replaceAll(" ", "-").toLowerCase());
                }

                //Check if rating Cell is parsable Java Number using apache.commons NumberUtils,
                //If parsable, set to that number in cell, if not then set as 0 by default
                tempPvp.setArenaOffense((row.size() > arenaOffenseIndex && NumberUtils.isCreatable(row.get(arenaOffenseIndex).toString())) ? Double.parseDouble(row.get(arenaOffenseIndex).toString()) : 0);
                tempPvp.setArenaDefense((row.size() > arenaDefenseIndex && NumberUtils.isCreatable(row.get(arenaDefenseIndex).toString())) ? Double.parseDouble(row.get(arenaDefenseIndex).toString()) : 0 );
                tempPvp.setGwOffense((row.size() > gwOffenseIndex && NumberUtils.isCreatable(row.get(gwOffenseIndex).toString())) ? Double.parseDouble(row.get(gwOffenseIndex).toString()) : 0);
                tempPvp.setGwDefense((row.size() > gwDefenseIndex && NumberUtils.isCreatable(row.get(gwDefenseIndex).toString())) ? Double.parseDouble(row.get(gwDefenseIndex).toString()) : 0);
                tempPvp.setAverage();

                //Set recommendations
                tempPvp.setRecommendedSetList((row.size() > recSetIndex) ? new ArrayList<>(Arrays.asList(row.get(recSetIndex).toString().split("\n"))) : new ArrayList<>());
                tempPvp.setRecommendedNeckList((row.size() > recNeckIndex) ? new ArrayList<>(Arrays.asList(row.get(recNeckIndex).toString().split("\n"))) : new ArrayList<>());
                tempPvp.setSuggestedRoleList((row.size() > suggestedRoleIndex) ? new ArrayList<>(Arrays.asList(row.get(suggestedRoleIndex).toString().split("\n"))) : new ArrayList<>());

                //Artifact recommendations
                tempPvp.setRecommendedArtifactImageId((row.size() > recArtIndex) ? row.get(recArtIndex).toString() : "");

                //Generate Alternate Artifact List
                List<String> altArtList = new ArrayList<>();
                altArtList.add((row.size() > altArtOneIndex) ? row.get(altArtOneIndex).toString() : "");
                altArtList.add((row.size() > altArtTwoIndex) ? row.get(altArtTwoIndex).toString() : "");
                altArtList.removeAll(Collections.singleton(""));
                tempPvp.setAlternateArtifactImageIdList(altArtList);

                tempPvp.setNote((row.contains(noteIndex)) ? row.get(noteIndex).toString() : "");
                heroPvpRatingMap.put(tempPvp.getNameId(), tempPvp);
            }
            System.out.println("Retrieved PVPTier ratings: " + heroPvpRatingMap.size());
        }

        //PVETier Tier List
        values = results.getValueRanges().get(1).getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            List<Object> header = values.get(0);
            int nameIndex, huntIndex, abyssIndex, raidIndex, averageIndex, recSetIndex, recNeckIndex, suggestedRoleIndex, recArtIndex, altArtOneIndex, altArtTwoIndex, noteIndex;
            nameIndex = huntIndex = abyssIndex = raidIndex = averageIndex = recSetIndex = recNeckIndex = suggestedRoleIndex = recArtIndex = altArtOneIndex = altArtTwoIndex = noteIndex = -1;
            int c = 0;

            //Set all header index
            for (Object h : header){
                switch ( h.toString().replaceAll("\\s+","").toLowerCase()) {
                    case "name":
                        nameIndex = c;

                    case "hunt":
                        huntIndex = c;

                    case "abyss":
                        abyssIndex = c;

                    case "raid":
                        raidIndex = c;

                    case "pveaverage":
                        averageIndex = c;

                    case "recommendedsets":
                        recSetIndex = c;

                    case "neck":
                        recNeckIndex = c;

                    case "suggestedroles":
                        suggestedRoleIndex = c;

                    case "recommendedartifact":
                        recArtIndex = c;

                    case "alternateartifact":
                        if (altArtOneIndex < 0)
                            altArtOneIndex = c;
                        else
                            altArtTwoIndex = c;

                    case "note":
                        noteIndex = c;
                }
                c++;
            }
            values.remove(0);

            Map<String, PVETier> heroPveRatingMap = new HashMap<>();
            for (List row : values) {
                PVETier tempPve = new PVETier();

                if (row.size() > nameIndex){
                    tempPve.setNameId(row.get(nameIndex).toString().replaceAll("\n","").replaceAll(" & "," ").replaceAll(" ", "-").toLowerCase());
                }

                //Check if rating Cell is parsable Java Number using apache.commons NumberUtils,
                //If parsable, set to that number in cell, if not then set as 0 by default
                tempPve.setHunt((row.size() > huntIndex && NumberUtils.isCreatable(row.get(huntIndex).toString())) ? Double.parseDouble(row.get(huntIndex).toString()) : 0);
                tempPve.setAbyss((row.size() > abyssIndex && NumberUtils.isCreatable(row.get(abyssIndex).toString())) ? Double.parseDouble(row.get(abyssIndex).toString()) : 0 );
                tempPve.setRaid((row.size() > raidIndex && NumberUtils.isCreatable(row.get(raidIndex).toString())) ? Double.parseDouble(row.get(raidIndex).toString()) : 0);
                tempPve.setAverage();

                //Set recommendations
                tempPve.setRecommendedSetList((row.size() > recSetIndex) ? new ArrayList<>(Arrays.asList(row.get(recSetIndex).toString().split("\n"))) : new ArrayList<>());
                tempPve.setRecommendedNeckList((row.size() > recNeckIndex) ? new ArrayList<>(Arrays.asList(row.get(recNeckIndex).toString().split("\n"))) : new ArrayList<>());
                tempPve.setSuggestedRoleList((row.size() > suggestedRoleIndex) ? new ArrayList<>(Arrays.asList(row.get(suggestedRoleIndex).toString().split("\n"))) : new ArrayList<>());

                //Artifact recommendations
                tempPve.setRecommendedArtifactImageId((row.size() > recArtIndex) ? row.get(recArtIndex).toString() : "");

                //Generate Alternate Artifact List
                List<String> altArtList = new ArrayList<>();
                altArtList.add((row.size() > altArtOneIndex) ? row.get(altArtOneIndex).toString() : "");
                altArtList.add((row.size() > altArtTwoIndex) ? row.get(altArtTwoIndex).toString() : "");
                altArtList.removeAll(Collections.singleton(""));
                tempPve.setAlternateArtifactImageIdList(altArtList);

                tempPve.setNote((row.contains(noteIndex)) ? row.get(noteIndex).toString() : "");
                heroPveRatingMap.put(tempPve.getNameId(), tempPve);
            }
            System.out.println("Retrieved PVETier ratings: " + heroPveRatingMap.size());
        }

        //Artifacts Tier List
        values = results.getValueRanges().get(2).getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            List<Object> header = values.get(0);
            int nameIndex, imgIdIndex, pveIndex, pvpIndex, descIndex;
            nameIndex = imgIdIndex = pveIndex = pvpIndex = descIndex = -1;
            int c = 0;

            //Set all header index
            for (Object h : header){
                switch ( h.toString().replaceAll("\\s+","").toLowerCase()) {
                    case "name":
                        nameIndex = c;
                        imgIdIndex = c+1;

                    case "pve":
                        pveIndex = c;

                    case "pvp":
                        pvpIndex = c;

                    case "description":
                        descIndex = c;
                }
                c++;
            }
            values.remove(0);

            Map<String, ArtifactTier> artifactRatingMap = new HashMap<>();
            for (List row : values) {
                ArtifactTier tempArtRec = new ArtifactTier();

                if (row.size() > nameIndex){
                    tempArtRec.setNameId(row.get(nameIndex).toString().replaceAll("\n","").replaceAll("'","").replaceAll(" & "," ").replaceAll(" ", "-").toLowerCase());
                }

                //Artifact recommendations
                tempArtRec.setArtifactImageId((row.size() > imgIdIndex) ? row.get(imgIdIndex).toString() : "");

                //PVETier and PVPTier ratings
                tempArtRec.setTierEnvironment((row.size() > pveIndex) ? row.get(pveIndex).toString() : "-");
                tempArtRec.setTierPlayer((row.size() > pvpIndex) ? row.get(pvpIndex).toString() : "-");

                tempArtRec.setDescription((row.contains(descIndex)) ? row.get(descIndex).toString() : "");
                artifactRatingMap.put(tempArtRec.getNameId(), tempArtRec);
            }
            System.out.println("Retrieved Artifact ratings: " + artifactRatingMap.size());
        }

    }
*/
}
