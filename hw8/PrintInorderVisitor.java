package hw8;

public class PrintInorderVisitor implements Visitor {
	
	StringBuffer result;
	
	public PrintInorderVisitor(){
		result = new StringBuffer();
	}

	public String toString(){
		return result.toString();
	}
	
	@Override
	public void visit(Constant e) {
		result.append(e.toString());
	}

	@Override
	public void visit(VarExp e) {
		result.append(e.getName());
	}

	@Override
	public void visit(NotExp e) {
		result.append("NOT ");
		e.getVar().accept(this);
	}

	@Override
	public void visit(AndExp e) {
		
		if (e.getLeft().getExpressionCode() > AndExp.AND) {
			result.append("(");
			e.getLeft().accept(this);
			result.append(")");
			result.append(" AND ");
		} else {
			e.getLeft().accept(this);
			result.append(" AND ");
		}
		
		if (e.getRight().getExpressionCode() >= AndExp.AND) {
			result.append("(");
			e.getRight().accept(this);
			result.append(")");
		} else {
			e.getRight().accept(this);
		}
	}

	@Override
	public void visit(OrExp e) {
		if (e.getLeft().getExpressionCode() > OrExp.OR) {
			result.append("(");
			e.getLeft().accept(this);
			result.append(")");
			result.append(" OR ");
		} else {
			e.getLeft().accept(this);
			result.append(" OR ");
		}
		
		if (e.getRight().getExpressionCode() >= OrExp.OR) {
			result.append("(");
			e.getRight().accept(this);
			result.append(")");
		} else {
			e.getRight().accept(this);
		}
	}


}
