package org.example.operatorPredictor;

import org.example.utils.QuestionFinder;
import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        String originalProblem = "total";
        String lowerCaseProblem = originalProblem.toLowerCase();
        String processedProblem = removePunctuation(lowerCaseProblem);

        MathWordProblem mathWordProblem = BinPosRoRunner.runTextAnalysis(processedProblem);

        String problemQuestion = QuestionFinder.getQuestion(processedProblem);
        String problemBody = "";

        if (problemQuestion != null) {
            problemBody = processedProblem.replace(problemQuestion, "");

        }else{
            System.out.println("The question was not found!");
        }

        System.out.println(CategoryClassification.getClassification(mathWordProblem));

        System.out.println(mathWordProblem.getAllTokens());

    }

    private static String removePunctuation(String text) {
        Pattern punctuationPattern = Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]");
        return punctuationPattern.matcher(text).replaceAll("");
    }

}
