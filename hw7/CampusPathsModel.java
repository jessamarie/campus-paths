package hw7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hw4.Digraph;
import hw4.Edge;
import hw6.MarvelPaths2Finder;


/**
 * This class models the campus and performs
 * operations on the data such as path finding
 * and getting the cardinal direction of a path
 */


public class CampusPathsModel {
	
	/**
	 * This class is not an ADT
	 */
	
	/** Holds the graph */
	private static Digraph<Location, Double> graph;
	
	/** Holds all of the locations */
	private static Set<Location> locations;
	
	/** Holds all of the buildings */
	private static Set<Location> buildings;

	/**
	 * Creates the model from data in the file
	 * 
	 * @param locations_file the file which contains the location data
	 * @param paths_file the file which contains the edge data
	 * @requires locations_file != null paths_file != null
	 * @throws IllegalArgumentException
	 *             if the locations_file or paths_file is null, if the data is failed to be read or
	 *             the file data does not match our expected data.
	 */
	public void createModel(String locations_file, String paths_file){
		if (locations_file == null)
			throw new IllegalArgumentException("locations file cannot be null.");
		if (paths_file == null)
			throw new IllegalArgumentException("paths file cannot be null.");

		// Initialize the graph
		graph = new Digraph<Location, Double>();
		
		// To hold location data
		locations = new HashSet<Location>();
		
		// To hold location data
		buildings = new HashSet<Location>();
		
		// To hold location edge data
		Set<List<Integer>> pathways = new HashSet<List<Integer>>();
		
		
		// parse data and throw exception if files can't be read
		try {
			CampusPathsParser.readLocationData(locations_file, locations, buildings);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error can't find file: "
					+ locations_file + " " + e);
		}
		
		// parse edge data and throw exception if file can't be read
		try {
			CampusPathsParser.readPathsData(paths_file, pathways);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error can't find file: "
					+ paths_file + " " + e);
		}
	
		
		// add nodes (locations) to the graph
		for (Location location : locations) {
			graph.insertNode(location);
		}
		
		// iterate through each pathway
		for(List<Integer> pathway : pathways){
			
			int id1 = pathway.get(0);
			int id2 = pathway.get(1);
			
			// get the locations for ids
			Location loc1 = new Location();
			Location loc2 = new Location();

			for (Location l : locations) {
				if (l.getID() == id1) {
					loc1 = l;
				}
				if (l.getID() == id2) {
					loc2 = l;
				}
			}

			// Finally calculate the length of the pathway using the
			// Distance formula and add the edges to the graph
			Double distance = loc1.getDistanceTo(loc2);

			// Add distances to the graph
			graph.insertEdge(loc1, loc2, distance);
			graph.insertEdge(loc2, loc1, distance);
			
		}
		
	}
	

	/**
	 * findPath uses dijkstras algorithm to find the shortest path from one location
	 * to another
	 * 
	 * @param start the starting destination
	 * @param end the ending destination
	 * @requires start and end exist in the graph and all edge weights in the
	 *           graph are nonnegative
	 * @return an ArrayList of edges that represents a path
	 */
	public ArrayList<Edge<Location, Double>> findPath(int id1, int id2) {
				
		Location loc1 = getLocation(id1);
		Location loc2 = getLocation(id2);
		
		return new ArrayList<Edge<Location, Double>>(MarvelPaths2Finder.dijkstras(graph, loc1, loc2));
	}

	/**
	 * getBuildings gets a list all the buildings, but not intersections
	 * 
	 * @return a string that contains all buildings from locations
	 * 			in lexicographic order
	 */
	public String[] getBuildings() {
		
		Iterator<Location> it = buildings.iterator();
		List<String> names = new ArrayList<String>();
		
		while (it.hasNext()) {
				names.add(it.next().getName());
		}
		
		Collections.sort(names, new Comparator<String>() {
					@Override
					public int compare(String l1, String l2) {
						if (!(l1.equals(l2))) {

							return l1.compareTo(l2);
						}
						return 0;
					}
		});
		
		String[] nameStrings = new String[names.size()];
		nameStrings = names.toArray(nameStrings);
		
		return nameStrings;
	}
	
	
	/**
	 * Returns the name of a building
	 * 
	 * @return a String of the building name
	 */
	public String getBuildingName(Integer id) {
		
		String loc = "";
		
		for(Location l : locations){
			if(l.getID() == id){
				loc = l.getName();
			}
		}
		
		return loc;
	}
	
	/**
	 * Returns the id of a building
	 * 
	 * @return the id of location corresponding with the name
	 * or -1 if the location is not a building
	 */
	public int getBuildingID(String name) {
		
		int loc = -1;
		
		for(Location l : buildings){
			if(l.getName().equals(name)){
				loc = l.getID();
			}
		}
		
		return loc;
	}
	
	/**
	 * @returns building x-coordinate
	 */
	public double getX(String name) {
		
		//get the building id
		int id = getBuildingID(name);
		//get the location object associated with the id
		Location loc = getLocation(id);
		
		return loc.getXCoordinate();
	}
	
	/**
	 * @returns building y-coordinate
	 */
	public double getY(String name) {
		
		//get the building id
		int id = getBuildingID(name);
		//get the location object associated with the id
		Location loc = getLocation(id);
		
		return loc.getYCoordinate();
	}
	
	/**
	 * Returns the if the id is a building 
	 * 
	 * @param arg the parameter to be evaluated
	 * @return true if the argument is a building
	 */
	public boolean isBuilding(int id) {
		
		for (Location b : buildings) {
			if (b.getID() == id) {
					return true;
				}
		}

		return false;	
	}

	/**
	 * getDirection calculates the cardinal direction of an 
	 * individual path
	 * 
	 * @param previous the origin 
	 * @param next the destination
	 * @return the cardinal direction from previous to next
	 */

	public String getDirection(int previousLoc, int nextLoc) {
		
		Location x = getLocation(previousLoc);
		Location y = getLocation(nextLoc);
		
		String dir = x.getDirectionTo(y);
		
		return dir;
	}
	
	/**
	 * getLocation is a helper method that returns the location of
	 * the specified argument
	 * 
	 * @requires the id exists
	 * @param id the id to be search for in
	 * @return a location that corresponds with the id
	 *  
	 */

	private Location getLocation(int id) {
		
		Location loc = new Location();
		for(Location l : locations){
			if(l.getID() == id){
				loc = l;
			}
		}
		
		return loc;
	}
}
