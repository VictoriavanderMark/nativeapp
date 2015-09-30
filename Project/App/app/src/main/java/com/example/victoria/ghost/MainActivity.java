package com.example.victoria.ghost;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private Lexicon lexicon;
    private Game game;
    private String sourceFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceFile = "english.txt";
        lexicon = new Lexicon(this,sourceFile);
        game = new Game(lexicon);




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void printTurn() {
        Boolean turn = game.turn();
        if (turn == true) {
            System.out.println("It's P1's turn");
        } else {
            System.out.println("It's P2's turn");
        }
    }

    public void getWinner() {
        Boolean winner = game.winner();
        String winnerName;
        if (winner == true) {
            winnerName = "P1";
        } else {
            winnerName = "P2";
        }
        System.out.println("OVERWINNING " + winnerName);
    }

}
