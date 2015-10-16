/*
 *      Project: Ghost
 *      File: GameActivity.java
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
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


/**
 * The GameActivity class creates a playable game Acitivity and processes all the user input
 */
public class GameActivity extends Activity{

    private Game game;
    private EditText letterInput;
    private String wordSoFar;
    private String P1name;
    private String P2name;
    private TextView P1;
    private TextView P2;
    final private int MAX_OPACITY_VIEWS = 255;
    final private String MAX_OPACITY_TEXT = "#000000";

    /*
     * When created, the game becomes ready to play and the unitial layout is set.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setUpGame();
        setInitialLayout();
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
            showSettingsIntent.putExtra("CalledInGame", true);
            this.startActivity(showSettingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * When touching the 'back'-button, apart from finishing the Activity, the opacity
     * of the textViews is being resetted. In this way, they are showed correctly in
     * ChoosePlayersActivity.java.
     */
    @Override
    public void onBackPressed() {
        resetOpacity();
        finish();
    }

    /*
     * Loads a lexicon (depending on the defined language in the Shared Preferences) and specifies
     * and initialises all the necessary variables for starting the game.
     */
    private void setUpGame() {
        String lexiconSource = PreferenceManager.getDefaultSharedPreferences(this).getString(
                SettingsActivity.KEY_PREF_SYNC_CONN, "");
        Lexicon lexicon = new Lexicon(this,lexiconSource);
        game = new Game(lexicon);
        wordSoFar = "";
        setUpLetterInput();
    }

    /*
     * Sets up the way for the players to enter their guesses by creating an EditText view.
     */
    private void setUpLetterInput() {
        letterInput = (EditText) findViewById(R.id.guesser);
        letterInput.requestFocus();

        if(letterInput.requestFocus()) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        setLetterInputListener();
    }

    /*
     * Sets a listener for the letterInput view. When the user presses ENTER, the guess
     * is processed.
     */
    private void setLetterInputListener() {
        letterInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    String enteredLetter = letterInput.getText().toString();
                    wordSoFar = wordSoFar + enteredLetter;
                    enterLetter(enteredLetter);
                    return true;
                }
                return false;
            }
        });
    }

    /*
     * After entering a letter, the letter is processed within the game object and the layout
     * is adjusted accordingly.
     */
    private void enterLetter(String enteredLetter) {
        if(enteredLetter.length() > 0) {
            game.guess(enteredLetter);

            if(game.ended()){
                String winner = getWinnerName();
                String loser = winner.equals(P1name) ? P2name : P1name;
                showGameResult(winner, loser);

            } else {
                letterInput.setText("");
                showWord();
                updateOpacity();
            }
        }
    }

    /*
     * Returns the name of the player that won the game
     */
    private String getWinnerName() {
        Boolean P1winner = game.P1winner();
        return P1winner ? P1name : P2name;
    }

    /*
     * Redirects to the winning Activity, adding the information needed for provided the
     * players all the relevant on how the game ended.
     */
    private void showGameResult(String winner, String loser) {
        String gibberish = getString(R.string.gibberish);
        String existingWord = getString(R.string.existing_word);

        int numPossible = game.getNumPossible();
        String reason = numPossible == 0 ? gibberish : existingWord;

        Intent gameWonIntent = new Intent(this, WinningActivity.class );
        gameWonIntent.putExtra("Loser", loser);
        gameWonIntent.putExtra("Winner", winner );
        gameWonIntent.putExtra("ReasonWon", reason);
        gameWonIntent.putExtra("WordSpelled", wordSoFar);

        startActivity(gameWonIntent);
        resetOpacity();
        finish();
    }

    /*
     * Updates the word shown on the screen, based on the sequence of letters that the
     * players have been entering by turns.
     */
    private void showWord() {
        TextView wordSoFarView = (TextView) findViewById(R.id.wordSoFar);
        String wordText = wordSoFarView.getText().toString();
        for(int i = 0; i< wordSoFar.length(); i++) {
            wordSoFarView.setText(wordText + "  " + wordSoFar.charAt(i));
        }
    }

    /*
     * Defines the layout in which the Activity starts. During the game, the text within
     * the textview and the opacity rate of their backgrounds vary.
     */
    private void setInitialLayout () {
        P1 = (TextView) findViewById(R.id.P1);
        P2 = (TextView) findViewById(R.id.P2);
        updateOpacity();
        setPlayerNames();
    }


    /*
     * Depending on which player's turn it is, the opacity of their respective
     * textViews are adapted.
     */
    private void updateOpacity() {
        int MIN_OPACITY_VIEWS = 70;
        String MIN_OPACITY_TEXT = "#A39898";
        if(game.turn()) {
            P1.getBackground().setAlpha(MAX_OPACITY_VIEWS);
            P1.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
            P2.getBackground().setAlpha(MIN_OPACITY_VIEWS);
            P2.setTextColor(Color.parseColor(MIN_OPACITY_TEXT));
        } else {
            P2.getBackground().setAlpha(MAX_OPACITY_VIEWS);
            P2.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
            P1.getBackground().setAlpha(MIN_OPACITY_VIEWS);
            P1.setTextColor(Color.parseColor(MIN_OPACITY_TEXT));
        }
    }

    /*
     * Resets the opacity of the players' textViews, making them both non-transparent
     * (as they were in ChoosePlayersActivity.java).
     */
    private void resetOpacity() {
        P1.getBackground().setAlpha(MAX_OPACITY_VIEWS);
        P2.getBackground().setAlpha(MAX_OPACITY_VIEWS);
        P1.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
        P2.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
    }

    /*
     * Adjusts the names of the players shown on screen, based on the information
     * passed by the preceding Activity (ChoosePlayersActivity.java)
     */
    private void setPlayerNames() {
        Intent startNewGameIntent = getIntent();
        P1name = startNewGameIntent.getExtras().getString("P1name");
        P2name = startNewGameIntent.getExtras().getString("P2name");
        ((  (TextView) findViewById(R.id.P1))).setText(P1name);
        ((  (TextView) findViewById(R.id.P2))).setText(P2name);
    }

}
