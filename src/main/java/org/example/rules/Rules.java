package org.example.rules;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.MathWordProblem;
import org.example.utils.CoreferenceSubstitution;
import org.example.utils.PhraseSplitterOnVerbs;
import org.example.utils.Tokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rules {

    private ArrayList<String> sentences;
    private ArrayList<ArrayList<String>> tokensPerSentence;
    private ArrayList<ArrayList<String>> msdPerSentence;
    private ArrayList<ArrayList<String>> typesPerSentence;
    private ArrayList<ArrayList<String>> posPerSentence;
    private MathWordProblem mathWordProblem;
    private ArrayList<String> tokens;
    ArrayList<Entity> entities = new ArrayList<>();
    ArrayList<String> posesie;
    ArrayList<String> regexes = new ArrayList<>();
    ArrayList<String> relations = new ArrayList<>();


    public StringBuilder equation = new StringBuilder();

    public Rules(MathWordProblem mathWordProblem) throws IOException {
        PhraseSplitterOnVerbs splitter = new PhraseSplitterOnVerbs(mathWordProblem);
        this.sentences = splitter.getSentences();
        for(String s: sentences){
            System.out.println(s);
        }
        setAttributes();
        posesie = (ArrayList<String>) Files.readAllLines(Paths.get("src/main/java/org/example/files/posesie.txt"));
    }

    private void setAttributes() throws IOException {
        StringBuilder builder = new StringBuilder();
        for(String sentence : sentences){
            builder.append("S: ").append(sentence).append(" ");
        }
        MathWordProblem analyzedSentences = BinPosRoRunner.runTextAnalysis(builder.toString());
        tokensPerSentence = analyzedSentences.getTokensPerSentence();
        msdPerSentence = analyzedSentences.getMSDPerSentence();
        typesPerSentence = analyzedSentences.getTypesPerSentence();
        posPerSentence = analyzedSentences.getPOSPerSentence();
        tokens = analyzedSentences.getAllTokens();
        this.mathWordProblem = analyzedSentences;
        setRegexArray();
    }

    public void setRegexArray() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/files/rules.txt"));
        String line, regex, relation;
        while((line = reader.readLine()) != null){
            regex = line.substring(0, line.indexOf("----")).strip();
            relation = line.substring(line.indexOf("----") + 5, line.lastIndexOf("----")).strip();

            regexes.add(regex);
            relations.add(relation);
        }
        reader.close();
    }


    //Dacă între două numere din aceeași propoziție se află „și” acele două numere trebuie adunate
    public void rule1(){
        boolean firstNrFound;
        boolean siWasFound;

        int firstNr = 0, secondNr;

        for (int i = 0; i < tokensPerSentence.size(); i++) {
            firstNrFound = false;
            siWasFound = false;

            for (int j = 0; j < tokensPerSentence.get(i).size(); j++) {
                String token = tokensPerSentence.get(i).get(j);

                if (msdPerSentence.get(i).get(j) != null && msdPerSentence.get(i).get(j).equals("M")
                        && ((j < tokensPerSentence.get(i).size() - 1 && msdPerSentence.get(i).get(j + 1).equals("Ncfprn") || msdPerSentence.get(i).get(j + 1).equals("Ncmprn"))
                        || (j < tokensPerSentence.get(i).size() - 2 && msdPerSentence.get(i).get(j + 2).equals("Ncfprn") || msdPerSentence.get(i).get(j + 2).equals("Ncmprn")))) {

                    if (!firstNrFound) {
                        firstNrFound = true;
                        firstNr = Integer.parseInt(token);
                        continue;
                    }

                    if (siWasFound) {
                        secondNr = Integer.parseInt(token);
                        equation.append("(").append(firstNr).append(" + ").append(secondNr).append(")");
                    }
                }

                if (token.equals("și")) {
                    siWasFound = true;
                }
            }
        }
    }

    public void checkForEntities(){

        ArrayList<String> owners = new ArrayList<>();

        for(int i = 0; i < tokensPerSentence.size(); i++){

            int ownersPerSentence = 0;

            for(int j = 0; j < tokensPerSentence.get(i).size(); j++){

                String pos = posPerSentence.get(i).get(j);
                String type = typesPerSentence.get(i).get(j);
                String token = tokensPerSentence.get(i).get(j);

                if(pos != null && pos.equals("NOUN") && type.equals("proper") && !owners.contains(token)){
                    ownersPerSentence++;
                }

            }

            if(ownersPerSentence == 1){

                Entity currentEntity = null;
                boolean possession = false;
                boolean quantityFound = false;
                boolean itemFound = false;

                for(int j = 0; j < tokensPerSentence.get(i).size(); j++){

                    String pos = posPerSentence.get(i).get(j);
                    String type = typesPerSentence.get(i).get(j);
                    String token = tokensPerSentence.get(i).get(j);

                    if(pos != null && pos.equals("NOUN") && type.equals("proper") && !owners.contains(token)){
                        owners.add(token);
                        currentEntity = new Entity();
                        currentEntity.owner = token;
                        entities.add(currentEntity);
                    }

                    if(currentEntity != null){
                        if(pos != null && pos.equals("VERB")){
                            if(posesie.contains(token)){
                                possession = true;
                            }
                        }
                    }

                    if(possession && pos != null && pos.equals("NUMERAL")){
                        currentEntity.quantity = Double.parseDouble(token);
                        quantityFound = true;
                    }

                    if(quantityFound && pos != null && pos.equals("NOUN") && type.equals("common")){
                        currentEntity.item = token;
                        itemFound = true;
                    }

                    if(itemFound && pos != null && pos.equals("ADJECTIVE")){
                        currentEntity.attribute = token;
                    }


                }
            }

        }

        for(Entity entity : entities){
            System.out.println("\n");
            System.out.println(entity.owner);
            System.out.println(entity.item);
            System.out.println(entity.quantity);
            System.out.println(entity.attribute);
            System.out.println("\n");
        }
    }

    public void checkForRelations(){

        if(entities.size() == 2){

            Entity firstEntity = null, secondEntity = null;

            int indexPF = -1; //index of the sentence where the pattern was found;
            String relation = null;
            int nr;

            for(int i = 0; i < sentences.size(); i++){

                for(int j = 0; j < regexes.size(); j++){

                    Pattern pattern = Pattern.compile(regexes.get(j));
                    Matcher matcher = pattern.matcher(sentences.get(i));

                    if(matcher.find()){
                        nr = Integer.parseInt(matcher.group(1));
                        indexPF = i;
                        relations.set(j, relations.get(j) + " " + nr);
                        relation = relations.get(j);
                        break;
                    }

                }

                if(indexPF != -1){
                    break;
                }
            }


            if(indexPF != -1){
                for(int i = 0; i < posPerSentence.get(indexPF).size(); i++){
                    String pos = posPerSentence.get(indexPF).get(i);
                    String type = typesPerSentence.get(indexPF).get(i);
                    String token = tokensPerSentence.get(indexPF).get(i);

                    if(pos != null && type != null && pos.equals("NOUNS") && type.equals("proper")){
                        for(int j = 0; j < entities.size(); j++){
                            if(entities.get(j).owner.equals(token)){
                                if(firstEntity == null){
                                    firstEntity = entities.get(j);
                                    firstEntity.relation = relation;
                                }else{
                                    secondEntity = entities.get(j);
                                }
                                break;
                            }
                        }
                    }

                }
            }

            if(firstEntity != null && secondEntity != null){
                firstEntity.relatedTo = secondEntity;
            }

        }
    }

    public void createEquation(){
         if(entities.size() == 2){
             for(Entity entity : entities){
                 if(entity.quantity != null && entity.relation != null){
                     equation.append(entity.quantity).append(" = ").append(entity.relation);
                 }
             }
         }
    }



    public static void main(String[] args) throws IOException {

        String sentence = "Ana are 8 nuci. Ea are cu 10 mai multe nuci decât Maria. Câte nuci are Maria?";

        MathWordProblem mathWordProblem = BinPosRoRunner.runTextAnalysis(sentence);

        CoreferenceSubstitution coreferenceSubstitution = new CoreferenceSubstitution(mathWordProblem);

        String result = coreferenceSubstitution.getSubstitution();

        mathWordProblem = BinPosRoRunner.runTextAnalysis(result);

        Rules rules = new Rules(mathWordProblem);

        rules.checkForEntities();

        rules.checkForRelations();

        rules.createEquation();

        System.out.println(rules.equation);


    }

}
