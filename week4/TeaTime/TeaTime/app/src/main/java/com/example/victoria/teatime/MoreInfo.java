package com.example.victoria.teatime;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MoreInfo extends Activity{

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Intent activityCalled = getIntent();
        type = activityCalled.getExtras().getString("Type");
        setText();

    }


    public void setText(){
        TextView info = (TextView) findViewById(R.id.info);
        info.setText("In the future, more information about " + type + " Tea can be found here!");
    }
}
