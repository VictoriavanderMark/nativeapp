/*
 *      Project: Ghost
 *      File: Game.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;


/**
  * The Game class creates an object, containing all the important information about
  * the game that is currently being played.
  */
public class Game {

    private String guessedWord;
    private Lexicon lexicon;
    private boolean P1turn;

    /*
     * Creates a new game, given a lexicon of words existing in a specific language.
     */
    Game(Lexicon lexicon) {
        P1turn = true;
        guessedWord = "";
        this.lexicon = lexicon;
    }

    /*
     * Processes the consequence of entering a specific letter for the game.
     */
    public void guess(String guessedLetter) {
        guessedWord = lexicon.getLastGuess() + guessedLetter;
        lexicon.filter(guessedWord);
        P1turn = !P1turn;
    }

    /*
     * Returns a boolean, indicating whether it's Player 1's turn or not (and thus P2's).
     */
    public boolean turn() {
        return P1turn;
    }

    /*
     * Returns the number of legal words that can still be typed, given the entered
     * letters so far.
     */
    public int getNumPossible() {
        return lexicon.count();
    }

    /*
     * Returns a boolean, indicating whether the game has come to and end or not.
     */
    public boolean ended() {
        int numPossible = getNumPossible();
        return (numPossible == 0 || lexicon.getFiltered().contains(guessedWord.toLowerCase()));
    }

    /*
     * Returns a boolean, indicating whether Player 1 won or not (and thus, P1 won).
     * This is based on the assumption that the turn changes after a player made a move.
     * Therefore the player whose turn it is not, made the game end by typing either an
     * existing word or gibberish (and thereby lost).
     */
    public boolean P1winner() { return P1turn; }
}
