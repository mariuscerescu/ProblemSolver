package org.example.solvers;

import org.example.Problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverManager {

    private Map<String, Solver> solvers;

    public SolverManager(Problem problem){
        solvers = new HashMap<>();
        solvers.put("simpleAddition", new SimpleAdditionSolver(problem));
        solvers.put("simpleDeletion", new SimpleDeletionSolver(problem));
    }

    public Map<String, Solver> getSolvers(){
        return solvers;
    }

    public static void main(String[] args) {
        SolverManager manager = new SolverManager(new Problem("Maria are 5 mere si primește încă 3 mere de la Ana. Câte mere are Maria acum?"));
        System.out.println(manager.solvers.get("simpleAddition").getSolutions().get(0));
        System.out.println(manager.solvers.get("simpleAddition").getSolutions().get(1));
    }

}
