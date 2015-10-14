package com.example.victoria.ghost;

import java.io.Serializable;


public class Player implements Serializable{

    private int score;
    private String name;

    Player(String name) {
        score = 0;
        this.name = name;
    }

    public int getScore() {return score;}


    public void updateScore() {
        score++;
    }

    public String getName(){ return name; }

}
