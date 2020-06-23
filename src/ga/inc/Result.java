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
public class Result {
    
		private Solution solution;
		private float satisfiability;
		private long time;


		public Result(Clauses clset, Solution solution, long time) {
			this.solution = solution;
			this.satisfiability = (float)solution.satisfiedClauses(clset)/clset.getNumberClause();
			this.time = time;
		}


		public Solution getSolution() { return solution; }
		public float getSatisfiability() { return satisfiability; }
		public long getTime() { return time; }    
    
}
