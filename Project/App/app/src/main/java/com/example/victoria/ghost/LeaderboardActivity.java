package com.example.victoria.ghost;

import android.view.View;
import android.widget.ImageButton;

public class LeaderboardActivity extends NameListAbstractActivity {

    public void initialiseButtons() {
        delete = (ImageButton) findViewById(R.id.delete);

    }
    public void showButtons() {
        delete.setVisibility(View.VISIBLE);
    }

    public void hideButtons() {
        delete.setVisibility(View.INVISIBLE);

    }

}
