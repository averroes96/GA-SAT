/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.inc;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Clause {
    
	private ArrayList<Integer> literals = new ArrayList<>();


	public Clause(ArrayList<Integer> literals) {
            literals.forEach((literal) -> {
                this.literals.add(literal);
            });
	}


	public int getLiteral(int position){
		return this.literals.get(position);
	}


	public int getNumbreLiterals() {
		return this.literals.size();
	}


	public boolean changeLiteral(int position, int literal) { /* Change a literal by another one in position "position" */
		if((position < 0) || (position >= this.literals.size())) /* Error : index out of array's bounds */
			return false;

		this.literals.set(position, literal);

		return true;
	}


	@Override
	public String toString() {
		return "Clause [literals=" + literals + "]";
	}
        
}
