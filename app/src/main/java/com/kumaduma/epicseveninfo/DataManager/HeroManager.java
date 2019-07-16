package com.kumaduma.epicseveninfo.DataManager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kumaduma.epicseveninfo.Model.Adapters.SimpleHeroTierAdapter;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.Model.SimpleHeroTier;
import com.kumaduma.epicseveninfo.Model.Tier.PVETier;
import com.kumaduma.epicseveninfo.Model.Tier.PVPTier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HeroManager {
    private static HeroManager sHeroInstance;

    private static final String TAG = "HeroManager";
    private final String API_URL = "https://epicsevendb-apiserver.herokuapp.com/api/";

    private HeroManager(){}  //private constructor.
    private ArrayList<SimpleHero> heroList = new ArrayList<>();
    private ArrayList<SimpleHeroTier> heroTierList = new ArrayList<>();
    private Map<String,Hero> heroMap = new HashMap<>();
    private String version = "";
    private Set<String> buffs = new HashSet<>();
    private Set<String> debuffs = new HashSet<>();

    private boolean heroListLoaded = false;

    public static HeroManager getInstance(){
        if (sHeroInstance == null){ //if there is no instance available... create new one
            sHeroInstance = new HeroManager();
            sHeroInstance.loadHeroList();
        }

        return sHeroInstance;
    }

    private void loadHeroList(){
        OkHttpClient client = new OkHttpClient();

        String url = API_URL + "hero/";

        Request request = new Request.Builder()
                .url(url)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to call Hero List API", e);
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        Gson gson = new Gson();
                        JSONObject myJson = new JSONObject(myResponse);
                        JSONArray jArray = myJson.getJSONArray("results");
                        heroList = new ArrayList<>();

                        version = myJson.getJSONObject("meta").getString("apiVersion");

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonSimpleHero = jArray.getJSONObject(i);
                            SimpleHero tempHero = gson.fromJson(jsonSimpleHero.toString(), SimpleHero.class);
                            heroList.add(tempHero);
                        }
                        Collections.sort(heroList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
                        heroListLoaded = true;

                        for (SimpleHero hero: heroList){
                            ArrayList<String> tempBuffs = hero.getBuffs();
                            ArrayList<String> tempDebuffs = hero.getDebuffs();
                            for (String buff: tempBuffs){
                                buffs.add(buff);
                            }
                            for (String debuff: tempDebuffs){
                                debuffs.add(debuff);
                            }
                        }
                        buffs.remove("");
                        debuffs.remove("");

                        countDownLatch.countDown();
                    } catch (JSONException e) {
                        Log.e(TAG, "unexpected JSON exception", e);
                        countDownLatch.countDown();
                        // Do something to recover ... or kill the app.
                    }
                } else {
                    Log.e(TAG, "Unexpected Response", new IOException());
                    countDownLatch.countDown();
                }
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e){
            Log.e(TAG, "getHeroList: Process was interrupted!", e );
        }
    }

    public ArrayList<SimpleHero> getHeroList(){
        return heroList;
    }

    public ArrayList<SimpleHeroTier> getHeroTierList(){
        return heroTierList;
    }

    public Hero getHeroById(String heroId){

        if (heroMap.containsKey(heroId)){
            return heroMap.get(heroId);
        }

        CountDownLatch heroCountDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL+ "hero/" + heroId)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                heroCountDownLatch.countDown();
                Log.e(TAG, "onFailure: Could not make request!!", e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        final String myResponse = response.body().string();
                        JSONObject myJson = new JSONObject(myResponse);
                        JSONArray jArray = myJson.getJSONArray("results");
                        Gson gson = new Gson();
                        Hero hero = gson.fromJson(jArray.get(0).toString(), Hero.class);
                        heroMap.put(hero.getFileId(), hero);
                        heroCountDownLatch.countDown();
                    } catch (JsonSyntaxException e){
                        Log.e(TAG, "onResponse: Hero Class/JSON type mismatch - ", e);
                        Log.d(TAG, "onResponse: " + e.getCause());
                        heroCountDownLatch.countDown();
                    } catch (JSONException e){
                        Log.e(TAG, "onResponse: Hero to JSON conversion error - ", e);
                        Log.d(TAG, "onResponse: " + e.getCause());
                        heroCountDownLatch.countDown();
                    } catch (IOException e){
                        Log.e(TAG, "onResponse: Unexpected Response ! - ", e);
                        heroCountDownLatch.countDown();
                    }
                } else {
                    heroCountDownLatch.countDown();
                    Log.i(TAG, "onResponse: Critical! Failed to get Response");
                }
            }
        });

        try {
            heroCountDownLatch.await();
        } catch (InterruptedException e){
            Log.e(TAG, "getHero: Process was interrupted!", e );
        } finally {
            if (heroMap.containsKey(heroId)){
                return heroMap.get(heroId);
            } else {
                Log.i(TAG, "getHero: Failed to retrieve hero!");
                return null;
            }
        }
    }

    public String getVersion(){
        return version;
    }

    public ArrayList<String> getBuffList(){ return new ArrayList<String>(buffs); }

    public ArrayList<String> getDebuffList(){ return new ArrayList<String>(debuffs); }

    void updateHeroTierList(){
        TierManager tm = TierManager.getInstance();
        if (tm.tierListIsLoaded()){
            for (SimpleHero h: heroList){
                String pveAvg, pvpAvg;
                pveAvg = pvpAvg = "";
                PVETier tempPve = tm.getPveTierById(h.getFileId());
                if (tempPve != null && tempPve.getAverage() != 0)
                    pveAvg = Double.toString(tempPve.getAverage());

                PVPTier tempPvp = tm.getPvpTierById(h.getFileId());
                if (tempPvp != null && tempPvp.getAverage() != 0)
                    pvpAvg = Double.toString(tempPvp.getAverage());
                SimpleHeroTierAdapter heroTierAdapter = new SimpleHeroTierAdapter(h, pveAvg, pvpAvg);
                heroTierList.add(heroTierAdapter.getHero());
            }
        }
    }
}
