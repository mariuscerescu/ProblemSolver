package org.example.solvers;


import org.example.Problem;

import java.util.ArrayList;
import java.util.List;

public class SimpleDeletionSolver extends Solver{

    private Problem problem;
    private List<String> solutions;
    private String equation = null;
    private String result;

    public SimpleDeletionSolver(Problem problem){
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
            int max = Math.max(numbers.get(0), numbers.get(1));
            int min = Math.min(numbers.get(0), numbers.get(1));
            equation = max + " - " + min + " = x";
            result = Integer.toString(max - min);
            solutions.add(equation);
            solutions.add(result);
        }
    }

    public static void main(String[] args) {
        SimpleDeletionSolver solver = new SimpleDeletionSolver(new Problem("Maria are 5 mere si primește încă 3 mere de la Ana. Câte mere are Maria acum?"));
    }

}
