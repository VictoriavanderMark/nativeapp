package com.example.victoria.ghost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoria on 5-10-15.
 */
public class ChoosePlayer extends Activity{

    private ArrayList<String> names = new ArrayList<String>();
    private String selected;
    private int selectedPos;
    private ImageButton delete;
    private ImageButton choose;
    private ArrayAdapter<String> nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        delete = (ImageButton) findViewById(R.id.delete);
        choose = (ImageButton) findViewById(R.id.choose);

        final ListView nameView = (ListView) findViewById(R.id.nameview);
        readNames();

        selected = "";

        nameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (selected.equals(names.get(pos))) {
                    unselectName(pos, nameView);
                } else {
                    unselect(nameView);
                    selectName(pos, nameView);

                }
            }
        });


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

    public void selectName(int pos, ListView nameView) {
        selected = names.get(pos);
        selectedPos = pos;
        nameView.getChildAt(pos).setBackgroundColor(Color.rgb(189, 189, 189));
        delete.setVisibility(View.VISIBLE);
        choose.setVisibility(View.VISIBLE);
    }

    public void unselectName(int pos, ListView nameView) {
        selected = "";
        nameView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
        delete.setVisibility(View.INVISIBLE);
        choose.setVisibility(View.INVISIBLE);
    }

    public void unselect(ListView nameView) {
        nameView.getChildAt(selectedPos).setBackgroundColor(Color.TRANSPARENT);
        selected = "";
        selectedPos = -1;
        delete.setVisibility(View.INVISIBLE);
        choose.setVisibility(View.INVISIBLE);
    }

    public void readNames(){
        try {
            String FILENAME = "names";
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;

            while((line = reader.readLine())!=null){
                names.add(line);
            }

            updateNames();

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateNames() {
        nameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        ListView nameList = (ListView) findViewById(R.id.nameview);
        nameList.setAdapter(nameAdapter);

        try {
            FileOutputStream fos = openFileOutput("names", Context.MODE_PRIVATE);
            for (String s : names) {
                fos.write((s + "\n").getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addName(View v) {
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input)
                .setMessage(R.string.enter_player)
                .setPositiveButton(R.string.create_player, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String addedName = input.getText().toString();
                        if (!(names.contains(addedName))) {
                            names.add(addedName);
                            updateNames();
                        } else {
                            Toast.makeText(getApplicationContext(),"Already exists!",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                })
                .setNegativeButton(R.string.cancel_player, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        builder.show();
    }

    public void removeName(View v) {
        names.remove(selected);
        updateNames();
        selected = "";
        delete.setVisibility(View.INVISIBLE);
        choose.setVisibility(View.INVISIBLE);
    }

    public void choosePlayer(View v) {
        Intent playerChosen = new Intent(this, ChoosePlayers.class);
        playerChosen.putExtra("Name", selected);
        setResult(RESULT_OK, playerChosen);
        finish();

    }

}
