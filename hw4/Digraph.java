package hw4;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>Digraph</b> represents a mutable directed labeled graph.
 * <p>
 * 
 * @specfield edgeList : holds list of outgoing edges for each vertex
 * @specfield adjList : holds an adjacency list for each vertex
 *
 */

public class Digraph<V,E extends Comparable<E>> {

	/** Holds adjacency list for each vertex */
	private Map<Vertex<V>, ArrayList<Edge<V,E>>> adjList;
	
	/** Holds list of Digraph edges */
	private List<Edge<V,E>> edgeList;
	

	// Abstraction Function:
	// Digraph, g, represents a directed labeled graph
	// {} if g is an empty graph
	// if a is a node in g, then
	// {a=[], ...} if a has no outgoing edge and
	// {a=[<a,b,f>, <a,c,f>, ...], b=[...], c=[...]} o.w.
	// where b, c are destinations of a's outgoing edges,
	// and e, f are labels of those edges, respectively.
	//
	// Representation Invariant:
	// for every Graph g;
	// graph != null &&
	// adjList.keySet() has no duplicates or null elements
	// adjList.get(i) is not null
	// Any node included in an edge must be in adjList.keySet()

	/** A constant enabling the CheckRep method */
	private final static boolean ENABLE_CHECKREP = false;

	/**
	 * @effects Constructs a new empty Digraph
	 */

	public Digraph() {
		edgeList = new ArrayList<Edge<V,E>>();
		adjList = new HashMap<Vertex<V>, ArrayList<Edge<V,E>>>();
		
		checkRep();
	}

	/**
	 * Adds a node to the graph if it is not already in the graph
	 * 
	 * @param node the string to be added as a node to the graph
	 * @modifies this
	 * @requires node != null
	 * @effects adds a node to the graph
	 * @throws IllegalArgumentException
	 *             if node already exists or is null
	 * @return The new Vertex object
	 */

	public void insertNode(V node) {
		checkRep();
		
		if (node == null) {
			throw new IllegalArgumentException("Node can't be null");
		}

		if (adjList.containsKey(new Vertex<V>(node))) {
			throw new IllegalArgumentException("This node already exists");
		}
		
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
		// adds node to the graph
		adjListCopy.put(new Vertex<V>(node), new ArrayList<Edge<V,E>>());

		checkRep();
	}

	/**
	 * Removes a node and it's edges (if the node exists)
	 * 
	 * @param node the node to be removed
	 * @requires node != null
	 * @modifies nodeList
	 * @effects removes the node from graph
	 * @throws IllegalArgumentException
	 *             if node does not exist or is null
	 * @return the old Vertex object
	 */

	public void removeNode(V node) {
		checkRep();
		
		if (node == null) {
			throw new IllegalArgumentException("Node can't be null");
		}

		doesNodeExist(node);

		// Removes node from the graph (and it's edges)
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
		adjListCopy.remove(new Vertex<V>(node));
		
		checkRep();
	}

	/**
	 * Adds a directed labeled edge to the graph
	 * 
	 * @param src the origin (parent) of the edge
	 * @param dest the destination (child) of the edge
	 * @param label the label of the edge
	 * @requires src, dest, label != null
	 * @modifies this
	 * @effects adds a directed labeled edge to the graph 
	 * @throws IllegalArgumentException
	 *             if src or dest does not exist or are null
	 * @return the new Edge objects
	 */
	public void insertEdge(V src, V dest, E label) {
		
		checkRep();

		if (src == null || dest == null || label == null)
			throw new IllegalArgumentException(
					"Nodes and label cannot be null.");

		doesNodeExist(src);
		doesNodeExist(dest);

		// Create and add edge to graph
		Edge<V,E> newEdge = new Edge<V,E>(src, dest, label);
		List<Edge<V,E>> edgeListCopy = edgeList;
		
		edgeListCopy.add(newEdge);
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;

		adjListCopy.get(new Vertex<V>(newEdge.getParent())).add(newEdge);

		checkRep();
	}

	/**
	 * Removes a directed labeled edge from the graph
	 * 
	 * @param src the origin (parent) of the edge
	 * @param dest the destination (child) of the edge
	 * @param label label of the edge
	 * @requires src, dest, label != null
	 * @modifies this
	 * @effects removes a directed labeled edge from the graph 
	 * @throws IllegalArgumentException
	 *             if src or dest, or edge do not exist or are null
	 * @return the old Edge object
	 */

	public void removeEdge(V src, V dest, E label) {
		
		checkRep();

		if (src == null || dest == null || label == null)
			throw new IllegalArgumentException(
					"Nodes and label cannot be null.");

		doesNodeExist(src);
		doesNodeExist(dest);
		
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		List<Edge<V,E>> edgeListCopy = edgeList;
		
		Edge<V,E> oldEdge = new Edge<V,E>(src, dest, label);
		
		// Look for edge and remove it from adjList
		for (Edge<V,E> e : edgeListCopy) {
			if (e.equals(oldEdge)) {
				adjListCopy.get(new Vertex<V>(src)).remove(oldEdge);
				edgeListCopy.remove(oldEdge);
				break;
			}
		}

		checkRep();
	}

	/**
	 * Returns a list of nodes adjacent to a given node 
	 * i.e all the children of a given node
	 * 
	 * @param node a node in the digraph
	 * @requires node != null
	 * @throws IllegalArgumentException
	 *             if node does not exist or is null
	 * @return a list of outgoing nodes or an empty list if there are none
	 */

	public Set<Vertex<V>> getAdjacentNodes(V node) {
		
		checkRep();

		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null.");
		}

		doesNodeExist(node);

		Set<Vertex<V>> children = new HashSet<Vertex<V>>();
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
		for (Edge<V,E> e : adjListCopy.get(new Vertex<V>(node))) {
			children.add(new Vertex<V>(e.getChild()));
		}
		
		return children;
	}
	
	/**
	 * Returns a List of nodes in the graph
	 * 
	 * @return a list of nodes or an empty list if there are none
	 */

	public Set<Vertex<V>> getVertices() {
		checkRep();
		
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
		return adjListCopy.keySet();
	}

	
	/**
	 * Returns a list of edges in the graph
	 * 
	 * @return a list of edges or an empty list if there are none
	 */
	public List<Edge<V,E>> getEdges() {
		checkRep();
		List<Edge<V,E>> edgeListCopy = edgeList;
		
		return edgeListCopy;
	}

	/**
	 * Returns whether the node is in the graph or not
	 * 
	 * @param node the node contained in the graph
	 * @throws IllegalArgumentException
	 *             if node is null or does not exist
	 * @return true if element is this and false o.w.
	 */

	public boolean containsNode(V node) {
		
		checkRep();
		
		if (node == null){
			throw new IllegalArgumentException("Node cannot be null.");
		}
    	
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
    	return adjListCopy.containsKey(new Vertex<V>(node));
	}

	/**
	 * Returns whether the graph is empty or not
	 * 
	 * @return true if the graph is empty and false o.w
	 */

	public boolean isEmpty() {
		checkRep();
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		return adjListCopy.isEmpty();
	}

	/**
	 * Returns whether two nodes are adjacent so if 
	 * (src -> dest) means that src is adjacent to dest
	 * but not vica verca.
	 * 
	 * @param src the origin (parent node)
	 * @param dest the destination (child node)
	 * @requires src,dest != null
	 * @throws IllegalArgumentException
	 *             if src or dest node do not exist or are null
	 * @return true if src -> dest or false o.w
	 */

	public boolean containsEdge(V src, V dest) {
		checkRep();
		if (src == null || dest == null)
			throw new IllegalArgumentException("Nodes cannot be null.");
		
		doesNodeExist(src);
		doesNodeExist(dest);
		
		// Iterate over the edge List for key node.
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
		for(Edge<V,E> edge : adjListCopy.get(new Vertex<V>(src))){
			if(edge.getChild().equals(dest))
				return true;
		}
		return false;
	}
	
	// ADDED WITH HW5
	/**
	 * Returns a set of outgoing edges of node
	 * 
	 * @param node
	 * @requires n != null
	 * @throws IllegalArgumentException if node <var>n</var> 
	 * is not in this.nodes
	 * @return a set of outgoing edges of node <var>n</var>

	 */
	public Set<Edge<V,E>> incidentEdges(V node) {
		checkRep();
		if (node == null)
			throw new IllegalArgumentException("node cannot be null.");
		
		doesNodeExist(node);
		
		ArrayList<Edge<V,E>> edges = adjList.get(new Vertex<V>(node));
		
		return new HashSet<Edge<V,E>>(edges);
	}

	/**
	 * Helper Method to see if node exists in the graph and throws an Exception
	 * if it is not in the graph.
	 * 
	 * @param node the node to be evaluated
	 * @throws IllegalArgumentException if node does not exist in the graph.
	 */

	private void doesNodeExist(V node) {
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		
    	if (!adjListCopy.containsKey(new Vertex<V>(node))) {
    		throw new IllegalArgumentException("Vertex " + node.toString()+ " does not exist in graph");
    	}
    	
	}

	/**
	 * Returns string representation of the graph.
	 * 
	 * @return string representation of the graph
	 */
	public String toString() {
		Map<Vertex<V>, ArrayList<Edge<V,E>>> adjListCopy = adjList;
		return adjListCopy.toString();
	}

	/**
	 * Checks that the representation invariant holds (if any).
	 */

	private void checkRep() throws RuntimeException {

		if (ENABLE_CHECKREP) {

			// Check if the graph is null
			if (adjList == null)
				throw new RuntimeException("The Graph cannot be null.");

			// Check if edgeList is null
			if (edgeList == null) {
				throw new RuntimeException("The Edge list can't be null.");
			}

			

			for (Vertex<V> v : adjList.keySet()) {
				// make sure no node is null
				if (v == null)
					throw new RuntimeException("No node can be null.");

				// Be sure no edge elements are null
				for (Edge<V,E> e : adjList.get(v)) {
					if (e == null) {
						throw new RuntimeException("No edge can be null.");
					}
					
					// node must exist in keySet before it can be an element
					// of an edge
					if (!adjList.containsKey(e.getChild()))
						throw new RuntimeException(
								"Destination node of edge must be in the graph before the edge exists.");

				}

			}
		}
	}

} // End Graph
