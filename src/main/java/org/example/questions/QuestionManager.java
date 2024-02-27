package org.example.questions;

import org.example.Problem;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {

    private final List<Question> questions;

    public QuestionManager(Problem problem){
        questions = new ArrayList<>();
        questions.add(new Q1ProblemTermsUnderstandingPrompter());
        questions.add(new Q2ProblemQuestionPrompter(problem));
        questions.add(new Q3ProblemDataPrompter(problem));
        questions.add(new Q4ProblemSolvingAlgorithmPrompter(problem));
    }

    public void askQuestions(){
        for(Question question : questions){
            question.start();
        }
    }

}
