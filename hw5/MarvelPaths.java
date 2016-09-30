package hw5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hw4.Digraph;
import hw4.Vertex;
import hw4.Edge;

/**
 * This class builds a graph using data from a file and
 * also computes the shortest path from one node to another
 * node
 * 
 * This class is not an ADT
 * 
 */

public class MarvelPaths {

	private final static String FILENAME = "hw5/data/marvel.csv";
	
	public Digraph<String,String> marvel_graph;
	
	 /** 
	 * This class is not an ADT so does not
	 * have an Abstraction Function or Representation Invariant
	 * 
	 */

	
	/**
	 * Creates a graph from data in the file
	 * 
	 * @param filename the file which contains the graph data
	 * @requires filename != null
	 * @throws IllegalArgumentException if the filename is null, if the data is 
	 * 		   failed to be read or the file data does not match our expected data.
	 */
	public Digraph<String,String> createNewGraph(String filename) {

		if (filename == null) {
			throw new IllegalArgumentException("The filename cannot be null.");
		}

		marvel_graph = new Digraph<String,String>();

		// To hold character data
		Set<String> characters = new HashSet<String>();

		// Maps characters to books
		Map<String, Set<String>> books = new HashMap<String, Set<String>>();

		
		// throw exception if file can't be read
		try {
			MarvelParser.readData(filename, books, characters);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error can't find file: "
					+ filename + " " + e);
		}
		

		// add nodes (characters) to the graph
		for (String character : characters) {
			marvel_graph.insertNode(character);
		}

		// add edges (books) to the graph
		for (String book : books.keySet()) {

			Set<String> chars = books.get(book);
			ArrayList<String> charList = new ArrayList<String>(chars);

			int i = 1;
			for (String char1 : chars) {
				Set<String> subset = new HashSet<String>(charList.subList(i,
						charList.size()));

				for (String char2 : subset) {

					// Create non-reflexive edges
					if (!(char1.equals(char2))) {
						marvel_graph.insertEdge(char1,char2,book);
						marvel_graph.insertEdge(char2, char1, book);
					}
				}
				i++;

			}
		}
		
		return marvel_graph;

	}
	
	/**
	 * findPath formats the the shortest path from node1 to node2
	 * 
	 * @param node1 the starting node
	 * @param node2 the ending node
	 * @requires node1 != null && node2 != null
	 * @return a string of the shortest path from start to end, or path not found if 
	 * no path exists from start to end
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	
	public String findPath(String node1, String node2) {

		String pathway = "";

		if (node1 == null || node2 == null)
			throw new IllegalArgumentException("nodes cannot be null.");

		if (!(marvel_graph.containsNode(node1))
				|| !(marvel_graph.containsNode(node2))) {

			if (!marvel_graph.containsNode(node1))
				pathway += "unknown character " + node1 + "\n";
			if (!(node1.equals(node2))
					&& !marvel_graph.containsNode(node2))
				pathway += "unknown character " + node2 + "\n";

		} else {

			pathway = "path from " + node1 + " to " + node2 + ":\n";

			
			if (node1.equals(node2)) {
				return pathway;
			}
			

			ArrayList<Edge<String,String>> path = BFS(node1, node2);

			if (path.size() == 0) {
				return pathway + "no path found\n";
			}

			// Format the path from node1 to node 2
			String previous = node1;
			String next;

			for (int i = 1; i < path.size(); i++) {
				next = path.get(i).getParent();
				String edge = path.get(i - 1).getLabel();
				pathway += previous + " to " + next + " via " + edge + "\n";
				previous = next;
			}

			String edge = path.get(path.size() - 1).getLabel();
			pathway += previous + " to " + node2 + " via " + edge + "\n";

		}

		return pathway;
	}
	
	/**
	 * BFS finds the shortest path from start to dest
	 * 
	 * @param start the starting node
	 * @param dest the ending node
	 * @requires start and dest exist in the graph
	 * @return an ArrayList of edges that represent a path
	 */
	
	private ArrayList<Edge<String,String>> BFS(String start, String dest){
		
		// Q = queue, or "worklist" of nodes to visit
		LinkedList<String> visitList = new LinkedList<String>();

		// M = Map from nodes to paths
		// Key = visited node
		// Value = path from start to that node
		HashMap<String, ArrayList<Edge<String,String>>> paths = new HashMap<String, ArrayList<Edge<String,String>>>();
		// A path is a list; YOU decide whether it is a list of nodes, or edges,
		// or node data, or edge data, or nodes and edges, or sth else
		
		
		// Add start to Q
		visitList.add(start); 
		
	    // add start ->[] to M (start mapped to an empty list)
		paths.put(start, new ArrayList<Edge<String,String>>()); 
		
		// while Q, or visitList, is not empty
		while (!(visitList.isEmpty())) {

			//Dequeue next node n
			String newParent = visitList.removeFirst();

			//If n is dest return the path associated with n in M
			//i.e If newParent is dest return the path 
			//associated with newParent in paths
			if (newParent.equals(dest)) {
				List<Edge<String,String>> path = paths.get(newParent);
				return new ArrayList<Edge<String,String>>(path);
			}
			
			// Orders edges lexicographically
			Set<Edge<String,String>> edgeSet = new TreeSet<Edge<String,String>>(new Comparator<Edge<String,String>>() {
				public int compare(Edge<String,String> e1, Edge<String,String> e2) {
					
					if (!(e1.getChild().equals(e2.getChild())))
						return e1.getChild().compareTo(e2.getChild());

					if (!(e1.getLabel().equals(e2.getLabel())))
						return e1.getLabel().compareTo(e2.getLabel());

					return 0;
				}
			});
			
			//get all incident edges of current node
			edgeSet.addAll(marvel_graph.incidentEdges(newParent));
			
			// for each edge e={n,m}:
			for (Edge<String,String> e : edgeSet) {
				
				// Let destination = m
				String destination = e.getChild();

				// if m is not in M (paths) i.e m has not been visited.
				if (!(paths.containsKey(destination))) {

					// Let p be the path n maps to in M (paths)
					ArrayList<Edge<String,String>> p = paths.get(newParent);
					
					//Let p' be the path formed by appending e to p
					ArrayList<Edge<String,String>> p_next = new ArrayList<Edge<String,String>>(p);
					p_next.add(e);
					
					//add m->p' to M (paths)
					paths.put(destination, p_next);
					
					// add m to visitList (Q) i.e
					// Mark the node as visited
					String nextDestination = destination;
					
					//add destination to the worklist
					visitList.add(nextDestination);
				}
			}
		}
		
		// If the loop terminates, then no path exists from start->dest
		return new ArrayList<Edge<String,String>>();
	}
	
	/**
	 * Main an interface to find path between two characters
	 * using command line arguments
	 * 
	 * @param args
	 * @throws Exception 
	 */
/*	public static void main(String[] args) throws Exception{
		
		MarvelPaths mp = new MarvelPaths();
		
		mp.createNewGraph(FILENAME);
		
		if (args.length != 2) {
			
			ArrayList<String> list = new ArrayList<String>();
			for (String a : args){
				list.add(a);
			}
			
			throw new IllegalArgumentException("Incorrect number of arguments in command line\n"
					+ "Format is: \"Character 1\" \"Character 2\"\n"
					+ "You entered: " + list );
        }
        
		String char1 = args[0];
		String char2 = args[1];
		
		System.out.println(mp.findPath(char1, char2));
		
	}*/

}
