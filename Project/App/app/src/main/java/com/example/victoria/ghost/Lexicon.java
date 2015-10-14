package com.example.victoria.ghost;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Lexicon {

    private Set<String> lexicon = new HashSet<String>();
    private Set<String> filtered = new HashSet<String>();
    private String lastGuess;

    Lexicon(Context context, String sourcePath) {
        getWords(context, sourcePath);
        this.lastGuess = "";
    }

    public void getWords(Context context, String sourcePath) {
        try {
            InputStream is = context.getApplicationContext().getAssets().open(sourcePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            String lastAddedWord = "none";

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

    public int count() { return filtered.size(); }

    public String getLastGuess() { return lastGuess; }

    public Set<String> getFiltered() { return this.filtered; }


    public void filter(String guessedWord) {
        lastGuess = guessedWord;

        if(filtered.size() == 0) {
            fillFilterFirstTime();
        } else {
            fillFilter();
        }
    }

    public void fillFilterFirstTime() {
        for (String l : lexicon) {
            if (l.startsWith(lastGuess.toLowerCase())) {
                filtered.add(l);
            }
        }
    }

    public void fillFilter() {
        Set<String> temp = new HashSet<String>();
        for(String f: filtered) {
            temp.add(f);
        }
        for (String t : temp) {
            if (!(t.startsWith(lastGuess.toLowerCase()))) {
                filtered.remove(t);
            }
        }
    }
}
