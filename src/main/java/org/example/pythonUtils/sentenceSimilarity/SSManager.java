package org.example.pythonUtils.sentenceSimilarity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SSManager {

    private static final String sentence1FilePath = "src/main/java/org/example/pythonUtils/sentenceSimilarity/files/sentence1.txt";
    private static final String sentence2FilePath = "src/main/java/org/example/pythonUtils/sentenceSimilarity/files/sentence2.txt";
    private static final String similarityFilePath = "src/main/java/org/example/pythonUtils/sentenceSimilarity/files/similarity.txt";

    public static double getSimilarity(String sentence1, String sentence2){

        try {
            BufferedWriter writerSentence1 = new BufferedWriter(new FileWriter(sentence1FilePath));
            BufferedWriter writerSentence2 = new BufferedWriter(new FileWriter(sentence2FilePath));

            writerSentence1.write(sentence1);
            writerSentence2.write(sentence2);

            writerSentence1.close();
            writerSentence2.close();

            SSRunner.runScript();

            BufferedReader reader = new BufferedReader(new FileReader(similarityFilePath));

            double similarity = Double.parseDouble(reader.readLine());

            reader.close();

            return similarity;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
