package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * Created by victoria on 1-10-15.
 */
public class Won extends Activity{


    private String winner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        showWinner();


        View winning = findViewById(R.id.winner);
        View root = winning.getRootView();
//        root.setBackgroundColor(Color.parseColor("#F1F8E9"));

    }

    @Override
    public void onBackPressed() {}


    public void showWinner() {
        Intent called = getIntent();
        winner = called.getExtras().getString("Winner");
        String word = called.getExtras().getString("Word");
        String reason = called.getExtras().getString("Reason");
        TextView playerWon = (TextView) findViewById(R.id.winner);
        playerWon.setText(winner + " won! \"" + word + "\" was spelled by the other player, which is " + reason + ".");
    }

    public void reset(View view) {
        this.finish();
    }

}