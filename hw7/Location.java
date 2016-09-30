package hw7;

import org.junit.Assert;

/**
 * Location b is an immutable object which represents the coordinates of a
 * location on a map.
 * 
 * @specfield name : the location's full name (if applicable)
 * @specfield id : the location's id
 * @specfield xCoordinate : the x-coordinate of the location on the map
 * @specfield yCoordinate : the y-coordinate of the location on the map
 *
 */
public class Location implements Comparable<Location> {

	/*
	 * Location b represents the coordinates of a location on a map
	 * 
	 * AF<name, id, xCoordinate, yCoordinate> = an location on a map name is the
	 * name of the location id is the id of the location x is the x coordinate
	 * on the map y is the y coordinate on the map
	 * 
	 * RI: name != null && id is an integer && xCoordinate, yCoordinate are
	 * doubles
	 */
	private final String name;
	private final int id;
	private final double xCoordinate;
	private final double yCoordinate;

	/**
	 * Constructor - create a new empty location
	 * 
	 */
	public Location() {
		this.name = "";
		this.id = Integer.MAX_VALUE;
		this.xCoordinate = Double.MAX_VALUE;
		this.yCoordinate = Double.MAX_VALUE;
		checkRep();
	}
	
	/**
	 * Constructor - create a new location with a name such as a building
	 * 
	 * @require name is not null id, xCoordinate and xCoordinate > 0
	 * @param name
	 *            the location's name
	 * @param id
	 *            the id of the location
	 * @param xCoordinate
	 *            the x-coordinate of the location on the map
	 * @param yCoordinate
	 *            the y-coordinate of the location on the map
	 */
	public Location(String name, int id, double xCoordinate, double yCoordinate) {
		this.name = name;
		this.id = id;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		checkRep();
	}

	/**
	 * Constructor - create a new nameless location such as an intersection
	 * 
	 * @require id, xCoordinate and xCoordinate > 0
	 * @param id
	 *            the id of the location
	 * @param xCoordinate
	 *            the x-coordinate of the location on the map
	 * @param yCoordinate
	 *            the y-coordinate of the location on the map
	 */
	public Location(int id, double xCoordinate, double yCoordinate) {
		this.name = "";
		this.id = id;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		checkRep();
	}

	/**
	 * Checks that the representation invariant holds.
	 * 
	 * @throws a
	 *             RuntimeException if the rep invariant is violated.
	 */
	private void checkRep() {
		
		if (name == null)
			throw new IllegalArgumentException("short name is null");
		if (id <= 0)
			throw new IllegalArgumentException("id is less than or equal to 0");
		if (xCoordinate <= 0)
			throw new IllegalArgumentException("xCoordinate is not valid");
		if (yCoordinate <= 0)
			throw new IllegalArgumentException("yCoordinate is not valid");
	}

	/**
	 * Returns the location's name
	 * 
	 * @return the name of the entrance
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the location's id
	 * 
	 * @return the id of the location
	 */
	public int getID() {
		return id;
	}

	/**
	 * Returns the location's x-coordinate
	 * 
	 * @return the x-coordinate of the location
	 */
	public double getXCoordinate() {
		return xCoordinate;
	}

	/**
	 * Returns the location's y-coordinate
	 * 
	 * @return the y-coordinate of the location
	 */
	public double getYCoordinate() {
		return yCoordinate;
	}

	/**
	 * returns the distance
	 *
	 * @return the distance from this to the origin
	 */
	public double getDistanceTo(Location l) {
		
		Double x = (this.xCoordinate-l.xCoordinate)*(this.xCoordinate-l.xCoordinate);
		Double y = (this.yCoordinate-l.yCoordinate)*(this.yCoordinate-l.yCoordinate);

		return Math.sqrt(x + y);

	}
	
	/**
	 * returns the cardinal direction from this to dest
	 * 
	 * @param dest the destination to be compared with this
	 * @return North if the angle is [0.22.5) or [337.5, 360)
	 * 		   NorthEast if [22.5, 67.5)
	 *         East if [67.5,112.5)
	 *         SouthEast if [112.5,157.5)
	 *         South if [157.5,202.5)
	 *         SouthWest if [202.5,247.5)
	 *         West if [247.5,292.5)
	 *         NorthWest if [292.5,337.5)
	 */
	public String getDirectionTo(Location dest) {
		
		Location origin = this;
		
		double angle = getAngle(origin,dest);
		
		//System.out.println(dest.name + " angle =  " + angle);
		
		final double one_rotation = 360;
		final double one_sixteenth_rotation = one_rotation/16; // 22.5 deg
		final double three_sixteenth_rotation = 3 * one_sixteenth_rotation;; // 67.5 deg
		final double five_sixteenth_rotation = 5 * one_sixteenth_rotation; // 112.5 deg
		final double seven_sixteenth_rotation = 7 * one_sixteenth_rotation; // 157.5 deg
		final double nine_sixteenth_circle = 9 * one_sixteenth_rotation; // 202.5 deg
		final double eleven_sixteenth_rotation = 11 * one_sixteenth_rotation; // 247.5 deg
		final double thirteen_sixteenth_rotation = 13 * one_sixteenth_rotation; // 292.5 deg
		final double fifteen_sixteenth_rotation = 15 * one_sixteenth_rotation; // 337.5 deg
	    
		if (angle >= one_sixteenth_rotation && angle < three_sixteenth_rotation)
			return "NorthEast";
		else if (angle >= three_sixteenth_rotation && angle < five_sixteenth_rotation)
			return "East";
		else if (angle >= five_sixteenth_rotation && angle < seven_sixteenth_rotation)
			return "SouthEast";
		else if (angle >= seven_sixteenth_rotation && angle < nine_sixteenth_circle)
			return "South";
		else if (angle >= nine_sixteenth_circle && angle < eleven_sixteenth_rotation)
			return "SouthWest";
		else if (angle >= eleven_sixteenth_rotation && angle < thirteen_sixteenth_rotation)
			return "West";
		else if (angle >= thirteen_sixteenth_rotation && angle < fifteen_sixteenth_rotation)
			return "NorthWest";
		else 
			return "North";
	}
	
	/**
	 * Helper method calculates the angle in degrees
	 * from the the origin to source.
	 *
	 * @requires points are on the same coordinate space
	 * @param this the point we are rotating around
	 * @param dest the location to calculate the angle toward
	 * @return angle in degrees in the range of [0,360), rotating clockwise.  
	 * This is the angle from the origin to source
	 */
	public static double getAngle(Location origin, Location dest)
	{

		//compares dest to prev and uses new origin
		double deltaX = dest.getXCoordinate() - origin.getXCoordinate();
		double deltaY = dest.getYCoordinate() - origin.getYCoordinate();
		
	    // calculate the angle theta from the deltaY and deltaX values
	    // 0 currently points EAST.  .  
	    double theta = Math.atan2(deltaY,deltaX);

	    // ADD to rotate the theta angle clockwise by 90 degrees 
	    // (this makes 0 point NORTH)
	    theta += Math.PI/2.0;

	    // convert from rad -> deg for an angle in the range [0->270],[-180,0]
	    double angle = Math.toDegrees(theta);

	    // convert to positive range [0-360) to remove negative angles
	    if (angle < 0) {
	        angle += 360;
	    }

	    //TODO:
/*	    System.out.println("Angle from " + "(" + origin.getXCoordinate() + "," + origin.getYCoordinate() + ")" + " to " + "(" + dest.getXCoordinate() + "," + dest.getYCoordinate() + ")");
	    System.out.println("theta = " + theta);
	    System.out.println("angle = " + angle);
	    System.out.println();*/
	    
	    return angle;
	}

	/**
	 * @return a String represents a location in form: name, id,
	 *         xCoordinate,yCoordinate
	 * 
	 */
	@Override
	public String toString() {
		return "<" + name + ", " + id + ", " + Math.round(xCoordinate) + ", "
				+ Math.round(yCoordinate) + ">";
	}

	/**
	 * @return true if o has the same name, id, xCoordinate, and yCoordinate
	 *         with this, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Location))
			return false;

		Location b = (Location) obj;

		return b.getName().equals(this.getName()) 
				&& b.getID() == this.getID()
				&& b.getXCoordinate() == this.getXCoordinate()
				&& b.getYCoordinate() == this.getYCoordinate();
	}

	/**
	 * @return a standard hashcode
	 */
	@Override
	public int hashCode() {
		return getName().hashCode() ^ getID() ^ (int) getXCoordinate()
				^ (int) getYCoordinate();
	}

	/**
	 * Compares this object with the specified object for distance.
	 * 
	 * @param l
	 *            the location to be compared with this
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         closer than, equal to, or further than the specified object to
	 *         the origin, respectively.
	 */

	public int compareTo(Location l) {

		double d1 = Math.sqrt(this.xCoordinate * this.xCoordinate
				+ this.yCoordinate * this.yCoordinate);
		double d2 = Math.sqrt(l.xCoordinate * l.xCoordinate + l.yCoordinate
				* l.yCoordinate);

		double compare = d1 - d2;

		if (compare < 0)
			return -1;
		if (compare > 0)
			return 1;

		return 0;
	}
	

} // end Location class

