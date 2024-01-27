package org.example;

import org.example.utils.PhraseSplitter;
import org.example.utils.QuestionFinder;

import java.util.ArrayList;

public class  Main {

    public static void main(String[] args) {

        String problem = "Ana are 8 nuci. Ea are de 2 ori mai multe nuci decât Maria. Câte nuci are Maria?";

        QuestionFinder qfinder = new QuestionFinder();

        String question = qfinder.getQuestion(problem);

        String problemNoQ = problem.replace(question, "");

        PhraseSplitter phraseSplitter = new PhraseSplitter();

        ArrayList<String> sentences = phraseSplitter.getData(problem);

        System.out.println("Problema: " + problem);

        System.out.println("1. Primul pas ar fi să identificăm ce anume trebuie să aflăm - in acest caz: " + question);

        System.out.println("2. Ce date avem?\n" + "Știm că:");

        for(String sentence : sentences){
            System.out.println("- " + sentence);
        }


    }

}
