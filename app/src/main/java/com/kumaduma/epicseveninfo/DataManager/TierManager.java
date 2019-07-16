package com.kumaduma.epicseveninfo.DataManager;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;
import com.kumaduma.epicseveninfo.Model.Tier.PVETier;
import com.kumaduma.epicseveninfo.Model.Tier.PVPTier;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TierManager {
    private static TierManager sTierInstance;

    private static final String TAG = "TierManager";

    private Map<String, PVPTier> pvpTierMap = new HashMap<>();
    private Map<String, PVETier> pveTierMap = new HashMap<>();
    private Map<String, ArtifactTier> artifactTierImgMap = new HashMap<>();
    private Map<String, ArtifactTier> artifactTierMap = new HashMap<>();

    private boolean tierListLoaded = false;

    //Used to access singleton
    public static TierManager getInstance(){
        if (sTierInstance == null){
            sTierInstance = new TierManager();
        }

        return sTierInstance;
    }

    //Used in loading menu
    public static TierManager getInstance(Context context){
        if (sTierInstance == null){ //if there is no instance available... create new one
            sTierInstance = new TierManager();
            sTierInstance.loadTierList(context);
        }

        return sTierInstance;
    }

    private void loadTierList(Context context){
        try {
            SheetsCredential sc = new SheetsCredential(context);

            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
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

            Log.d(TAG, "loadTierList: " +  results.getValueRanges().size() + " ranges retrieved. \n");

            //PVP List from Tier list
            List<List<Object>> values = results.getValueRanges().get(0).getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No PVP data found.");
            } else {
                List<Object> header = values.get(0);
                int nameIndex, arenaOffenseIndex, arenaDefenseIndex, gwOffenseIndex, gwDefenseIndex, averageIndex, recSetIndex, recNeckIndex, suggestedRoleIndex, recArtIndex, altArtOneIndex, altArtTwoIndex, noteIndex;
                nameIndex = arenaOffenseIndex = arenaDefenseIndex = gwOffenseIndex = gwDefenseIndex = averageIndex = recSetIndex = recNeckIndex = suggestedRoleIndex = recArtIndex = altArtOneIndex = altArtTwoIndex = noteIndex = 100;
                int c = 0;

                //Set all header index
                for (Object h : header) {
                    switch (h.toString().replaceAll("\\s+", "").toLowerCase()) {
                        case "name":
                            nameIndex = c;
                            break;

                        case "arenaoffense":
                            arenaOffenseIndex = c;
                            break;

                        case "arenadefense":
                            arenaDefenseIndex = c;
                            break;

                        case "gwoffense":
                            gwOffenseIndex = c;
                            break;

                        case "gwdefense":
                            gwDefenseIndex = c;
                            break;

                        case "pvpaverage":
                            averageIndex = c;
                            break;

                        case "recommendedsets":
                            recSetIndex = c;
                            break;

                        case "neck":
                            recNeckIndex = c;
                            break;

                        case "suggestedroles":
                            suggestedRoleIndex = c;
                            break;

                        case "recommendedartifact":
                            recArtIndex = c;
                            break;

                        case "alternateartifact":
                            if (altArtOneIndex > 99)
                                altArtOneIndex = c;
                            else altArtTwoIndex = c;
                            break;

                        case "note":
                            if (noteIndex > 99) {
                                noteIndex = c;
                            }
                            break;
                    }
                    c++;
                }
                values.remove(0);

                for (List row : values) {
                    PVPTier tempPvp = new PVPTier();

                    if (row.size() > nameIndex) {
                        tempPvp.setNameId(row.get(nameIndex).toString().replaceAll("\n", "").replaceAll(" & ", " ").replaceAll(" ", "-").toLowerCase());
                    }

                    //Check if rating Cell is parsable Java Number using apache.commons NumberUtils,
                    //If parsable, set to that number in cell, if not then set as 0 by default
                    tempPvp.setArenaOffense((row.size() > arenaOffenseIndex && NumberUtils.isCreatable(row.get(arenaOffenseIndex).toString())) ? Double.parseDouble(row.get(arenaOffenseIndex).toString()) : 0);
                    tempPvp.setArenaDefense((row.size() > arenaDefenseIndex && NumberUtils.isCreatable(row.get(arenaDefenseIndex).toString())) ? Double.parseDouble(row.get(arenaDefenseIndex).toString()) : 0);
                    tempPvp.setGwOffense((row.size() > gwOffenseIndex && NumberUtils.isCreatable(row.get(gwOffenseIndex).toString())) ? Double.parseDouble(row.get(gwOffenseIndex).toString()) : 0);
                    tempPvp.setGwDefense((row.size() > gwDefenseIndex && NumberUtils.isCreatable(row.get(gwDefenseIndex).toString())) ? Double.parseDouble(row.get(gwDefenseIndex).toString()) : 0);
                    tempPvp.setAverage();

                    //Set recommendations
                    tempPvp.setRecommendedSetList((row.size() > recSetIndex) ? new ArrayList<>(Arrays.asList(row.get(recSetIndex).toString().split("\n"))) : new ArrayList<>());
                    tempPvp.setRecommendedNeckList((row.size() > recNeckIndex) ? new ArrayList<>(Arrays.asList(row.get(recNeckIndex).toString().split("\n"))) : new ArrayList<>());
                    tempPvp.setSuggestedRoleList((row.size() > suggestedRoleIndex) ? new ArrayList<>(Arrays.asList(row.get(suggestedRoleIndex).toString().split("\n"))) : new ArrayList<>());

                    //Artifact recommendations
                    tempPvp.setRecommendedArtifactImageId((row.size() > recArtIndex) ? row.get(recArtIndex).toString().replaceAll("\\s+", "") : "");

                    //Generate Alternate Artifact List
                    List<String> altArtList = new ArrayList<>();
                    altArtList.add((row.size() > altArtOneIndex) ? row.get(altArtOneIndex).toString().replaceAll("\\s+", "") : "");
                    altArtList.add((row.size() > altArtTwoIndex) ? row.get(altArtTwoIndex).toString().replaceAll("\\s+", "") : "");
                    altArtList.removeAll(Collections.singleton(""));
                    tempPvp.setAlternateArtifactImageIdList(altArtList);

                    tempPvp.setNote((row.size() > noteIndex) ? row.get(noteIndex).toString() : "");
                    Log.d(TAG, "loadTierList: " + tempPvp.getNote());
                    pvpTierMap.put(tempPvp.getNameId(), tempPvp);
                }
            }

            //PVE Tier List
            values = results.getValueRanges().get(1).getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                List<Object> header = values.get(0);
                int nameIndex, huntIndex, abyssIndex, raidIndex, averageIndex, recSetIndex, recNeckIndex, suggestedRoleIndex, recArtIndex, altArtOneIndex, altArtTwoIndex, noteIndex;
                nameIndex = huntIndex = abyssIndex = raidIndex = averageIndex = recSetIndex = recNeckIndex = suggestedRoleIndex = recArtIndex = altArtOneIndex = altArtTwoIndex = noteIndex = 100;
                int c = 0;

                //Set all header index
                for (Object h : header) {
                    switch (h.toString().replaceAll("\\s+", "").toLowerCase()) {
                        case "name":
                            nameIndex = c;
                            break;

                        case "hunt":
                            huntIndex = c;
                            break;

                        case "abyss":
                            abyssIndex = c;
                            break;

                        case "raid":
                            raidIndex = c;
                            break;

                        case "pveaverage":
                            averageIndex = c;
                            break;

                        case "recommendedset":
                            recSetIndex = c;
                            break;

                        case "neck":
                            recNeckIndex = c;
                            break;

                        case "suggestedroles":
                            suggestedRoleIndex = c;
                            break;

                        case "recommendedartifact":
                            recArtIndex = c;
                            break;

                        case "alternateartifact":
                            if (altArtOneIndex > 99)
                                altArtOneIndex = c;
                            else altArtTwoIndex = c;
                            break;

                        case "note":
                            if (noteIndex > 99)
                                noteIndex = c;
                            break;

                    }
                    c++;
                }
                values.remove(0);

                for (List row : values) {
                    PVETier tempPve = new PVETier();

                    if (row.size() > nameIndex) {
                        tempPve.setNameId(row.get(nameIndex).toString().replaceAll("\n", "").replaceAll(" & ", " ").replaceAll(" ", "-").toLowerCase());
                    }

                    //Check if rating Cell is parsable Java Number using apache.commons NumberUtils,
                    //If parsable, set to that number in cell, if not then set as 0 by default
                    tempPve.setHunt((row.size() > huntIndex && NumberUtils.isCreatable(row.get(huntIndex).toString())) ? Double.parseDouble(row.get(huntIndex).toString()) : 0);
                    tempPve.setAbyss((row.size() > abyssIndex && NumberUtils.isCreatable(row.get(abyssIndex).toString())) ? Double.parseDouble(row.get(abyssIndex).toString()) : 0);
                    tempPve.setRaid((row.size() > raidIndex && NumberUtils.isCreatable(row.get(raidIndex).toString())) ? Double.parseDouble(row.get(raidIndex).toString()) : 0);
                    tempPve.setAverage();

                    //Set recommendations
                    tempPve.setRecommendedSetList((row.size() > recSetIndex) ? new ArrayList<>(Arrays.asList(row.get(recSetIndex).toString().split("\n"))) : new ArrayList<>());
                    tempPve.setRecommendedNeckList((row.size() > recNeckIndex) ? new ArrayList<>(Arrays.asList(row.get(recNeckIndex).toString().split("\n"))) : new ArrayList<>());
                    tempPve.setSuggestedRoleList((row.size() > suggestedRoleIndex) ? new ArrayList<>(Arrays.asList(row.get(suggestedRoleIndex).toString().split("\n"))) : new ArrayList<>());

                    //Artifact recommendations
                    tempPve.setRecommendedArtifactImageId((row.size() > recArtIndex) ? row.get(recArtIndex).toString().replaceAll("\\s+", "") : "");

                    //Generate Alternate Artifact List
                    List<String> altArtList = new ArrayList<>();
                    altArtList.add((row.size() > altArtOneIndex) ? row.get(altArtOneIndex).toString().replaceAll("\\s+", "") : "");
                    altArtList.add((row.size() > altArtTwoIndex) ? row.get(altArtTwoIndex).toString().replaceAll("\\s+", "") : "");
                    altArtList.removeAll(Collections.singleton(""));
                    tempPve.setAlternateArtifactImageIdList(altArtList);

                    tempPve.setNote((row.size() > noteIndex) ? row.get(noteIndex).toString() : "");
                    pveTierMap.put(tempPve.getNameId(), tempPve);
                }
            }

            //Artifacts Tier List
            values = results.getValueRanges().get(2).getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                List<Object> header = values.get(0);
                int nameIndex, imgIdIndex, pveIndex, pvpIndex, descIndex;
                nameIndex = imgIdIndex = pveIndex = pvpIndex = descIndex = 100;
                int c = 0;

                //Set all header index
                for (Object h : header) {
                    switch (h.toString().replaceAll("\\s+", "").toLowerCase()) {
                        case "name":
                            nameIndex = c;
                            imgIdIndex = c + 1;
                            break;

                        case "pve":
                            pveIndex = c;
                            break;

                        case "pvp":
                            pvpIndex = c;
                            break;

                        case "description":
                            descIndex = c;
                            break;
                    }
                    c++;
                }
                values.remove(0);

                for (List row : values) {
                    ArtifactTier tempArtRec = new ArtifactTier();

                    if (row.size() > nameIndex) {
                        tempArtRec.setNameId(row.get(nameIndex).toString().replaceAll("\n", "").replaceAll("'", "").replaceAll(" & ", " ").replaceAll("\\(.*\\)", "").trim().replaceAll(" ", "-").toLowerCase());
                    }

                    //Artifact recommendations
                    tempArtRec.setArtifactImageId((row.size() > imgIdIndex) ? row.get(imgIdIndex).toString().replaceAll("\\s+", "") : "");

                    //PVE and PVP ratings
                    tempArtRec.setTierEnvironment((row.size() > pveIndex) ? row.get(pveIndex).toString() : "-");
                    tempArtRec.setTierPlayer((row.size() > pvpIndex) ? row.get(pvpIndex).toString() : "-");

                    tempArtRec.setDescription((row.size() > descIndex) ? row.get(descIndex).toString() : "");
                    if (tempArtRec.getNameId() != null)
                        artifactTierMap.put(tempArtRec.getNameId(), tempArtRec);
                    artifactTierImgMap.put(tempArtRec.getArtifactImageId(), tempArtRec);
                }
            }
            tierListLoaded = true;

            HeroManager.getInstance().updateHeroTierList();
            ArtifactManager.getInstance().updateArtifactTierList();


        } catch (IOException e){
            Log.e(TAG, "loadTierList: " + e.toString(), e );
        }
    }

    public ArtifactTier getArtifactTierByImgId(String artiImgId){
        if (artifactTierImgMap.containsKey(artiImgId))
            return artifactTierImgMap.get(artiImgId);
        else
            return null;
    }

    public ArtifactTier getArtifactTierById(String artiId){
        if (artifactTierMap.containsKey(artiId))
            return artifactTierMap.get(artiId);
        else
            return null;
    }

    public PVETier getPveTierById(String heroId){
        if (pveTierMap.containsKey(heroId))
            return pveTierMap.get(heroId);
        else
            return null;
    }

    public PVPTier getPvpTierById(String heroId){
        if (pvpTierMap.containsKey(heroId))
            return pvpTierMap.get(heroId);
        else
            return null;
    }

    public Set<String> getHeroesByArtifactId(String artifactId){
        if (artifactTierMap.containsKey(artifactId)) {
            Set<String> heroList = new HashSet<>();
            ArtifactTier art = artifactTierMap.get(artifactId);
            String artImgId = art.getArtifactImageId();
            for (PVPTier pvp :pvpTierMap.values()){
                if (pvp.getRecommendedArtifactImageId().equals(artImgId)
                        || pvp.getAlternateArtifactImageIdList().contains(artImgId)){
                    heroList.add(pvp.getNameId());
                }
            }
            for (PVETier pve :pveTierMap.values()){
                if (pve.getRecommendedArtifactImageId().equals(artImgId)
                        || pve.getAlternateArtifactImageIdList().contains(artImgId)){
                    heroList.add(pve.getNameId());
                }
            }

            return heroList;
        }
        else return null;
    }

    public boolean tierListIsLoaded() {
        return tierListLoaded;
    }
}
