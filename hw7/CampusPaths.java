package hw7;

import hw4.Edge;

import java.util.List;
import java.util.ArrayList;

/**
 * CampusPaths is the controller which processes user input and
 * produces output. CampusPaths controls the model and view.
 * 
 */

public class CampusPaths {
	
	/**
	 * This class is not an ADT
	 */
	
	/** Holds the model */
	private static CampusPathsModel model;
	
	/** Holds the view */
	private static CampusPathsView view;
	
	/** constants holding file data */
	private final static String LOCATION_FILE = "hw7/data/RPI_map_data_Nodes.csv";
	private final static String PATHS_FILE = "hw7/data/RPI_map_data_Edges.csv";
	

	/**
	 * promptForBuildings processes the 'r' option and prompts the user
	 * for the ids or names of two buildings and outputs the 
	 * shortest between them.
	 * 
	 * @requires the id has a name (is a building)
	 * @effects prints directions for the shortest route between
	 * 			the buildings input by the user
	 */
	
	private static void promptForBuildings() {
		
		String start = "", end = "", input;
		int id1 = 0, id2 = 0;
		
		//prompt for values and parse if an ID
		view.prompt("First building id/name, followed by Enter: ");
		input = view.getStringInput();

		if (view.isInteger(input)) {
			id1 = Integer.parseInt(input);
		}else{
			
			// take the input and get either the building ID
			// or -1 if not a building
			start = input;
			id1 = model.getBuildingID(start);
		}

		view.prompt("Second building id/name, followed by Enter: ");
		input = view.getStringInput();

		if (view.isInteger(input)) {
			id2 = Integer.parseInt(input);
			end = model.getBuildingName(id2);
		}else{
			end = input;
			id2 = model.getBuildingID(end);
		}

		String path = "";

		if (!(model.isBuilding(id1)) || !(model.isBuilding(id2))) {

			if (!(model.isBuilding(id1))){
				
				if(id1 != -1){
					path += "Unknown building: [" + id1 + "]";
				}else{
					path += "Unknown building: [" + start + "]";
				}
			}
			
			if (!(model.isBuilding(id2)))
				if((start.equals(end) && id1 != id2) || !(start.equals(end)) && id2 != -1){
					path += "\nUnknown building: [" + id2 + "]";
				}else if(!(start.equals(end))){
					path += "\nUnknown building: [" + end + "]";
				}
		} else {
			path = setUpPath(id1, id2);
		}

		view.print(path);
				
	}
	
	private static String setUpPath(int id1, int id2) {
		
		String start = model.getBuildingName(id1);
		String end = model.getBuildingName(id2);
		
		String pathway = "";

			pathway = "Path from " + start + " to " + end + ":\n";

			if (id1 == id2) {
				double distance = 0.0;
				pathway += String.format("Total distance: %.3f pixel units.", distance);
				return pathway;
			}

			ArrayList<Edge<Location, Double>> path = model.findPath(id1, id2);

			if (path.size() == 0) {
				return "There is no path from " +  start + " to " + end + ".";
			}

			// Format the path from start to end
			
			// Set previous to the starting location
			int previousID = path.get(0).getParent().getID();
			String next;

			//initialize total distance
			Double totalDist = 0.0;

			for (int i = 1; i < path.size(); i++) {

				// get the next location in the path
				int nextID =  path.get(i).getChild().getID();
				next = model.getBuildingName(nextID);
		
				// get the distance of previous to next
				Double distance = path.get(i).getLabel();
				
				// get the cardinal direction from the previous to next
				String direction = model.getDirection(previousID, nextID);

				// add the distance to the total
				totalDist += distance;
				
				if (next.length() == 0) {
					// get the id of the intersection
					pathway += String.format("\tWalk %s to (Intersection %d)\n", direction, nextID);
				} else {
					pathway += String.format("\tWalk %s to (%s)\n", direction, next);
				}
				
				// make previous destination the one we are at
				previousID = nextID;
			}

			pathway += String.format("Total distance: %.3f pixel units.", totalDist);

		return pathway;
		
	}

	/**
	 * findPath processes the 'b' option and outputs all buildings
	 * in the form name, id in lexicographic order of name
	 * 
	 * @requires the id has a name (is a building)
	 * @effects prints all buildings in lexicographic order
	 */
	
	private static void listAllBuildings(){
		// DONE
		String[] locs = model.getBuildings();
		String list = "";
		
		list += locs[0] + "," + model.getBuildingID(locs[0]);	
		for (int i = 1; i < locs.length; i++) {
			list += "\n" + locs[i] + "," + model.getBuildingID(locs[i]);		
		}
		
		view.print(list);
	}
	
	
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
 		// q causes main method to return don't use s.exit
 		// m prints menu of all commands mqrb
 		// use switch to process input

		model = new CampusPathsModel();
		view = new CampusPathsView();
		
		model.createModel(LOCATION_FILE, PATHS_FILE);
		
		view.open();
		
		boolean quit = false;
		
		while (!quit) {
			
			// Recieve input
			String input = view.getStringInput();
			
			if(input.length() == 0){
				view.print("Unknown option");
			
			}else{

				char choice = input.charAt(0);
				
				if(choice == 'r'){				
					promptForBuildings();
				}else if(choice == 'b'){
					listAllBuildings();
				}else if(choice == 'q'){
					quit = true;
					view.close();
				}else if(choice == 'm'){
					view.printMenu();
				}else{
					view.print("Unknown option");
				}
			}
		}
		
		
		
	}
	
}
