package org.example;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {

    private static final Properties properties;
    private static final String propertiesName = "tokenize,ssplit,pos,lemma,depparse";
    private static StanfordCoreNLP stanfordCoreNLP = null;

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    private Pipeline(){}

    public static synchronized StanfordCoreNLP getPipeline(){

        if(stanfordCoreNLP == null){
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }

        return stanfordCoreNLP;

    }

}