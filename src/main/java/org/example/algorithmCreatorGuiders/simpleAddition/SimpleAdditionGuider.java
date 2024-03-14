package org.example.algorithmCreatorGuiders.simpleAddition;

import org.example.algorithmCreatorGuiders.AlgorithmCreatorGuider;
import org.example.pythonUtils.sentenceSimilarity.SSManager;
import org.example.questions.Q1ProblemTermsUnderstandingPrompter;
import org.example.utils.TextNormalization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleAdditionGuider extends AlgorithmCreatorGuider {

    private final List<String> steps;
    private final List<List<String>> advicesList;

    public SimpleAdditionGuider(){
        steps = new ArrayList<>();
        advicesList = new ArrayList<>();
        importSteps();
        importAdvices();
    }

    private void importSteps(){

            File algorithmFolder = new File("src/main/java/org/example/solvingAlgorithms/simpleAddition");
            String[] files = algorithmFolder.list();

            assert files != null;
            for(String file : files){
                String filePath = algorithmFolder.getPath() + "/" + file;
                try {
                    steps.add(Files.readString(Paths.get(filePath)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    private void importAdvices(){
        File adviceFolder = new File("src/main/java/org/example/algorithmCreatorGuiders/simpleAddition/Advices");
        String[] steps = adviceFolder.list();

        for(String step : steps){

            String folderPath = adviceFolder + "/" + step;

            File folder = new File(folderPath);

            String[] files = folder.list();

            List<String> advices = new ArrayList<>();

            assert files != null;
            for(String file : files){

                String adviceFilePath = folderPath + "/" + file;

                try {
                    advices.add(Files.readString(Paths.get(adviceFilePath)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            advicesList.add(advices);

        }

    }

    public void start(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdu pe rând fiecare etapă a procesului de rezolvare.");


        for(int i = 0; i < steps.size(); i++){

            while(true){

                System.out.print(":");
                String userAnswer = TextNormalization.getProcessedText(scanner.nextLine());

                if(userAnswer.contains("ce înseamnă")){
                    String term = userAnswer.split(" ")[2];
                    System.out.println(Q1ProblemTermsUnderstandingPrompter.getDefinition(term));
                    i--;
                    break;
                }

                double similarity = SSManager.getSimilarity(steps.get(i), userAnswer);

                if(similarity > 0.45){
                    System.out.println("Corect!");
                    System.out.println("Introdu următoarea etapă.");
                    break;
                }else{
                    if(!advicesList.get(i).isEmpty()){

                        System.out.println(advicesList.get(i).get(0));
                        advicesList.get(i).remove(0);
                    }else{
                        System.out.println("Mai încearcă!");
                    }
                }
            }

        }

    }

    public static void main(String[] args) {
        SimpleAdditionGuider guider = new SimpleAdditionGuider();

        guider.start();
    }

}
