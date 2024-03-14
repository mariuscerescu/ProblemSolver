package org.example.questions;

import org.example.Problem;

import java.util.ArrayList;
import java.util.List;

public class Summarizer extends Question{

    private String processedQuestion;
    private List<String> data;
    private Problem problem;

    public Summarizer(Problem problem){
        this.problem = problem;
            String question = problem.getQuestion();
            if(question != null){
                processedQuestion = question.toLowerCase().substring(0, problem.getQuestion().length()-1);
            }else{
                processedQuestion = null;
            }
           getData();
    }

    @Override
    public void askQuestion() {

        if(data.isEmpty() || processedQuestion == null){
            return;
        }

        System.out.print("Deci noi cunoaștem că trebuie să aflăm " + processedQuestion + " și că avem ");
        for(int i = 0; i < data.size(); i++){
            System.out.print(data.get(i));
            if(i < data.size() - 1){
                System.out.print(" și ");
            }
        }

        System.out.println(".");
    }

    private void getData(){
        List<String> data = new ArrayList<>();

        ArrayList<String> posTags = problem.getProblemMetaData().posTags;
        ArrayList<String> types = problem.getProblemMetaData().typeTags;
        ArrayList<String> tokens = problem.getProblemMetaData().tokens;

        for(int i = 0; i < tokens.size() - 2; i++){

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
