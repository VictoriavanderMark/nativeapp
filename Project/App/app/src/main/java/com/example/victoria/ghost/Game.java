package com.example.victoria.ghost;

public class Game {

    private String guessedWord;
    private Lexicon lexicon;
    private boolean P1turn;

    Game(Lexicon lexicon) {
        P1turn = true;
        guessedWord = "";
        this.lexicon = lexicon;
    }

    public void guess(String guessedLetter) {
        guessedWord = lexicon.getLastGuess() + guessedLetter;
        lexicon.filter(guessedWord);
        P1turn = !P1turn;
    }

    public boolean turn() {
        return P1turn;
    }

    public int getNumPossible() {
        return lexicon.count();
    }

    public boolean ended() {
            int numPossible = getNumPossible();
            if (numPossible == 0 || lexicon.getFiltered().contains(guessedWord.toLowerCase())) {
                return true;
            }
        return false;
    }

    public boolean P1winner() { return P1turn; }
}
