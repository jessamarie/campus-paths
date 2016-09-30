package hw8;

public class VarExp extends BooleanExp {

	final static int VAR = 1;

	private String varname;

	public VarExp(String varname) {
		this.varname = varname;
	}

	public String getName() {
		return varname;
	}

	public boolean evaluate(Context c) {
		return c.lookup(varname);
	}

	public int getExpressionCode() {
		return VAR;
	}
	
	@Override
	public String printPreorder() {
		return varname.toString();
	}

	@Override
	public String printInOrder() {

		return varname.toString();
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(varname);
		return result.toString();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

}
