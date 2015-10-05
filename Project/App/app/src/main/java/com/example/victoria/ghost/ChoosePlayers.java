package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by victoria on 5-10-15.
 */
public class ChoosePlayers extends Activity{

    private String P1name;
    private String P2name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);

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


    public void getNames() {
        P1name = "Patata 1";
        P2name = "Potato 2";
    }

    public void choosePlayer(View v) {
        //String player = v.getResources().getResourceEntryName(v.getId());
//        System.out.println(player);
//        System.out.println("JE HEBT GEKLIKT WOEEH");
        Intent choosePlayer = new Intent(this, ChoosePlayer.class);
        final int result = 1;
        startActivityForResult(choosePlayer, result);

    }

    public void playGame(View view) {
        getNames();
        Intent startGame = new Intent(this, Play.class );
        startGame.putExtra("P1name", P1name.toUpperCase());
        startGame.putExtra("P2name", P2name.toUpperCase());
        startActivity(startGame);

    }

}
