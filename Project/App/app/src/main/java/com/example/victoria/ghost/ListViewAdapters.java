package com.example.victoria.ghost;

/**
 * Created by victoria on 8-10-15.
 */

import static com.example.victoria.ghost.Constants.FIRST_COLUMN;
import static com.example.victoria.ghost.Constants.SECOND_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapters extends BaseAdapter {

    public ArrayList<Player> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    ListViewAdapters(Activity activity,ArrayList<Player> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.colum_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.name);
            txtSecond=(TextView) convertView.findViewById(R.id.score);

        }

        Player player=list.get(position);
        txtFirst.setText(player.getName());
        txtSecond.setText(Integer.toString(player.getScore()));

        return convertView;
    }
}
