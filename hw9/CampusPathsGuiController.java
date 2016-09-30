package hw9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import hw4.Edge;
import hw7.CampusPathsModel;
import hw7.Location;

/**
 * CampusPathsGuiController is the controller of the 
 * CampusPaths program. It connects the theModel and the view
 * by receiving user input
 * 
 */

public class CampusPathsGuiController{
	
	/** constants holding file data */
	private final static String LOCATION_FILE = "hw7/data/RPI_map_data_Nodes.csv";
	private final static String PATHS_FILE = "hw7/data/RPI_map_data_Edges.csv";
	
	/**
	 *  No Representation Invariant or Abstraction Function 
	 */
	
	private static CampusPathsModel theModel;
	private static CampusPathsGuiView theView;
	
	private String startBuilding;
	private String destinationBuilding;
	
	private double startingXCoordinate;
	private double startingYCoordinate;
	private double destinationXCoordinate;
	private double destinationYCoordinate;

	
	/**
	 * Constructor creates the controller and adds listeners
	 * to the view
	 */
	
	public CampusPathsGuiController(CampusPathsGuiView theView,
			CampusPathsModel theModel) {

		this.theView = theView;
		this.theModel = theModel;

		startingXCoordinate = 0.0;
		startingYCoordinate = 0.0;
		destinationXCoordinate = 0.0;
		destinationYCoordinate = 0.0;

		theView.addResetListener(new ResetHandler());
		theView.addFindPathtistener(new FindPathHandler());
		theView.addStartListener(new StartSelectionHandler());
		theView.addDestListener(new DestSelectionHandler());
		theView.addClickListener(new MouseHandler());

	}
	
	/**
	 * Notifies the view when the model loads data
	 */
	public void notifyViewOfBuildings() {

		try {
			theView.updateBuildingLists(theModel.getBuildings());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * StartSelectionHandler handles the event when a starting point is marked
	 */

	private class StartSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {

			startBuilding = theView.getStart();
			startingXCoordinate = theModel.getX(startBuilding);
			startingYCoordinate = theModel.getY(startBuilding);

			// notify the view and update the points on the map
			theView.updatePoints(startingXCoordinate, startingYCoordinate,
					destinationXCoordinate, destinationYCoordinate);
		}
	}
	
	/**
	 * DestSelectionHandler handles the event when a destination point is marked
	 */

	private class DestSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {

			destinationBuilding = theView.getDestination();
			destinationXCoordinate = theModel.getX(destinationBuilding);
			destinationYCoordinate = theModel.getY(destinationBuilding);

			// notify the view and update the points on the map
			theView.updatePoints(startingXCoordinate, startingYCoordinate,
					destinationXCoordinate, destinationYCoordinate);

		}
	}
	
	/**
	 * Handles the event when the Find Path button is clicked
	 *
	 */
	private class FindPathHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			startBuilding = theView.getStart();
			destinationBuilding = theView.getDestination();

			int start = theModel.getBuildingID(startBuilding);
			int dest = theModel.getBuildingID(destinationBuilding);

			ArrayList<Edge<Location, Double>> path = theModel.findPath(start,
					dest);

			ArrayList<ArrayList<Double>> coordinates = new ArrayList<ArrayList<Double>>();

			// Iterate through paths
			for (Edge<Location, Double> edge : path) {

				// holds the current coordinates
				ArrayList<Double> nextDestination = new ArrayList<Double>();

				// get the current location
				Location currentDest = edge.getChild();

				// adds the current destination coordinates to nextDestination
				nextDestination.add(currentDest.getXCoordinate());
				nextDestination.add(currentDest.getYCoordinate());

				// add nextDestination to the coordinates list
				coordinates.add(nextDestination);

			}

			theView.updatePath(coordinates);

		}
	}

	/**
	 * Handles the event that the reset button is
	 */
	private class ResetHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			theView.reset();
		}

	}

	/**
	 * Handles the event that the mouse button is clicked
	 */
	private class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// theView.displayErrorMessage(e.getX() + " " + e.getY());
		}

	}
}