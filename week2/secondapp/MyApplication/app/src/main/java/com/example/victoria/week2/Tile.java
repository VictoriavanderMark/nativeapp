package com.example.victoria.week2;

import android.widget.ImageButton;


public class Tile {

    private int image;
    private boolean set;
    private ImageButton tilePlace;

    public Tile(ImageButton tilePlace){
        this.image = R.drawable.base;
        set = false;
        this.tilePlace = tilePlace;
    }

    public boolean isSet(){
        return this.set;
    }

    public ImageButton getButton() {
        return this.tilePlace;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        System.out.println("first image: " + this.image);
        this.image = image;
        System.out.println("now image: " + this.image);
        tilePlace.setImageResource(image);
        this.set = true;
    }

    public void reset(int image) {
        this.setImage(image);
        this.set = false;
    }
}
