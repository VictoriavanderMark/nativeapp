package com.example.victoria.ghost;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class PlayerListActivity extends NameListActivity {

    public void initialiseButtons() {
        delete_button = (ImageButton) findViewById(R.id.delete);
        choose_button = (ImageButton) findViewById(R.id.choose);
    }

    public void showButtons() {
        delete_button.setVisibility(View.VISIBLE);
        choose_button.setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        delete_button.setVisibility(View.INVISIBLE);
        choose_button.setVisibility(View.INVISIBLE);
    }

    public void choosePlayer(View v) {
        Intent playerChosenIntent = new Intent(this, ChoosePlayersActivity.class);
        playerChosenIntent.putExtra("Name", selectedName);
        setResult(RESULT_OK, playerChosenIntent);
        finish();
    }
}
