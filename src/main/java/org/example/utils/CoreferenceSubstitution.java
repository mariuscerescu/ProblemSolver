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

    public CoreferenceSubstitution(MathWordProblem mathWordProblem) {
        tokens = mathWordProblem.getAllTokens();
        pos = mathWordProblem.getAllPOSTags();
        gender = mathWordProblem.getAllGenderTags();
        number = mathWordProblem.getAllNumberTags();
        type = mathWordProblem.getAllTypeTags();
        this.originalProblem = mathWordProblem.problem;
        tokenIndices = getTokenIndices(originalProblem, tokens);
    }

    public String getSubstitution() {
        StringBuilder result = new StringBuilder(originalProblem);

        for (int i = 0; i < tokens.size(); i++) {


            if (pos.get(i) != null && type.get(i) != null &&
                    pos.get(i).equals("PRONOUN") && type.get(i).equals("personal")) {

                boolean propenNounWasFound = false;

                String pronounFound = tokens.get(i);
                String pronounFoundGender = gender.get(i);
                String pronounFoundNumber = number.get(i);

                for (int j = i - 1; j >= 0; j--) {
                    if (pos.get(j) != null && gender.get(j) != null && number.get(j) != null && type.get(j) != null &&
                            pos.get(j).equals("NOUN") && type.get(j).equals("proper") && gender.get(j).equals(pronounFoundGender) &&
                            number.get(j).equals(pronounFoundNumber)) {
                        String noun = tokens.get(j);
                        int tokenIndex = tokenIndices.get(i);

                        result.replace(tokenIndex, tokenIndex + pronounFound.length(), noun);
                        tokens.set(i, noun);
                        tokenIndices = getTokenIndices(result.toString(), tokens);
                        propenNounWasFound = true;
                        break;
                    }
                }

                if(!propenNounWasFound){
                    for (int j = i - 1; j >= 0; j--) {
                        if (pos.get(j) != null && gender.get(j) != null && number.get(j) != null &&
                                pos.get(j).equals("NOUN") && gender.get(j).equals(pronounFoundGender) &&
                                number.get(j).equals(pronounFoundNumber)) {
                            String noun = tokens.get(j);
                            int tokenIndex = tokenIndices.get(i);

                            result.replace(tokenIndex, tokenIndex + pronounFound.length(), noun);
                            tokens.set(i, noun);
                            tokenIndices = getTokenIndices(result.toString(), tokens);
                            break;
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    private static ArrayList<Integer> getTokenIndices(String problem, List<String> tokens) {
        ArrayList<Integer> tokenIndices = new ArrayList<>();
        StringBuilder tempProblem = new StringBuilder(problem);

        for (String token : tokens) {
            tokenIndices.add(tempProblem.indexOf(token));
            tempProblem.replace(tempProblem.indexOf(token), tempProblem.indexOf(token) + token.length(), " ".repeat(token.length()));
        }
        return tokenIndices;
    }

    public static void main(String[] args) {
        CoreferenceSubstitution coreferenceSubstitution = new CoreferenceSubstitution(BinPosRoRunner.runTextAnalysis("Ion are 6 morcovi. El are de 3 ori mai puțini morcovi decât Cronț. Câți morcovi are Cronț?"));
        System.out.println(coreferenceSubstitution.getSubstitution());
    }
}
