package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by victoria on 1-10-15.
 */
public class WinningActivity extends Activity{

    private String winnerName;
    private String loserName;
    private String wordSpelled;
    private String reasonWon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        showWinner();
    }

    @Override
    public void onBackPressed() {}

    public void showWinner() {
        getIntentInfo();
        updateScore();
        showWinningDetails();
    }

    public void updateScore() {
        try {
            FileInputStream fis = openFileInput(getResources().getString(R.string.leaderboard_sourcefile));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Player> players= (ArrayList<Player>) ois.readObject();
            for (Player p : players) {
                if(p.getName().equals(winnerName)) {
                    p.updateScore();
                    break;
                }
            }
            ois.close();

            FileOutputStream fos = openFileOutput(getResources().getString(R.string.leaderboard_sourcefile), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();


        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void getIntentInfo() {
        Intent gameWonIntent = getIntent();
        winnerName = gameWonIntent.getExtras().getString("Winner");
        loserName = gameWonIntent.getExtras().getString("Loser");
        wordSpelled = gameWonIntent.getExtras().getString("WordSpelled");
        reasonWon = gameWonIntent.getExtras().getString("ReasonWon");
    }

    public void showWinningDetails() {
        ((TextView) findViewById(R.id.winner_essential_message)).setText(winnerName + " won!".toUpperCase());
        ((TextView) findViewById(R.id.winner_details_message)).setText(wordSpelled + "\" was spelled by " + loserName + ", which is " + reasonWon + ".");
        ((TextView) findViewById(R.id.winner_details_message)).setText(loserName + " spelled \"" + wordSpelled.toLowerCase() + "\", which is " + reasonWon + ".");
    }

    public void returnHome(View v) {
        Intent showMainScreenIntent = new Intent(this, MainActivity.class);
        showMainScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(showMainScreenIntent);
        finish();
    }

    public void showLeaderboard(View v) {
        Intent showLeaderboardIntent = new Intent(this, LeaderboardActivity.class);
        showLeaderboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(showLeaderboardIntent);
        finish();
    }

    public void resetGame(View view) {
        Intent startNewGameIntent = new Intent(this, GameActivity.class);
        startNewGameIntent.putExtra("P1name", loserName.toUpperCase());
        startNewGameIntent.putExtra("P2name", winnerName.toUpperCase());
        startActivity(startNewGameIntent);
        this.finish();
    }

}
