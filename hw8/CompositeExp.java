package hw8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * CompositeExp is an interface for composite expressions
 *
 */

public abstract class CompositeExp extends BooleanExp {
	
	private List<BooleanExp> expressions;

	public CompositeExp(){
		this.expressions = new ArrayList<BooleanExp>();
	}
	
    public void add(BooleanExp expr){
    	expressions.add(expr); 
    }
    
    /**
     * Returns an iterator over the elements in expressions
     */
    public final Iterator<BooleanExp> iterator() {
        return this.expressions.iterator();
    }
	
	/**
	 * evaluates this boolean expression
	 */
	public abstract boolean evaluate(Context c);
	
	/**
	 * prints this boolean expression
	 */
	public abstract String printPreorder();
	
	/**
	 * prints this boolean expression in order
	 */
	public abstract String printInOrder();

	public abstract String toString();
	
	//
	public void accept(Visitor v){
		
		// for each child of this node
		for (BooleanExp child : expressions) {
			// traverses the structure rooted at child, performing vs operation
			// on every element
			//child.visit(v);
		}
	}

}
