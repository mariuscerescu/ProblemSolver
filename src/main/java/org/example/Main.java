package org.example;

import org.example.questions.QuestionManager;

import java.util.Scanner;

public class  Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdu o problemă de matematică.");
        System.out.print(":");

        String text = scanner.nextLine();

        Problem problem = new Problem(text);

        QuestionManager manager = new QuestionManager(problem);

        manager.askQuestions();
    }

}
