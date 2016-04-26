package com.yourapp.myapplication;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsPreference extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add the xml resource
        addPreferencesFromResource(R.layout.user_settings);
        getPreferenceScreen().setTitle("Settings");

    }
}
