package com.kumaduma.epicseveninfo.Activity;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.kumaduma.epicseveninfo.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey);
    }
}
