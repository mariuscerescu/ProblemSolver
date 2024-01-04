package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        MathWordProblem mathWordProblem = BinPosRoRunner.runTextAnalysis("M-am gândit la un număr. Am împărțit sfertul lui în jumătate, apoi am triplat numărul obținut. Am mărit rezultatul cu 2 și am obținut 8. La ce număr m-am gândit?");

        ArrayList<ArrayList<String>> tokensPerSentence = mathWordProblem.getTokensPerSentence();

        for(String token : tokensPerSentence.get(2)){
            System.out.println(token);
        }

    }
}
