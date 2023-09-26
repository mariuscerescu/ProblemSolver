package org.example.problemMetaData;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "POS_Output")
public class MathWordProblem {
    private List<Sentence> sentences;

    @XmlElement(name = "S")
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    // Utility method to get the problem
    public String getProblem(){
        StringBuilder problem = new StringBuilder();
        List<String> sentences = getAllSentences();
        for(String sentence : sentences){
            problem.append(sentence).append(" ");
        }

        return problem.toString();
    }

    // Utility method to get all words as a list of tokens
    public List<String> getAllTokens() {
        List<String> tokens = new ArrayList<>();
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                tokens.add(word.getContent());
            }
        }
        return tokens;
    }

    // Utility method to get all sentences as a list of strings
    public List<String> getAllSentences() {
        List<String> allSentences = new ArrayList<>();
        for (Sentence sentence : sentences) {
            StringBuilder sentenceText = new StringBuilder();
            for (Word word : sentence.getWords()) {
                sentenceText.append(word.getContent()).append(" ");
            }
            allSentences.add(sentenceText.toString().trim());
        }
        return allSentences;
    }

}


