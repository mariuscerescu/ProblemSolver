package org.example.utils;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;

import java.util.ArrayList;
import java.util.List;

public class CoreferenceSubstitution {

    private ArrayList<String> tokens;
    private ArrayList<String> pos;
    private ArrayList<String> gender;
    private ArrayList<String> number;
    private ArrayList<String> type;
    private String originalProblem;
    private ArrayList<Integer> tokenIndices;

    public CoreferenceSubstitution(MathWordProblem mathWordProblem){
        tokens = mathWordProblem.getAllTokens();
        pos = mathWordProblem.getAllPOSTags();
        gender = mathWordProblem.getAllGenderTags();
        number = mathWordProblem.getAllNumberTags();
        type = mathWordProblem.getAllTypeTags();
        this.originalProblem = mathWordProblem.problem;
        tokenIndices = getTokenIndices(originalProblem, tokens);
    }

    public String getSubstitution(){

        String result = originalProblem;

        String pronounFound, pronounFoundGender, pronounFoundNumber;

        for(int i = 0; i < tokens.size(); i++){

            if(pos.get(i) != null && type.get(i) != null){
                if(pos.get(i).equals("PRONOUN") && type.get(i).equals("personal")){
                    pronounFound = tokens.get(i);
                    pronounFoundGender = gender.get(i);
                    pronounFoundNumber = number.get(i);

                    for(int j = i - 1; j >= 0; j--){
                        if(pos.get(j) != null && gender.get(j) != null && number.get(j) != null){
                            if(pos.get(j).equals("NOUN") && gender.get(j).equals(pronounFoundGender) && number.get(j).equals(pronounFoundNumber)){
                                String noun = tokens.get(j);
                                result = originalProblem.substring(0, tokenIndices.get(i)) + noun + originalProblem.substring(tokenIndices.get(i) + pronounFound.length());
                            }
                        }

                    }
                }
            }
        }
        return result;
    }

    private static ArrayList<Integer> getTokenIndices(String problem, List<String> tokens) {
        ArrayList<Integer> tokenIndices = new ArrayList<>();

        StringBuilder tempProblem = new StringBuilder();
        tempProblem.append(problem);
        StringBuilder replacement = new StringBuilder();

        for(String token : tokens){
            tokenIndices.add(tempProblem.indexOf(token));
            replacement.setLength(0);
            replacement.append("~".repeat(token.length()));
            tempProblem.replace(tempProblem.indexOf(token), tempProblem.indexOf(token) + token.length(), replacement.toString());
        }
        return tokenIndices;
    }

    public static void main(String[] args) {

        CoreferenceSubstitution coreferenceSubstitution = new CoreferenceSubstitution(BinPosRoRunner.runTextAnalysis("Ana are 8 nuci. Ea are de 2 ori mai multe nuci decât Maria. Câte nuci are Maria?"));
        System.out.println(coreferenceSubstitution.getSubstitution());
    }

}
