package org.example.questions;

import org.checkerframework.checker.units.qual.A;
import org.example.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Q3 implements Question{

    private final String question;
    private String userAnswer;
    private Problem problem;

    private List<String> data;

    public Q3(Problem problem){
        question = "Care sunt datele problemei?";
        this.problem = problem;
        getData();
    }

    public void start() {

        int[] foundData = new int[data.size()];
        
        System.out.println(data);

        Scanner scanner = new Scanner(System.in);

        System.out.println(question);
        System.out.println("Introdu toate datele pe rând.");

        while(true){
            System.out.print(":");
            userAnswer = scanner.nextLine().toLowerCase().strip();

            if(data.contains(userAnswer)){

                if(foundData[data.indexOf(userAnswer)] == 0){
                    foundData[data.indexOf(userAnswer)] = 1;

                    boolean isFilledWithOne = Arrays.stream(foundData)
                            .allMatch(x -> x == 1);

                    if(isFilledWithOne){
                        System.out.println("Super, ai identificat corect toate datele.");
                        break;
                    }

                    System.out.println("Corect, continuă.");
                }else{
                    System.out.println("Această valoare a fost deja introdusă.");
                }

            }else{
                System.out.println("Ai introdus un răspuns greșit. Mai încearcă!");
            }
        }

    }

    private void getData(){
        List<String> data = new ArrayList<>();

        ArrayList<String> posTags = problem.getProblemMetaData().posTags;
        ArrayList<String> tokens = problem.getProblemMetaData().tokens;

        for(int i = 0; i < posTags.size() - 2; i++){

            if((posTags.get(i) != null && posTags.get(i + 1) != null && posTags.get(i + 2) != null) && (posTags.get(i).equals("NUMERAL") && posTags.get(i + 1).equals("ADPOSITION") && posTags.get(i + 2).equals("NOUN"))){
                data.add(tokens.get(i) + " " + tokens.get(i + 1) + " " + tokens.get(i + 2));
            }else if((posTags.get(i) != null && posTags.get(i + 1) != null) && (posTags.get(i).equals("NUMERAL") && posTags.get(i + 1).equals("NOUN"))){
                data.add(tokens.get(i) + " " + tokens.get(i + 1));
            }

        }

        this.data = data;

    }
}
