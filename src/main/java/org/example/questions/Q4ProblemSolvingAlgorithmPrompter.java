package org.example.questions;

import org.example.Problem;
import org.example.classification.ClassifierManager;
import org.example.solvers.Solver;
import org.example.solvers.SolverManager;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Q4ProblemSolvingAlgorithmPrompter implements Question{

    private final String question;
    private String userAnswer;
    private Problem problem;
    private List<String> algorithm;
    private HashMap<String, List<String>> algorithms;
    private List<String> solutions;
    private List<Queue<String>> advicesList;
    private String classification;

    public Q4ProblemSolvingAlgorithmPrompter(Problem problem) {
        question = "Pe baza acestor informații, ai vreo idee despre cum am putea rezolva această problemă?";
        this.problem = problem;
        algorithm = new ArrayList<>();
        algorithms = new HashMap<>();
        advicesList = new ArrayList<>();

        try {
            loadAlgorithms();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClassifierManager classifierManager = new ClassifierManager(problem.getText());
        classification = classifierManager.findClassification();

        if(classification != null){
            algorithm = algorithms.get(classification);
        }else{
            algorithm = null;
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

    private void loadAlgorithms() throws IOException {
        File algorithmsFolder = new File("src/main/java/org/example/solvingAlgorithms");
        String[] files = algorithmsFolder.list();

        assert files != null;
        for(String file : files){
            algorithms.put(file.substring(0, file.length()-4), Files.readAllLines(Paths.get("src/main/java/org/example/solvingAlgorithms/" + file)));
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
            Queue<String> queue = new LinkedList<>();

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
    public void start() {

        Scanner scanner = new Scanner(System.in);

        System.out.print(question);
        System.out.println(" [da/nu]");

        while(true){
            System.out.print(":");
            userAnswer = scanner.nextLine().toLowerCase().strip();

            if(userAnswer.equals("da")){
                System.out.println("Excelent! Care este strategia ta pentru a rezolva această problemă?");

                System.out.print(":");
                userAnswer = scanner.nextLine().toLowerCase().strip();

                break;

            }else if (userAnswer.equals("nu")){

                if(algorithm == null){
                    System.out.println("Nu am putut clasifica această problemă!");
                    break;
                }

                System.out.println("Nici o problemă! Vom folosi o abordare pas cu pas pentru a rezolva această problemă.");
                for(int i = 0; i < algorithm.size(); i++){

                    Queue<String> advicesQueue = advicesList.get(i);

                    String[] stepBreakdown = algorithm.get(i).split("\\|");

                    for(String subStep : stepBreakdown){
                        System.out.println(subStep);
                    }

                    System.out.println(solutions.get(i));

                    while(true){
                    System.out.print(":");
                    userAnswer = scanner.nextLine().toLowerCase().strip();

                        if(userAnswer.equals("ajutor")){
                            if(!advicesQueue.isEmpty()){
                                System.out.println(advicesQueue.remove());
                            }else{
                                System.out.println("La moment nu te pot ajuta.");
                            }
                        }else if(userAnswer.equals(solutions.get(i))){
                            System.out.println("Corect!");
                            break;
                        }else{
                            System.out.println("Răspunsul este greșit! Mai încearcă.");
                        }
                    }

                }

                System.out.println("Felicitări ai rezolvat problema cu succes!");
                break;

            }else{
                System.out.println("Nu am înțeles ce ai introdus! Încearcă din nou.");
            }

        }

    }
}
