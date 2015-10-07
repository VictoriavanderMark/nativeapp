package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by victoria on 5-10-15.
 */
public class ChoosePlayers extends Activity{

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
            Intent intent = new Intent(this, Settings.class);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data !=null) {
            chosenName = data.getExtras().getString("Name").toUpperCase();
            System.out.println("gekzoen:" + chosenName);

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
        //String player = v.getResources().getResourceEntryName(v.getId());
//        System.out.println(player);
//        System.out.println("JE HEBT GEKLIKT WOEEH");
        viewToChange = v;
        Intent choosePlayer = new Intent(this, ChoosePlayer.class);
        final int result = 1;
        startActivityForResult(choosePlayer, result);


    }

    public void setName() {
        if (chosenName.length() > 0) {
            System.out.println("Hij is langer dan 0");
            System.out.println("The chosen name!" + chosenName);
            ((TextView) viewToChange).setText(chosenName);
            System.out.println("viewToChange text " + ((TextView) viewToChange).getText().toString());
            updateNames();
        }
    }

    public void playGame(View view) {
        updateNames();
        Intent startGame = new Intent(this, Play.class );
        startGame.putExtra("P1name", P1name.toUpperCase());
        startGame.putExtra("P2name", P2name.toUpperCase());
        startActivity(startGame);

    }

}
