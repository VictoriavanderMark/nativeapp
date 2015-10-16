/*
 *      Project: Ghost
 *      File: ListViewAdapter.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
  * This ListViewAdapter extends the normal adapter, but adding a second value to each row.
  */
public class ListViewAdapter extends BaseAdapter {

    public ArrayList<Player> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    /*
     * The adapter is created, given the activity that uses it and the array list of Players
     * it will have to process
     */
    ListViewAdapter(Activity activity, ArrayList<Player> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    /*
     * Returns the amount of Players to be shown
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /*
     * Returns the Player at the given position
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /*
     * Returns the Id of the item clicked on. Since this is never used, value is 0.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*
     * For each row (each player), the information about its name and highscore
     * is retrieved, which will be shown in the ListView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.colum_row, parent, false);

            txtFirst=(TextView) convertView.findViewById(R.id.name);
            txtSecond=(TextView) convertView.findViewById(R.id.score);

        }

        Player player=list.get(position);
        txtFirst.setText(player.getName());
        txtSecond.setText(Integer.toString(player.getScore()));

        return convertView;
    }
}
