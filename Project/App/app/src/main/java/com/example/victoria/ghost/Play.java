package com.example.victoria.ghost;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Play extends Activity{

    private Lexicon lexicon;
    private Game game;
    private String sourceFile;
    private EditText letterGuesser;
    private String entered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        sourceFile = "english.txt";
        lexicon = new Lexicon(this,sourceFile);
        game = new Game(lexicon);
        entered = "";

        letterGuesser = (EditText) findViewById(R.id.guesser);
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
        }
    }

    public void showWord() {

        TextView word = (TextView) findViewById(R.id.word);
        String wordText = word.getText().toString();
        for(int i = 0; i< entered.length(); i++) {
            word.setText(wordText + "  " + entered.charAt(i));
        }

    }

    public void freeze(String winner) {
        Intent winGame = new Intent(this, Won.class );
        final int result = 1;

        winGame.putExtra("Word", entered);
        winGame.putExtra("Winner", winner );
        startActivityForResult(winGame, result);
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



}
