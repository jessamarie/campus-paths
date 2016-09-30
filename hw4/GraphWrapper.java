package hw4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <b>GraphWrapper</b> gw is a wrapper class which redirects methods from
 * the Digraph class.
 * <p>
 *
 */

public class GraphWrapper {
	
	private Digraph<String,String> graph;
	
	public GraphWrapper(){
		graph = new Digraph<String,String>();
	}
	
	public void addNode(String nodeData){
		graph.insertNode(nodeData);
	}
	
	public void addEdge(String parentNode, String childNode, String edgeLabel){
		graph.insertEdge(parentNode, childNode, edgeLabel);
	}
	
	public Iterator<String> listNodes(){
		
		Set<Vertex<String>> verts = new HashSet<Vertex<String>>();
		verts = graph.getVertices();
		
		List<String> vertStrings = new ArrayList<String>();
		
		for(Vertex<String> v : verts){
			vertStrings.add(v.getLabel());
		}	
		
		Collections.sort(vertStrings);
		
		return vertStrings.iterator();
	}
	
	  /**
	   * Return a collection of vertices adjacent to a given vertex parentNode.
	   *   i.e., the set of all vertices children where edges parentNode -> children 
	   *   exist in the graph.
	   *   
	   * @param parentNode one of the vertices in the graph
	   * @return an iterable collection of vertices adjacent to parentNode in the graph
	   */
	public Iterator<String> listChildren(String parentNode){
		
		/**
		 *  It returns iterator which returns the list of childNode(edgeLabel) in lexicographical 
		 *  (alphabetical) order by node name and secondarily by edge label. 
		 *  childNode(edgeLabel) means there is an edge with label edgeLabel
		 *   from parentNode to childNode. If there are multiple edges from parentNode to some childNode, 
		 *   there should be separate entry for each edge. If there is a reflexive edge, parentNode(edgeLabel) 
		 *   should be in the list. 
		 */
		
		Set<Edge<String,String>> kids = graph.incidentEdges(parentNode);
		List<String> kidsStrings = new ArrayList<String>();
		
		for(Edge<String,String> e : kids){
			kidsStrings.add(e.getChild() + "(" + e.getLabel() + ")");
		}
		
		Collections.sort(kidsStrings);
		
		return kidsStrings.iterator();
	}	

}