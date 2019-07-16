package com.kumaduma.epicseveninfo;

import android.util.Log;

import com.google.gson.Gson;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiUnitTest {
    private static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String TAG = "ApiUnitTest";

    @Test
    public void api_isRunning() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://epicsevendb-apiserver.herokuapp.com/api/hero";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assertTrue("Successful response from Epic Seven DB", response.isSuccessful());
            }
        });
    }

    @Test
    public void api_hero_isRunning() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://epicsevendb-apiserver.herokuapp.com/api/hero";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("APITest", response.toString());
                    System.out.println(response.toString());
                    assertTrue(true);
                }
                assertTrue(false);
            }
        });
    }

    @Test
    public void api_hero_id_isRunning() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://epicsevendb-apiserver.herokuapp.com/api/hero/aramintha";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        Gson gson = new Gson();
                        JSONObject myJson = new JSONObject(myResponse);
                        JSONArray jArray = myJson.getJSONArray("results");

                        String version = myJson.getJSONObject("meta").getString("apiVersion");

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonSimpleHero = jArray.getJSONObject(i);
                            SimpleHero tempHero = gson.fromJson(jsonSimpleHero.toString(), SimpleHero.class);
                        }

                        System.out.print(version);
                        assertEquals("0.3.0", version);
                    } catch (JSONException e) {
                        Log.e(TAG, "unexpected JSON exception", e);
                    }
                }
            }
        });

    }


}
