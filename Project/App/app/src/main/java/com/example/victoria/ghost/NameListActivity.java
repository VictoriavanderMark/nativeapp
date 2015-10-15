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

public abstract class NameListActivity extends Activity {


    protected String selectedName;
    protected ImageButton delete_button;
    protected ImageButton choose_button;
    private int positionSelected;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private ArrayList<Player> players = new ArrayList<Player>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

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


    private void readNames(){
        playerNames = new ArrayList<String>();
            players = getStoredPlayers();
            for(Player p: players) {
                String name = p.getName();
                playerNames.add(name);
            }
            updateNames();
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Player> getStoredPlayers() {
        try {
            FileInputStream fis = openFileInput(
                    getResources().getString(R.string.leaderboard_sourcefile));
            ObjectInputStream ois = new ObjectInputStream(fis);

            players = (ArrayList<Player>) ois.readObject();
            ois.close();
            fis.close();
            return players;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Player>();
    }

    private void updateNames() {
        ListViewAdapter adapter = new ListViewAdapter(this, players);
        ListView nameList = (ListView) findViewById(R.id.nameview);
        nameList.setAdapter(adapter);
        storeModifiedPlayers();
    }

    private void storeModifiedPlayers() {
        try {
            FileOutputStream fos = openFileOutput(
                    getResources().getString(R.string.leaderboard_sourcefile),
                    Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initialiseLayout() {
        selectedName = "";
        initialiseButtons();
        setListeners();
    }

    protected void initialiseButtons(){}

    private void setListeners() {
        final ListView nameView = (ListView) findViewById(R.id.nameview);
        nameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (selectedName.equals(playerNames.get(pos))) {
                    unselectName(pos, nameView);
                } else if (selectedName.length() > 0) {
                    unselectView(nameView);
                    selectName(pos, nameView);
                } else {
                    selectName(pos, nameView);
                }
            }
        });
    }

    private void unselectName(int pos, ListView nameView) {
        selectedName = "";
        nameView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
        hideButtons();
    }

    private void unselectView(ListView nameView) {
        nameView.getChildAt(positionSelected).setBackgroundColor(Color.TRANSPARENT);
        selectedName = "";
        positionSelected = -1;
        hideButtons();
    }

    private void selectName(int pos, ListView nameView) {
        selectedName = playerNames.get(pos);
        positionSelected = pos;
        nameView.getChildAt(pos).setBackgroundColor(Color.rgb(189, 189, 189));
        showButtons();
    }

    protected void hideButtons(){}

    protected void showButtons(){}

    public void enterNewName(View v) {
        final EditText nameInput = new EditText(this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(nameInput)
                .setMessage(R.string.enter_player)
                .setPositiveButton(R.string.create_player, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        processEnteredName(nameInput);
                    }
                })
                .setNegativeButton(R.string.cancel_player, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

        builder.show();
    }

    private void processEnteredName(EditText nameInput) {
        String addedName = nameInput.getText().toString().toUpperCase();

        if (addedName.length() == 0) {
            showToast(getResources().getString(R.string.no_name_entered));
        } else if (playerNames.contains(addedName)) {
            showToast(getResources().getString(R.string.existing_name_entered));
        } else {
            createPlayer(addedName);
        }
    }

    private void createPlayer(String addedName) {
        Player newPlayer = new Player(addedName);
        players.add(newPlayer);
        playerNames.add(addedName);
        updateNames();
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    public void removeName(View v) {
        hideButtons();
        playerNames.remove(selectedName);
        for(Player p:players) {
            if(p.getName().equals(selectedName)) {
                players.remove(p);
                break;
            }
        }
        selectedName = "";
        updateNames();
    }
}
