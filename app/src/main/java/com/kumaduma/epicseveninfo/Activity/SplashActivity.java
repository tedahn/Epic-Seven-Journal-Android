package com.kumaduma.epicseveninfo.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.DataManager.TierManager;
import com.kumaduma.epicseveninfo.MainActivity;
import com.kumaduma.epicseveninfo.Model.Settings.Contributor;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private Handler progressHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView splashBackground = this.findViewById(R.id.splash_background);
        ImageView imageView = this.findViewById(R.id.splash_loading_image);
        ProgressBar loadingBar = this.findViewById(R.id.splash_loading_bar);
        TextView loadingMessage = this.findViewById(R.id.splash_loading_hint);

        loadingBar.setMax(4);
        boolean isCharacterLoadingScreen = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("es_loading_screen", false);

        if (isCharacterLoadingScreen) {

            InputStream inputStream = this.getResources().openRawResource(R.raw.character_background);
            Map<String, String> backgroundList = new HashMap<>();
            String text = null;
            try (final Reader reader = new InputStreamReader(inputStream)) {
                text = CharStreams.toString(reader);
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String,String>>(){}.getType();
                backgroundList = gson.fromJson(text, type);

                imageView.setVisibility(View.INVISIBLE);
                List<String> backgrounds = new ArrayList<>(backgroundList.values());
                Random rand = new Random();
                int n = rand.nextInt(backgrounds.size());
                Picasso.get()
                        .load(backgrounds.get(n))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(splashBackground);
            } catch (Exception e){
                Log.e(TAG, "onCreateView: ", e);
            }
        }
        new Thread(()-> {
                try {
                    ArtifactManager.getInstance();
                    progressHandler.post(() -> {
                        loadingBar.incrementProgressBy(1);
                        loadingMessage.setText("Loading Artifacts");});

                    CatalystManager.getInstance().loadCatalystZodiacJson(this);;
                    progressHandler.post(() -> {
                        loadingBar.incrementProgressBy(1);
                        loadingMessage.setText("Loading Catalysts");});

                    HeroManager.getInstance();
                    progressHandler.post(() -> {
                        loadingBar.incrementProgressBy(1);
                        loadingMessage.setText("Loading Heroes");});

                    TierManager.getInstance(this);
                    progressHandler.post(() -> {
                        loadingBar.incrementProgressBy(1);
                        loadingMessage.setText("Loading Discord Tier List");});
                    //heroManager.mapAllHeroes();

                    //sleep(1000);  //Delay of 10 seconds
                } catch (Exception e) {
                    Log.e(TAG, "onCreate: " + e.toString(), e);
                } finally {
                    Intent i = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
        }).start();
    }
}
