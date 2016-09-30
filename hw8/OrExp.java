package hw8;

public class OrExp extends CompositeExp {

	private BooleanExp leftExp;
	private BooleanExp rightExp;
	
	final static int OR = 3;

	public OrExp(BooleanExp left, BooleanExp right) {
		leftExp = left;
		rightExp = right;
	}

	public BooleanExp getLeft() {
		return leftExp;
	}

	public BooleanExp getRight() {
		return rightExp;
	}
	
	public int getExpressionCode(){
		return OR;
	}

	public boolean evaluate(Context c) {
		return leftExp.evaluate(c) || rightExp.evaluate(c);
	}

	public String printPreorder() {
		return this.toString();
	}

	public String printInOrder() {
		StringBuffer result = new StringBuffer();

		if (this.getLeft().getExpressionCode() > OR) {
			result.append("(");
			result.append(this.getLeft().printInOrder());
			result.append(")");

		} else {
			result.append(this.getLeft().printInOrder());

		}
		result.append(" OR ");
		if (this.getRight().getExpressionCode() >= OR) {
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

		result.append("OR ");
		result.append(leftExp.toString());
		result.append(" ");
		result.append(rightExp.toString());

		return result.toString();
	}

	public void accept(Visitor v) {
		//leftExp.accept(v);
		//rightExp.accept(v);
		v.visit(this);
	}
	



}
