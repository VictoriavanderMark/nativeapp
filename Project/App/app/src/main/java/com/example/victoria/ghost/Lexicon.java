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
import java.util.Iterator;
import java.util.Set;


public class Lexicon {

    private Set<String> lexicon = new HashSet<String>();

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
                if(line.length()>3 & !(line.startsWith(lastWord))) {
                    lexicon.add(line);
                    lastWord = line;

                }
            }







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
//            ArrayList<String> temp = new ArrayList<String>();
//            for(String f: filtered) {
//                temp.add(f);
//            }
//
//
//            for (String t : temp) {
//                if (!(t.startsWith(input.toLowerCase()))) {
//
//                    filtered.remove(t);
//                }
//            }



//            for(int i = filtered.size() -1; i>= 0; i--) {
//                if(!(filtered.get(i).startsWith(input.toLowerCase()))){
//                    filtered.remove(filtered.get(i));
//                }
//            }



            String word;
            for(int i = 0; i<filtered.size(); i++) {
                word = filtered.get(i);

                if(!(word.startsWith(input.toLowerCase()))){
                    filtered.remove(word);
                    i--;
                }
            }


//            for(Iterator<String> it = filtered.iterator(); it.hasNext();) {
//                String s = it.next();
//                if(!(s.startsWith(input.toLowerCase()))){
//                    it.remove();
//                }
//            }


        }

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
