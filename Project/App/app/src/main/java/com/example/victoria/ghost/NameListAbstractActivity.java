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

public abstract class NameListAbstractActivity extends Activity {


    protected String selectedPlayer;
    private ArrayList<String> names = new ArrayList<String>();
    protected ImageButton delete;
    protected ImageButton choose;
    private int selectedPos;
    private ArrayList<Player> players = new ArrayList<Player>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        readNames();
        initialiseLayout();
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

    protected void initialiseLayout() {
        initialiseButtons();
        selectedPlayer = "";
        final ListView nameView = (ListView) findViewById(R.id.nameview);
        nameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (selectedPlayer.equals(names.get(pos))) {
                    unselectName(pos, nameView);
                } else if (selectedPlayer.length()>0) {
                    unselect(nameView);
                    selectName(pos, nameView);

                } else {
                    selectName(pos, nameView);
                }
            }
        });
    }




    protected void initialiseButtons(){}
    protected void hideButtons(){}
    protected void showButtons(){};

    private void unselectName(int pos, ListView nameView) {
        selectedPlayer = "";
        nameView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
        hideButtons();
    }

    private void unselect(ListView nameView) {
        nameView.getChildAt(selectedPos).setBackgroundColor(Color.TRANSPARENT);
        selectedPlayer = "";
        selectedPos = -1;
        hideButtons();
    }


    private void selectName(int pos, ListView nameView) {
        selectedPlayer = names.get(pos);
        selectedPos = pos;
        nameView.getChildAt(pos).setBackgroundColor(Color.rgb(189, 189, 189));
        showButtons();
    }

    private void readNames(){
        try {
            FileInputStream fis = openFileInput(getResources().getString(R.string.leaderboard_sourcefile));
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

    private void updateNames() {

        ListViewAdapter adapter = new ListViewAdapter(this, players);



        ListView nameList = (ListView) findViewById(R.id.nameview);

        nameList.setAdapter(adapter);


        try {
            FileOutputStream fos = openFileOutput(getResources().getString(R.string.leaderboard_sourcefile), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addName(View v) {
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
                        } else {
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
        names.remove(selectedPlayer);
        // remove from players array
        for(Player p:players) {
            if(p.getName().equals(selectedPlayer)) {
                players.remove(p);
                break;
            }
        }
        updateNames();
        selectedPlayer = "";
        hideButtons();
    }
}
