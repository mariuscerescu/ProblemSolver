package org.example.utils;

import org.example.Problem;

import java.util.ArrayList;
import java.util.List;

public class DataFinder {

    private final Problem problem;

    public DataFinder(Problem problem){
        this.problem = problem;
    }

    public List<String> getData(){
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

        return data;

    }

}
