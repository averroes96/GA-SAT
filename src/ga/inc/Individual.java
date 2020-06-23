/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.inc;

/**
 *
 * @author user
 */
public class Individual {
    
	private Solution solution;
	private int evaluation;


	public Individual(Clauses clset) { /* Constructor 1 : Create an individual with a random solution */
		this.solution = new Solution(clset.getNumberVariables());
		this.solution.randomSolution();
		this.evaluation = this.solution.satisfiedClauses(clset);
	}

	public Individual(Clauses clset, Solution sol) { /* Constructor 2 : Create an individual with a specific solution */
		this.solution = new Solution(sol);
		this.evaluation = sol.satisfiedClauses(clset);
	}

	public Individual(Individual ind) { /* Constructor 3 : Copy an individual */
		this.solution = new Solution(ind.getSolution());
		this.evaluation = ind.getEvaluation();
	}


	public Solution getSolution() { return solution; }
        
	public int getEvaluation() { return evaluation; }


	public void makeMutation(Clauses clset, int position) {
		this.solution.invertLiteral(position);
		this.evaluation = this.solution.satisfiedClauses(clset); /* Update the "valuation" value (the solution has been changed) */
	}


	@Override
	public String toString() {
		return "Individual [solution=" + solution + ", evaluation=" + evaluation + "]";
	}    
    
}
