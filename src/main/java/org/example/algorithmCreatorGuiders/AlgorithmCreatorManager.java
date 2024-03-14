package org.example.algorithmCreatorGuiders;

import org.example.algorithmCreatorGuiders.simpleAddition.SimpleAdditionGuider;

import java.util.HashMap;

public class AlgorithmCreatorManager {

    private final HashMap<String, AlgorithmCreatorGuider> guiders;

    public AlgorithmCreatorManager(){
        guiders = new HashMap<>();
        guiders.put("simpleAddition", new SimpleAdditionGuider());
    }

    public AlgorithmCreatorGuider getAlgCrGuider(String problemType){
        return guiders.get(problemType);
    }

}
