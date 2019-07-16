package com.kumaduma.epicseveninfo.DataManager;

import android.content.Context;
import android.util.Log;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kumaduma.epicseveninfo.Model.Settings.Contributor;
import com.kumaduma.epicseveninfo.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class SettingsManager {
    private static final String TAG = "SettingsManager";
    private static SettingsManager sSettingsInstance;

    public static SettingsManager getInstance(){
        if (sSettingsInstance == null){ //if there is no instance available... create new one
            sSettingsInstance = new SettingsManager();
        }

        return sSettingsInstance;
    }


    public void loadContributors(Context context) {
    }
}
