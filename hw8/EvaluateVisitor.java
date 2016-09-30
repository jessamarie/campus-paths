package hw8;

public class EvaluateVisitor implements Visitor {
	
	private boolean eval;
	private Context c;
	
	public EvaluateVisitor(Boolean eval, Context c){
		this.eval = eval;
		this.c = c;
	}
	
	public boolean getEval(){
		return eval;
	}
	
	public boolean evaluate(BooleanExp exp, Context c){
		
		return exp.evaluate(c);
	}
	
	@Override
	public void visit(Constant e) {
		eval = e.getValue();
	}

	@Override
	public void visit(VarExp e) {
		eval = c.lookup(e.getName());
	}

	@Override
	public void visit(NotExp e) {
		eval = evaluate(e, c);

	}

	@Override
	public void visit(AndExp e) {
		e.getLeft().accept(this);
		e.getRight().accept(this);
		
     	eval = evaluate(e.getLeft(),c) && evaluate(e.getRight(),c);
	}

	@Override
	public void visit(OrExp e) {
		e.getLeft().accept(this);
		e.getRight().accept(this);
		eval = evaluate(e.getLeft(),c) || evaluate(e.getRight(),c);
	}

}
