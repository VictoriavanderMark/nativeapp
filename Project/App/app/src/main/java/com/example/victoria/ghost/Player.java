/*
 *      Project: Ghost
 *      File: Player.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import java.io.Serializable;


/**
 * Creates a Player object, containing a name and its amount of games won.
 */
public class Player implements Serializable{

    private int score;
    private String name;

    /*
     * Creates a new player with a given name, starting with score zero.
     */
    Player(String name) {
        score = 0;
        this.name = name;
    }

    /*
     * Returns the amount of games the player won.
     */
    public int getScore() {return score;}

    /*
     * Adds one to the player's score.
     */
    public void updateScore() {
        score++;
    }

    /*
     * Returns the name of the player.
     */
    public String getName(){ return name; }
}
