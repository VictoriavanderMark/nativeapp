package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);



        //play();
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
            Intent intent = new Intent(this, Settings.class);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void choosePlayers(View view) {
        Intent choosePlayers = new Intent(this, ChoosePlayers.class);
        startActivity(choosePlayers);
    }

    public void showTutorial(View v) {
        ArrayList<Player> test= new ArrayList<Player>();

        try {
            FileOutputStream fos = openFileOutput("leaderboard.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(test);
            oos.close();
            System.out.println("geschreven");




        } catch(IOException e) {
            e.printStackTrace();

        }

        //TODO tutorial
    }


    public void showLeaderboard(View v) {
        Intent getLeaderboard = new Intent(this, LeaderboardActivity.class);
        startActivity(getLeaderboard);

    }





}
