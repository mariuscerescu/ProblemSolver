package org.example;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MetaDataExtractor;
import org.example.problemMetaData.ProblemMetaData;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        MetaDataExtractor metaData = new MetaDataExtractor(BinPosRoRunner.runTextAnalysis("La mare, Mihaela a adunat 30 de scoici și 5 pietricele. Câte obiecte formează colecția Mihaelei"));

        System.out.println(metaData.lemmaTags);

    }

}
