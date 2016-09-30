package hw8;

public class NotExp extends CompositeExp {

	final static int NOT = 1;

	private BooleanExp var;

	public NotExp(BooleanExp var) {
		this.var = var;
	}

	public BooleanExp getVar() {
		return var;
	}

	public int getExpressionCode() {
		return NOT;
	}

	public boolean evaluate(Context c) {
		return !var.evaluate(c);
	}

	public String printPreorder() {
		return this.toString();
	}

	public String printInOrder() {
		return this.toString();
	}

	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("NOT ");
		result.append(var.toString());

		return result.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
	 

}
