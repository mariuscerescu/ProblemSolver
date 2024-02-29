package org.example.classification;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ClassifierManager {

    private List<Classifier> classifiers;

    public ClassifierManager(String problem){
        classifiers = new ArrayList<>();
        classifiers.add(new SimpleAdditionClassifier(problem));
    }

    public String findClassification(){
        for(Classifier classifier : classifiers){
            String result = classifier.classifyProblem();
            if(result != null){
                return result;
            }
        }
        return null;
    }

}
