package org.example;

import java.util.*;
import java.util.regex.*;

public class MathWordProblem{

    // Structura pentru a stoca informațiile despre problemele matematice
    static class MathProblem {
      String problemText;
      List<String> numbers;
      List<String> operations;

        // Constructor
        public MathProblem(String problemText) {
            this.problemText = problemText;
            this.numbers = extractNumbers(problemText);
            this.operations = extractOperations(problemText);
        }

        // Funcție pentru extragerea numerelor din text
        private List<String> extractNumbers(String text) {
            List<String> numbers = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                numbers.add(matcher.group());
            }
            return numbers;
        }

        // Funcție pentru extragerea operațiilor din text
        private List<String> extractOperations(String text) {
            List<String> operations = new ArrayList<>();
            if (text.contains("plus") || text.contains("adaugă")) {
                operations.add("plus");
            }
            // Adăugați aici alte cuvinte cheie pentru operații
            return operations;
        }
    }

    // Enum pentru tipurile de operații
    enum OperationType {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, UNKNOWN
    }

    // Funcție pentru determinarea tipului de operație pe baza cuvintelor cheie
    private static OperationType determineOperationType(String operation) {
        return switch (operation.toLowerCase()) {
            case "plus", "adaugă" -> OperationType.ADDITION;
            case "minus", "scade" -> OperationType.SUBTRACTION;
            case "înmulțește" -> OperationType.MULTIPLICATION;
            case "împarte" -> OperationType.DIVISION;
            default -> OperationType.UNKNOWN;
        };
    }

    // Funcție pentru rezolvarea problemei matematice
    public static int solveMathProblem(MathProblem problem) {
        int result = 0;
        if (problem.numbers.size() < 2) {
            System.out.println("Nu sunt suficiente numere pentru a efectua o operație.");
            return result;
        }

        int num1 = Integer.parseInt(problem.numbers.get(0));
        int num2 = Integer.parseInt(problem.numbers.get(1));

        if (problem.operations.size() == 0) {
            System.out.println("Nu a fost detectată nicio operație.");
            return result;
        }

        OperationType operationType = determineOperationType(problem.operations.get(0));

        switch (operationType) {
            case ADDITION -> result = num1 + num2;
            case SUBTRACTION -> result = num1 - num2;
            case MULTIPLICATION -> result = num1 * num2;
            case DIVISION -> {
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Împărțire la zero!");
                }
            }
            default -> System.out.println("Operație necunoscută.");
        }

        return result;
    }

    // Funcție pentru determinarea subiectului problemei
    private static String determineSubject(String text) {
        return text.split(" ")[0];
    }

    // Funcție pentru afișarea problemei rezolvate
    public static void displaySolvedProblem(MathProblem problem, int result) {
        String subject = determineSubject(problem.problemText);
        System.out.println(result);
    }

    public static void main(String[] args) {
        MathProblem problem = new MathProblem("La mare, Mihaela adaugă 30 de scoici și 5 pietricele. Câte obiecte formează colecția Mihaelei?");
        int result = solveMathProblem(problem);
        displaySolvedProblem(problem, result);
    }
}
