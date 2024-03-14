package org.example.questions;

import org.example.Problem;
import org.example.utils.DataFinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Q3ProblemDataPrompter extends Question{

    private final String question;
    private final List<String> data;
    private List<String> advices;

    public Q3ProblemDataPrompter(Problem problem){
        question = """
                Identifică datele problemei, acestea sunt toate cifrele sau informațiile concrete
                menționate și gândește-te la cum fiecare dintre acestea contribuie la rezolvarea
                întrebării tale. De exemplu, dacă ai '5 mere' și '7 banane', acestea sunt datele tale.
                Introdu toate datele pe rând.""";

        DataFinder dataFinder = new DataFinder(problem);
        data = dataFinder.getData();
        loadAdvices();
    }

    private void loadAdvices(){
        advices = new ArrayList<>();
        String folderPath = "src/main/java/org/example/guidance/q3Guidance";
        File folder = new File(folderPath);
        String[] files = folder.list();
        List<String> advice;
        StringBuilder builder = new StringBuilder();
        assert files != null;
        for(String file: files){
            builder.setLength(0);
            try {
                advice = Files.readAllLines(Paths.get(folderPath + "/" + file));

                for(int i = 0; i < advice.size(); i++){
                    builder.append(advice.get(i));
                    if(i < advice.size() -1){
                        builder.append("\n");
                    }
                }

                advices.add(builder.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void askQuestion() {

        if(data.isEmpty()){
            return;
        }

        int[] foundData = new int[data.size()];
        
        System.out.println(data);

        Scanner scanner = new Scanner(System.in);

        System.out.println(question);

        while(true){
            System.out.print(":");
            String userAnswer = scanner.nextLine().toLowerCase().strip();

            if(userAnswer.equals("ajutor")){
                if(!advices.isEmpty()){
                    System.out.println(advices.get(0));
                    advices.remove(0);
                }else{
                    System.out.println("La moment nu te pot ajuta.");
                }
            }else if(userAnswer.contains("ce înseamnă")){
                String term = userAnswer.split(" ")[2];
                System.out.println(Q1ProblemTermsUnderstandingPrompter.getDefinition(term));
            }else if(data.contains(userAnswer)){

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
//                System.out.println("Ai introdus un răspuns greșit. Mai încearcă!");
                if(!advices.isEmpty()){
                    System.out.println(advices.get(0));
                    advices.remove(0);
                }else{
                    System.out.println("Mai încearcă.");
                }
            }
        }

    }

}
