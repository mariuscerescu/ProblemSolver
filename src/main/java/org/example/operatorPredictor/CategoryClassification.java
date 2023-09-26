package org.example.operatorPredictor;

import org.example.problemMetaData.MathWordProblem;
import org.example.problemMetaData.Sentence;
import org.example.problemMetaData.Word;
import java.util.List;


public class CategoryClassification {

    public static String getClassification(MathWordProblem mathWordProblem) {

        String problem = mathWordProblem.getProblem();
        List<String> tokens = mathWordProblem.getAllTokens();
        List<Sentence> sentences = mathWordProblem.getSentences();

        //Comparison Classification
        if(isWordPresent(tokens, "decât") || problem.contains("la fel de")){
            return "compare";
        }
        for (Sentence sentence : sentences) {
            List<Word> words = sentence.getWords();
            for (int j = 0; j < words.size() - 1; j++) {
                String word = words.get(j).getContent();
                String thePosAfter = words.get(j + 1).getPos();
                if (word.equals("mai") && (thePosAfter.equals("ADVERB") || thePosAfter.equals("ADJECTIVE"))) {
                    return "compare";
                }
            }
        }

        //Combination Classification
//        for(String token : tokens){
//            if(isWordPresent(tokens, ""))
//        }

        return "Classification not found";
    }


    private static boolean isWordPresent(List<String> tokens, String word){
        for(String token : tokens){
            if(token.equals(word)){
                return true;
            }
        }
        return false;
    }

}
