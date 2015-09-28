package com.example.victoria.ghost;

import java.util.ArrayList;
import java.util.HashSet;


public class Lexicon {

    private HashSet<String> lexicon = new HashSet<String>();

    private ArrayList<String> filtered = new ArrayList<String>();
    private String currentGuess;
    private String resultWord;

//    Lexicon(String sourcePath) {
//
//    }

    Lexicon(String lexicon) {
        String[] words = lexicon.split("\\r?\\n");
        for(String s:words) {
            this.lexicon.add(s);
        }
        this.currentGuess="";

    }

    public String getLastGuess() {
        return currentGuess;

    }

    public void filter(String input) {
        currentGuess = input;
        if( filtered.size()==0) {
//            System.out.println("HIJ IS LEEG");
            for (String f : lexicon) {
                if (f.matches("(?i)" + input + ".*")) {
                    filtered.add(f);
                }
            }
        } else {
            //System.out.println("HIJ IS NIET LEEG");

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
        for (String f:filtered){
            System.out.println("Deel van filtered: " + f);
        }
    }

    public int count() {
        int numWords = filtered.size();
        if (numWords == 1){
            resultWord = filtered.get(0);
        }
        return  numWords;

    }

    public String result() {
        return resultWord;
    }

    public void reset() {
        filtered = new ArrayList<String>();
    }

}
