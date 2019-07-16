package com.kumaduma.epicseveninfo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;
import com.kumaduma.epicseveninfo.Model.Hero.Stats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;
public class JsonUnitTest {
    private static final String TAG = "JsonUnitTest";
    @Test
    public void HeroJsonToObject(){
        List<Hero> herolist = new ArrayList<>();

        CountDownLatch heroCountDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://epicsevendb-apiserver.herokuapp.com/api/hero/cermia")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                heroCountDownLatch.countDown();
                fail();
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
                        herolist.add(hero);
                        System.out.println("It worked!");
                        heroCountDownLatch.countDown();
                    } catch (JsonSyntaxException e){
                        System.out.print("JsonSyntaxException (type mismatch): " + e.getLocalizedMessage() + "\n");
                        System.out.print(e.getStackTrace());
                        heroCountDownLatch.countDown();
                    } catch (JSONException e){
                        System.out.print("JSONException (Conversion error): " + e.getMessage() + "\n" + e.getCause().getMessage() +"\n");
                        System.out.print(e.getStackTrace());
                        heroCountDownLatch.countDown();
                    } catch (IOException e){
                        System.out.print("IO Exception (response conversion): " + e.getMessage() + "\n" + e.getCause().getMessage() +"\n");
                        System.out.print(e.getStackTrace());
                        heroCountDownLatch.countDown();
                    }
                } else {
                    heroCountDownLatch.countDown();
                    fail();
                }
            }
        });

        try {
            heroCountDownLatch.await();
        } catch (InterruptedException e){
            Log.e(TAG, "getHero: Process was interrupted!", e );
            fail();
        } finally {
            if (herolist.isEmpty()){
                fail("herolist is Empty");
            }
            Hero cermia = herolist.get(0);

            Iterator<Map.Entry<String, Stats>> itr = cermia.getStats().entrySet().iterator();

            while (itr.hasNext()) {
                Map.Entry<String, Stats> entry = itr.next();
                System.out.print("Atk at "+ entry.getKey() + " is - " + entry.getValue().getAtk() + "\n");
            }
            assertEquals("Cermia", cermia.getName());
        }

    }

    @Test
    public void ArtifactJsonToObject(){



        List<Artifact> al = new ArrayList<>();
        CountDownLatch artifactCountDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://epicsevendb-apiserver.herokuapp.com/api/artifact/abyssal-crown")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                artifactCountDownLatch.countDown();
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
                        al.add(artifact);
                        artifactCountDownLatch.countDown();
                    } catch (JsonSyntaxException e){
                        System.out.print(e.getMessage());
                        artifactCountDownLatch.countDown();
                    } catch (JSONException e){
                        System.out.print(e.getMessage());
                        artifactCountDownLatch.countDown();
                    } catch (IOException e){
                        System.out.print(e.getMessage());
                        artifactCountDownLatch.countDown();
                    }
                } else {
                    artifactCountDownLatch.countDown();
                }
            }
        });


        try {
            artifactCountDownLatch.await();
        } catch (InterruptedException e){
            System.out.print(e.getMessage());
            fail();
        } finally {
            Artifact a = al.get(0);
            System.out.print(a.getExclusive());
            System.out.print("\n");
            System.out.print(a.getName());
            System.out.print("\n");
            System.out.print(a.getMax());
            System.out.print("\n");
            System.out.print(a.getBase());
            System.out.print("\n");
            System.out.print(a.getFileId());
            System.out.print("\n");
            System.out.print(a.getLoreDescription());
            System.out.print("\n");
            System.out.print(a.getSkillDescription());
            System.out.print("\n");
            System.out.print(a.getRarity());
            System.out.print("\n");
        }
    }
}
