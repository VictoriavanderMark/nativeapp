package com.example.victoria.teatime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private ArrayList<String> teas = new ArrayList<String>();
    private ArrayAdapter<String> teaAdapter;
    private int selected; //TODO: array selected
    ImageButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView teaView = (ListView) findViewById(R.id.listview);
        selected = -1;
        readTeas();


        teaView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int pos, long id) {
                selectTea(pos, teaView);
                return true;

            }
        });

        teaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                if(selected == pos) {
                    unselectTea(pos, teaView);
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectTea(int pos, ListView teaView) {
        selected = pos;
        teaView.getChildAt(pos).setBackgroundColor(Color.rgb(189,189,189));

        delete = (ImageButton) findViewById(R.id.delete);
        delete.setVisibility(View.VISIBLE);



    }


    public void unselectTea(int pos, ListView teaView){
        selected = -1;
        teaView.getChildAt(pos).setBackgroundColor(Color.TRANSPARENT);
        delete = (ImageButton) findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);


    }


    public void readTeas() {
        try {
            String FILENAME = "tea";
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;


            while((line = reader.readLine()) != null) {
                teas.add(line);
            }


            updateTeas();


            fis.close();



        } catch(IOException  e) {
            e.printStackTrace();
        }
    }


    public void updateTeas() {
        teaAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, teas);

        ListView teaList = (ListView) findViewById(R.id.listview);
        teaList.setAdapter(teaAdapter);

        try {
            FileOutputStream fos = openFileOutput("tea", Context.MODE_PRIVATE);
            for (String s : teas) {
                fos.write((s + "\n").getBytes());
            }

            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void addTea(View view) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        builder .setView(input)
                .setMessage(R.string.enter_tea)

                .setPositiveButton(R.string.create_tea, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String addedTea = input.getText().toString();
                        teas.add(addedTea);
                        updateTeas();
                    }
                })
                .setNegativeButton(R.string.cancel_tea, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


        builder.show();

    }

    public void removeTea(View v) {
        teas.remove(selected);
        updateTeas();
        selected = -1;
        delete = (ImageButton) findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);

    }




















//    public void makeList() {
//        String[] teas = {"rooibos", "kamille", "groen", "engels"};
//        try {
//            FileOutputStream fos = openFileOutput("tea", Context.MODE_PRIVATE);
//            for (String s : teas) {
//                System.out.println("hoi " + s);
//                fos.write((s + "\n").getBytes());
//            }
//
//            fos.close();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }


}
