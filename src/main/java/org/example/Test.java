package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;
import org.example.pythonUtils.sentenceSimilarity.SSManager;
import org.matheclipse.core.expression.F;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {


//        Problem problem = new Problem("Dacă ai 3 mere și primești încă 2 mere de la un prieten, câte mere ai în total?");
//
//        System.out.println(problem.getQuestion());

//        File algorithmFolder = new File("src/main/java/org/example/solvingAlgorithms/simpleAddition/advice1.txt");
//
//        System.out.println(Files.readString(Paths.get(algorithmFolder.getPath())));


        System.out.println(SSManager.getSimilarity("salut, cum petreci ziua?", "salut, ce mai faci?"));

    }

}
