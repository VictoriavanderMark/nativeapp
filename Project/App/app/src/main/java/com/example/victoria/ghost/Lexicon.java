/*
 *      Project: Ghost
 *      File: Lexicon.java
 *      Date: 16 October 2015
 *
 *      Author: Victoria van der Mark
 *      StudentNo: 10549544
 */

package com.example.victoria.ghost;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


/**
  * The Lexicon object holds the lexicon used in the game, which keeps being filtered by
  * the word that the players are forming.
  */
public class Lexicon {

    private Set<String> lexicon = new HashSet<String>();
    private Set<String> filtered = new HashSet<String>();
    private String lastGuess;

    /*
     * Constructs a Lexicon object, based on the context of the game Activity and the
     * path leading to the lexicon file.
     */
    Lexicon(Context context, String sourcePath) {
        getWords(context, sourcePath);
        this.lastGuess = "";
    }

    /*
     * Reads all the words from the sourcefile, creating a lexicon.
     * To make this progress faster, only words containing 3 or more letters are read (since
     * they only count as words from 4 letters on).
     * Additionally, words that are an extension of another word are also ignored, since they
     * can never be spelled by the players.
     */
    private void getWords(Context context, String sourcePath) {
        try {
            InputStream is = context.getApplicationContext().getAssets().open(sourcePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            String lastAddedWord = "none"; // Placeholder by which the first word never begins
                while((line = reader.readLine()) != null) {
                    if(line.length() > 3 & !(line.startsWith(lastAddedWord))) {
                        lexicon.add(line);
                        lastAddedWord = line;
                    }
                }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the amount of words that could still be created by the players, given
     * the word spelled so far.
     */
    public int count() { return filtered.size(); }

    /*
     * Returns the last "word so far" that was processed by the lexicon.
     */
    public String getLastGuess() { return lastGuess; }

    /*
     * Returns the set of words that could still be created by the players, given the
     * word spelled so far.
     */
    public Set<String> getFiltered() { return this.filtered; }

    /*
     * Given the word spelled so far, the lexicon is filtered out, after which only
     * the words that can still be spelled remain in "filtered".
     */
    public void filter(String guessedWord) {
        lastGuess = guessedWord;

        if(filtered.size() == 0) {
            fillFilterFirstTime();
        } else {
            fillFilter();
        }
    }

    /*
     * When the first letter is guessed, the list of filtered out words is still empty,
     * so the words from the lexicon are added if they start with the given letter.
     */
    private void fillFilterFirstTime() {
        for (String l : lexicon) {
            if (l.startsWith(lastGuess.toLowerCase())) {
                filtered.add(l);
            }
        }
    }

    /*
     * If it's not the first time the list of filtered words is created, remove the words
     * that do not start with the word typed so far.
     */
    private void fillFilter() {
        Set<String> temp = new HashSet<String>();
        for(String f: filtered) {
            temp.add(f);
        }
        // A temporary set is used, for not getting indexing issues.
        for (String t : temp) {
            if (!(t.startsWith(lastGuess.toLowerCase()))) {
                filtered.remove(t);
            }
        }
    }
}
