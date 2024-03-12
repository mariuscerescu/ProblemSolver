package org.example.solvers;


import org.example.Problem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SimpleAdditionSolver extends Solver{

    private Problem problem;
    private List<String> solutions;
    private String equation = null;
    private String result;

    public SimpleAdditionSolver(Problem problem){
        this.problem = problem;
        solutions = new ArrayList<>();
        obtainSolutions();
    }

    public List<String> getSolutions(){
        return solutions;
    }

    private void obtainSolutions(){
        List<Integer> numbers = new ArrayList<>();
        List<String> tokens = problem.getProblemMetaData().tokens;
        List<String> posTags = problem.getProblemMetaData().posTags;

        for(int i = 0; i < tokens.size(); i++){

            if(posTags.get(i) != null && posTags.get(i).equals("NUMERAL")){
                numbers.add(Integer.parseInt(tokens.get(i)));
            }

        }

        if(numbers.size() == 2){
            equation = numbers.get(0) + " + " + numbers.get(1) + " = x";
            result = Integer.toString(numbers.get(0) + numbers.get(1));
            solutions.add(equation);
            solutions.add(result);
        }
    }

    public static void main(String[] args) {
        SimpleAdditionSolver solver = new SimpleAdditionSolver(new Problem("Maria are 5 mere si primește încă 3 mere de la Ana. Câte mere are Maria acum?"));
    }

}
