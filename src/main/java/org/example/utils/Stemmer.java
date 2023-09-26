package org.example.utils;
import org.tartarus.snowball.ext.RomanianStemmer;

public class Stemmer {

    private RomanianStemmer stemmer;

    public Stemmer(){
        stemmer = new RomanianStemmer();
    }

    public String getStem(String word){

        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();

    }

}
