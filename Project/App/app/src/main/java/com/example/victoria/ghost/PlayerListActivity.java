/*
 *      Project: Ghost
 *      File: PlayerListActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;


/**
 * The PlayerList class is based on an Activity that shows a list of names.
 * Additionaly, when selecting or unselecting a name, the delete and choose buttons become
 * visible or unvisible respectively.
 */
public class PlayerListActivity extends NameListActivity {

    /*
     * When the Activity is created, the buttons should be invisible.
     */
    public void initialiseButtons() {
        delete_button = (ImageButton) findViewById(R.id.delete);
        choose_button = (ImageButton) findViewById(R.id.choose);
    }

    /*
     * Makes the delete and choose buttons visible.
     */
    public void showButtons() {
        delete_button.setVisibility(View.VISIBLE);
        choose_button.setVisibility(View.VISIBLE);
    }

    /*
     * Makes the delete button invisible
     */
    public void hideButtons() {
        delete_button.setVisibility(View.INVISIBLE);
        choose_button.setVisibility(View.INVISIBLE);
    }

    /*
     * When a player is chosen, his name is passed on to the intent that called.
     */
    public void choosePlayer(View v) {
        Intent playerChosenIntent = new Intent(this, ChoosePlayersActivity.class);
        playerChosenIntent.putExtra("Name", selectedName);
        setResult(RESULT_OK, playerChosenIntent);
        finish();
    }
}
