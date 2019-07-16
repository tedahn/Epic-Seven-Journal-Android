package com.kumaduma.epicseveninfo.DataManager;

import android.content.Context;
import android.util.Log;

import com.google.api.client.util.IOUtils;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatalystManager {
    private static CatalystManager sCatalystInstance;

    private static final String TAG = "CatalystManager";
    private final String API_URL = "https://epicsevendb-apiserver.herokuapp.com/api/";

    private CatalystManager(){}  //private constructor.
    private List<Catalyst> catalystList = new ArrayList<>();
    private Map<String,Integer> catalystMap = new HashMap<String,Integer>();
    private Map<String,String> catalystZodiacMap = new HashMap<>();

    private boolean catalystListLoaded = false;
    private boolean catalystZodiacMapIsLoaded = false;

    public static CatalystManager getInstance(){
        if (sCatalystInstance == null){ //if there is no instance available... create new one
            sCatalystInstance = new CatalystManager();
            sCatalystInstance.loadCatalystList();
        }

        return sCatalystInstance;
    }

    private void loadCatalystList(){

        OkHttpClient client = new OkHttpClient();

        String url = API_URL + "item/";

        Request request = new Request.Builder()
                .url(url)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to call Item List API", e);
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        JSONObject myJson = new JSONObject(myResponse);

                        JSONArray jArray = myJson.getJSONArray("results");
                        catalystList= new ArrayList<>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            Gson gson = new Gson();
                            Catalyst tempCatalyst = gson.fromJson(json.toString(),Catalyst.class);
                            catalystList.add(tempCatalyst);
                        }
                        Collections.sort(catalystList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
                        int pos = 0;
                        for (Catalyst c: catalystList){
                            catalystMap.put(c.getFileId(), pos);
                            pos++;
                        }
                        catalystListLoaded = true;
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
            Log.e(TAG, "getCatalystList: Process was interrupted!", e );
        }

    }

    public void loadCatalystZodiacJson(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.catalyst_zodiac0719);
        String text = null;
        try (final Reader reader = new InputStreamReader(inputStream)) {
            text = CharStreams.toString(reader);
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            catalystZodiacMap = gson.fromJson(text, type);

            catalystZodiacMapIsLoaded = true;

        } catch (Exception e){
            Log.e(TAG, "loadCatalystZodiacJson: Could not load CatalystZodiac Json file!!", e);
            catalystZodiacMapIsLoaded = false;
        }




    }

    public List<Catalyst> getCatalystList(){
        ArrayList<Catalyst> list = new ArrayList<>();
        for (Catalyst c : catalystList)
            if (c.getType().equals("catalyst")) list.add(c);

        return list;
    }

    public List<Catalyst> getAllItems(){
        return catalystList;
    }

    public Catalyst getCatalystById(String fileId){
        if (catalystMap.containsKey(fileId)){
            return catalystList.get(catalystMap.get(fileId));
        } else {
            return null;
        }
    }

    public String getCatalystZodiacById(String fileId){
        if (catalystZodiacMap.containsKey(fileId)){
            return catalystZodiacMap.get(fileId);
        } else {
            return null;
        }
    }
}
