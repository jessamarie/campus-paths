package hw6;

import hw4.Digraph;
import hw4.Edge;
import hw4.Vertex;
import hw5.MarvelPaths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;

public class MarvelPaths2 {

	private Digraph<String, Double> double_graph;

	/*
	 * This class is not an ADT so does not have an Abstraction Function or
	 * Representation Invariant
	 * 
	 */

	/**
	 * Creates a graph from data in the file
	 * 
	 * @param filename
	 *            the file which contains the graph data
	 * @requires filename != null
	 * @throws IllegalArgumentException
	 *             if the filename is null, if the data is failed to be read or
	 *             the file data does not match our expected data.
	 */

	public void createNewGraph(String filename) {

		if (filename == null)
			throw new IllegalArgumentException("filename cannot be null.");

		// builds graph using hw 5 - used to obtain data so far
		Digraph<String, String> marvel_graph = new Digraph<String, String>();
		MarvelPaths mp = new MarvelPaths();
		marvel_graph = mp.createNewGraph(filename);

		// Initialize the new graph
		double_graph = new Digraph<String, Double>();

		// add characters to the graph
		Set<Vertex<String>> nodes = marvel_graph.getVertices();
		for (Vertex<String> node : nodes) {
			double_graph.insertNode(node.getLabel());
		}

		// Get characters of marvel graph
		List<Vertex<String>> nodesList = new ArrayList<Vertex<String>>(nodes);

		// For each character
		for (Vertex<String> source : nodesList) {

			// get the characters incident edges
			List<Edge<String, String>> edges = new ArrayList<Edge<String, String>>(
					marvel_graph.incidentEdges(source.getLabel()));

			// while the character still has edges left to look at.
			while (!edges.isEmpty()) {

				// Get the next edge and remove it from edges
				Edge<String, String> edge = edges.get(0);
				edges.remove(0);

				String destination = edge.getChild();

				double count = 1.0; // keeps track of common books
				int j = 0;

				// Search for other edges that have the same destination
				// as the current node. If so, then add 1.0 to count
				// Remove these edges as we go.
				while (j < edges.size()) {
					if (edges.get(j).getChild().equals(destination)) {
						count += 1.0;
						edges.remove(j);
					} else {
						j++;
					}
				}

				// Finally add the edges weight into the graph
				Double weight = 1 / count;
				double_graph.insertEdge(source.getLabel(), destination, weight);

			}
		}

	}

	/**
	 * findPath formats the the minumum cost path from CHAR1 to CHAR2
	 * 
	 * @param CHAR1
	 *            the starting node
	 * @param CHAR2
	 *            the ending node
	 * @requires CHAR1 != null && CHAR2 != null
	 * @return a string of the minumum-cost path from start to end, or path not
	 *         found if no path exists from start to end
	 * @throws IllegalArgumentException
	 *             if either CHAR1 or CHAR2 is not in the graph
	 */
	public String findPath(String CHAR1, String CHAR2) {

		String pathway = "";

		if (CHAR1 == null || CHAR2 == null)
			throw new IllegalArgumentException("nodes cannot be null.");

		if (!(double_graph.containsNode(CHAR1))
				|| !(double_graph.containsNode(CHAR2))) {

			if (!double_graph.containsNode(CHAR1))
				pathway += "unknown character " + CHAR1 + "\n";
			if (!(CHAR1.equals(CHAR2)) && !double_graph.containsNode(CHAR2))
				pathway += "unknown character " + CHAR2 + "\n";

		} else {

			pathway = "path from " + CHAR1 + " to " + CHAR2 + ":\n";

			if (CHAR1.equals(CHAR2)) {
				double weight = 0.0;
				pathway += String.format("total cost: %.3f\n", weight);
				return pathway;
			}

			ArrayList<Edge<String, Double>> path = MarvelPaths2Finder.dijkstras(double_graph, CHAR1, CHAR2);

			if (path.size() == 0) {
				return pathway + "no path found\n";
			}

			// Format the path from CHAR1 to CHAR2
			String previous = CHAR1;
			String next;

			Double weightSum = 0.0;

			for (int i = 1; i < path.size(); i++) {

				next = path.get(i).getChild();

				Double weight = path.get(i).getLabel();

				weightSum += weight;

				pathway += String.format("%s to %s with weight %.3f\n",
						previous, next, weight);
				previous = next;
			}

			pathway += String.format("total cost: %.3f\n", weightSum);
		}

		return pathway;
	}
	
	/**
	 * AddConnection adds a new weight to the graph
	 * This method is used for testing purposes
	 * 
	 * @param char1
	 * @param char2
	 * @requires char1 and char2 exist in the graph
	 * @param weight the weight to be added
	 * @modifies double_graph
	 * @effect adds a node to the graph
	 */

	public void addConnection(String char1, String char2, double weight) {
		double_graph.insertEdge(char1, char2, weight);
	}
	
	/**
	 * removeConnection removes a weight from the graph
	 * This method is used for testing purposes
	 * 
	 * @param char1
	 * @param char2
	 * @param weight the weight to be removed
	 * @requires char1 and char2 exist in the graph
	 * @modifies double_graph
	 * @effect removes a node from the graph
	 */

	public void removeConnection(String char1, String char2, double weight) {
		double_graph.removeEdge(char1, char2, weight);
	}
}
