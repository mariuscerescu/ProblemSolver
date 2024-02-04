package org.example.questions;

import org.apache.lucene.util.packed.DirectMonotonicReader;
import org.example.Problem;
import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Q1 implements Question{

    private final String question;
    private String userAnswer;
    private Problem problem;

    public Q1(Problem problem){
        question = "Ce trebuie să aflăm în această problemă?";
        this.problem = problem;
    }

    public void start(){

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.println(question);
            System.out.print(":");
            userAnswer = scanner.nextLine();

            if(userAnswer.equals(problem.getQuestion())){

            }


    }

}
