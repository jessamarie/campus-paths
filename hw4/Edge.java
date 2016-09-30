package hw4;

/**
 * <b>Vertex</b> represents a mutable edge in a graph
 * <p>
 * 
 * @specfield src : holds the origin node
 * @specfield dest : holds the destination node
 * @specfield label : holds the label of the node
 */

public class Edge<V, E extends Comparable<E>> implements Comparable<Edge<V, E>> {
	
	/** Holds src and dest label */
    public V src, dest;
    
    /** Holds current label */
    public E label;
    
    //Abstraction Function
    // Edge e represents an edge in a graph
    // such that if e is an Edge than e is
    // in the form of
    // <src, dest, label> 
    
    // Rep Invariant
    // label != null

    /**
     * @param src the origin node
     * @param dest the destination node
     * @param label the edge label
     * @effects constructs a new edge
     * @throws IllegalArgumentException "null" if any edge components are passed in as null
     */
    public Edge(V src, V dest, E label) {
        if (src == null || dest == null || label == null ) {
            throw new IllegalArgumentException("No Edge component can be null");
        }
        
        this.src = src;
        this.dest = dest;
        this.label = label;
        
    }
    
    /**
     * Returns the label of this edge.
     *
     * @return the label of this edge
     */
    public E getLabel() {
    	return label;
    }
    
    /**
     * Returns the origin vertex (parent)
     *
     * @return the parent of this edge
     */
    public V getParent() {
    	return src;
    }
    
    /**
     * Returns the destination vertex (Child)
     *
     * @return the child of this edge
     */
    public V getChild() {
    	return dest;
    }

    /**
     * Returns a String representation of this object
     * 
     * @return String representation of this Edge
     */
    public String toString() {
    	 return "<" + getParent() + ", " + getChild() + ", " + getLabel() + ">";
    }
    
    /**
     * Standard Hashcode function
     */
    
	public int hashCode() {
		return getParent().hashCode() ^ getChild().hashCode() ^ getLabel().hashCode();
	}
	
	/**
	 * returns whether two Edges are identical
	 * @return true if the edges are identical
	 */
	public boolean equals(Object obj) {
		
		  if (! (obj instanceof Edge) ) 
				return false;

	    Edge<?,?> e = (Edge<?,?>)obj;
	    
	    return e.getParent().equals(this.getParent()) &&       
	        e.getChild().equals(this.getChild()) &&
	        e.getLabel().equals(this.getLabel());
	}
	

	/**
	 * Compares this object with the specified object for order. 
	 * 
	 * @param node the object to be compared
	 * @return a negative integer, zero, or a positive integer as 
	 * this object is less than, equal to, or greater than 
	 * the specified object
	 */
	
	public int compareTo(Edge<V,E> e) {
		
		if(!(this.getLabel().equals(e.getLabel()))){
			return this.getLabel().compareTo(e.getLabel());
		}
		
		if(!(this.getChild().equals(e.getChild()))){
			return dest.hashCode() - e.getChild().hashCode();
		}
		
		return 0;
	}
}
