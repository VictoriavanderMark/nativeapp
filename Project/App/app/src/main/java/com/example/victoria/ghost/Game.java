package com.example.victoria.ghost;


public class Game {

    private Lexicon lexicon;
    private String currentGuess;
    private boolean P1turn;


    Game(Lexicon lexicon) {
        this.lexicon = lexicon;
        currentGuess = "";
        P1turn = true;
    }

    public void guess(String guess) {
        currentGuess = lexicon.getLastGuess()+guess;
        System.out.println("Current Word: " + currentGuess);

        System.out.println("JA HOI");
        lexicon.filter(currentGuess);
        System.out.println("BEN KLAAR");
        P1turn = !P1turn;

        System.out.println("---------------");
    }

    public boolean turn() {
        return P1turn;
    }

    public int getNumPossible() {
        return lexicon.count();
    }

    public boolean ended() {
            int numPossible = getNumPossible();


            if (numPossible == 0 || lexicon.getFiltered().contains(currentGuess.toLowerCase())) {
                lexicon.setResultWord(currentGuess);
                return true;

            }

        return false;

    }

    public boolean winner() {
        return P1turn;
    }
}
