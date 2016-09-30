package hw8;

public interface Visitor {
	
	public void visit(Constant e);
	public void visit(VarExp e);
	public void visit(NotExp e);
	public void visit(AndExp e);
	public void visit(OrExp e);
}
