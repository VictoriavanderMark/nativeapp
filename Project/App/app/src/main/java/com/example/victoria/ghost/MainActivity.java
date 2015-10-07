package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //play();
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

    public void choosePlayers(View view) {
        Intent choosePlayers = new Intent(this, ChoosePlayers.class);
        final int result = 1;
        startActivityForResult(choosePlayers, result);
    }


//    public void playGame(View view) {
//        Intent getName = new Intent(this, Play.class );
//        final int result = 1;
//        startActivityForResult(getName, result);
//    }



}
