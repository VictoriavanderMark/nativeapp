/*
 *      Project: Ghost
 *      File: NameListActivity.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

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


/*
 * The NameListActivity class creates a list, showing player names and their scores.
 * Depending on the child that extends it, certain actions can be performed after selecting
 * a player.
 */
public abstract class NameListActivity extends Activity {

    protected String selectedName;
    protected ImageButton delete_button;
    protected ImageButton choose_button;
    private int positionSelected;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private ArrayList<Player> players = new ArrayList<Player>();

    /*
     * On creating, the stored names are retrieved and the initial layout is set.
     */
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

    /*
     * The list of players is saved within the activity and the layout is updated
     * accordingly
     */
    private void readNames(){
        playerNames = new ArrayList<String>();
            players = getStoredPlayers();
            for(Player p: players) {
                String name = p.getName();
                playerNames.add(name);
            }
            updateNames();
    }

    /*
     * Reads the players and their scores from the file storing the leaderboard.
     */
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


    /*
     * After changes are made, the ListView is updated with the appertunant adapter and
     * the modified set of players is saved.
     */
    private void updateNames() {
        ListViewAdapter adapter = new ListViewAdapter(this, players);
        ListView nameList = (ListView) findViewById(R.id.nameview);
        nameList.setAdapter(adapter);
        storeModifiedPlayers();
    }

    /*
     * Saves the set of modified player to the given file.
     */
    private void storeModifiedPlayers() {
        try {
            FileOutputStream fos = openFileOutput(
                    getResources().getString(R.string.leaderboard_sourcefile), Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Defines the layout in which the Activity starts. When (un)selecting
     * a player, this layout will be modified.
     */
    protected void initialiseLayout() {
        selectedName = "";
        initialiseButtons();
        setListeners();
    }

    /*
     * Extra buttons can be set visibile, depending on the class that extends this
     * Activity
     */
    protected void initialiseButtons(){}

    /*
     * Sets the listeners for the items in the list. An item is selected when clicked on and
     * deselected when clicked on again (or when another item is clicked)
     */
    private void setListeners() {
        final ListView nameView = (ListView) findViewById(R.id.nameview);
        nameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (selectedName.equals(playerNames.get(pos))) {
                    unselect(pos, nameView);
                } else if (selectedName.length() > 0) {
                    unselect(nameView);
                    selectName(pos, nameView);
                } else {
                    selectName(pos, nameView);
                }
            }
        });
    }

    /*
     * Unselects the currently selected item within the listView
     */
    private void unselect(ListView nameView) {
        positionSelected = -1;  // No position selected, value needed for comparisons
        unselect(positionSelected, nameView);
    }

    /*
     * Unselects a specific name within the listView
     */
    private void unselect(int pos, ListView nameView) {
        selectedName = "";
        nameView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
        hideButtons();
    }

    /*
     * Selects a specific name within the listView
     */
    private void selectName(int pos, ListView nameView) {
        selectedName = playerNames.get(pos);
        positionSelected = pos;
        nameView.getChildAt(pos).setBackgroundColor(Color.rgb(189, 189, 189));
        showButtons();
    }

    /*
     * When selecting an item, buttons may be hidden depending on the class that extends
     * the Activity
     */
    protected void hideButtons(){}

    /*
     * When selecting an item, buttons may be hidden depending on the class that extends
     * the Activity
     */
    protected void showButtons(){}

    /*
     * When the button for adding a new player is clicked on, a dialog window appears,
     * in which the user can enter the name of the player he wants to create.
     */
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

    /*
     * When the name of a new player is entered, it is only accepted if the name doesn't exist
     * yet, and contains a minimum of one letters.
     */
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

    /*
     * Shows a toast containing a given text.
     */
    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Adds a player to the game and the stored set of existing players.
     */
    private void createPlayer(String addedName) {
        Player newPlayer = new Player(addedName);
        players.add(newPlayer);
        playerNames.add(addedName);
        updateNames();
    }

    /*
     * Removes a player from the game and the stored set of existing players.
     */
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
