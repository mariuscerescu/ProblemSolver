package org.example.problemMetaData;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MetaDataExtractor {

    public ArrayList<String> tokens;
    public ArrayList<ArrayList<String>> tokensPerSentence;
    public ArrayList<String> posTags;
    public ArrayList<ArrayList<String>> posTagsPerSentence;
    public ArrayList<String> msdTags;
    public ArrayList<ArrayList<String>> msdTagsPerSentence;
    public ArrayList<String> typeTags;
    public ArrayList<ArrayList<String>> typeTagsPerSentence;
    public ArrayList<String> genderTags;
    public ArrayList<ArrayList<String>> genderTagsPerSentence;
    public ArrayList<String> numberTags;
    public ArrayList<ArrayList<String>> numberTagsPerSentence;
    public ArrayList<String> lemmaTags;
    public ArrayList<ArrayList<String>> lemmaTagsPerSentence;


    public MetaDataExtractor(ProblemMetaData metaData){
        tokens = metaData.getAllTokens();
        tokensPerSentence = metaData.getTokensPerSentence();
        posTags = metaData.getAllPOSTags();
        posTagsPerSentence = metaData.getPOSPerSentence();
        msdTags = metaData.getAllMSDTags();
        msdTagsPerSentence = metaData.getMSDPerSentence();
        typeTags = metaData.getAllTypeTags();
        typeTagsPerSentence = metaData.getTypesPerSentence();
        genderTags = metaData.getAllGenderTags();
        genderTagsPerSentence = metaData.getGenderPerSentence();
        numberTags = metaData.getAllNumberTags();
        numberTagsPerSentence = metaData.getNumbersPerSentence();
        lemmaTags = metaData.getAllLemmaTags();
        lemmaTagsPerSentence = metaData.getLemmasPerSentence();
    }

}
