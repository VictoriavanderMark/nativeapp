package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Created by victoria on 1-10-15.
 */
public class WinningActivity extends Activity{

    private String winner;
    private String loser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        showWinner();

    }

    @Override
    public void onBackPressed() {}


    public void showWinner() {
        Intent gameWonIntent = getIntent();
        winner = gameWonIntent.getExtras().getString("Winner");
        loser = gameWonIntent.getExtras().getString("Loser");
        String word = gameWonIntent.getExtras().getString("Word");
        String reason = gameWonIntent.getExtras().getString("Reason");
        TextView playerWon = (TextView) findViewById(R.id.winner);
        playerWon.setText(winner + " won!".toUpperCase());
        TextView winningDetails = (TextView) findViewById(R.id.winner_details);
        winningDetails.setText(word + "\" was spelled by " + loser + ", which is " + reason + ".");
        winningDetails.setText(loser + " spelled \"" + word.toLowerCase() + "\", which is " + reason + ".");

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

    public void reset(View view) {

        Intent startNewGameIntent = new Intent(this, GameActivity.class);
        startNewGameIntent.putExtra("P1name", loser.toUpperCase());
        startNewGameIntent.putExtra("P2name", winner.toUpperCase());
        startActivity(startNewGameIntent);

        this.finish();
    }

}
