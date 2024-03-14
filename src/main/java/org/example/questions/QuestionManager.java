package org.example.questions;

import org.example.Problem;
import org.example.classification.ClassifierManager;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {

    private final List<Question> questions;
    private final Problem problem;

    public QuestionManager(Problem problem){
        this.problem = problem;
        questions = new ArrayList<>();
        questions.add(new Q1ProblemTermsUnderstandingPrompter());
        questions.add(new Q2ProblemQuestionPrompter(problem));
        questions.add(new Q3ProblemDataPrompter(problem));
        questions.add(new Summarizer(problem));
        questions.add(new Q4ProblemSolvingAlgorithmPrompter(problem));
    }

    public void askQuestions(){
        if(problemIsValid()){
            for(Question question : questions){
                question.askQuestion();
            }
        }else{
            System.out.print("Îmi pare rău, nu pot rezolva această problemă.");
        }
    }

    public boolean problemIsValid(){
        ClassifierManager classifierManager = new ClassifierManager(problem.getText());
        String classification = classifierManager.findClassification();

        return classification != null;
    }

}
