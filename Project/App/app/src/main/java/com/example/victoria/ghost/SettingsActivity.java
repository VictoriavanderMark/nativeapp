package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by victoria on 7-10-15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_SYNC_CONN = "pref_language";

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KEY_PREF_SYNC_CONN)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Intent showSettingsIntent = getIntent();
        Boolean calledInGame = showSettingsIntent.getBooleanExtra("CalledInGame", false);
        if(calledInGame) {
            Toast.makeText(getApplicationContext(), "Changes will be effective after restarting the game",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
