package hw8;

public class Constant extends BooleanExp {

	final static int CONST = 0;

	private Boolean constant;

	public Constant(Boolean constant) {
		this.constant = constant;
	}

	public boolean getValue() {
		return constant;
	}

	@Override
	public int getExpressionCode() {
		return CONST;
	}
	
	@Override
	public boolean evaluate(Context c) {
		return constant;
	}

	@Override
	public String printPreorder() {
		return constant.toString();
	}

	@Override
	public String printInOrder() {
		return constant.toString();
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(constant);
		return result.toString();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
