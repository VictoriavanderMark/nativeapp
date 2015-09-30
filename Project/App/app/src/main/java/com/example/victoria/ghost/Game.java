package com.example.victoria.ghost;


public class Game {

    private Lexicon lexicon;
    private boolean P1turn;
    private String currentGuess;


    Game(Lexicon lexicon) {
        this.lexicon = lexicon;
        P1turn = true;
        currentGuess = "";
    }

    public void guess(String guess) {
        String lastGuess = lexicon.getLastGuess();
        currentGuess = lastGuess+guess;
        System.out.println("Current Word: " + currentGuess);
        lexicon.filter(currentGuess);
        P1turn = !P1turn;
        System.out.println("---------------");
    }

    public boolean turn() {
        return P1turn;
    }

    public boolean ended() {
        if(currentGuess.length() > 3) {
            int numPossible = lexicon.count();

            if (numPossible == 0 || lexicon.getFiltered().contains(currentGuess)) {
                lexicon.setResultWord(currentGuess);
                return true;
            } else if (numPossible == 1) {
                if (lexicon.result().equals(currentGuess)) {
                    lexicon.setResultWord(currentGuess);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean winner() {
        return P1turn;
    }
}
