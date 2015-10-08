package com.example.victoria.ghost;

import java.io.Serializable;

/**
 * Created by victoria on 8-10-15.
 */
public class Player implements Serializable{

    private String name;
    private int score;

    Player(String name) {
        this.name = name;
        score = 0;
    }

    public void updateScore() {
        score++;
    }

    public String getName(){
        return name;
    }

    public int getScore() {return score;}
}
