package com.example.victoria.ghost;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Play extends Activity{

    private Lexicon lexicon;
    private Game game;
    private String sourceFile;
    private EditText letterGuesser;
    private String entered;
    private Drawable P1;
    private Drawable P2;
    private int MIN_OPACITY_P1 = 70;
    private int MIN_OPACITY_P2 = 50;
    private int MAX_OPACITY_P1 = 255;
    private int MAX_OPACITY_P2 = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        sourceFile = "english.txt";
        lexicon = new Lexicon(this,sourceFile);
        game = new Game(lexicon);
        entered = "";

        P1 = findViewById(R.id.P1).getBackground();
        P2 = findViewById(R.id.P2).getBackground();
        P2.setAlpha(MIN_OPACITY_P2);

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


    public void play(String g) {
        if(g.length()>0) {
            printTurn();

            game.guess(g);
            if(game.ended()){
                System.out.println("GAME OVER!");
                String winner = getWinner();
                System.out.println("RESULT: " + lexicon.result());
                freeze(winner);

            } else {
                showWord();
                letterGuesser.setText("");
                setOpacity();
            }
        }

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

    public void setOpacity() {

        if(game.turn()) {
            P2.setAlpha(MIN_OPACITY_P2);
            P1.setAlpha(MAX_OPACITY_P1);
        } else {
            P2.setAlpha(MAX_OPACITY_P2);
            P1.setAlpha(MIN_OPACITY_P1);
        }
    }

    public void freeze(String winner) {
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
            winnerName = "P1";

        } else {
            winnerName = "P2";

        }
        System.out.println("OVERWINNING " + winnerName);

        return winnerName;
    }

    public void reset() {
        lexicon.reset();
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
