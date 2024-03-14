package org.example.utils;

public class TextNormalization {

    public static String getProcessedText(String text){

        text = text.toLowerCase();

        text = text.strip();

        text = text.replaceAll("\\s+", " ");

        return text;
    }


}
