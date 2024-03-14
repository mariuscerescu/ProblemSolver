package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

        Problem problem = new Problem("Ion asd ssdfsd dfsd arsfs de as dasasda s10 ani. Câți ani arasdas e Ion");

        System.out.println(problem.getQuestion());

    }

}
