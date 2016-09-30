package hw8;

public class AndExp extends CompositeExp {

	private BooleanExp leftExp;
	private BooleanExp rightExp;
	final static int AND = 2;

	public AndExp(BooleanExp left, BooleanExp right) {
		leftExp = left;
		rightExp = right;
	}

	public BooleanExp getLeft() {
		return leftExp;
	}

	public BooleanExp getRight() {
		return rightExp;
	}

	public boolean evaluate(Context c) {
		return leftExp.evaluate(c) && rightExp.evaluate(c);
	}

	public String printPreorder() {
		return this.toString();
	}
	
	public int getExpressionCode(){
		return AND;
	}

	public String printInOrder() {
		StringBuffer result = new StringBuffer();

		if (this.getLeft().getExpressionCode() > AND) {
			// if left operand is an expression of equal or lower precedence,
			// parens are needed
			result.append("(");
			result.append(this.getLeft().printInOrder());
			result.append(")");
		} else {
			// otherwise, i.e., of higher precedence, no parens needed
			result.append(this.getLeft().printInOrder());
		}
		
		result.append(" AND ");
		
		if (this.getRight().getExpressionCode() >= AND) {
			result.append("(");
			result.append(this.getRight().printInOrder());
			result.append(")");
		} else {
			result.append(this.getRight().printInOrder());
		}
	
		return result.toString();
	}

	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("AND ");
		result.append(leftExp.toString());
		result.append(" ");
		result.append(rightExp.toString());

		return result.toString();
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
	 

}
