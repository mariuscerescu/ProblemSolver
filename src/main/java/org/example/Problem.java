package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;
import org.example.utils.PhraseSplitter;
import org.example.utils.PhraseSplitterOnVerbs;
import org.example.utils.QuestionFinder;

import java.util.List;

public class Problem {

    private String text;
    private List<String> sentences;
    private String question;
    private MetaDataExtractor problemMetaData;
    private MetaDataExtractor questionMetaData;


    public Problem(String text){

        this.text = text;

        question = QuestionFinder.getQuestion(text);

//        PhraseSplitterOnVerbs splitter = new PhraseSplitterOnVerbs(BinPosRoRunner.runTextAnalysis(text));
        PhraseSplitter splitter = new PhraseSplitter();

        this.sentences = splitter.getData(text);


        StringBuilder builder = new StringBuilder();

        for(String sentence : sentences){
            builder.append("S: ").append(sentence).append(" ");
        }

        problemMetaData = new MetaDataExtractor(BinPosRoRunner.runTextAnalysis(builder.toString()));

//        questionMetaData = new MetaDataExtractor((BinPosRoRunner.runTextAnalysis(question)));
    }

    public MetaDataExtractor getProblemMetaData(){
        return problemMetaData;
    }

    public MetaDataExtractor getQuestionMetaData(){
        return questionMetaData;
    }

    public String getQuestion(){
        return question;
    }

    public String getText(){
        return text;
    }

    public List<String> getSentences(){
        return sentences;
    }

}
