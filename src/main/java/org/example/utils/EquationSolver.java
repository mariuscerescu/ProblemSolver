package org.example.utils;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
public class EquationSolver {

    public static String solve(String equation) {
        // Create an instance of ExprEvaluator
        ExprEvaluator util = new ExprEvaluator();
        // Return the solution
        return util.evaluate("Solve(" + equation + ", x)").toString();
    }

    // Define the main method
    public static void main(String[] args) {



        System.out.println(EquationSolver.solve("4 + x == 1"));

    }

}
