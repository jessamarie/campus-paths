package hw4.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import hw4.*;

/* DigraphTest Documentation
 * 
 * I decided to first break up my tests into three parts: Vertex Tests,
 Edge Tests, and and Digraph Tests. This makes it easier to start small,
 making sure the Vertex and Edge classes work before moving on to the Digraph.
 For each test case, I worked generally from the top to the bottom of the main 
 classes, unless there was a more important method to test first. In the DigraphTest,
 I often tested many elements at once (with inspiration from IntPriorityQueueTest.java),
 since this ensures that the elements will work well together and it may not have 
 been as effective if done otherwise. For each method I made sure to test for valid and
 invalid inputs specified in the specifications  

 */

public class DigraphTest {

	private static Digraph<String,String> graph;

	// Pre-defined Vertices and Edges for testing
	private static Vertex<String> v1 = new Vertex<String>("v1");
	private static Vertex<String> v2 = new Vertex<String>("v2");
	private static Vertex<String> v3 = new Vertex<String>("v3");
	private static Edge<String,String> e1 = new Edge<String,String>("v1", "v2", "label1");
	private static Edge<String,String> e2 = new Edge<String,String>("v1", "v3", "label2");
	private static Edge<String,String> e3 = new Edge<String,String>("v3", "v2", "label2");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		graph = new Digraph<String,String>();
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// // Digraph Tests
	// /////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testDigraphConstructor() {
		assertTrue(graph.isEmpty());
	}

	@Test
	public void testDigraphToStringEmpty() {
		assertEquals("{}", graph.toString());
	}

	@Test
	public void testOneElement() {

		graph.insertNode("v1");
		assertEquals("{v1=[]}", graph.toString());

		graph.removeNode("v1");
		assertEquals("{}", graph.toString());

		assertTrue(graph.isEmpty());
	}

	@Test
	public void testContainsNode(){
		
		graph.insertNode("v1");
		assertTrue(graph.containsNode("v1"));

		graph.removeNode("v1");
		assertFalse(graph.containsNode("v1"));

		assertTrue(graph.isEmpty());
	}

	@Test
	public void testMultipleElements() {

		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");

		assertTrue(graph.containsNode("v1"));
		assertTrue(graph.containsNode("v2"));
		assertTrue(graph.containsNode("v3"));

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertFalse(graph.containsNode("v1"));
		assertFalse(graph.containsNode("v2"));
		assertFalse(graph.containsNode("v3"));

		assertTrue(graph.isEmpty());

	}

	@Test
	public void testInsertReflexiveEdges() {
		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertEdge("v1", "v1", "label1");
		graph.insertEdge("v2", "v2", "label2");

		assertTrue(graph.containsEdge("v1", "v1"));
		assertTrue(graph.containsEdge("v2", "v2"));

		graph.removeNode("v1");
		graph.removeNode("v2");

		assertTrue(graph.isEmpty());
	}

	@Test
	public void testMultipleEdges() {

		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");
		graph.insertNode("v4");

		graph.insertEdge("v1", "v2", "label1");
		graph.insertEdge("v1", "v3", "label2");
		graph.insertEdge("v2", "v4", "label3");
		graph.insertEdge("v1", "v4", "label4");

		assertTrue(graph.containsEdge("v1", "v2"));
		assertTrue(graph.containsEdge("v1", "v3"));
		assertTrue(graph.containsEdge("v1", "v4"));

		graph.removeEdge("v1", "v4", "label4");

		assertFalse(graph.containsEdge("v1", "v4"));

		graph.removeEdge("v1", "v2", "label1");
		graph.removeEdge("v1", "v3", "label2");
		graph.removeEdge("v1", "v4", "label3");

		assertFalse(graph.containsEdge("v1", "v2"));
		assertFalse(graph.containsEdge("v1", "v3"));
		assertFalse(graph.containsEdge("v1", "v4"));

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");
		graph.removeNode("v4");

		assertTrue(graph.isEmpty());

	}
	
	@Test
	public void RemoveNodeWithEdges() {
		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");

		graph.insertEdge("v3", "v1", "label1");
		graph.insertEdge("v3", "v2", "label2");

		assertTrue(graph.containsEdge("v3", "v1"));
		assertTrue(graph.containsEdge("v3", "v2"));

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testInsertNodeExceptions() {
		// Node is null
		boolean thrown = false;
		try {
			graph.insertNode(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// Node already Exists
		thrown = false;

		graph.insertNode("v1");
		try {
			graph.insertNode("v1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
		graph.removeNode("v1");

		assertTrue(graph.isEmpty());

	}

	@Test
	public void testRemoveNodeExceptions() {

		boolean thrown = false;

		// When node is null
		try {
			graph.removeNode(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		thrown = false;

		// When node does not exist

		try {
			graph.removeNode("v1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testInsertEdgeExceptions() {
		graph.insertNode("v1");
		graph.insertNode("v2");

		// Insert edge with null vertex
		boolean thrown = false;
		try {
			graph.insertEdge("v1", null, "label1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// Insert edge with null vertex (other)
		thrown = false;
		try {
			graph.insertEdge(null, "v1", "label1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// Insert edge with null label
		thrown = false;
		try {
			graph.insertEdge("v1", "v2", null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// vertex that does not exist in graph
		thrown = false;
		try {
			graph.insertEdge("v1", "v3", "label");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		graph.removeNode("v1");
		graph.removeNode("v2");
		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testRemoveEdgeExceptions() {
		graph.insertNode("v1");
		graph.insertNode("v2");

		// remove edge with null vertex
		boolean thrown = false;
		try {
			graph.removeEdge("v1", null, "label1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// remove edge with null vertex (other)
		thrown = false;
		try {
			graph.removeEdge(null, "v1", "label1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// remove edge with null label
		thrown = false;
		try {
			graph.removeEdge("v1", "v2", null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// remove edge with vertex that does not exist in graph
		thrown = false;
		try {
			graph.removeEdge("v1", "v3", "label");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		graph.removeNode("v1");
		graph.removeNode("v2");
		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testContainsNodeExceptions() {

		// Node is null
		boolean thrown = false;
		try {
			graph.containsNode(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testAreAdjacentExceptions() {
		graph.insertNode("v1");

		// Source node is null
		boolean thrown = false;
		try {
			graph.containsEdge(null, "v1");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// Destination node is null
		thrown = false;
		try {
			graph.containsEdge("v1", null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		// Node does not exist
		thrown = false;
		try {
			graph.containsEdge("v1", "v2");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

		graph.removeNode("v1");
		assertTrue(graph.isEmpty());

	}

	@Test
	public void testGetAdjacentNodesException() {

		// if node is null
		boolean thrown = false;
		try {
			graph.getAdjacentNodes(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}
	
	@Test
	public void testGetIncidentEdgesException() {

		// if node is null
		boolean thrown = false;
		try {
			graph.incidentEdges(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	// Added for homework 5
	@Test
	public void testGetIncidentEdges() {
		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");
		graph.insertEdge("v1", "v2", "label1");
		graph.insertEdge("v1", "v3", "label2");

		Set<Edge<String,String>> incidentEdges = new HashSet<Edge<String,String>>();
		incidentEdges.add(e1);
		incidentEdges.add(e2);

		assertEquals(incidentEdges, graph.incidentEdges("v1"));

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertTrue(graph.isEmpty());
	}
	
	@Test
	public void testGetAdjacentNodes() {

		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");
		graph.insertEdge("v1", "v2", "label1");
		graph.insertEdge("v1", "v3", "label2");

		Set<Vertex<String>> adjNodes_V1 = new HashSet<Vertex<String>>();
		adjNodes_V1.add(v2);
		adjNodes_V1.add(v3);

		assertEquals(adjNodes_V1, graph.getAdjacentNodes("v1"));

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertTrue(graph.isEmpty());

	}
	
	@Test
	public void testGetVertices() {
		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");

		Set<Vertex<String>> nodes = new HashSet<Vertex<String>>();
		nodes.add(v1);
		nodes.add(v2);
		nodes.add(v3);

		assertEquals(nodes, graph.getVertices());

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertTrue(graph.isEmpty());

	}
		
	@Test
	public void testGetEdges() {

		graph.insertNode("v1");
		graph.insertNode("v2");
		graph.insertNode("v3");
		graph.insertEdge("v1", "v2", "label1");
		graph.insertEdge("v1", "v3", "label2");
		graph.insertEdge("v3", "v2", "label2");

		List<Edge<String,String>> edges = new ArrayList<Edge<String,String>>();
		edges.add(e1);
		edges.add(e2);
		edges.add(e3);

		assertEquals(edges, graph.getEdges());

		graph.removeNode("v1");
		graph.removeNode("v2");
		graph.removeNode("v3");

		assertTrue(graph.isEmpty());
	}

}
