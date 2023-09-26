package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class QuestionFinder {

    private static final ArrayList<String> questionWordsList = new ArrayList<>();
    private static final ArrayList<String> actionWordsList = new ArrayList<>();

    static{
        try {
            Path questionWordsPath = Paths.get("src/main/java/org/example/files/QuestionWords.txt");
            questionWordsList.addAll(Files.readAllLines(questionWordsPath));
            Path actionWordsPath = Paths.get("src/main/java/org/example/files/ActionWords.txt");
            actionWordsList.addAll(Files.readAllLines(actionWordsPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuestion(String problem){

        String question;

        Tokenizer tokenizer = new Tokenizer();

        int startIndex = 0, endIndex = problem.length();

        String startingWord = null;

        ArrayList<String> tokens = tokenizer.getTokens(problem);

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

        if(tokens.contains("?")){
            endIndex = problem.indexOf("?") + 1;
            String compoundWord = "";
            boolean isCompound = false;
            for(int i = tokens.indexOf("?"); i >= 0; i--){
                if(i > 0){
                    compoundWord = tokens.get(i - 1) + " " + tokens.get(i);
                }
                String tempStartingWord = null;
                for(String qWord: questionWordsList){
                    if(compoundWord.equalsIgnoreCase(qWord)){
                        tempStartingWord = compoundWord;
                        isCompound = true;
                        break;
                    }
                    if(tokens.get(i).equalsIgnoreCase(qWord)){
                        tempStartingWord = tokens.get(i);
                        isCompound = false;
                        break;
                    }
                }
                if(tempStartingWord != null){
                    startingWord = tempStartingWord;
                    startIndex = isCompound ? tokenIndices.get(i - 1) : tokenIndices.get(i);
                    if(i > 0){
                        int k = isCompound ? 2 : 1;
                        if(tokens.get(i - k).equals(".") || tokens.get(i - k).equals("!") || tokens.get(i - k).equals(",")){
                            break;
                        }
                    }
                }

            }

        }

        if(startingWord == null){
            for(int i = 0; i < tokens.size(); i++){
                for(String aWord : actionWordsList){
                    if(aWord.equalsIgnoreCase(tokens.get(i))) {
                        startingWord = tokens.get(i);
                        startIndex = tokenIndices.get(i);
                        break;
                    }
                }
                if(startingWord != null){
                    for(int j = problem.indexOf(startingWord); j < problem.length(); j++){
                        if(problem.charAt(j) == '.' || problem.charAt(j) == '!' || problem.charAt(j) == '?'){
                            endIndex = j + 1;
                            break;
                        }
                    }
                    break;
                }
            }
        }

        if(startingWord == null){
            String compoundWord = "";
            boolean isCompound;
            for(int i = 0; i < tokens.size(); i = isCompound ? i+2 : i+1){
                isCompound = false;
                if(i < tokens.size() - 1){
                    compoundWord = tokens.get(i) + " " + tokens.get(i + 1);
                }
                String tempStartingWord = null;
                for(String qWord : questionWordsList) {
                    if(compoundWord.equalsIgnoreCase(qWord)) {
                        tempStartingWord = compoundWord;
                        isCompound = true;
                        break;
                    }
                    if(tokens.get(i).equalsIgnoreCase(qWord)) {
                        tempStartingWord = tokens.get(i);
                        break;
                    }
                }
                if (tempStartingWord != null) {
                    startingWord = tempStartingWord;
                    startIndex = tokenIndices.get(i);
                    if(i > 0){
                        if(tokens.get(i - 1).equals(".") || tokens.get(i - 1).equals(",") || tokens.get(i - 1).equals("!")){
                            for(int j = problem.indexOf(startingWord); j < problem.length(); j++){
                                if(problem.charAt(j) == '.' || problem.charAt(j) == '!'){
                                    endIndex = j + 1;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

            }
        }

        if(startingWord != null){
            question = problem.substring(startIndex, endIndex);
            return question;
        }

        return null;

    }

    public static void main(String[] args) {
        QuestionFinder qfinder = new QuestionFinder();

        System.out.println(qfinder.getQuestion("Weng câștigă 12, cât a câștigat dolari pe oră ca babysitter. Ieri, ea a făcut doar 50 de minute de babysitting. Cât a câștigat"));


    }

}
