package com.example.victoria.week1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainScreen extends Activity {

    private Button coinThrowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        coinThrowButton = (Button) findViewById(R.id.throw_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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


    public void getSide(View view) {
        int min = 0;
        int max = 1;
        String side;
        String imSource;
        Random r = new Random();
        int sideNum = r.nextInt(max - min + 1) + min;

        ImageView i = new ImageView(this);
        i = (ImageView) findViewById(R.id.imageView);

        sideNum = (Math.random() <= 0.5) ? 0 : 1;
        if(sideNum == 0){
            side = "head";
            i.setImageResource(R.drawable.head);

        } else {
            side = "tail";
            i.setImageResource(R.drawable.tail);
        }

        TextView t= new TextView(this);
        t = (TextView) findViewById(R.id.outcomeText);
        t.setText(side);






    }
}
