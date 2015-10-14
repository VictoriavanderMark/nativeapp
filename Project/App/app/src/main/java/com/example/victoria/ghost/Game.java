package com.example.victoria.ghost;

public class Game {

    private Lexicon lexicon;
    private String guessedWord;
    private boolean P1turn;

    Game(Lexicon lexicon) {
        this.lexicon = lexicon;
        guessedWord = "";
        P1turn = true;
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
                lexicon.setResultWord(guessedWord);
                return true;
            }
        return false;
    }

    public boolean P1winner() { return P1turn; }
}
