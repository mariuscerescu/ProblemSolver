package org.example;

import java.util.*;
import java.util.regex.*;

public class MathWordProblem{

    // Structure to store information about math problems
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

        // Function to extract numbers from text
        private List<String> extractNumbers(String text) {
            List<String> numbers = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                numbers.add(matcher.group());
            }
            return numbers;
        }

        // Function to extract operations from text
        private List<String> extractOperations(String text) {
            List<String> operations = new ArrayList<>();
            if (text.toLowerCase().contains("plus") || text.toLowerCase().contains("adaugă")) {
                operations.add("plus");
            }
            if (text.toLowerCase().contains("minus") || text.toLowerCase().contains("scade")) {
                operations.add("minus");
            }
            if (text.toLowerCase().contains("înmulțește")) {
                operations.add("înmulțește");
            }
            if (text.toLowerCase().contains("împarte")) {
                operations.add("împarte");
            }
            return operations;
        }
    }

    enum OperationType {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, UNKNOWN
    }

    private static OperationType determineOperationType(String operation) {
        return switch (operation.toLowerCase()) {
            case "plus", "adaugă" -> OperationType.ADDITION;
            case "minus", "scade" -> OperationType.SUBTRACTION;
            case "înmulțește" -> OperationType.MULTIPLICATION;
            case "împarte" -> OperationType.DIVISION;
            default -> OperationType.UNKNOWN;
        };
    }

    public static int solveMathProblem(MathProblem problem) {
        int result = 0;
        if (problem.numbers.size() < 2) {
            System.out.println("Not enough numbers to perform an operation.");
            return result;
        }

        int num1 = Integer.parseInt(problem.numbers.get(0));
        int num2 = Integer.parseInt(problem.numbers.get(1));

        if (problem.operations.size() == 0) {
            System.out.println("No operation detected.");
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
                    System.out.println("Division by zero!");
                }
            }
            default -> System.out.println("Unknown operation.");
        }

        return result;
    }

    private static String determineSubject(String text) {
        return text.split(" ")[3]; // Assuming subject is the fourth word in the sentence
    }

    public static void displaySolvedProblem(MathProblem problem, int result) {
        String subject = determineSubject(problem.problemText);
        System.out.println("The result for '" + subject + "' is: " + result);
    }

    public static void main(String[] args) {
        MathProblem problem = new MathProblem("Ion a alcătuit un ierbar din 97 de plante: 90 din Codrii Orheiului, iar restul din Stepa Bugeacului. Câte plante de stepă sunt în ierbar?");
        int result = solveMathProblem(problem);
        displaySolvedProblem(problem, result);
    }
}
