package hw7.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import hw4.Edge;
import hw7.CampusPathsModel;
import hw7.Location;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class tests the CampusPathsModel class
 */

public class CampusPathsModelTest {

	private static CampusPathsModel model;

	/** constants holding file data */
	private final static String LOCATION_FILE = "hw7/data/RPI_map_data_Nodes.csv";
	private final static String PATHS_FILE = "hw7/data/RPI_map_data_Edges.csv";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new CampusPathsModel();
		model.createModel(LOCATION_FILE, PATHS_FILE);

	}
	
	@Test
	public void testLocationFileNull() throws Exception {

		boolean thrown = false;

		try {
			model.createModel(null, PATHS_FILE);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testPathsFileNull() throws Exception {

		boolean thrown = false;

		try {
			model.createModel(LOCATION_FILE, null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testLocationFileNotFound() {

		boolean thrown = false;

		try {
			model.createModel("Nonexistantfile", PATHS_FILE);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testPathFileNotFound() {

		boolean thrown = true;
		try {
			model.createModel(LOCATION_FILE, "nonexistantFile");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testReadGoodFile() {

		boolean thrown = false;

		try {
			model.createModel(LOCATION_FILE, PATHS_FILE);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertFalse(thrown);

	}

	@Test
	public void testReadFile() {
		model.createModel(LOCATION_FILE, PATHS_FILE);
	}


	@Test
	public void testPathFound() {
		model.createModel("hw7/data/SmallMapNodes.csv", "hw7/data/SmallMapEdges.csv");
		
		ArrayList<Edge<Location, Double>> expected = new ArrayList<Edge<Location, Double>>();
		Location center = new Location("Center",1,5,5);
		Location north = new Location("N",9,5,1);
		Edge<Location,Double> e1 = new Edge<Location,Double>(center, center, 0.0);
		Edge<Location,Double> e2 = new Edge<Location,Double>(center, north, 4.0);
		expected.add(e1);
		expected.add(e2);
		
		ArrayList<Edge<Location, Double>> actual = model.findPath(1,9);
		
		assertEquals(expected, actual);

	}
	
	@Test
	public void testGetBuildings(){
		model.createModel("hw7/data/GetBuildingsNodes.csv", "hw7/data/GetBuildingsEdges.csv");
	
		String[] expected = {"Home", "School"};
		
		String[] actual = model.getBuildings();
		
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetBuildingName() {
		model.createModel(LOCATION_FILE, PATHS_FILE);

		assertEquals("87 Gymnasium", model.getBuildingName(11));

	}
	
	@Test
	public void testGetBuildingID() {
		model.createModel(LOCATION_FILE, PATHS_FILE);

		assertEquals(11, model.getBuildingID("87 Gymnasium"));

	}
	
	@Test
	public void testIsBuilding() {
		model.createModel(LOCATION_FILE, PATHS_FILE);

		assertTrue(model.isBuilding(11));
		assertFalse(model.isBuilding(99));

	}
	
	@Test
	public void testGetDirectionSmallMap() {
		model.createModel("hw7/data/SmallMapNodes.csv", "hw7/data/SmallMapEdges.csv");
		
		assertEquals("NorthEast", model.getDirection(1,2));
		assertEquals("East", model.getDirection(1,3));
		assertEquals("SouthEast", model.getDirection(1,4));
		assertEquals("South", model.getDirection(1,5));
		assertEquals("SouthWest", model.getDirection(1,6));
		assertEquals("West", model.getDirection(1,7));
		assertEquals("NorthWest", model.getDirection(1,8));
		assertEquals("North", model.getDirection(1, 9));

	}
}
