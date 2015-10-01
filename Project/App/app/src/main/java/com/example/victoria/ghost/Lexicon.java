package com.example.victoria.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;


public class Lexicon {

    private HashSet<String> lexicon = new HashSet<String>();

    private ArrayList<String> filtered = new ArrayList<String>();
    private String currentGuess;
    private String resultWord;

    Lexicon(Context context, String sourcePath ) {
        this.currentGuess = "";

        try {
            InputStream words = context.getApplicationContext().getAssets().open(sourcePath);
            getWords(words);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getFiltered() {
        return this.filtered;
    }

    public void getWords(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        String lastWord = "potato";
        try {
            while((line = reader.readLine()) != null) {
                if(line.length()>3 & !line.startsWith(lastWord)) {
                    lexicon.add(line);
                    lastWord = line;
                }
            }
            System.out.println("DICTIONARY LOADED " + lexicon.size());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastGuess() {
        return currentGuess;

    }

    public void filter(String input) {
        currentGuess = input;

        if( filtered.size()==0) {
            for (String f : lexicon) {
                if (f.matches("(?i)" + input + ".*")) {
                    filtered.add(f);
                }
            }
        } else {
            ArrayList<String> temp = new ArrayList<String>();
            for(String f: filtered) {
                temp.add(f);
            }

            for (String t : temp) {
                if (!(t.matches(input + ".*"))) {
                    filtered.remove(t);
                }
            }
        }

       

        System.out.println("COUNT: " + filtered.size());
    }

    public void setResultWord(String result) {
        resultWord = result;
    }

    public int count() {
        int numWords = filtered.size();
        return  numWords;

    }

    public String result() {
        return resultWord;
    }

    public void reset() {
        filtered = new ArrayList<String>();
    }

}
