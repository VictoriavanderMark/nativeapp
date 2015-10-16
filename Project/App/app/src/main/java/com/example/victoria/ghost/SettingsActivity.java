/*
 *      Project: Ghost
 *      File: SettingsActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */


package com.example.victoria.ghost;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

/**
 * Creates the Settings screen, in which the player can choose the language of the lexicon.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String KEY_PREF_SYNC_CONN = "pref_language";

    /*
     * On creating, a new fragment is created for retrieving the settings
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        checkForInGame();

    }

    /*
     * Retrieves settings from shared preferences
     */
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    /*
     * When the Activity is called from the Game Activity, this information is added to the intent.
     * If so, a toast is shown, explaining the situation. If this information is not available,
     * the default value is "false".
     */
    private void checkForInGame() {
        Intent showSettingsIntent = getIntent();
        Boolean calledInGame = showSettingsIntent.getBooleanExtra("CalledInGame", false);
        if(calledInGame) {Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.in_game_changed), Toast.LENGTH_SHORT).show();
        }
    }
}
