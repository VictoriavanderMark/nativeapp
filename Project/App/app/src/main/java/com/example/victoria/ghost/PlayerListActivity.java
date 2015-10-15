package com.example.victoria.ghost;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class PlayerListActivity extends NameListAbstractActivity {

    public void initialiseButtons() {
        delete = (ImageButton) findViewById(R.id.delete);
        choose = (ImageButton) findViewById(R.id.choose);
    }

    public void showButtons() {
        delete.setVisibility(View.VISIBLE);
        choose.setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        delete.setVisibility(View.INVISIBLE);
        choose.setVisibility(View.INVISIBLE);
    }

    public void choosePlayer(View v) {
        Intent playerChosenIntent = new Intent(this, ChoosePlayersActivity.class);
        playerChosenIntent.putExtra("Name", selectedPlayer);
        setResult(RESULT_OK, playerChosenIntent);
        finish();

    }

}
