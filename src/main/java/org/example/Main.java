package org.example;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        String problem = "Ken a creat un pachet de îngrijire pentru a-l trimite fratelui său, care era plecat la internat.  Ken a așezat o cutie pe un cântar, apoi a turnat în cutie suficiente jeleuri pentru a aduce greutatea la 2 kilograme.  Apoi, a adăugat suficiente negrese pentru ca greutatea să se tripleze.  Apoi, a adăugat încă 2 kilograme de jeleuri.  Și, în cele din urmă, a adăugat suficienți viermi gumați pentru a dubla din nou greutatea.  Care a fost greutatea finală a cutiei de bunătăți, în kilograme?";

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
