package org.example.questions;

import org.example.Problem;

import java.util.Scanner;

public class Q2 implements Question {

    private final String question;
    private String userAnswer;
    private Problem problem;

    public Q2(Problem problem) {
        question = "Ce trebuie să aflăm în această problemă?";
        this.problem = problem;
    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

        System.out.println(question);

        while (true) {

            System.out.print(":");
            userAnswer = scanner.nextLine().toLowerCase().strip();

            if (userAnswer.equals(problem.getQuestion().toLowerCase())) {
                System.out.println("Corect! Acesta este răspunsul, să continuăm rezolvarea.");
                break;
            }else{
                System.out.println("Mai încearcă.");
            }


        }

    }
}