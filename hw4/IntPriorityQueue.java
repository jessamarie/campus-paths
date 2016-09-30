package hw4;

import java.util.*;

public class IntPriorityQueue {

	/** <b>IntPriorityQueue</b> is a <b>mutable</b> priority queue of integers.                                                                                       
    It uses the heap data structure, represented as array, to implement the                                                                                       
    a subset of the operations of a priority queue ADT. The array is 1-based.                                                                                     
    */

    private List<Integer> heap;
    private int size;

    // TODO: Fill in the abstraction function and representation invariant.                                                                                       

 // Abstraction Function:
    // AF(r) IntPriorityQueue heap represents a binary tree in the form of a max heap
    // heap = [Integer.Max_Value, a_1, a_2,...a_k], where k is the number of
    // elements in heap and the 0th element equals Integer.Max_Value,
    // For all nodes i in heap,
    // r[i] = 1,
    // r[i/2] = parent of i,
    // r[2i] = left child of i,
    // r[2i+1] = right child of i
    // if size = 0, heap = [Integer.Max_Value]
    //
    // Representation Invariant:   
    // heap[0] = Integer.MAX_VALUE;
    // heap != null &&
    // Must be a complete binary tree, 
    // in other words: # of Left Children >= # of right Children &&
    // foreach i in heap, 0 < i <= size && heap.get(i) != null  &&
    // i of Parent < i of children &&
    // heap.get(i) >= heap.get(2i) for the Left Child &&
    // heap.get(i) >= heap.get(2i+1) for the Right Child &&
    // parent = floor[(i)/2]                       

    /** @effects: creates a new empty IntPriorityQueue                                                                                                            
     */
    public IntPriorityQueue() {
        heap = new ArrayList<Integer>();
        heap.add(Integer.MAX_VALUE);
        size = 0;
        
        checkRep();
    }

    /** @param: heap The list containing the priority queue elements                                                                                              
        @param: size The size of the queue                                                                                                                        
        @requires: heap and size comply to rep invariant described above                                                                                          
        @effects: creates a new IntPriorityQueue of size size with elements heap                                                                                  
     */
    public IntPriorityQueue(List<Integer> heap, int size) {
        this.heap = heap;
        this.size = size;
        
        checkRep();
    }

    /** @param: index The index for which heap property must be restored                                                                                          
    	@requires: 0 < index <= size                                                                                                                              
    	@modifies: this                                                                                                                                           
    	@effects: restores heap property for index                                                                                                                
     */
    private void upheap(int index) {
    	if ((0 >= index) || (index > size))
    		throw new IllegalArgumentException("0 < index <= size");
    	else {
    		int value = heap.get(index);
    		int k = index;
    		// restores the heap property for index moving elements                                                                                                   
    		// smaller than heap[index] down the heap                                                                                                                 
    		while (heap.get(k/2) <= value) {
    			heap.set(k, heap.get(k/2)); k = k/2;
    		}
    		// inserts value at appropriate place                                                                                                                     
    		heap.set(k,value);
    	}
    }

    /** @param: value The new integer value to insert in the queue                                                                                                
    	@modifies: this                                                                                                                                           
    	@effects: inserts value at appropriate place, increases size by one                                                                                       
     */
    public void insert(int value) {
    	
    	checkRep();
    	
    	size = size + 1;
    	// add value at the end of array                                                                                                                          
    	if (size == heap.size()) {
    		heap.add(size, value);
    	}
    	else {
    		heap.set(size, value);
    	}
    	// now resotore the heap property moving value up if necessary                                                                                            
    	upheap(size);
    	
    	checkRep();
    }

    /** @param: index The index for which heap property must be restored                                                                                          
    	@requires: 0 < index <= size                                                                                                                              
    	@modifies: this                                                                                                                                           
    	@effects: moves heap[index] down the heap until heap property is restored                                                                                 
    */
    private void downheap(int index) {
    	if ((0 >= index) || (index > size)) {
    		throw new IllegalArgumentException("index must be: 0 < index <= size");
    	}
    	else {
    		int value = heap.get(index);
    		int k = index;
    		while (k <= size/2) {
    			int j = k+k;
    			// heap[j] and heap[j+1] are the two children of heap[k]                                                                                              
    			// we need the larger of the two                                                                                                                      
    			if (j < size && heap.get(j) < heap.get(j+1)) j = j+1;
    			// if value is larger then the larger of k's children, we have                                                                                        
    			// found place for value, exit the loop                                                                                                               
    			if (value >= heap.get(j)) break;
    			// otherwise, replace heap[k] with the larger of its children                                                                                         
    			heap.set(k, heap.get(j));
    			// and move k down the heap                                                                                                                           
    			k = j;
    		}
    		// insert value at appropriate place                                                                                                                      
    		heap.set(k,value);
    	}

    }
    
    /** @return: The largest value in heap. If priority queue has duplicate keys,                                                                                
    			 we take "largest" to mean "any largest value"                                                                                                   
		@modifies: this                                                                                                                                           
		@effects: Removes largest value, restores heap property, reduces size by one                                                                              
		@throws: QueueEmpty exception if this queue is empty.
     */

    public int remove() {
    	
    	checkRep();
    	
    	if (isEmpty()) {
    		throw new QueueEmpty("Cannot remove element of an empty queue!"); 
    	}
    	else {
    		int value = heap.get(1);
    		heap.set(1,heap.get(size));
    		size = size - 1;
    		if (!isEmpty()) downheap(1);
    		
    		checkRep();
    		
    		return value;
    	}
    }

    /** @returns: true if heap is empty
     * 			  false otherwise
     */
    public boolean isEmpty() {
    	if (size == 0) return true;
    	else return false;
    }
    
    /** @returns: size of heap
     */
    public int size() {
    	return size;
    }
    
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	// Throws a RuntimeException if the rep invariant is violated.
	private void checkRep() throws RuntimeException {
		if (heap == null) {
			throw new RuntimeException("heap == null");
		}

		// Checks that size is within heap bounds
		if ((size < 0) || (size > heap.size())) {
			throw new IllegalArgumentException(
					"index must be: 0 < index <= size");
		}

		// make sure heap[0] = Integer.Max_Value
		if (heap.get(0) != Integer.MAX_VALUE) {
			throw new RuntimeException("heap[0] must be Integer.Max_Value");
		}

		// Heap size must always have at least 1 element!
		if (heap.size() == 0) {
			throw new RuntimeException("Heap size cannot be 0");

		} else {

			for (int i = 0; i < heap.size() - 1; i++) {

				// No element in heap can be null
				if (heap.get(i) == null) {
					throw new RuntimeException("heap.get(i)== null");
				}

				// Parent value must be greater than child value
				// We make sure we are within the bounds of size
				// since after the remove method the heap itself 
				// has non-heap elements in the heap
				if (i > 0 && i <= size && (heap.get(i) > heap.get(i / 2)) ) {
					throw new RuntimeException("Parent is smaller than the child!");
				}
			}
			
			//Check non-heap elements
		}
	}
}

class QueueEmpty extends RuntimeException {
	public QueueEmpty(String string) {
		super(string);
	}
}
