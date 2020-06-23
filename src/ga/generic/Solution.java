/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.generic;

/**
 *
 * @author user
 */
public abstract class Solution<S, T> {

    S solution ;

    public Solution(S solution) {
        setValue(solution);
    }
    
    public Solution(){
        
    }
    

    abstract public Solution<S, T> randomSolution(Problem<T ,S> p);

    abstract public boolean isSolution(Problem<T ,S> p);

    abstract public int Fitness(Problem<T ,S> p) ;

    abstract public int Distance(Solution<S, T> s) ;

    abstract public void update(int position)  ;

    public S getValue(){
        return solution ;
    }

    public void setValue(S solution){
        this.solution =solution ;
    }


}
