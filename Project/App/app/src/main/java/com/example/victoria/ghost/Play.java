package com.example.victoria.ghost;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Play extends Activity{

    private Lexicon lexicon;
    private Game game;
    private String sourceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        sourceFile = "english.txt";
        lexicon = new Lexicon(this,sourceFile);
        game = new Game(lexicon);
        play();

    }



    public void play() {

        String[] guesses = {"a","p","p","l","e", "p"};
        for(String g:guesses) {
            printTurn();
            game.guess(g);
            if(game.ended()){
                System.out.println("GAME OVER!");
                getWinner();
                System.out.println("RESULT: " + lexicon.result());

                break;
            }
        }
    }
    public void printTurn() {
        Boolean turn = game.turn();
        if (turn) {
            System.out.println("It's P1's turn");
        } else {
            System.out.println("It's P2's turn");
        }
    }

    public void getWinner() {
        Boolean winner = game.winner();
        String winnerName;
        if (winner) {
            winnerName = "P1";
        } else {
            winnerName = "P2";
        }
        System.out.println("OVERWINNING " + winnerName);
    }



}
