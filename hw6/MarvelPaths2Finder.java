package hw6;

import hw4.Digraph;
import hw4.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class MarvelPaths2Finder {

	/*
	 * This class is not an ADT so does not have an Abstraction Function or
	 * Representation Invariant
	 * 
	 */
	
	/**
	 * dijkstras finds the shortest path from start to end
	 * 
	 * @param start
	 *            the starting node
	 * @param end
	 *            the ending node
	 * @requires start and end exist in the graph and all edge weights in the
	 *           graph are nonnegative
	 * @return an ArrayList of edges that represents a path
	 */
	public static <V> ArrayList<Edge<V, Double>> dijkstras(Digraph<V,Double> double_graph, V start, V dest) {
		// start = starting node
		// dest = destination node

		// active = priority queue. Each element is a path from start to a given
		// node. A path's "priority" in the queue is the total cost of that path
		// Nodes for which no path is known yet are not in the queue.
		Comparator<ArrayList<Edge<V, Double>>> c = createWeightComparator();
		PriorityQueue<ArrayList<Edge<V, Double>>> active = new PriorityQueue<ArrayList<Edge<V, Double>>>(c);

		// finished = set of nodes for which we know the min-cost path from
		// start.
		Set<V> finished = new HashSet<V>();

		// Initially we only know of the path from start to itself, which has a
		// cost of zero because it contains no edges.
		// Add a path from start to itself to active
		ArrayList<Edge<V, Double>> pathToSelf = new ArrayList<Edge<V, Double>>();
		pathToSelf.add(new Edge<V, Double>(start, start, 0.0));
		active.add(pathToSelf);

		// while active is non-empty:
		while (!active.isEmpty()) {

			// minPath is the lowest-cost path in active and is the minimum-cost
			// path for some node
			// minPath = active.removeMin()
			ArrayList<Edge<V, Double>> minPath = active.poll();

			// minDest = destination node in minPath
			Edge<V, Double> last = minPath.get(minPath.size() - 1);
			V minDest = last.getChild();
			
			// if minDest is dest: return minPath
			if (minDest.equals(dest)) {
				return minPath;
			}

			// if minDest is in finished: continue
			if (finished.contains(minDest)) {
				continue;
			}

			// for each edge e = {minDest, child}:
			for (Edge<V, Double> e : double_graph.incidentEdges(minDest)) {

				// If we don't know the minimum-cost path from start to child,
				// examine the path we've just found
				// if child is not in finished:
				if (!finished.contains(e.getChild())) {

					// newPath = minPath + e
					ArrayList<Edge<V, Double>> newPath = new ArrayList<Edge<V, Double>>(
							minPath);
					newPath.add(e);

					// add newPath to active
					active.add(newPath);
				}
			}
			// add minDest to finished
			finished.add(minDest);
		}

		// If the loop terminates, then no path exists from start to dest.
		// The implementation should indicate this to the client.
		return new ArrayList<Edge<V, Double>>();
	}
	
	/**
	 * help function which returns a comparator which compares
	 * the weights of two paths if their are multiple edges between
	 * two nodes
	 * 
	 * The smaller one has the smaller sum of all the weight of edges.
	 * @param <V>
	 * @param <V>
	 * 
	 * @return the comparator used to compare weights of two paths.
	 */
	private static <V> Comparator<ArrayList<Edge<V, Double>>> createWeightComparator() {
		
		Comparator<ArrayList<Edge<V, Double>>> cmp = new Comparator<ArrayList<Edge<V, Double>>>() {
		
			public int compare(ArrayList<Edge<V, Double>> path1, ArrayList<Edge<V, Double>> path2) {
	
				double p1 = 0.0;
				double p2 = 0.0;
				for (int i = 0; i < path1.size(); i++) {
					p1 = p1 + path1.get(i).getLabel();
				}
				for (int i = 0; i < path2.size(); i++) {
					p2 = p2 + path2.get(i).getLabel();
				}
				if (p1 - p2 < 0) {
					return -1;
				} else if (p1 - p2 == 0) {
					return 0;
				} else {
					return 1;
				}
				
			}// end compare
			
		}; 
		return cmp;
	}
}
