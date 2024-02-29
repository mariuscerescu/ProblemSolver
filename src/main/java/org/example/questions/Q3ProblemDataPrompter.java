package org.example.questions;

import org.example.Problem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Q3ProblemDataPrompter implements Question{

    private final String question;
    private String userAnswer;
    private Problem problem;
    private List<String> data;
    private Queue<String> advices;

    public Q3ProblemDataPrompter(Problem problem){
        question = "Care sunt datele problemei?";
        this.problem = problem;
        getData();
        loadAdvices();
    }

    private void loadAdvices(){
        advices = new LinkedList<>();
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


    public void start() {

        int[] foundData = new int[data.size()];
        
        System.out.println(data);

        Scanner scanner = new Scanner(System.in);

        System.out.println(question);
        System.out.println("Introdu toate datele pe rând.");

        while(true){
            System.out.print(":");
            userAnswer = scanner.nextLine().toLowerCase().strip();

            if(userAnswer.equals("ajutor")){
                if(!advices.isEmpty()){
                    System.out.println(advices.remove());
                }else{
                    System.out.println("La moment nu te pot ajuta.");
                }
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
                System.out.println("Ai introdus un răspuns greșit. Mai încearcă!");
            }
        }

    }

    private void getData(){
        List<String> data = new ArrayList<>();

        ArrayList<String> posTags = problem.getProblemMetaData().posTags;
        ArrayList<String> types = problem.getProblemMetaData().typeTags;
        ArrayList<String> tokens = problem.getProblemMetaData().tokens;

        for(int i = 0; i < posTags.size() - 2; i++){

            if((posTags.get(i) != null && posTags.get(i + 1) != null && posTags.get(i + 2) != null) && (posTags.get(i).equals("NUMERAL") && posTags.get(i + 1).equals("ADPOSITION") && posTags.get(i + 2).equals("NOUN") && types.get(i + 2).equals("common"))){
                String extractedData = tokens.get(i) + " " + tokens.get(i + 1) + " " + tokens.get(i + 2);
                if(!data.contains(extractedData)){
                    data.add(extractedData);
                }
            }else if((posTags.get(i) != null && posTags.get(i + 1) != null) && (posTags.get(i).equals("NUMERAL") && posTags.get(i + 1).equals("NOUN") && types.get(i + 1).equals("common"))){
                String extractedData = tokens.get(i) + " " + tokens.get(i + 1);
                if(!data.contains(extractedData)){
                    data.add(extractedData);
                }
            }

        }

        this.data = data;

    }
}
