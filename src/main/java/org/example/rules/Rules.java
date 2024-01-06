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
    private ArrayList<String> tokens;

    public StringBuilder equation = new StringBuilder();

    public Rules(MathWordProblem mathWordProblem){
        this.mathWordProblem = mathWordProblem;
        tokens = mathWordProblem.getAllTokens();
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
            builder.append("S: ").append(sentence).append(" ");
        }
        MathWordProblem analyzedSentences = BinPosRoRunner.runTextAnalysis(builder.toString());
        tokensPerSentence = analyzedSentences.getTokensPerSentence();
        msdPerSentence = analyzedSentences.getMSDPerSentence();
    }


    //Dacă între două numere din aceeași propoziție se află „și” acele două numere trebuie adunate
    public void rule1(){
        boolean firstNrFound;
        boolean siWasFound;

        int firstNr = 0, secondNr;

        for (int i = 0; i < tokensPerSentence.size(); i++) {
            firstNrFound = false;
            siWasFound = false;

            for (int j = 0; j < tokensPerSentence.get(i).size(); j++) {
                String token = tokensPerSentence.get(i).get(j);

                if (msdPerSentence.get(i).get(j).equals("M")
                        && ((j < tokensPerSentence.get(i).size() - 1 && msdPerSentence.get(i).get(j + 1).equals("Ncfprn") || msdPerSentence.get(i).get(j + 1).equals("Ncmprn"))
                        || (j < tokensPerSentence.get(i).size() - 2 && msdPerSentence.get(i).get(j + 2).equals("Ncfprn") || msdPerSentence.get(i).get(j + 2).equals("Ncmprn")))) {

                    if (!firstNrFound) {
                        firstNrFound = true;
                        firstNr = Integer.parseInt(token);
                        continue;
                    }

                    if (siWasFound) {
                        secondNr = Integer.parseInt(token);
                        equation.append("(").append(firstNr).append(" + ").append(secondNr).append(")");
                    }
                }

                if (token.equals("și")) {
                    siWasFound = true;
                }
            }
        }
    }


    public static void main(String[] args) {

        String sentence = "Ronț are 6 morcovi. El are de 3 ori mai puțini morcovi decât Cronț. Câți morcovi are Cronț?";

        Rules rules = new Rules(BinPosRoRunner.runTextAnalysis(sentence));

        rules.rule1();
        System.out.println(rules.equation);
    }

}
