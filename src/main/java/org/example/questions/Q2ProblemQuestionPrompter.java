package org.example.questions;

import org.example.Problem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Q2ProblemQuestionPrompter extends Question {

    private final String question;
    private String userAnswer;
    private String processedProblemQuestion;
    private Problem problem;
    private List<String> advices;

    public Q2ProblemQuestionPrompter(Problem problem) {
        question = """
                Pentru a înțelege mai bine ce se cere în problema dată, este important să identifici
                enunțul problemei, care este sub forma unei întrebări. Citește
                cu atenție problema și încearcă să identifici scopul principal. De exemplu, dacă problema
                vorbește despre calcularea unei distanțe, întrebarea ar putea fi 'Ce distanță trebuie
                să calculez?'. Dacă este vorba despre compararea cantităților, întrebarea ar putea fi
                'Care cantitate este mai mare sau cum se compară acestea?'.""";

        this.problem = problem;
        String problemQuestion = problem.getQuestion();
        if(problemQuestion != null){
            processedProblemQuestion = problemQuestion.toLowerCase().substring(0, problemQuestion.length() - 1);
        }else{
            processedProblemQuestion = null;
        }

        loadAdvices();
    }

    private void loadAdvices(){
        advices = new ArrayList<>();
        String folderPath = "src/main/java/org/example/guidance/q2Guidance";
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

        if(processedProblemQuestion == null){
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println(question);

        System.out.println(processedProblemQuestion);

        while (true) {

            System.out.print(":");
            userAnswer = scanner.nextLine().toLowerCase().strip();

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
            }else if (userAnswer.contains(processedProblemQuestion)) {
                System.out.println("Corect! Acesta este răspunsul, să continuăm rezolvarea.");
                break;
            }else{
//                System.out.println("Mai încearcă.");
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