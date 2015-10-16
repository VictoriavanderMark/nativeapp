/*
 *      Project: Ghost
 *      File: WinningActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The Winning class shows all the information about how and why the game ended. The players
 * may choose to play again, return to the main screen or go to the leaderboard.
 */
public class WinningActivity extends Activity{

    private String winnerName;
    private String loserName;
    private String wordSpelled;
    private String reasonWon;

    /*
     * On creating,the information about the winner is shown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        processGameEnding();
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
     * Given the presented options, pressing "back" is suppressed
     */
    @Override
    public void onBackPressed() {}

    /*
     * After retrieving the information about the game, the leaderboard is updated and
     * the information is shown.
     */
    public void processGameEnding() {
        getIntentInfo();
        updateScore();
        showWinningDetails();
    }

    /*
     * Adds one to the score of the player that won the game.
     */
    private void updateScore() {
            String leaderboardSourcefile = getResources().getString(R.string.leaderboard_sourcefile);
            ArrayList<Player> players = readPlayers(leaderboardSourcefile);
            for (Player p : players) {
                if(p.getName().equals(winnerName)) {
                    p.updateScore();
                    break;
                }
            }
            savePlayers(players, leaderboardSourcefile);
    }

    /*
     * Reads the list of players and their scores from the file in which the
     * leaderboard is stored.
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Player> readPlayers(String leaderboardSourcefile) {
        try {
            FileInputStream fis = openFileInput(leaderboardSourcefile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Player> players = (ArrayList<Player>) ois.readObject();
            ois.close();
            return  players;
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Player>();
    }

    /*
     * After updating the winner's score, the modified leaderboard is saved.
     */
    private void savePlayers(ArrayList<Player> players, String leaderboardSourcefile) {
        try {
            FileOutputStream fos = openFileOutput(leaderboardSourcefile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Gets all te values passed by by the calling Intent.
     */
    private void getIntentInfo() {
        Intent gameWonIntent = getIntent();
        winnerName = gameWonIntent.getExtras().getString("Winner");
        loserName = gameWonIntent.getExtras().getString("Loser");
        wordSpelled = gameWonIntent.getExtras().getString("WordSpelled");
        reasonWon = gameWonIntent.getExtras().getString("ReasonWon");
    }

    /*
     * Displays the information about the end of the game to the players.
     */
    private void showWinningDetails() {
        String winningVerb = getString(R.string.winning_verb);
        String spellingVerb = getString(R.string.spelling_verb);
        String winningRelPronoun = getString(R.string.winning_relative_pronoun);

        ((TextView) findViewById(R.id.winner_essential_message)).setText(winnerName + winningVerb);
        ((TextView) findViewById(R.id.winner_details_message)).setText(loserName +
                spellingVerb + wordSpelled.toLowerCase() + winningRelPronoun + reasonWon);
    }

    /*
     * When the button for returning home is clicked on, the user return to the app's main screen.
     * Information about previously visited acitivities is deleted due to memory concerns.
     */
    public void returnHome(View v) {
        Intent showMainScreenIntent = new Intent(this, MainActivity.class);
        showMainScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(showMainScreenIntent);
        finish();
    }

    /*
     * When the button for showing the leaderboad is clicked on, the Leaderboard Activity is
     * called on.
     * Information about previously visited acitivities is deleted due to memory concerns.
     */
    public void showLeaderboard(View v) {
        Intent showLeaderboardIntent = new Intent(this, LeaderboardActivity.class);
        showLeaderboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(showLeaderboardIntent);
        finish();
    }

    /*
     * When the button for resetting the game is clicked on, a new game is started in the Game
     * Activity.
     * Information about the visited acitivity is deleted due to memory concerns.
     */
    public void resetGame(View view) {
        Intent startNewGameIntent = new Intent(this, GameActivity.class);
        startNewGameIntent.putExtra("P1name", loserName.toUpperCase());
        startNewGameIntent.putExtra("P2name", winnerName.toUpperCase());
        startActivity(startNewGameIntent);
        this.finish();
    }
}
