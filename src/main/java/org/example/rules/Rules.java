package org.example.rules;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;
import org.example.utils.PhraseSplitterOnVerbs;
import org.example.utils.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;

public class Rules {

    public ArrayList<String> sentences;
    public ArrayList<ArrayList<String>> tokens = new ArrayList<>();

    private MathWordProblem mathWordProblem;

    public Rules(MathWordProblem mathWordProblem){
        PhraseSplitterOnVerbs splitter = new PhraseSplitterOnVerbs(mathWordProblem);
        this.sentences = splitter.getSentences();
        for(String s: sentences){
            System.out.println(s);
        }
        setTokens();
    }

    private void setTokens(){
        Tokenizer tokenizer = new Tokenizer();

        for(String sentence : sentences){
            tokens.add(tokenizer.getTokens(sentence));
        }

    }

    //Dacă între două numere din aceeași propoziție se află „și” acele două numere trebuie adunate
    public String rule1(){

        boolean firstNrFound;
        boolean siWasFound = false;

        int firstNr = 0, secondNr;

        for (ArrayList<String> sentence : tokens) {

            firstNrFound = false;

            for (String token : sentence) {

                if (token.matches("\\d+")) {
                    if (!firstNrFound) {
                        firstNrFound = true;
                        firstNr = Integer.parseInt(token);
                        continue;
                    }

                    if (siWasFound) {
                        secondNr = Integer.parseInt(token);
                        return "(" + firstNr + " + " + secondNr + ")";
                    }
                }

                if (token.equals("și")) {
                    siWasFound = true;
                }

            }
        }

        return null;
    }

    public static void main(String[] args) {

        String sentence = "Un ceasornicar a reparat 9 ceasuri? Trebuie să repare încă 15 ceasuri de mână și 7 ceasuri de perete. Câte comenzi de reparație? avea ceasornicarul?";

        Rules rules = new Rules(BinPosRoRunner.runTextAnalysis(sentence));

        System.out.println(rules.rule1());
    }

}
