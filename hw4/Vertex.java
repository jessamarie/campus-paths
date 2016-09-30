package hw4;

/**
 * <b>Vertex</b> represents a mutable node in a graph
 * <p>
 * 
 * @specfield nodeLabel: holds the the name of the node
 *
 */

public class Vertex<V> implements Comparable<Vertex<V>> {
    
	/** Holds the current node */
   private V nodeLabel;
   
   // Abstraction Function
   // Vertex v represents a node in a graph in the form of a string
   // such that if v is a Vertex with nodeLabel = "label" than v
   // is in the form of:
   // label
   
   // Rep Invariant
   // nodeLabel != null
    
	/**
	 * @param label the label attached to this vertex
	 * @throws IllegalArgumentException "null" if vertex is passed in as null
	 * @effects constructs a new vertex
	 */
    
	public Vertex(V nodeLabel){
		if (nodeLabel == null)
			throw new IllegalArgumentException("Vertex cannot be null");
		this.nodeLabel = nodeLabel;
	}
    
    /**
     * Returns this vertex' label
     *
     * @return label of vertex
     */
    
    public V getLabel(){
		return nodeLabel;
    }
	  
	/**
	 * A string representation of this object
	 * 
	 * @return the label of this vertex
	 */
	public String toString() {
		return getLabel().toString();
	}
	
    /**
     * Standard Hashcode function
     */
	public int hashCode() {
		return getLabel().hashCode();
	}
	
	/**
	 * returns whether two nodes are equal
	 * @return true if the labels are equal
	 */
	public boolean equals(Object obj) {
		  if (! (obj instanceof Vertex) ) 
				return false;
		  
	    Vertex<?> v = (Vertex<?>) obj;
	    return v.getLabel().equals(this.getLabel());
	    
	}
	

	/**
	 * Compares this object with the specified object for order. 
	 * 
	 * @param node the object to be compared
	 * @return a negative integer, zero, or a positive integer as 
	 * this object is less than, equal to, or greater than 
	 * the specified object
	 */
	
	public int compareTo(Vertex<V> node) {
		
		if(!(this.getLabel().equals(node.getLabel()))){
			return this.getLabel().hashCode()- node.getLabel().hashCode();
			//return this.getLabel().compareTo(node.getLabel());
		}
		
		return 0;
	}
}
