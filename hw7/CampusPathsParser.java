package hw7;

import java.util.*;
import java.io.*;

/**
 * DataParser loads data from the edge and nodes files
 *
 */

public class CampusPathsParser {

	/**
	 * This class is not an ADT
	 */

	/**
	 * @param: filename The path to the "CSV" files that contain the Name, id,
	 *         x-coordinate, y-coordinate on each line separated by a comma
	 * @param: buildings a set to hold the buildings
	 * @modifies buildings
	 * @effects: adds parsed Name, id, x-coordinate, y-coordinate to buildings
	 *           set
	 * @throws: IOException if file cannot be read of file not a CSV file
	 */
	public static void readLocationData(String filename, Set<Location> locations, Set<Location> building_names)
			throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			int i = line.indexOf(",");
			if ((i == -1)) {
				throw new IOException(
						"File " + filename + " not a CSV (Name, id, x-coordinate, y-coordinate) file.");
			}
			
			String[] components = line.split(",");

			// Parse the data
			String name = components[0];
			int id = Integer.parseInt(components[1]);
			double xCoordinate = Double.parseDouble(components[2]);
			double yCoordinate = Double.parseDouble(components[3]);

			// Adds the location to the locations Set
			if (name.length() == 0) {
				Location l = new Location(id, xCoordinate, yCoordinate);
				locations.add(l);
			} else {
				Location l = new Location(name, id, xCoordinate, yCoordinate);
				locations.add(l);
				building_names.add(l);
			}
		}
	}

	/**
	 * @param: filename The path to the "CSV" files that contain the id1,id2 on
	 *         each line separated by a comma
	 * @param: distances to store all the distances 
	 * @modifies distance
	 * @effects: adds parsed id1 and id2 to the campus_graph
	 * @throws: IOException if file cannot be read of file not a CSV file
	 */
	public static void readPathsData(String filename, Set<List<Integer>> pathways) throws IOException {

		// TODO: How is the graph represented?
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			int i = line.indexOf(",");

			if ((i == -1)) {
				throw new IOException("File " + filename
						+ " not a CSV (id1,id2) file.");
			}
			
			String[] components = line.split(",");

			// Parse the data
			int id1 = Integer.parseInt(components[0]);
			int id2 = Integer.parseInt(components[1]);
			
			
			//add ids to a list
			List<Integer> pathway = new ArrayList<Integer>();
			pathway.add(id1);
			pathway.add(id2);
			
			//add pathway to pathways set
			pathways.add(pathway);


		}// end while
	
	}// end method

}// end class
