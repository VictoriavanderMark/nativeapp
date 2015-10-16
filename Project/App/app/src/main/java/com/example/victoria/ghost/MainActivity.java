/*
 *      Project: Ghost
 *      File: MainActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * The MainActivity class shows the main screen of the application. The user may choose to
 * read the tutorial, view the leaderboard or start a new game.
 */
public class MainActivity extends Activity {

    /*
     * On creating, the default values of the shared preferences are loaded.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent getSettingsIntent = new Intent(this, SettingsActivity.class);
            this.startActivity(getSettingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * On pressing the button for playing the game, the Activity for selecting players
     * is opened.
     */
    public void playGame(View view) {
        Intent choosePlayersIntent = new Intent(this, ChoosePlayersActivity.class);
        startActivity(choosePlayersIntent);
    }

    /*
     * On pressing the button for showing the tutorial, the Activity for reading
     * the instructions is opened.
     */
    public void showTutorial(View v) {
        Intent showTutorialIntent = new Intent(this, TutorialActivity.class);
        startActivity(showTutorialIntent);

    }

    /*
     * On pressing the button for showing the leaderboard, the Activity for loading
     * the leaderboard is opened, in which all players and their respective scores
     * are visible.
     */
    public void showLeaderboard(View v) {
        Intent showLeaderboardIntent = new Intent(this, LeaderboardActivity.class);
        startActivity(showLeaderboardIntent);
    }
}
