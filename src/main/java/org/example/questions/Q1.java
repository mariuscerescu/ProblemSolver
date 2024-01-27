package org.example.questions;

import org.apache.lucene.util.packed.DirectMonotonicReader;
import org.example.Problem;
import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;

import java.util.Scanner;

public class Q1 {

    private final String question = "Ce se caută în această problemă?";
    private String userAnswer;
    private MetaDataExtractor userAnswerMetaData;

    public void start(Problem problem){

        Scanner scanner = new Scanner(System.in);

        System.out.println(question);

        userAnswer = scanner.nextLine();

        userAnswerMetaData = new MetaDataExtractor(BinPosRoRunner.runTextAnalysis(userAnswer));

    }


}
