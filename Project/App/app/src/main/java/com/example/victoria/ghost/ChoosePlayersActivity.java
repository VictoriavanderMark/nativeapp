package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by victoria on 5-10-15.
 */
public class ChoosePlayersActivity extends Activity{

    private String P1name;
    private String P2name;
    private boolean P1selected;
    private boolean P2selected;
    private String chosenName;
    private View viewToChange;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);


        startGameButton = (Button) findViewById(R.id.play_button);
        startGameButton.getBackground().setAlpha(120);
        startGameButton.setTextColor(Color.parseColor("#7C7272"));
        startGameButton.setClickable(false);



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
            this.startActivity(showSettingsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent playerChosenIntent) {
        if(playerChosenIntent !=null) {
            chosenName = playerChosenIntent.getExtras().getString("Name").toUpperCase();
            setName();
        }
    }





    public void updateNames() {
        P1name = ((Button) findViewById(R.id.P1button)).getText().toString();
        P2name = ((Button) findViewById(R.id.P2button)).getText().toString();
        if(!(P1name.equals(getResources().getString(R.string.choose_p1)))) {
            P1selected = true;
        }
        if(!(P2name.equals(getResources().getString(R.string.choose_p2)))) {
            P2selected = true;
        }
        if(P1selected & P2selected) {
            startGameButton.getBackground().setAlpha(255);
            startGameButton.setTextColor(Color.parseColor("#000000"));
            startGameButton.setClickable(true);

        }
    }

    public void choosePlayer(View v) {
        chosenName = "";
        viewToChange = v;
        Intent showPlayerListIntent = new Intent(this, PlayerListActivity.class);
        final int result = 1;
        startActivityForResult(showPlayerListIntent, result);


    }

    public void setName() {
        if (chosenName.length() > 0) {
            ((TextView) viewToChange).setText(chosenName);
            updateNames();
        }
    }

    public void playGame(View view) {
        if(!P1name.equals(P2name)) {
            startGameButton.setText("LOADING...");
            Intent startNewGameIntent = new Intent(this, GameActivity.class);
            startNewGameIntent.putExtra("P1name", P1name.toUpperCase());
            startNewGameIntent.putExtra("P2name", P2name.toUpperCase());
            startActivity(startNewGameIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please choose two different players",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
