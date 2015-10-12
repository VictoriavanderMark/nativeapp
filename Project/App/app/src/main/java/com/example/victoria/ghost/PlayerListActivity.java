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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by victoria on 5-10-15.
 */
public class PlayerListActivity extends Activity{

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private String selected;
    private int selectedPos;
    private ImageButton delete;
    private ImageButton choose;
    private ListViewAdapter adapter;

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
                } else if (selected.length()>0) {
                    unselect(nameView);
                    selectName(pos, nameView);

                } else {
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent showSettingsIntent = new Intent(this, SettingsActivity.class);
            this.startActivity(showSettingsIntent);

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
            FileInputStream fis = openFileInput("leaderboard.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            players = (ArrayList<Player>) ois.readObject();
            names = new ArrayList<String>();
            for(Player p: players) {
                String name = p.getName();
                names.add(name);
            }
            ois.close();

            updateNames();

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void updateNames() {

        adapter = new ListViewAdapter(this, players);


        ListView nameList = (ListView) findViewById(R.id.nameview);
        nameList.setAdapter(adapter);

        try {
            FileOutputStream fos = openFileOutput("leaderboard.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();


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
                        String addedName = input.getText().toString().toUpperCase();
                        if (addedName.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Please enter a name",
                                    Toast.LENGTH_LONG).show();

                        } else if (!(names.contains(addedName))) {
                            names.add(addedName);
                            Player newPlayer = new Player(addedName);
                            players.add(newPlayer);
                            updateNames();
                        } else

                        {
                            Toast.makeText(getApplicationContext(), "Already exists!",
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
        // remove from players array
        for(Player p:players) {
            if(p.getName().equals(selected)) {
                players.remove(p);
                break;
            }
        }
        updateNames();
        selected = "";
        delete.setVisibility(View.INVISIBLE);
        choose.setVisibility(View.INVISIBLE);
    }

    public void choosePlayer(View v) {
        Intent playerChosenIntent = new Intent(this, ChoosePlayersActivity.class);
        playerChosenIntent.putExtra("Name", selected);
        setResult(RESULT_OK, playerChosenIntent);
        finish();

    }

}
