package com.example.victoria.friendsr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class SecondScreen extends Activity {

    private String character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.more_info);

        Intent activityCalled = getIntent();

        character = activityCalled.getExtras().getString("Character");

    }

    public void onEnterName(View view) {

        EditText newName = (EditText) findViewById(R.id.name_edit_text);

        String name = String.valueOf(newName.getText());

        Intent goingBack = new Intent();

        goingBack.putExtra("EnteredName", name);
        goingBack.putExtra("Character", character);

        setResult(RESULT_OK, goingBack);

        finish();

    }
}
