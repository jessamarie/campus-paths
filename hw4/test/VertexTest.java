package hw4.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import hw4.Vertex;

import org.junit.BeforeClass;
import org.junit.Test;

public class VertexTest {

	private static Vertex<String> vertex;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertex = new Vertex<String>("node");
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// // Vertex Tests
	// /////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testConstructorExceptions() {

		boolean thrown = false;

		// When a node is null
		try {
			new Vertex<String>(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testGetLabel() {
		assertEquals("node", vertex.getLabel());
	}

	@Test
	public void testToString() {
		assertEquals("node", vertex.toString());
	}

	@Test
	public void testEqualsSymmetric() {
		Vertex x = new Vertex<String>("node");
		Vertex y = new Vertex<String>("node");
		assertTrue(x.equals(y));
		assertTrue(y.equals(x));
		assertEquals(x.hashCode(), y.hashCode());

	}

	@Test
	public void testEqualsReflexive() {
		assertTrue(vertex.equals(vertex));
		assertEquals(vertex.hashCode(), vertex.hashCode());
	}

	@Test
	public void testVertexEqualsTransitive() {
		Vertex x = new Vertex<String>("node");
		Vertex y = new Vertex<String>("node");
		Vertex z = new Vertex<String>("node");

		assertTrue("Transitive test fails x,y", x.equals(y));
		assertTrue("Transitive test fails y,z", y.equals(x));
		assertTrue("Transitive test fails x,z", x.equals(z));

		assertEquals("Hashcodes not equal: x,y ", x.hashCode(), y.hashCode());
		assertEquals("Hashcodes not equal: y,z ", y.hashCode(), z.hashCode());
		assertEquals("Hashcodes not equal: x,z ", x.hashCode(), z.hashCode());

	}
	
	@Test
	public void testObjectsDontEqual() {
		Vertex<String> x = new Vertex<String>("x");
		Vertex<String> y = new Vertex<String>("y");
		
		assertFalse("Equals test failed x!=y : parents different", x.equals(y));
		
		assertThat(x.hashCode(), is(not(y.hashCode())));

	}

	@Test
	public void testEqualsNonVertexObject() {
		Vertex<String> v1 = new Vertex<String>("node");
		assertFalse("Object is not an instance of Vertex", v1.equals(new Double(5.0)));

	}

	@Test
	public void testVertexCompareTo() {
		Vertex<String> a = new Vertex<String>("A");
		Vertex<String> a2 = new Vertex<String>("A");
		Vertex<String> b = new Vertex<String>("B");

		assertEquals(0, a.compareTo(a2));
		assertEquals(-1, a.compareTo(b));
		assertEquals(1, b.compareTo(a));

	}

}
