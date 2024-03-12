package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;
import org.matheclipse.core.expression.F;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {


        Problem problem = new Problem("Da dă ai 3asd as das d mere și primești încă 2 mere de la un prieten, câte mere ai în total la mare?");

        System.out.println(problem.getQuestion());



    }

}
