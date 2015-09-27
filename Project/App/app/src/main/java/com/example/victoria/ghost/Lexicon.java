package com.example.victoria.ghost;

import java.util.ArrayList;

/**
 * Created by victoria on 27-9-15.
 */
public class Lexicon {

    private String[] lexicon;
    private ArrayList<String> filtered = new ArrayList<String>();
    private String resultWord;

    Lexicon(String sourcePath) {

    }

    Lexicon(String[] lexicon) {
        this.lexicon = lexicon;

    }


    public void filter(String input) {
        if( filtered.size()==0) {
            System.out.println("HIJ IS LEEG");
            for (String f : lexicon) {
                if (f.matches("(?i)" + input + ".*")) {
                    System.out.println("Deze " + input + " matcht met " + f);
                    filtered.add(f);
                } else {
                    System.out.println("Deze " + input + " matcht niet met " + f);

                }
            }
        } else {
            System.out.println("HIJ IS NIET LEEG");
            for (String f : filtered) {
                if (!(f.matches(input + ".*"))) {
                    filtered.remove(f);
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
