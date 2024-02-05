package org.example.questions;

import org.example.utils.Stemmer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Q1 implements Question{

    private final String question;
    private String userAnswer;

    private Map<String, String> dictionary;

    public Q1(){
        question = "Înțelegi toate cuvintele folosite în formularea problemei?";
        impDic();
    }

    private void impDic(){

        Stemmer stemmer = new Stemmer();

        Map<String, String> dic = new HashMap<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/org/example/files/dictionary.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        while(true){
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] split = line.split(" - ");
            dic.put(stemmer.getStem(split[0]), split[1]);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dictionary = dic;

    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        Stemmer stemmer = new Stemmer();

        System.out.print(question);
        System.out.println(" [da/nu]");

        System.out.print(":");
        userAnswer = scanner.nextLine();

        while(!userAnswer.equals("da") && !userAnswer.equals("nu")){
            System.out.println("Nu am înțeles ce ai introdus.");
            System.out.println("Introdu din nou.");
            System.out.print(":");
            userAnswer = scanner.nextLine();
        }

        if(userAnswer.equals("da")){
            System.out.println("Super, atunci mergem mai departe.");
            return;
        }

        System.out.println("Introdu câte un cuvânt din cuvintele neclare.");
        System.out.println("Dacă nu mai ai cuvinte neclare scrie „gata”.");

        while(true){
            System.out.print(":");
            userAnswer = scanner.nextLine();

            if(userAnswer.equals("gata")){
                System.out.println("Super, atunci mergem mai departe.");
                break;
            }

            String userAnswerStem = stemmer.getStem(userAnswer.strip().toLowerCase());

            if(dictionary.containsKey(userAnswerStem)){

                System.out.println(dictionary.get(userAnswerStem));

            }else{
                System.out.println("Nu știu ce înseamnă acest cuvânt.");
                System.out.println("Introdu altul.");
            }
        }


    }

}
