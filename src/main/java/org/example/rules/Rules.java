package org.example.rules;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;
import org.example.utils.PhraseSplitterOnVerbs;
import org.example.utils.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;

public class Rules {

    private ArrayList<String> sentences;
    private ArrayList<ArrayList<String>> tokensPerSentence;
    private ArrayList<ArrayList<String>> msdPerSentence;

    private MathWordProblem mathWordProblem;

    public Rules(MathWordProblem mathWordProblem){
        this.mathWordProblem = mathWordProblem;
        PhraseSplitterOnVerbs splitter = new PhraseSplitterOnVerbs(mathWordProblem);
        this.sentences = splitter.getSentences();
        for(String s: sentences){
            System.out.println(s);
        }
        setTokensAndMSDPerSentence();
    }

    public void setTokensAndMSDPerSentence(){
        StringBuilder builder = new StringBuilder();
        for(String sentence : sentences){
            builder.append(sentence).append(" ");
        }
        MathWordProblem analyzedSentences = BinPosRoRunner.runTextAnalysis(builder.toString());
        tokensPerSentence = analyzedSentences.getTokensPerSentence();
        msdPerSentence = analyzedSentences.getMSDPerSentence();
    }


    //Dacă între două numere din aceeași propoziție se află „și” acele două numere trebuie adunate
    public String rule1(){

        boolean firstNrFound;
        boolean siWasFound = false;

        int firstNr = 0, secondNr;

        for (ArrayList<String> sentence : tokensPerSentence) {

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

        String sentence = "M-am gândit la un număr. Am împărțit sfertul lui în jumătate,  am triplat numărul obținut. Am mărit rezultatul cu 2 și am obținut 8. La ce număr m-am gândit?";

        Rules rules = new Rules(BinPosRoRunner.runTextAnalysis(sentence));

        System.out.println(rules.rule1());
    }

}
