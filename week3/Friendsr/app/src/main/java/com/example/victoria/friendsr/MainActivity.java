package com.example.victoria.friendsr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    private String[] names = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String FILENAME = "names";
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;

            int i = 0;
            while((line = reader.readLine()) != null) {
                names[i] = line;
                i++;
            }
            fis.close();

            adjustNames();

        } catch(IOException  e) {
            e.printStackTrace();
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

    public void onPickFriend(View view) {
        Intent getName = new Intent(this, SecondScreen.class );
        final int result = 1;


        String character = view.getResources().getResourceName(view.getId());
        getName.putExtra("Character", character);

        startActivityForResult(getName, result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            String nameSentBack = data.getStringExtra("EnteredName");
            String character = data.getStringExtra("Character")+"name";
            TextView nameField = (TextView)findViewById(getResources().getIdentifier(character, "id", getPackageName()));
            nameField.setText(nameSentBack);
            storeChanges();
        } catch(RuntimeException ignored) {
        }


    }

    public void storeChanges() {
        String FILENAME = "names";
        names[0] = ((TextView)findViewById(getResources().getIdentifier("chandlername", "id", getPackageName()))).getText().toString();
        names[1] = ((TextView)findViewById(getResources().getIdentifier("joeyname", "id", getPackageName()))).getText().toString();
        names[2] = ((TextView)findViewById(getResources().getIdentifier("monicaname", "id", getPackageName()))).getText().toString();
        names[3] = ((TextView)findViewById(getResources().getIdentifier("phoebename", "id", getPackageName()))).getText().toString();
        names[4] = ((TextView)findViewById(getResources().getIdentifier("rachelname", "id", getPackageName()))).getText().toString();
        names[5] = ((TextView)findViewById(getResources().getIdentifier("rossname", "id", getPackageName()))).getText().toString();

        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (String s : names) {
                fos.write((s+ "\n").getBytes());
            }

            fos.close();
        } catch(IOException ignored) {}

    }

    public void adjustNames() {
        ((TextView)findViewById(getResources().getIdentifier("chandlername", "id", getPackageName()))).setText(names[0]);
        ((TextView)findViewById(getResources().getIdentifier("joeyname", "id", getPackageName()))).setText(names[1]);
        ((TextView)findViewById(getResources().getIdentifier("monicaname", "id", getPackageName()))).setText(names[2]);
        ((TextView)findViewById(getResources().getIdentifier("phoebename", "id", getPackageName()))).setText(names[3]);
        ((TextView)findViewById(getResources().getIdentifier("rachelname", "id", getPackageName()))).setText(names[4]);
        ((TextView)findViewById(getResources().getIdentifier("rossname", "id", getPackageName()))).setText(names[5]);
    }
}
