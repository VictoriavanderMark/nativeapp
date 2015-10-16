/*
 *      Project: Ghost
 *      File: ChoosePlayersActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */
package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * The ChoosePlayersActivity lets the players enter their names (for which they are
 * redirected to NameListActivity), after which they can start the game.
 */
public class ChoosePlayersActivity extends Activity{

    private String P1name;
    private String P2name;
    private String chosenPlayer;
    private boolean P1chosen;
    private boolean P2chosen;
    private View viewToChange;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);

        //Disable the button until all players have been chosen
        startGameButton = (Button) findViewById(R.id.start_game_button);
        startGameButton.getBackground().setAlpha(120);
        startGameButton.setTextColor(Color.parseColor("#7C7272"));
        startGameButton.setClickable(false);
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
            Intent showSettingsIntent = new Intent(this, SettingsActivity.class);
            this.startActivity(showSettingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * On receiving results from a called activity (in this case only PlayerList Activity),
     * the name of the chosen player is processed.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent playerChosenIntent) {
        if(playerChosenIntent != null) {
            chosenPlayer = playerChosenIntent.getExtras().getString("Name").toUpperCase();
            setName();
        }
    }

    /*
     * Adjusts the name shown on screen, based on the chosen player in the PlayerList Activity
     */
    private void setName() {
        if (chosenPlayer.length() > 0) {
            ((TextView) viewToChange).setText(chosenPlayer);
            updateNames();
        }
    }

    /*
     * When a player is picked, it's textView is marked as being 'chosen'. When both
     * players have been chosen, the button for starting the game becomes clickable and
     * the game may start.
     */
    private void updateNames() {
        P1name = ((Button) findViewById(R.id.choose_p1_button)).getText().toString();
        P2name = ((Button) findViewById(R.id.choose_p2_button)).getText().toString();

        if(!(P1name.equals(getResources().getString(R.string.choose_p1_text)))) {
            P1chosen = true;
        }
        if(!(P2name.equals(getResources().getString(R.string.choose_p2_text)))) {
            P2chosen = true;
        }
        if(P1chosen & P2chosen) {
            startGameButton.getBackground().setAlpha(255);
            startGameButton.setTextColor(Color.parseColor("#000000"));
            startGameButton.setClickable(true);
        }
    }

    /*
     * When clicking on a player's textView, the user is redirected to a list
     * of all stored Players, where he may choose his own name.
     */
    public void choosePlayer(View view) {
        chosenPlayer = "";
        viewToChange = view;

        Intent showPlayerListIntent = new Intent(this, PlayerListActivity.class);
        final int result = 1;
        startActivityForResult(showPlayerListIntent, result);
    }

    /* After clicking on the button for starting the game, the names of the
     * chosen players are stored and the Game Activity is called on*/
    public void playGame(View view) {
        if(!P1name.equals(P2name)) {
            startGameButton.setText(R.string.loading_game_text);

            Intent startNewGameIntent = new Intent(this, GameActivity.class);
            startNewGameIntent.putExtra("P1name", P1name.toUpperCase());
            startNewGameIntent.putExtra("P2name", P2name.toUpperCase());

            startActivity(startNewGameIntent);
            finish();

        // A player should not compete against himself
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.same_players_chosen),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
