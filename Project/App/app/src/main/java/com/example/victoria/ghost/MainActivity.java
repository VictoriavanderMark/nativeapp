package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

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

    public void playGame(View view) {
        Intent choosePlayersIntent = new Intent(this, ChoosePlayersActivity.class);
        startActivity(choosePlayersIntent);
    }

    public void showTutorial(View v) {
        Intent showTutorialIntent = new Intent(this, TutorialActivity.class);
        startActivity(showTutorialIntent);

    }


    public void showLeaderboard(View v) {
        Intent showLeaderboardIntent = new Intent(this, LeaderboardActivity.class);
        startActivity(showLeaderboardIntent);

    }





}
