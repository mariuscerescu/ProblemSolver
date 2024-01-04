package org.example.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    public ArrayList<String> getTokens(String text){

        text = text.replaceAll("(\\d)\\.(\\d)", "$17614721$2");

        text = text.replaceAll("([a-zA-Z])\\-([a-zA-Z])", "$1qpydzvt$2");

        text = text.replaceAll("-(?=\\d)", " 6250285");

        text = text.replaceAll(",", " , ")
                   .replaceAll("\\.", " . ")
                   .replaceAll("\\?", " ? ")
                   .replaceAll("!", " ! ");

        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(text.split("\\s+|(?<=\\p{L})(?=\\P{L})|(?<=\\P{L})(?=\\p{L})|(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)"))); //\s+|(?<=\p{L})(?=\P{L})|(?<=\P{L})(?=\p{L}

//        tokens = combineWords(tokens);

        tokens.removeAll(Arrays.asList(null, "", " "));


        for(int i = 0; i < tokens.size(); i++){
            tokens.set(i, tokens.get(i).replaceAll("7614721", "."));
            tokens.set(i, tokens.get(i).replaceAll("qpydzvt", "-"));
            tokens.set(i, tokens.get(i).replaceAll("6250285", "-"));
        }

        return  tokens;

    }

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();

        ArrayList<String> tokens = tokenizer.getTokens("Ion a alcătuit un ierbar din 97 de plante: 90 din Codrii Orheiului, iar restul din Stepa Bugeacului. Câte plante de stepă sunt în ierbar?");

        System.out.println(Arrays.toString(tokens.toArray()));
    }

}
