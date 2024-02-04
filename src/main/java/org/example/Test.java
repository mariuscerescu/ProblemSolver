package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        MetaDataExtractor metaData = new MetaDataExtractor(BinPosRoRunner.runTextAnalysis("Ce număr de specimene de stepă conține ierbarul? Câte plante de stepă sunt în ierbar?"));

        System.out.println(metaData.lemmaTags);


    }

}
