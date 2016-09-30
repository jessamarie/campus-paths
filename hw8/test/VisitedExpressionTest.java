package hw8.test;

import static org.junit.Assert.*;
import hw8.BooleanExp;
import hw8.ExpressionParser;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

public class VisitedExpressionTest {

	private static ExpressionParser parser = null;
	private static HashMap<String,Boolean> contextMap = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	parser = new ExpressionParser();
    	contextMap = new HashMap<String,Boolean>();
    	contextMap.put("x", true);
    	contextMap.put("y", false);
    	contextMap.put("z", true);
    	contextMap.put("w", false);
    }

	@Test
	public void test0() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("x");
		parser.init(e,contextMap);
		assertEquals(true, parser.visitorEvaluate());
		assertEquals("x", parser.visitorPrint()); //ANA: new
	}
    
	@Test
	public void test1() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("true");
		parser.init(e,contextMap);
		assertEquals(true, parser.visitorEvaluate());
		assertEquals("true", parser.visitorPrint()); //ANA: new
	}
	
	@Test
	public void test2() {
		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("OR AND x y z");
		parser.init(e, contextMap);
		assertEquals(true, parser.visitorEvaluate());
		assertEquals("x AND y OR z", parser.visitorPrint());
	}

	@Test
	public void test3() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND OR x y OR x w");
		parser.init(e, contextMap);
		assertEquals(true, parser.visitorEvaluate());
		assertEquals("(x OR y) AND (x OR w)", parser.visitorPrint());

	}

	@Test
	public void test4() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("NOT x");
		parser.init(e, contextMap);
		assertEquals(false, parser.visitorEvaluate());
		assertEquals("NOT x", parser.visitorPrint());

	}
	
	@Test
	public void test5() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND NOT x OR x w");
		// assertEquals("AND NOT x OR x w", e.toString());
		parser.init(e, contextMap);
		assertEquals(false, parser.visitorEvaluate());
		assertEquals("NOT x AND (x OR w)", parser.visitorPrint());

	}

	
}
