package org.example;

import org.example.questions.QuestionManager;

public class  Main {

    public static void main(String[] args) {

        Problem problem = new Problem("Alex a economisit 7 dolari și apoi a primit alți 5 dolari de la bunicii săi pentru ziua lui. Câți dolari are Alex acum în total?");

        QuestionManager manager = new QuestionManager(problem);

        manager.askQuestions();
    }

}
