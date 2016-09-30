package hw6.test;

import static org.junit.Assert.*;
import hw4.Digraph;
import hw6.MarvelPaths2;

import org.junit.BeforeClass;
import org.junit.Test;

public class MarvelPaths2Test {

	private static MarvelPaths2 mp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mp = new MarvelPaths2();

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// // createNewGraph Tests
	// /////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testCSVFileNull() throws Exception {

		boolean thrown = false;

		try {
			mp.createNewGraph(null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testCSVFileDoesntExist() {

		boolean thrown = false;

		try {
			mp.createNewGraph("Nonexistantfile");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testReadGoodFile() {

		boolean thrown = false;

		try {
			mp.createNewGraph("hw6/data/AmoebaToZebra.csv");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertFalse(thrown);

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// // findPath Tests
	// /////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testFindPathWithNullStart() {

		boolean thrown = false;

		try {
			mp.findPath(null, "Eggs");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);

	}

	@Test
	public void testFindPathWithNullEnd() {

		boolean thrown = false;

		try {
			mp.findPath("Eggs", null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testUnknownStartChar() {
		mp.createNewGraph("hw6/data/AmoebaToZebra.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath("INVALID", "Zany Zebra");

		assertEquals(expected, actual);
	}

	@Test
	public void testUnknownEndChar() {
		mp.createNewGraph("hw6/data/AmoebaToZebra.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath("Zany Zebra", "INVALID");

		assertEquals(expected, actual);

	}

	@Test
	public void testUnknownStartAndEndCharsDifferent() {
		mp.createNewGraph("hw6/data/AmoebaToZebra.csv");
		String expected = "unknown character INVALID1\nunknown character INVALID2\n";
		String actual = mp.findPath("INVALID1", "INVALID2");

		assertEquals(expected, actual);

	}

	@Test
	public void testUnknownStartAndEndCharsSame() {
		mp.createNewGraph("hw6/data/AmoebaToZebra.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath("INVALID", "INVALID");

		assertEquals(expected, actual);

	}

	@Test
	public void testPathToSelf() {
		mp.createNewGraph("hw6/data/ShortPath1.csv");
		String expected = "path from Eggs to Eggs:\n";
		expected += "total cost: 0.000\n";
		String actual = mp.findPath("Eggs", "Eggs");

		assertEquals(expected, actual);

	}

	@Test
	public void testNoPathFound() {
		mp.createNewGraph("hw6/data/ShortPath1.csv");
		String expected = "path from Bacon to Butter:\nno path found\n";
		String actual = mp.findPath("Bacon", "Butter");

		assertEquals(expected, actual);

	}

	@Test
	public void testLengthOnePath() {
		mp.createNewGraph("hw6/data/ShortPath1.csv");

		String expected = "path from Butter to Eggs:\n";
		expected += "Butter to Eggs with weight 1.000\n";
		expected += "total cost: 1.000\n";

		String actual = mp.findPath("Butter", "Eggs");

		assertEquals(expected, actual);
	}

	@Test
	public void testReverseLengthOnePath() {
		mp.createNewGraph("hw6/data/ShortPath1.csv");

		String expected = "path from Eggs to Butter:\n";
		expected += "Eggs to Butter with weight 1.000\n";
		expected += "total cost: 1.000\n";

		String actual = mp.findPath("Eggs", "Butter");

		assertEquals(expected, actual);
	}

	@Test
	public void testLengthTwoPath() {

		mp.createNewGraph("hw6/data/ShortPath2.csv");

		String expected = "path from Butter to Eggs:\n";
		expected += "Butter to Bacon with weight 0.250\n";
		expected += "Bacon to Eggs with weight 0.500\n";
		expected += "total cost: 0.750\n";

		String actual = mp.findPath("Butter", "Eggs");

		assertEquals(expected, actual);
	}

	
	  //TODO:
	  
	  @Test 
	  public void testLeastCostEdgeTaken() {
	  mp.createNewGraph("hw6/data/ShortPath2.csv"); 
	  mp.addConnection("Eggs", "Bacon", 1.0);
	  
	  String expected = "path from Eggs to Bacon:\n"; expected +=
	  "Eggs to Bacon with weight 0.500\n"; expected += "total cost: 0.500\n";
	  
	  String actual = mp.findPath("Eggs", "Bacon");
	  
	  assertEquals(expected, actual); }
	 

	@Test
	public void testLongPath() {
		mp.createNewGraph("hw6/data/LongPath1.csv");

		String expected = "path from Butter to Fries:\n";
		expected += "Butter to Eggs with weight 0.250\n";
		expected += "Eggs to Bacon with weight 0.250\n";
		expected += "Bacon to Hamburger with weight 0.333\n";
		expected += "Hamburger to Fries with weight 0.200\n";
		expected += "total cost: 1.033\n";

		String actual = mp.findPath("Butter", "Fries");

		assertEquals(expected, actual);
	}

	@Test
	public void testLongPathChanged() {
		mp.createNewGraph("hw6/data/LongPath2.csv");

		String expected = "path from Butter to Fries:\n";
		expected += "Butter to Bacon with weight 0.250\n";
		expected += "Bacon to Hamburger with weight 0.333\n";
		expected += "Hamburger to Fries with weight 0.200\n";
		expected += "total cost: 0.783\n";

		String actual = mp.findPath("Butter", "Fries");

		assertEquals(expected, actual);
	}

	@Test
	public void testLongPathWithSameWeightPaths() {
		mp.createNewGraph("hw6/data/LongPath3.csv");

		String expected = "path from Butter to Fries:\n";
		expected += "Butter to Bacon with weight 0.500\n";
		expected += "Bacon to Hamburger with weight 0.333\n";
		expected += "Hamburger to Fries with weight 0.200\n";
		expected += "total cost: 1.033\n";

		String actual = mp.findPath("Butter", "Fries");

		assertEquals(expected, actual);
	}

}
