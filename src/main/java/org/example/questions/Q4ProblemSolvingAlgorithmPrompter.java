package org.example.questions;

import org.example.Problem;
import org.example.algorithmCreatorGuiders.AlgorithmCreatorGuider;
import org.example.algorithmCreatorGuiders.AlgorithmCreatorManager;
import org.example.classification.ClassifierManager;
import org.example.solvers.Solver;
import org.example.solvers.SolverManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Q4ProblemSolvingAlgorithmPrompter extends Question{

    private final String question;
    private String userAnswer;
    private Problem problem;
    private List<String> algorithm;
    private List<String> solutions;
    private List<List<String>> advicesList;
    private String classification;
    private AlgorithmCreatorGuider algorithmCreatorGuider;
    private Scanner scanner;

    public Q4ProblemSolvingAlgorithmPrompter(Problem problem) {
        question = "Pe baza acestor informații vom încerca să scriem algoritmul de rezolvare a acestei probleme.";
        this.problem = problem;
        algorithm = new ArrayList<>();
        advicesList = new ArrayList<>();
        scanner = new Scanner(System.in);

        ClassifierManager classifierManager = new ClassifierManager(problem.getText());
        classification = classifierManager.findClassification();

        AlgorithmCreatorManager algorithmCreatorManager = new AlgorithmCreatorManager();
        algorithmCreatorGuider = algorithmCreatorManager.getAlgCrGuider(classification);

        try {
            loadAlgorithm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SolverManager solverManager = new SolverManager(problem);
        Solver solver = solverManager.getSolvers().get(classification);

        if(solver != null){
            solutions = solver.getSolutions();
        }

        if(classification != null){
            loadAdvices();
        }
    }

    private void loadAlgorithm() throws IOException {
        File algorithmFolder = new File("src/main/java/org/example/solvingAlgorithms/" + classification);
        String[] files = algorithmFolder.list();

        assert files != null;
        for(String file : files){
            String filePath = algorithmFolder.getPath() + "/" + file;
            algorithm.add(readFileAsString(filePath));
        }
    }

    private void loadAdvices(){
        String folderPath = "src/main/java/org/example/guidance/q4Guidance/" + classification + "Guidance";
        File q4GuidanceFolder = new File(folderPath);
        String[] stepsFolder = q4GuidanceFolder.list();

        StringBuilder builder = new StringBuilder();

        assert stepsFolder != null;
        for(String stepFolderName: stepsFolder){
            String stepFolderPath = folderPath + "/" + stepFolderName;
            File stepFolder = new File(stepFolderPath);
            String[] files = stepFolder.list();
            List<String> queue = new ArrayList<>();

            for(String fileName : files){
                String filePath = stepFolderPath + "/" + fileName;
                builder.setLength(0);

                try {
                    List<String> adviceList = Files.readAllLines(Paths.get(filePath));

                    for(int i = 0; i < adviceList.size(); i++){
                        builder.append(adviceList.get(i));
                        if(i < adviceList.size() - 1){
                            builder.append("\n");
                        }
                    }

                    queue.add(builder.toString());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            advicesList.add(queue);

        }
    }

    @Override
    public void askQuestion() {

        System.out.println(question);

        algorithmCreatorGuider.start();

        System.out.println("În continuare vom folosi o abordare pas cu pas pentru a rezolva fiecare etapă din algoritmul de rezolvare creat.");
        for(int i = 0; i < algorithm.size(); i++){

            List<String> advicesQueue = advicesList.get(i);

            System.out.println(algorithm.get(i));

            System.out.println(solutions.get(i));

            while(true){
                System.out.print(":");
                userAnswer = scanner.nextLine().toLowerCase().strip();

                if(userAnswer.equals("ajutor")){
                    if(!advicesQueue.isEmpty()){
                        System.out.println(advicesQueue.get(0));
                        advicesQueue.remove(0);
                    }else{
                        System.out.println("La moment nu te pot ajuta.");
                    }
                }else if(userAnswer.contains("ce înseamnă")){
                    String term = userAnswer.split(" ")[2];
                    System.out.println(Q1ProblemTermsUnderstandingPrompter.getDefinition(term));
                }else if(userAnswer.equals(solutions.get(i))){
                    System.out.println("Corect!");
                    break;
                }else{
                    if(!advicesQueue.isEmpty()){
                        System.out.println(advicesQueue.get(0));
                        advicesQueue.remove(0);
                    }else{
                        System.out.println("Răspunsul este greșit! Mai încearcă.");
                    }
                }
            }

        }

        System.out.println("Felicitări ai rezolvat problema cu succes!");

    }

    public static String readFileAsString(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

}
