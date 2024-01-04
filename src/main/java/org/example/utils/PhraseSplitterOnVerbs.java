package org.example.utils;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;
import org.example.problemMetaData.Sentence;
import org.example.problemMetaData.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhraseSplitterOnVerbs {

    public ArrayList<String> getData(String problem) {

        ArrayList<String> sentences = new ArrayList<>();

        //Getting the Junction Points
        Path junctionPointsPath = Paths.get("src/main/java/org/example/files/junctionPoints.txt");
        List<String> junctionPointsList;
        try {
            junctionPointsList = Files.readAllLines(junctionPointsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Removing the sentence
        String question = QuestionFinder.getQuestion(problem);

        if(question != null){
            problem = problem.replace(question, "");
        }


        MathWordProblem mathWordProblem = BinPosRoRunner.runTextAnalysis(problem);
        List<Sentence> problemSentences = mathWordProblem.getSentences();

        //Tokenization
        //POS Tags extraction
        List<String> posTags = new ArrayList<>();
        List<String> tokens = new ArrayList<>();

        for(Sentence s : problemSentences){
            List<Word> words = s.getWords();
            for(Word w : words){
                posTags.add(w.getPos());
                tokens.add(w.getContent());
            }
        }

        ArrayList<Integer> tokenIndices = new ArrayList<>();

        StringBuilder tempProblem = new StringBuilder();
        tempProblem.append(problem);
        StringBuilder replacement = new StringBuilder();

        for(String token : tokens){
            tokenIndices.add(tempProblem.indexOf(token));
            replacement.setLength(0);
            replacement.append("~".repeat(token.length()));
            tempProblem.replace(tempProblem.indexOf(token), tempProblem.indexOf(token) + token.length(), replacement.toString());
        }

        int startIndex = 0;
        int endIndex = problem.length();
        int lastEndIndex = 0;
        boolean verbWasPresent = false;

        for(int i = 0; i < tokens.size(); i++){

            if(posTags.get(i) != null && posTags.get(i).equals("VERB")){
                verbWasPresent = true;
            }

            if(tokens.get(i).equals(".") || tokens.get(i).equals("!") || (tokens.get(i).equals(",") && verbWasPresent) || i == tokens.size() - 1){

                endIndex = tokenIndices.get(i);

                if(i == tokens.size() - 1 && !tokens.get(i).equals(".") && !tokens.get(i).equals("!") && !tokens.get(i).equals(",")){
                    endIndex = problem.length();
                }

                String subString = problem.substring(startIndex, endIndex);

                if(verbWasPresent || sentences.size() == 0 || tokens.get(lastEndIndex).equals(".") || tokens.get(lastEndIndex).equals("!")){
                    sentences.add(subString);
                }else{
                        String lastSent = sentences.get(sentences.size() - 1);
                        String concatSent = lastSent + ", " + subString;
                        sentences.set(sentences.size() - 1, concatSent);
                }

                lastEndIndex = i;
                verbWasPresent = false;

                //Setting the startIndex after the junctionPoint
                if (i + 1 < tokens.size()) {
                    String subStringToCheckJunctionPoint = problem.substring(tokenIndices.get(i + 1)).toLowerCase();
                    boolean nextWordIsJunctionPoint = false;
                    String[] foundJunctionPointWords = null;

                    for (String junctionPoint : junctionPointsList) {
                        String junctionPointLower = junctionPoint.toLowerCase();
                        if (subStringToCheckJunctionPoint.startsWith(junctionPointLower)) {
                            MathWordProblem jp = BinPosRoRunner.runTextAnalysis(junctionPoint);
                            List<Sentence> jpSentences = jp.getSentences();
                            List<String> jpTokens = new ArrayList<>();
                            for(Sentence s : jpSentences){
                                List<Word> words = s.getWords();
                                for(Word w : words){
                                    jpTokens.add(w.getContent());
                                }
                            }
                            foundJunctionPointWords = jpTokens.toArray(new String[0]);
                            nextWordIsJunctionPoint = true;
                            break;
                        }
                    }

                    if (!nextWordIsJunctionPoint) {
                        startIndex = tokenIndices.get(i + 1);
                        if (",".equals(tokens.get(i + 1)) && i + 2 < tokens.size()) {
                            startIndex = tokenIndices.get(i + 2);
                        }
                    } else {
                        int junctionPointEndIndex = i + foundJunctionPointWords.length + 1;
                        if (junctionPointEndIndex < tokenIndices.size()) {
                            startIndex = tokenIndices.get(junctionPointEndIndex);
                            if (",".equals(tokens.get(junctionPointEndIndex)) && junctionPointEndIndex + 1 < tokens.size()) {
                                startIndex = tokenIndices.get(junctionPointEndIndex + 1);
                            }
                        }
                    }
                }

            }

        }

        if(sentences.size() == 0){
            sentences.add(problem.substring(startIndex, endIndex));
        }
        return sentences;

    }

    public static void main(String[] args) {
        PhraseSplitterOnVerbs phraseSplitter = new PhraseSplitterOnVerbs();
        ArrayList<String> sentences = phraseSplitter.getData("La mare, Mihaela a adunat 30 de scoici și 5 pietricele. Câte obiecte formează colecția Mihaelei?");
        for(String sentence : sentences){
            System.out.println(sentence);
        }
    }

}
//Betty economisește bani pentru un portofel nou care costă 100 de dolari. Betty are doar jumătate din banii de care are nevoie. Părinții ei au decis să-i dea 15 dolari în acest scop, iar bunicii ei de două ori mai mult decât părinții ei. De câți bani mai are nevoie Betty pentru a cumpăra portofelul?
//Ken a creat un pachet de îngrijire pentru a-l trimite fratelui său, care era plecat la internat.  Ken a așezat o cutie pe un cântar, apoi a turnat în cutie suficiente jeleuri pentru a aduce greutatea la 2 kilograme.  Apoi, a adăugat suficiente negrese pentru ca greutatea să se tripleze.  Apoi, a adăugat încă 2 kilograme de jeleuri.  Și, în cele din urmă, a adăugat suficienți viermi gumați pentru a dubla din nou greutatea.  Care a fost greutatea finală a cutiei de bunătăți, în kilograme?
//Un monstru de mare adâncime iese din ape o dată la o sută de ani pentru a se hrăni cu o navă și a-și potoli foamea. În trei sute de ani, a consumat 847 de oameni. Navele au fost construite mai mari de-a lungul timpului, astfel încât fiecare navă nouă are de două ori mai mulți oameni decât ultima navă. Câți oameni se aflau pe nava pe care a mâncat-o monstrul în prima sută de ani?
//Într-un camion, există 26 de căști de protecție roz, 15 căști de protecție verzi și 24 de căști de protecție galbene.  Dacă Carl ia 4 căști de protecție roz, iar John ia 6 căști de protecție roz și de două ori mai multe căști de protecție verzi decât numărul de căști de protecție roz pe care le-a luat, calculați numărul total de căști de protecție care au rămas în camion.