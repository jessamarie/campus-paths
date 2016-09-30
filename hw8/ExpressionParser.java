package hw8;

import java.util.*;

/**
 * Class ExpressionParser encapsulates a Boolean expression and a Context in 
 * which the expression is evaluated
 */
public class ExpressionParser {
	
	private BooleanExp expression;
	private Context context;
	
	/**
	 * This method is part of public interface. DO NOT REMOVE or change signature.
	 * 
	 * @param e is encapsulated Boolean expression
	 * @param hm is String->Boolean context map
	 */	
	public void init(BooleanExp e, HashMap<String,Boolean> hm) {
		expression = e;
		context = new Context(hm);
	}
	
		
	/** 
	  *  This method is part of public interface. DO NOT REMOVE or change signature.
	  * 
	  *  @param: str the string expression in preorder. E.g., AND OR x y z represents (x OR y) AND z
	  *  @return: returns the corresponding Boolean expression or null if str is invalid 
	  *  static "position" is used to avoid passing "position" as argument to the recursive calls
	  *  str must be a sequence of white-space separated strings, e.g., "OR x y", "AND OR x y OR z w"
	 */	
	
	public static int position;	
	
	public static BooleanExp parse(String str) {
		
		if (position >= str.length()) {
			return null;
		}
		
		String token = readNextToken(str);
		
		// Advance "position" beyond token
		position += token.length();
		
		return parseLeftAndRightOperands(str, token);
			
	}
	
	/**
	 * readNextToken is a helper method to read the next token in
	 * and expression
	 * 
	 * @param str the expression being parsed
	 * @return the current token if i = -1 
	 * 			or the next token after the current position o.w
	 * 	
	 */
	private static String readNextToken(String str) {
		
		int i = str.indexOf(" ",position);

		// Read the next token from String str.
		if (i != -1)
			return str.substring(position,i+1);
		else 
			return str.substring(position);
		
	}
	
	/**
	 * parseLeftAndRightOperands is a helper method to parse a token into the left or a
	 * right operand and create a boolean expression
	 * 
	 * @param str the expression being parsed
	 * @param token the current token being parsed
	 * @return returns the corresponding Boolean expression or null if str is invalid 
	  *  static "position" is used to avoid passing "position" as argument to the recursive calls
	  *  str must be a sequence of white-space separated strings, e.g., "OR x y", "AND OR x y OR z w"
	 */
	private static BooleanExp parseLeftAndRightOperands(String str, String token) {
	
		// If token is AND, parse the left operand into "left",
		// then parse the right operand into "right" and create
		// an And Boolean Expression
		
		if (token.equals("AND ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) {
				return null;
			}
			
			return new AndExp(left, right);
			
		} else if (token.equals("OR ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) {
				return null;
			}

			return new OrExp(left, right);
			
		} else if (token.equals("NOT ")) {
			BooleanExp var = parse(str);
			if (var == null) {
				return null;
			}

			return new NotExp(var);
			
		} else if (token.equals("true") || token.equals("true ")) {
			return new Constant(true);
		} else if (token.equals("false") || token.equals("false ")) {
			return new Constant(false);
		}
		// Otherwise, the token is a variable (e.g., x, xyz).
		// Get rid of the white space if necessary
		else {
			if (token.charAt(token.length() - 1) == ' ') {
				token = token.substring(0, token.length() - 1);
			}
			return new VarExp(token);
		}
		
	}

	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @return boolean value of the enclosed expression,
	 *         evaluated in Context context.
	 */	
	public boolean evaluate() {
		return evaluate(context, expression);		
	}

	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @param preorder  If True, returns expression in Preorder, False returns Inorder 
	 * @return string corresponding to Preorder of expression if preorder is True
	 * 	   string corresponding to Inorder of expression otherwise
	 */	
	public String print(boolean preorder) {
		return print(preorder,expression);
	}
	
	/**
	 * 
	 * @param preorder
	 * @param exp
	 * @return string corresponding to Preorder of exp if preorder is True
	 * 	   string corresponding to Inorder of exp otherwise
	 */	
	
	private String print(boolean preorder, BooleanExp exp) {
		StringBuffer result = new StringBuffer();

		if (preorder) { // print in Preorder

			result.append(exp.printPreorder());

		} else { // print Inorder, getting rid of redundant parentheses

			result.append(exp.printInOrder());
		}

		return result.toString();

	}

	/**
	 * 
	 * @param context
	 * @param exp
	 * @return value of BooleanExp exp in Context context
	 */
	private boolean evaluate(Context context, BooleanExp exp) {
		return exp.evaluate(context);

	}
	
	public boolean visitorEvaluate() {
		
		Boolean consta = false;
		
		EvaluateVisitor eval = new EvaluateVisitor(consta, context);
		expression.accept(eval);
		
		return eval.getEval();
	}
	
	public String visitorPrint() {

		PrintInorderVisitor print = new PrintInorderVisitor();
		
		expression.accept(print);
		return print.toString();
	}
	
	
}
