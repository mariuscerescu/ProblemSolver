package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataFinder {

    public static void getData(String problem){

        Path junctionPointsPath = Paths.get("junctionPoints.txt");
        List<String> junctionPointsList;
        try {
            junctionPointsList = Files.readAllLines(junctionPointsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> data = new ArrayList<>();

        //Removing the sentence
        QuestionFinder questionFinder = new QuestionFinder();

        String question = questionFinder.getQuestion(problem);

        if(question != null){
            problem = problem.replace(question, "");
        }

        //Problem Analysis
        TextAnalyzer textAnalyzer = new TextAnalyzer();
        textAnalyzer.runTextAnalysis(problem);

        //Tokenization
        ArrayList<String> listOftokens = textAnalyzer.getListOfTokens();

        ArrayList<Integer> tokenIndices = new ArrayList<>();

        StringBuilder tempProblem = new StringBuilder();
        tempProblem.append(problem);
        StringBuilder replacement = new StringBuilder();

        for(String token : listOftokens){
            tokenIndices.add(tempProblem.indexOf(token));
            replacement.setLength(0);
            replacement.append("~".repeat(token.length()));

            tempProblem.replace(tempProblem.indexOf(token), tempProblem.indexOf(token) + token.length(), replacement.toString());
        }

        //Get list of POS
        ArrayList<String> listOfPos = textAnalyzer.getListOfPOS();

        int startIndex = 0;
        int endIndex = problem.length();

        int nrOfVerbs = 0;

        for(int i = 0; i < listOftokens.size(); i++){

            if(listOfPos.get(i).equals("VERB")){
                if(i + 1 < listOfPos.size() && listOfPos.get(i + 1).equals("VERB")){
                    continue;
                }
                nrOfVerbs++;
            }

            if((listOftokens.get(i).equals(".") || listOftokens.get(i).equals("!") || listOftokens.get(i).equals(",")) && nrOfVerbs > 0){

                endIndex = tokenIndices.get(i);
                nrOfVerbs = 0;
                data.add(problem.substring(startIndex, endIndex));

                if (i + 2 < tokenIndices.size() && junctionPointsList.contains(listOftokens.get(i+1).toLowerCase())) {
                    startIndex = tokenIndices.get(i + 2);
                } else if (i + 1 < tokenIndices.size()) {
                    startIndex = tokenIndices.get(i + 1);
                }

            }

        }

        for(String sentence: data){
            System.out.println(sentence);
        }

    }

    public static void main(String[] args) {
        getData("Mark are o grădină cu flori. El a plantat în ea plante de trei culori diferite. Zece dintre ele sunt galbene și mai sunt încă 80% din cele de culoare mov. Există doar 25% mai multe flori verzi decât flori galbene și violet. Câte flori are Mark în grădina sa?");
    }

}
//Betty economisește bani pentru un portofel nou care costă 100 de dolari. Betty are doar jumătate din banii de care are nevoie. Părinții ei au decis să-i dea 15 dolari în acest scop, iar bunicii ei de două ori mai mult decât părinții ei. De câți bani mai are nevoie Betty pentru a cumpăra portofelul?
//Julie citește o carte de 120 de pagini. Ieri, a reușit să citească 12 pagini, iar astăzi a citit de două ori mai multe pagini decât ieri. Dacă vrea să citească mâine jumătate din paginile rămase, câte pagini ar trebui să citească?