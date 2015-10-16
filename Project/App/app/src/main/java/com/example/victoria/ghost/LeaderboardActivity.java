/*
 *      Project: Ghost
 *      File: LeaderboardActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import android.view.View;
import android.widget.ImageButton;


/**
 * The LeaderBoard class is based on an Activity that shows a list of names.
 * Additionaly, when selecting or unselecting a name, the delete button becomes
 * visible or unvisible respectively.
 */
public class LeaderboardActivity extends NameListActivity {

    /*
     * When the Activity is created, the button should be invisible
     */
    public void initialiseButtons() {
        delete_button = (ImageButton) findViewById(R.id.delete);
    }

    /*
     * Makes the delete button visible
     */
    public void showButtons() {
        delete_button.setVisibility(View.VISIBLE);
    }

    /*
     * Makes the delete button invisible
     */
    public void hideButtons() {
        delete_button.setVisibility(View.INVISIBLE);
    }
}
