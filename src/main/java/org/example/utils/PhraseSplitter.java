package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PhraseSplitter {

    public static ArrayList<String> getData(String problem) {

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
        QuestionFinder questionFinder = new QuestionFinder();

        String question = questionFinder.getQuestion(problem);

        if(question != null){
            problem = problem.replace(question, "");
        }

        //Tokenization
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<String> tokens = tokenizer.getTokens(problem);

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
        int nrOfTokens = 0;

        for(int i = 0; i < tokens.size(); i++){

            nrOfTokens++;

            if(tokens.get(i).equals(".") || tokens.get(i).equals("!") || (tokens.get(i).equals(",") && nrOfTokens > 7) || i == tokens.size() - 1){

                endIndex = tokenIndices.get(i);

                if(i == tokens.size() - 1 && !tokens.get(i).equals(".") && !tokens.get(i).equals("!") && !tokens.get(i).equals(",")){
                    endIndex = problem.length();
                }

                String subString = problem.substring(startIndex, endIndex);

                if(nrOfTokens > 7 || sentences.size() == 0 || tokens.get(lastEndIndex).equals(".") || tokens.get(lastEndIndex).equals("!")){
                    sentences.add(subString);
                }else{
                        String lastSent = sentences.get(sentences.size() - 1);
                        String concatSent = lastSent + ", " + subString;
                        sentences.set(sentences.size() - 1, concatSent);
                }

                lastEndIndex = i;
                nrOfTokens = 0;

                if (i + 2 < tokens.size() && junctionPointsList.contains(tokens.get(i+1).toLowerCase())){
                        startIndex = tokenIndices.get(i + 2);
                        if(tokens.get(i + 2).equals(",")){
                            startIndex = tokenIndices.get(i + 3);
                        }
                } else if (i + 1 < tokenIndices.size()) {
                        startIndex = tokenIndices.get(i + 1);
                        if(tokens.get(i + 1).equals(",")){
                            startIndex = tokenIndices.get(i + 2);
                        }
                }

            }

        }

        if(sentences.size() == 0){
            sentences.add(problem.substring(startIndex, endIndex));
        }
        return sentences;

    }

//    public static void main(String[] args) {
//        ArrayList<String> sentences =  getData("Daisy este o cățelușă pudel care adoră să se joace cu jucăriile ei. Deseori le pierde în diverse moduri, iar stăpânul ei trebuie să le înlocuiască. Luni, Daisy s-a jucat cu 5 jucării pentru câini. Marți, Daisy a rămas cu 3 jucării pentru câini după ce a pierdut câteva, iar stăpânul ei s-a dus la magazin și i-a mai luat 3 jucării. Miercuri, toate jucăriile vechi și noi ale lui Daisy lipseau, așa că stăpânul ei s-a dus la magazin și i-a mai cumpărat 5 jucării. Dacă stăpânul lui Daisy ar fi găsit toate jucăriile pierdute, inclusiv cele noi, câte jucării de câine ar avea acum Daisy?");
//        for(String sentence : sentences){
//            System.out.println(sentence);
//        }
//    }

}
//Betty economisește bani pentru un portofel nou care costă 100 de dolari. Betty are doar jumătate din banii de care are nevoie. Părinții ei au decis să-i dea 15 dolari în acest scop, iar bunicii ei de două ori mai mult decât părinții ei. De câți bani mai are nevoie Betty pentru a cumpăra portofelul?
//Ken a creat un pachet de îngrijire pentru a-l trimite fratelui său, care era plecat la internat.  Ken a așezat o cutie pe un cântar, apoi a turnat în cutie suficiente jeleuri pentru a aduce greutatea la 2 kilograme.  Apoi, a adăugat suficiente negrese pentru ca greutatea să se tripleze.  Apoi, a adăugat încă 2 kilograme de jeleuri.  Și, în cele din urmă, a adăugat suficienți viermi gumați pentru a dubla din nou greutatea.  Care a fost greutatea finală a cutiei de bunătăți, în kilograme?
//Un monstru de mare adâncime iese din ape o dată la o sută de ani pentru a se hrăni cu o navă și a-și potoli foamea. În trei sute de ani, a consumat 847 de oameni. Navele au fost construite mai mari de-a lungul timpului, astfel încât fiecare navă nouă are de două ori mai mulți oameni decât ultima navă. Câți oameni se aflau pe nava pe care a mâncat-o monstrul în prima sută de ani?
//Într-un camion, există 26 de căști de protecție roz, 15 căști de protecție verzi și 24 de căști de protecție galbene.  Dacă Carl ia 4 căști de protecție roz, iar John ia 6 căști de protecție roz și de două ori mai multe căști de protecție verzi decât numărul de căști de protecție roz pe care le-a luat, calculați numărul total de căști de protecție care au rămas în camion.