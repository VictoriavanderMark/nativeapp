package com.example.victoria.ghost;

import android.view.View;
import android.widget.ImageButton;

public class LeaderboardActivity extends NameListActivity {

    public void initialiseButtons() {
        delete_button = (ImageButton) findViewById(R.id.delete);

    }
    public void showButtons() {
        delete_button.setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        delete_button.setVisibility(View.INVISIBLE);

    }

}
