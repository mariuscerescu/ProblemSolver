package org.example;

import org.example.questions.Q2;
import org.example.questions.Q1;
import org.example.questions.Q3;

public class  Main {

    public static void main(String[] args) {

    Problem problem = new Problem("Alecu a citit 60 de pagini din cartea „Balcoane cu elefanți” și i-au mai rămas 39 de pagini. Câte pagini are cartea");

        System.out.println(problem.getText());

        Q1 q1 = new Q1();
        Q2 q2 = new Q2(problem);
        Q3 q3 = new Q3(problem);

        q1.start();
        q2.start();
        q3.start();
    }

}
