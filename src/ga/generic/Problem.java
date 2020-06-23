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
public abstract class Problem <T, S> {

    private T instance ;
    long search_space ;
    public int SOLUTION_SIZE ;

    public Problem(T instance ) {
        this.instance = instance;
    }


    abstract public Solution<S, T> getEmptySolution() ;


    public void setSOLUTION_SIZE(int SOLUTION_SIZE) {
        this.SOLUTION_SIZE = SOLUTION_SIZE;
    }

    public T getInstance() {
        return instance;
    }

}