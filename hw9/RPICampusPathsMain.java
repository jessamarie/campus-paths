package hw9;

import javax.swing.SwingUtilities;

import hw7.CampusPathsModel;

/**
 * RPICampusPathsMain is the client
 * setup for CampusPaths
 * 
 */

public class RPICampusPathsMain {
	
	/** constants holding file data */
	private final static String LOCATION_FILE = "hw7/data/RPI_map_data_Nodes.csv";
	private final static String PATHS_FILE = "hw7/data/RPI_map_data_Edges.csv";
	
	/**
	 *  No Representation Invariant or Abstraction Function 
	 */
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            	try{
            		
            		CampusPathsModel theModel = new CampusPathsModel();
            		
            		CampusPathsGuiView theView = new CampusPathsGuiView();
            		
            		theModel.createModel(LOCATION_FILE, PATHS_FILE);
            		
            		CampusPathsGuiController controller = new CampusPathsGuiController(theView, theModel);

            		controller.notifyViewOfBuildings();
            		
            		theView.setVisible(true);
            		
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            }
        });	
	}

}
