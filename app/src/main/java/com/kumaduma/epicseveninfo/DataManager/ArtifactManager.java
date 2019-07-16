package com.kumaduma.epicseveninfo.DataManager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kumaduma.epicseveninfo.Model.Adapters.SimpleArtifactTierAdapter;
import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtifactManager {
    private static ArtifactManager sArtifactInstance;

    private static final String TAG = "ArtifactManager";
    private final String API_URL = "https://epicsevendb-apiserver.herokuapp.com/api/";

    private ArtifactManager(){}  //private constructor.
    private ArrayList<SimpleArtifact> artifactList = new ArrayList<>();
    private ArrayList<SimpleArtifactTier> artifactTierList = new ArrayList<>();
    private Map<String,Artifact> artifactMap = new HashMap<>();

    private boolean artifactListLoaded = false;

    public static ArtifactManager getInstance(){
        if (sArtifactInstance == null){ //if there is no instance available... create new one
            sArtifactInstance = new ArtifactManager();
            sArtifactInstance.loadArtifactList();
        }

        return sArtifactInstance;
    }

    private void loadArtifactList(){

        OkHttpClient client = new OkHttpClient();

        String url = API_URL + "artifact/";

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
                                JSONObject myJson = new JSONObject(myResponse);
                                Gson gson = new Gson();

                                JSONArray jArray = myJson.getJSONArray("results");

                                artifactList = new ArrayList<>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject jsonSimpleArtifact = jArray.getJSONObject(i);
                                    SimpleArtifact tempArtifact = gson.fromJson(jsonSimpleArtifact.toString(), SimpleArtifact.class);
                                    artifactList.add(tempArtifact);
                                }
                                Collections.sort(artifactList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
                                artifactListLoaded = true;
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
            Log.e(TAG, "getArtifactList: Process was interrupted!", e );
        }
    }

    public ArrayList<SimpleArtifact> getArtifactList(){
        return artifactList;
    }
    public ArrayList<SimpleArtifactTier> getArtifactTierList(){
        return artifactTierList;
    }


    public Artifact getArtifactById(String artifactId){

        if (artifactMap.containsKey(artifactId)){
            return artifactMap.get(artifactId);
        }

        CountDownLatch artifactCountDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL+ "artifact/" + artifactId)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                artifactCountDownLatch.countDown();
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
                        Artifact artifact = gson.fromJson(jArray.get(0).toString(), Artifact.class);
                        artifactMap.put(artifact.getFileId(), artifact);
                        artifactCountDownLatch.countDown();
                    } catch (JsonSyntaxException e){
                        Log.e(TAG, "onResponse: Artifact Class/JSON type mismatch - ", e);
                        artifactCountDownLatch.countDown();
                    } catch (JSONException e){
                        Log.e(TAG, "onResponse: Artifact to JSON conversion error - ", e);
                        artifactCountDownLatch.countDown();
                    } catch (IOException e){
                        Log.e(TAG, "onResponse: Unexpected Response ! - ", e);
                        artifactCountDownLatch.countDown();
                    }
                } else {
                    artifactCountDownLatch.countDown();
                    Log.i(TAG, "onResponse: Critical! Failed to get Response");
                }
            }
        });

        try {
            artifactCountDownLatch.await();
        } catch (InterruptedException e){
            Log.e(TAG, "getArtifact: Process was interrupted!", e );
        } finally {
            if (artifactMap.containsKey(artifactId)){
                return artifactMap.get(artifactId);
            } else {
                Log.i(TAG, "getArtifact: Failed to retrieve artifact!");
                return null;
            }
        }
    }

    void updateArtifactTierList(){

        TierManager tm = TierManager.getInstance();
        if (tm.tierListIsLoaded()){
            for(SimpleArtifact art : artifactList) {
                ArtifactTier artTier = TierManager.getInstance().getArtifactTierById(art.getFileId());
                if (artTier != null) {
                    SimpleArtifactTierAdapter artifactTierAdapter = new SimpleArtifactTierAdapter(art, artTier.getTierEnvironment(), artTier.getTierPlayer());
                    artifactTierList.add(artifactTierAdapter.getArtifact());
                } else {
                    String emptyTierString = " ";
                    SimpleArtifactTierAdapter artifactTierAdapter = new SimpleArtifactTierAdapter(art, emptyTierString, emptyTierString);
                    artifactTierList.add(artifactTierAdapter.getArtifact());
                }
            }
        }
    }
}
