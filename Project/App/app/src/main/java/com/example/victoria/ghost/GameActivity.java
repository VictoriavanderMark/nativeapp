package com.example.victoria.ghost;


import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GameActivity extends Activity{

    private Game game;
    private EditText letterInput;
    private String wordSoFar;
    private TextView P1;
    private TextView P2;
    private String P1name;
    private String P2name;
    private int MIN_OPACITY_VIEWS = 70;
    private int MAX_OPACITY_VIEWS = 255;
    private String MIN_OPACITY_TEXT = "#A39898";
    private String MAX_OPACITY_TEXT = "#000000";


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

    @Override
    public void onBackPressed() {
        resetOpacity();
        finish();
    }

    public void setUpGame() {
        String lexiconSource = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_SYNC_CONN, "");
        Lexicon lexicon = new Lexicon(this,lexiconSource);
        game = new Game(lexicon);
        wordSoFar = "";
        setUpLetterInput();
    }

    public void setUpLetterInput() {
        letterInput = (EditText) findViewById(R.id.guesser);
        letterInput.requestFocus();

        if(letterInput.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        setLetterInputListener();
    }

    public void setLetterInputListener() {
        letterInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    String enteredLetter = letterInput.getText().toString();
                    wordSoFar = wordSoFar + enteredLetter;
                    enterLetter(enteredLetter);
                    return true;
                }
                return false;
            }
        });

    }

    public void setInitialLayout () {
        P1 = (TextView) findViewById(R.id.P1);
        P2 = (TextView) findViewById(R.id.P2);
        updateOpacity();
        setPlayerNames();
    }

    public void updateOpacity() {
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

    public void resetOpacity() {
        P1.getBackground().setAlpha(MAX_OPACITY_VIEWS);
        P2.getBackground().setAlpha(MAX_OPACITY_VIEWS);
        P1.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
        P2.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
    }

    public void setPlayerNames() {
        Intent startNewGameIntent = getIntent();
        P1name = startNewGameIntent.getExtras().getString("P1name");
        P2name = startNewGameIntent.getExtras().getString("P2name");
        ((  (TextView) findViewById(R.id.P1))).setText(P1name);
        ((  (TextView) findViewById(R.id.P2))).setText(P2name);
    }

    public void enterLetter(String enteredLetter) {
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

    public String getWinnerName() {
        Boolean P1winner = game.P1winner();
        return P1winner ? P1name : P2name;
    }

    public void showGameResult(String winner, String loser) {
        int numPossible = game.getNumPossible();
        String reason = numPossible == 0 ? "gibberish" : "an existing word";

        Intent gameWonIntent = new Intent(this, WinningActivity.class );
        gameWonIntent.putExtra("Loser", loser);
        gameWonIntent.putExtra("Winner", winner );
        gameWonIntent.putExtra("ReasonWon", reason);
        gameWonIntent.putExtra("WordSpelled", wordSoFar);

        startActivity(gameWonIntent);
        resetOpacity();
        finish();
    }

    public void showWord() {
        TextView wordSoFarView = (TextView) findViewById(R.id.wordSoFar);
        String wordText = wordSoFarView.getText().toString();
        for(int i = 0; i< wordSoFar.length(); i++) {
            wordSoFarView.setText(wordText + "  " + wordSoFar.charAt(i));
        }
    }



//
//    public void reset() {
//        String newLanguage = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_SYNC_CONN, "");
//        if(!(lexiconSource.equals(newLanguage))) {
//            lexiconSource =  PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_PREF_SYNC_CONN, "");
//            lexicon = new Lexicon(this, lexiconSource);
//        } else {
//            lexicon.reset();
//        }
//        game = new Game(lexicon);
//        setOpacity();
//        wordSoFar = "";
//        resetText();
//        letterInput.requestFocus();
//        if(letterInput.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
////    }

//    public void resetText() {
//        TextView word = (TextView) findViewById(R.id.word);
//        word.setText(wordSoFar);
//        letterInput.setText("");
//
//    }



}
