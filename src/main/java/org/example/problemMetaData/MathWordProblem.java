package org.example.problemMetaData;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "POS_Output")
public class MathWordProblem {
    private List<Sentence> sentences;

    public String problem;

    @XmlElement(name = "S")
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    // Utility method to get all words as a list of tokens
    public ArrayList<String> getAllTokens() {
        ArrayList<String> tokens = new ArrayList<>();
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                tokens.add(word.getContent());
            }
        }
        return tokens;
    }

    public ArrayList<ArrayList<String>> getTokensPerSentence() {
        ArrayList<ArrayList<String>> tokensPerSentence = new ArrayList<>();
        for (Sentence sentence : sentences) {
            ArrayList<String> tokensInOneSentence = new ArrayList<>();
            for (Word word : sentence.getWords()) {
                tokensInOneSentence.add(word.getContent());
            }
            tokensPerSentence.add(tokensInOneSentence);
        }
        return tokensPerSentence;
    }

    public ArrayList<ArrayList<String>> getMSDPerSentence() {
        ArrayList<ArrayList<String>> msdPerSentence = new ArrayList<>();
        for (Sentence sentence : sentences) {
            ArrayList<String> msdInOneSentence = new ArrayList<>();
            for (Word word : sentence.getWords()) {
                msdInOneSentence.add(word.getMsd());
            }
            msdPerSentence.add(msdInOneSentence);
        }
        return msdPerSentence;
    }

    public ArrayList<ArrayList<String>> getPOSPerSentence() {
        ArrayList<ArrayList<String>> posPerSentence = new ArrayList<>();
        for (Sentence sentence : sentences) {
            ArrayList<String> posInOneSentence = new ArrayList<>();
            for (Word word : sentence.getWords()) {
                posInOneSentence.add(word.getPos());
            }
            posPerSentence.add(posInOneSentence);
        }
        return posPerSentence;
    }

    public ArrayList<ArrayList<String>> getTypesPerSentence() {
        ArrayList<ArrayList<String>> typesPerSentence = new ArrayList<>();
        for (Sentence sentence : sentences) {
            ArrayList<String> typesInOneSentence = new ArrayList<>();
            for (Word word : sentence.getWords()) {
                typesInOneSentence.add(word.getType());
            }
            typesPerSentence.add(typesInOneSentence);
        }
        return typesPerSentence;
    }

    public ArrayList<String> getAllPOSTags(){
        ArrayList<String> posTags = new ArrayList<>();
        for(Sentence s : sentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                posTags.add(w.getPos());
            }
        }

        return posTags;
    }

    public ArrayList<String> getAllMSDTags(){
        ArrayList<String> msdTags = new ArrayList<>();
        for(Sentence s : sentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                msdTags.add(w.getPos());
            }
        }

        return msdTags;
    }

    public ArrayList<String> getAllTypeTags(){
        ArrayList<String> typeTags = new ArrayList<>();
        for(Sentence s : sentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                typeTags.add(w.getType());
            }
        }

        return typeTags;
    }

    public ArrayList<String> getAllGenderTags(){
        ArrayList<String> genderTags = new ArrayList<>();
        for(Sentence s : sentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                genderTags.add(w.getGender());
            }
        }

        return genderTags;
    }

    public ArrayList<String> getAllNumberTags(){
        ArrayList<String> numberTags = new ArrayList<>();
        for(Sentence s : sentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                numberTags.add(w.getNumber());
            }
        }

        return numberTags;
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


