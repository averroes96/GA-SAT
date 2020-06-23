/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.generic;

import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author user
 */
public class SatSolution extends Solution<BitSet ,Instance> {


    public SatSolution(BitSet solution) {
        super(solution);
    }
    
    public SatSolution(int size) { 
	for(int i=0; i<size; i++)
            this.solution.set(i, false);
    }    

    @Override
    public Solution<BitSet, Instance> randomSolution(Problem<Instance, BitSet> p) {
        SatSolution s = new SatSolution(new BitSet(p.SOLUTION_SIZE)) ;
        for (int i = 0; i < p.SOLUTION_SIZE; i++)
            if (new Random().nextBoolean()) s.getValue().flip(i);
        return s;
    }

    @Override
    public boolean isSolution(Problem<Instance, BitSet> p) {
        return p.getInstance().getNbClauses() - Fitness(p)   == 0;
    }
    
	public int randomLiteral(int literals) { /* Generate a random literal */
		int randomLiteral;

		do {
			randomLiteral = (int) (Math.random()*100)%literals + 1;
		}while(this.solution.get(randomLiteral -1));

		return randomLiteral * (((int) (Math.random()*10)%2) == 0 ? 1 : -1);
	}    

    /**
     * fitness of sat is nb of sat clauses
     * @param p
     * @return fitness value to MAXIMIZE
     */
    @Override
    public int Fitness(Problem<Instance, BitSet> p) {
        BitSet satisfied = new BitSet(p.SOLUTION_SIZE);
        for (int i = 0; i < getValue().length(); i++)
                satisfied.or(p.getInstance().getLiterals()[getValue().get(i) ? 1 : 0][i]);
        return satisfied.cardinality();
    }
    
    /**
     * hamming distance
     * @param s
     * @return
     */
    @Override
    public int Distance(Solution<BitSet, Instance> s) {
        BitSet distance = copy().getValue() ;
        distance.xor(s.getValue());
        return distance.cardinality();
    }

    @Override
    public void update(int position) {
        getValue().flip(position);
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getValue().length(); i++)
            s.append(getValue().get(i) ? i + 1 : -(i + 1)).append(" ");
        return String.valueOf(s);
    }

    private SatSolution copy() {
        BitSet copyBS = new BitSet(getValue().length()) ;
        copyBS.or(getValue());
        return new SatSolution(copyBS);
    }

    public BitSet getSolution() {
        return solution;
    }
    
    public boolean getLiteral(int position) { /* Get a literal at position "position" */
		return this.solution.get(position);
    }

    public int getActiveLiterals() {
		int activeLiterals=0;

                activeLiterals = this.solution.stream().filter((literal) -> (literal != 0)).map((_item) -> 1).reduce(activeLiterals, Integer::sum);

		return activeLiterals;
    }

	public boolean changeLiteral(int position, boolean value) { /* Change truth value of literal in position "position" */
		if((position < 0) || (position >= this.solution.size())) /* Error : index out of array's bounds */
			return false;

		this.solution.set(position, value);

		return true;
	}
        
	public boolean invertLiteral(int position) { /* Invert the truth value of a literal position "position" */
		if((position < 0) || (position >= this.solution.length())) /* Error : index out of array's bounds */
			return false;

		this.solution.set(position, this.solution.get(position));

		return true;
	}

	public boolean deleteLiteral(int position) { /* Delete literal in position "position" from this solution (set to "0") */
		if((position < 0) || (position >= this.solution.size())) /* Error : index out of array's bounds */
			return false;

		this.solution.set(position, false);

		return true;
	}

	public int getSolutionSize() { /* Get number of literals in the solution */
		return this.solution.size();
	}        

	public Integer difference(SatSolution solution) { /* Find Hamiltonian Distance: The number of positions with different values between two solutions */
		int numDiff = 0;

		if(this.getSolutionSize() != solution.getSolutionSize())
			return null; /* If the size of the two solutions isn't the same, we cannot compare them */

		 for(int i=0; i<this.getSolutionSize(); i++)
			 if(this.getLiteral(i) != solution.getLiteral(i))
				 numDiff++; /* Count number of different positions */

		 return numDiff;
	}
    
}
