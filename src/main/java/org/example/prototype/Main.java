package org.example.prototype;

import org.example.problemMetaData.BinPosRoRunner;
import org.example.problemMetaData.ProblemMetaData;
import org.example.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private ArrayList<String> sentences;
    private ArrayList<ArrayList<String>> tokensPerSentence;
    private ArrayList<ArrayList<String>> msdPerSentence;
    private ArrayList<ArrayList<String>> typesPerSentence;
    private ArrayList<ArrayList<String>> posPerSentence;
    private ProblemMetaData problemMetaData;
    private ArrayList<String> tokens;
    private String question;
    private ProblemMetaData questionMetaData;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<String> posesie;
    private ArrayList<String> regexes = new ArrayList<>();
    private ArrayList<String> relations = new ArrayList<>();
    private StringBuilder equation = new StringBuilder();
    private ArrayList<String> patternExp = new ArrayList<>();
    private int indexPF = -1; //index of the sentence where the pattern was found;
    private String relation = null;
    private String patternExplanation = null;

    public Main(ProblemMetaData problemMetaData) throws IOException {
        question = QuestionFinder.getQuestion(problemMetaData.problem);
        PhraseSplitterOnVerbs splitter = new PhraseSplitterOnVerbs(problemMetaData);
        this.sentences = splitter.getSentences();
        for(String s: sentences){
            System.out.println(s);
        }
        setAttributes();
        posesie = (ArrayList<String>) Files.readAllLines(Paths.get("src/main/java/org/example/files/possession.txt"));
    }

    private void setAttributes() throws IOException {
        StringBuilder builder = new StringBuilder();
        for(String sentence : sentences){
            builder.append("S: ").append(sentence).append(" ");
        }
        ProblemMetaData analyzedSentences = BinPosRoRunner.runTextAnalysis(builder.toString());
        tokensPerSentence = analyzedSentences.getTokensPerSentence();
        msdPerSentence = analyzedSentences.getMSDPerSentence();
        typesPerSentence = analyzedSentences.getTypesPerSentence();
        posPerSentence = analyzedSentences.getPOSPerSentence();
        tokens = analyzedSentences.getAllTokens();
        this.problemMetaData = analyzedSentences;
        setRegexArray();
        questionMetaData = BinPosRoRunner.runTextAnalysis(this.question);
    }

    public void setRegexArray() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/files/rules.txt"));
        String line, regex, relation, patternExplication;
        while((line = reader.readLine()) != null){
            regex = line.substring(0, line.indexOf("----")).strip();
            relation = line.substring(line.indexOf("----") + 5, line.lastIndexOf("----")).strip();
            patternExplication = line.substring(line.indexOf("Explicatie:") + 12);
            patternExp.add(patternExplication);
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
            System.out.println();
            System.out.println(entity.owner);
            System.out.println(entity.item);
            System.out.println(entity.quantity);
            System.out.println(entity.attribute);
            System.out.println();
        }
    }

    public void checkForRelations(){

        if(entities.size() == 2){

            Entity firstEntity = null, secondEntity = null;

            int nr;

            for(int i = 0; i < sentences.size(); i++){

                for(int j = 0; j < regexes.size(); j++){

                    Pattern pattern = Pattern.compile(regexes.get(j));
                    Matcher matcher = pattern.matcher(sentences.get(i));

                    if(matcher.find()){
                        System.out.println("Relation was found!");
                        nr = Integer.parseInt(matcher.group(1));
                        indexPF = i;
                        relations.set(j, relations.get(j) + " " + nr);
                        relation = relations.get(j);
                        patternExplanation = patternExp.get(j);
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

                    if(pos != null && type != null && pos.equals("NOUN") && type.equals("proper")){
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

        String attribute = null;
        String pNoun = null;
        String cNoun = null;

        int pNouns = 0, attributes = 0, cNouns = 0, possession = 0;

        ArrayList<String> questionPOS = questionMetaData.getAllPOSTags();
        ArrayList<String> types = questionMetaData.getAllTypeTags();
        ArrayList<String> tokens = questionMetaData.getAllTokens();

        for(int i = 0; i < tokens.size(); i++){

            if(questionPOS.get(i) != null && questionPOS.get(i).equals("NOUN") && types.get(i) != null && types.get(i).equals("proper")){
                pNouns++;
                pNoun = tokens.get(i);
            }

            if(questionPOS.get(i) != null && questionPOS.get(i).equals("NOUN") && types.get(i) != null && types.get(i).equals("common")){
                cNouns++;
                cNoun = tokens.get(i);
            }

            if(questionPOS.get(i) != null && questionPOS.get(i).equals("ADJECTIVE")){
                attributes++;
                attribute = tokens.get(i);
            }

            if(posesie.contains(tokens.get(i))){
                possession++;
            }

        }

        if(pNouns == 1 && (attributes == 1 || attributes == 0) && cNouns == 1 && possession == 1){
            if(entities.size() == 2){
                for(Entity entity : entities){

                    if(attributes == 0){

                        if(entity.quantity != null && entity.relation != null && entity.relatedTo.owner.equals(pNoun) && entity.item.equals(cNoun) && entity.relatedTo.quantity == null){
                            equation.append(entity.quantity).append(" == ").append(entity.relation);
                            System.out.println(equation);
                        }

                    }else{

                        if(entity.quantity != null && entity.relation != null && entity.relatedTo.owner.equals(pNoun) && entity.item.equals(cNoun) && entity.relatedTo.quantity == null && entity.attribute.equals(attribute)){
                            equation.append(entity.quantity).append(" == ").append(entity.relation);
                            System.out.println(equation);
                        }

                    }


                }
            }
        }

    }

    public void showExplanation(){

        if(equation != null){

            System.out.println("Pentru a rezolva problema vom crea o ecuație din datele furnizate pe care apoi o vom rezolva pentru a obține răspunsul.");

            System.out.println("Notăm cu „x” informația pe care trebuie să o aflăm.");

            System.out.println("În cazul nostu „x” va reprezenta " + question.toLowerCase().replace("?", "."));

            System.out.println("Deoarece știm că " + entities.get(0).owner + " are " + entities.get(0).quantity + " " + entities.get(0).item + " și de asemenea, ");

            System.out.println(sentences.get(indexPF));

            System.out.println("Iar deoarece " + patternExplanation);

            System.out.println("Putem crea ecuația: " + equation);


        }

    }


    public static void main(String[] args) throws IOException {

        String sentence = "Ana are 8 nuci. Ea are de 2 ori mai multe nuci decât Maria. Câte nuci are Maria?";

        ProblemMetaData problemMetaData = BinPosRoRunner.runTextAnalysis(sentence);

        CoreferenceSubstitution coreferenceSubstitution = new CoreferenceSubstitution(problemMetaData);

        String result = coreferenceSubstitution.getSubstitution();

        problemMetaData = BinPosRoRunner.runTextAnalysis(result);

        Main rules = new Main(problemMetaData);

        rules.checkForEntities();

        rules.checkForRelations();

        rules.createEquation();

        System.out.println();

        rules.showExplanation();

        if(!rules.equation.isEmpty()){
            System.out.println("Răspunsul este : " + EquationSolver.solve(rules.equation.toString()));
        }


    }

}
