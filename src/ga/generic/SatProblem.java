/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.generic;

import java.util.BitSet;

/**
 *
 * @author user
 */
public class SatProblem extends Problem<Instance ,SatSolution> {

    public SatProblem(Instance instance) {
        super(instance);
        setSOLUTION_SIZE(instance.getNbVars());
    }

    @Override
    public Solution getEmptySolution() {
        return new SatSolution(new BitSet(SOLUTION_SIZE));
    }
}
