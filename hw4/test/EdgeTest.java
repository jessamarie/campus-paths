package hw4.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import hw4.Edge;

import org.junit.BeforeClass;
import org.junit.Test;

public class EdgeTest {

	private static Edge<String,String> edge;
	
    private static String v1 = "v1";
    private static String v2 = "v2";

	// Pre-defined labels for testing
	private static String label1 = "label1";
	private static String label2 = "label2";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// vertex = new Vertex<String>("node");
		edge = new Edge<String, String>(v1, v2, label1);
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// // Edge<String,String> Tests
	// /////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testConstructorExceptions() {

		boolean thrown = false;

		// When a node is null
		try {
			new Edge<String,String>(null, v1, label1);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;

		// When label is null
		try {
			new Edge<String,String>(v2, null, label1);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;

		// When label is null
		try {
			new Edge<String,String>(v2, v1, null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testGetLabel() {
		assertEquals(label1, edge.getLabel());
	}

	@Test
	public void testGetParent() {
		assertEquals(v1, edge.getParent());
	}

	@Test
	public void testGetChild() {
		assertEquals(v2, edge.getChild());
	}

	@Test
	public void testToString() {
		assertEquals("<v1, v2, label1>", edge.toString());
	}
	
	@Test
	public void testEqualsSymmetric() {
		Edge<String,String> x = new Edge<String,String>(v1, v2, label1);
		Edge<String,String> y = new Edge<String,String>(v1, v2, label1);

		assertTrue("Transitive test fails x,y", x.equals(y));
		assertTrue("Transitive test fails y,x", y.equals(x));
		assertEquals(x.hashCode(), y.hashCode());
	}

	@Test
	public void testEqualsReflexive() {
		assertTrue("Edge<String,String> Reflexive test failed", edge.equals(edge));
		assertEquals(edge.hashCode(), edge.hashCode());
	}

	@Test
	public void testEqualsTransitive() {
		Edge<String,String> x = new Edge<String,String>(v1, v2, label1);
		Edge<String,String> y = new Edge<String,String>(v1, v2, label1);
		Edge<String,String> z = new Edge<String,String>(v1, v2, label1);

		assertTrue("Transitive test fails x,y", x.equals(y));
		assertTrue("Transitive test fails y,z", y.equals(x));
		assertTrue("Transitive test fails x,z", x.equals(z));

		assertEquals("Hashcodes not equal: x,y ", x.hashCode(), y.hashCode());
		assertEquals("Hashcodes not equal: y,z ", y.hashCode(), z.hashCode());
		assertEquals("Hashcodes not equal: x,z ", x.hashCode(), z.hashCode());

	}
	
	@Test
	public void testEdgesDontEqual() {
		Edge<String,String> x = new Edge<String,String>(v1, v2, label1);
		Edge<String,String> a = new Edge<String,String>(v2, v2, label1);
		Edge<String,String> b = new Edge<String,String>(v1, v1, label1);
		Edge<String,String> c = new Edge<String,String>(v1, v2, label2);
		
		assertFalse("Equals test failed x!=a : parents different", x.equals(a));
		assertFalse("Equals test failed x!=b : children different", x.equals(b));
		assertFalse("Equals test failed x!=c : labels different", x.equals(c));
		
		assertThat(x.hashCode(), is(not(a.hashCode())));
		assertThat(x.hashCode(), not(b.hashCode()));
		assertThat(x.hashCode(), not(equalTo(c.hashCode())));
	}

	@Test
	public void testEqualsNonEdgeObject() {
		assertFalse("Object is not an instance of Edge", edge.equals(new Double(5.0)));

	}
	
	@Test
	public void testCompareToReturnsZero() {
		Edge<String,String> a = new Edge<String,String>("A", "B", "label1");
		Edge<String,String> b = new Edge<String,String>("A", "B", "label1");
		
		assertEquals("Edge<String,String> a = b. Should return 0", 0, a.compareTo(b));
		
		
	}
	
	@Test
	public void testCompareToReturnsNegOne() {
		Edge<String,String> a = new Edge<String,String>("A", "B", "label1");
		Edge<String,String> b = new Edge<String,String>("A", "C", "label1");
		Edge<String,String> c = new Edge<String,String>("A", "C", "label2");
		
		assertEquals("Edge<String,String> a child < b child. Must return -1", -1, a.compareTo(b));
		assertEquals("Edge<String,String> b label < c label. Must return -1", -1, b.compareTo(c));
		
	}
	
	@Test
	public void testCompareToReturnsPosOne() {
		Edge<String,String> a = new Edge<String,String>("A", "B", "label1");
		Edge<String,String> b = new Edge<String,String>("A", "C", "label1");
		
		assertEquals("Edge<String,String> b child > a child. Must return 1", 1, b.compareTo(a));
		
	}

}
