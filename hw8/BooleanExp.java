package hw8;

/**
 * 
 * BooleanExp is an immutable class. It represents boolean constants, boolean
 * variables, Or boolean expressions and And boolean expressions.
 *
 */

public abstract class BooleanExp {
	
	// Rep invariant:
	// exprCode is AND || OR || CONST || VAR
	// if exprCode == AND || exprCode == OR then left != null and right != null and value = null and varString = null
	// else if exprCode == CONST then left == null and right == null and value != null and varString == null
	// else if exprCode == VAR then left == null and right == null and value == null and varString != null

	public BooleanExp() {
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
	
	/**
	 * @return: String corresponding to Preorder of this BooleanExp
	 * E.g., if BooleanExp is CONST with value true, result is "true"
	 * If BooleanExp is AND with left VAR "x" and right VAR "x", 
	 * result is "AND x y"  
	 */
	public abstract String toString();

	/**
	 * gets the precedence of the current expression
	 */
	public abstract int getExpressionCode();
	
	/**
	 * accept visitor
	 */
	
	public abstract void accept(Visitor v);
}
