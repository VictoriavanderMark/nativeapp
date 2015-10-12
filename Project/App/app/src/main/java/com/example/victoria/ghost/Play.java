package com.example.victoria.ghost;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Play extends Activity{

    private Lexicon lexicon;
    private Game game;
    private EditText letterGuesser;
    private String entered;
    private TextView P1;
    private TextView P2;
    private String P1name;
    private String P2name;
    private String sourceFile;
    private int MIN_OPACITY_P1 = 70;
    private int MIN_OPACITY_P2 = 50;
    private int MAX_OPACITY_P1 = 255;
    private int MAX_OPACITY_P2 = 200;
    private String MIN_OPACITY_TEXT = "#A39898";
    private String MAX_OPACITY_TEXT = "#000000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        sourceFile = PreferenceManager.getDefaultSharedPreferences(this).getString(Settings_inGame.KEY_PREF_SYNC_CONN, "");
        System.out.println("SOURCEFILE: " + sourceFile);
        lexicon = new Lexicon(this,sourceFile);
        System.out.println(lexicon.getSize());
        game = new Game(lexicon);
        entered = "";

        initialiseLayout();



        letterGuesser = (EditText) findViewById(R.id.guesser);
        letterGuesser.requestFocus();
        if(letterGuesser.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        letterGuesser.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    String letter = letterGuesser.getText().toString();
                    System.out.println("DIT TYP JE JAJAJ " + letter);
                    entered = entered + letter;

                    play(letter);
                    return true;
                }
                return false;
            }
        });

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
            Intent intent = new Intent(this, Settings_inGame.class);
            this.startActivity(intent);
            System.out.println("ACTIVITY AFGELOPEN");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        System.out.println(sourceFile);
        System.out.println(PreferenceManager.getDefaultSharedPreferences(this).getString(Settings_inGame.KEY_PREF_SYNC_CONN, ""));
                Toast.makeText(getApplicationContext(), "Changes will be effective after restarting the game",
                        Toast.LENGTH_LONG).show(); //TODO werkt niet
    }



    public void changeLanguage(String newLanguage) {
        sourceFile = newLanguage;
        lexicon = new Lexicon(this, sourceFile);
        reset();

    }

    public void play(String g) {
        if(g.length()>0) {
            printTurn();

            game.guess(g);
            if(game.ended()){
                String winner = getWinner();
                String loser;
                if(winner.equals(P1name)) {
                    loser = P2name;
                } else {
                    loser = P1name;
                }
                freeze(winner, loser);

            } else {
                showWord();
                letterGuesser.setText("");
                setOpacity();
            }
        }

    }

    public void initialiseLayout() {
        P1 = (TextView) findViewById(R.id.P1);
        P2 = (TextView) findViewById(R.id.P2);
        setOpacity(true);
        setPlayerNames();
    }

    public void setPlayerNames() {
        Intent called = getIntent();
        P1name = called.getExtras().getString("P1name");
        P2name = called.getExtras().getString("P2name");
        ((  (TextView) findViewById(R.id.P1))).setText(P1name);
        ((  (TextView) findViewById(R.id.P2))).setText(P2name);


    }

    public void showWord() {

        TextView word = (TextView) findViewById(R.id.word);
        String wordText = word.getText().toString();
        for(int i = 0; i< entered.length(); i++) {
            word.setText(wordText + "  " + entered.charAt(i));
        }

    }

    public void resetText() {
        TextView word = (TextView) findViewById(R.id.word);
        word.setText(entered);
        letterGuesser.setText("");

    }

    public void setOpacity(Boolean P1turn) {
        if(P1turn) {
            P2.getBackground().setAlpha(MIN_OPACITY_P2);
            P2.setTextColor(Color.parseColor(MIN_OPACITY_TEXT));
            P1.getBackground().setAlpha(MAX_OPACITY_P1);
            P1.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
        } else {
            P2.getBackground().setAlpha(MAX_OPACITY_P2);
            P2.setTextColor(Color.parseColor(MAX_OPACITY_TEXT));
            P1.getBackground().setAlpha(MIN_OPACITY_P1);
            P1.setTextColor(Color.parseColor(MIN_OPACITY_TEXT));
        }
    }

    public void setOpacity() {
        setOpacity(game.turn());
    }


    public void updateScore(String winner) {
        try {
            FileInputStream fis = openFileInput("leaderboard.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Player> players= (ArrayList<Player>) ois.readObject();
            for (Player p : players) {
                if(p.getName().equals(winner)) {
                    p.updateScore();
                    break;
                }
            }
            ois.close();

            FileOutputStream fos = openFileOutput("leaderboard.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();


        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void freeze(String winner, String loser) {
        updateScore(winner);
        Intent winGame = new Intent(this, Won.class );
        final int result = 1;


        int numPossible = game.getNumPossible();
        String reason;
        if(numPossible == 0) {
            reason = "gibberish";
        } else {
            reason = "an existing word";
        }

        winGame.putExtra("Word", entered);
        winGame.putExtra("Winner", winner );
        winGame.putExtra("Loser", loser);
        winGame.putExtra("Reason", reason);
        startActivityForResult(winGame, result);
        System.out.println("IK GA RESETTEN");
        reset();
    }

    public void printTurn() {
        Boolean turn = game.turn();
        if (turn) {
            System.out.println("It's P1's turn");
        } else {
            System.out.println("It's P2's turn");
        }
    }

    public String getWinner() {
        Boolean winner = game.winner();
        String winnerName;
        if (winner) {
            winnerName = P1name;

        } else {
            winnerName = P2name;

        }

        return winnerName;
    }

    public void reset() {
        String newLanguage = PreferenceManager.getDefaultSharedPreferences(this).getString(Settings_inGame.KEY_PREF_SYNC_CONN, "");
        if(!(sourceFile.equals(newLanguage))) {
            sourceFile =  PreferenceManager.getDefaultSharedPreferences(this).getString(Settings_inGame.KEY_PREF_SYNC_CONN, "");
            lexicon = new Lexicon(this, sourceFile);
        } else {
            lexicon.reset();
        }
        game = new Game(lexicon);
        setOpacity();
        entered = "";
        resetText();
        letterGuesser.requestFocus();
        if(letterGuesser.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
