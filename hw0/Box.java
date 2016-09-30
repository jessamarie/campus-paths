/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This is a container can be used to contain Balls. The key
 * difference between a BallContainer and a Box is that a Box has a
 * finite volume. Once a box is full, a client cannot put in more Balls.
 */
public class Box implements Iterable<Ball> {

    /**
     * ballContainer is used to internally store balls for this Box
     */
    private BallContainer ballContainer;
    
    private double maxVolume; //holds volume capacity

    /**
     * Constructor that creates a new box.
     * @param maxVolume Total volume of balls that this box can contain.
     */
    public Box(double maxVolume) {
    	
    	ballContainer = new BallContainer();
    	this.maxVolume = maxVolume;
    }

    /**
     * Implements the Iterable interface for this box.
     * @return an Iterator over the Ball objects contained
     * in this box.
     */
    public Iterator<Ball> iterator() {
        return ballContainer.iterator();
    }


    /**
     * This method is used to add Ball objects to this box of
     * finite volume.  The method returns true if a ball is
     * successfully added to the box, i.e., ball is not already in the
     * box and if the box is not already full; and it returns false,
     * if ball is already in the box or if the box is too full to
     * contain the new ball.
     * @param b Ball to be added.
     * @return true if ball was successfully added to the box,
     * i.e. ball is not already in the box and if the box is not
     * already full. Returns false, if ball is already in the box or
     * if the box is too full to contain the new ball.
     */
    public boolean add(Ball b) {
    	
    	if (getVolume() + b.getVolume() > maxVolume)
    		return false;
    	else 
    		return ballContainer.add(b);
    		
    	}

    /**
     * This method returns an iterator that returns all the balls in
     * this box in ascending size, i.e., return the smallest Ball
     * first, followed by Balls of increasing size.
     * @return an iterator that returns all the balls in this box in
     * ascending size.
     */
    public Iterator<Ball> getBallsFromSmallest() {
    	
    	Set<Ball> unsorted = new LinkedHashSet<Ball>(); //unsorted temporary set
    	Set<Ball> sorted = new LinkedHashSet<Ball>();  //sorted set
    	
    	Ball first; //first ball in set
    	
    	for(Ball b: ballContainer)
    		unsorted.add(b);
    	

    	
    	//iterate through unsorted list until sorted
    	while(unsorted.size() != 0){
    		Iterator<Ball> i = unsorted.iterator();
    		first = i.next(); 
    		Ball next;
    		while(i.hasNext()){
    			next = i.next();
    			
    			// set first as ball with smallest volume
    			if(next.getVolume() < first.getVolume()){
    				first = next;
    			}
    		}
    			
    			unsorted.remove(first);
    			sorted.add(first);
 
    	}
    	
    	return sorted.iterator(); //returns the set sorted

    }

    /**
     * Removes a ball from the box. This method returns
     * <tt>true</tt> if ball was successfully removed from the
     * container, i.e. ball is actually in the box. You cannot
     * remove a Ball if it is not already in the box and so ths
     * method will return <tt>false</tt>, otherwise.
     * @param b Ball to be removed.
     * @return true if ball was successfully removed from the box,
     * i.e. ball is actually in the box. Returns false, if ball is not
     * in the box.
     */
    public boolean remove(Ball b) {
        return ballContainer.remove(b);
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the box.
     * @return the volume of the contents of the box.
     */
    public double getVolume() {
       return ballContainer.getVolume();
    }

    /**
     * Returns the number of Balls in this box.
     * @return the number of Balls in this box.
     */
    public int size() {
        return ballContainer.size();
    }

    /**
     * Empties the box, i.e. removes all its contents.
     */
    public void clear() {
        ballContainer.clear();
    }

    /**
     * This method returns <tt>true</tt> if this box contains
     * the specified Ball. It will return <tt>false</tt> otherwise.
     * @param b Ball to be checked if its in box
     * @return true if this box contains the specified Ball. Returns
     * false, otherwise.
     */
    public boolean contains(Ball b) {
        return ballContainer.contains(b);
    }

}
