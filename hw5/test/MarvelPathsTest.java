package hw5.test;

import static org.junit.Assert.*;

import hw5.MarvelPaths;
import org.junit.BeforeClass;
import org.junit.Test;

public class MarvelPathsTest {
	
	private static MarvelPaths mp;
	
 	@BeforeClass
 	public static void setUpBeforeClass() throws Exception {
 		mp = new MarvelPaths();

 		
 	}
 	
 	
 	///////////////////////////////////////////////////////////////////////////////////////
 	////  createNewGraph Tests
 	///////////////////////////////////////////////////////////////////////////////////////  
 	
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
		} catch (RuntimeException e){
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testCSVFileBadComma() {
		
		boolean thrown = false;
		
		try {
			mp.createNewGraph("hw5/data/Faulty.csv");
		} catch (RuntimeException e){
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testCSVFileStartLineQuoteMissing() {
		
		boolean thrown = false;
		
		try {
			mp.createNewGraph("hw5/data/Faulty2.csv");
		} catch (RuntimeException e){
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testCSVFileEndLineQuoteMissing() {
		
		boolean thrown = false;
		
		try {
			mp.createNewGraph("hw5/data/Faulty3.csv");
		} catch (RuntimeException e){
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testReadGoodFile() {
		
		boolean thrown = false;
		
		try {
			mp.createNewGraph("hw5/data/ShortPath.csv");
		} catch (RuntimeException e){
			thrown = true;
		}
		assertFalse(thrown);
		
	}
	
	
	
 	///////////////////////////////////////////////////////////////////////////////////////
 	////  findPath Tests
 	///////////////////////////////////////////////////////////////////////////////////////  
	
	
	@Test
	public void testFindPathWithNullStart() {
		
		boolean thrown = false;	
		
		try {
			mp.findPath( null, "Eggs");
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
		
	}
	
	@Test
	public void testFindPathWithNullEnd() {
		
		boolean thrown = false;	
		
		try {
			mp.findPath( "Eggs", null);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);		
	}
	
	@Test
	public void testUnknownStartChar() {
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath( "INVALID", "Bacon");
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testUnknownEndChar() {
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath( "Bacon", "INVALID");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testUnknownStartAndEndCharsDifferent() {
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "unknown character INVALID1\nunknown character INVALID2\n";
		String actual = mp.findPath( "INVALID1", "INVALID2");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testUnknownStartAndEndCharsSame() {
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "unknown character INVALID\n";
		String actual = mp.findPath( "INVALID", "INVALID");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testPathNotReflexive(){
		mp.createNewGraph("hw5/data/Reflexive.csv");
		String expected = "path from Toast and Jam to Toast and Jam:\n";
		String actual = mp.findPath( "Toast and Jam", "Toast and Jam");
		
		assertEquals(expected, actual);	
		
	}
	
	@Test
	public void testPathToSelf(){
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "path from Toast and Jam to Toast and Jam:\n";
		String actual = mp.findPath( "Toast and Jam", "Toast and Jam");
		
		assertEquals(expected, actual);	
		
	}
	
	@Test
	public void testNoPathFound() {
		mp.createNewGraph("hw5/data/ShortPath.csv");
		String expected = "path from Bacon to Eggs:\nno path found\n";
		String actual = mp.findPath( "Bacon", "Eggs");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testNoPathFoundAgain() {
		mp.createNewGraph("hw5/data/LongPath.csv");
		String expected = "path from Bacon to Eggs:\nno path found\n";
		String actual = mp.findPath( "Bacon", "Eggs");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testFlashToHumanRobot() {
		mp.createNewGraph("hw5/data/FlashToHumRob.csv");
		String expected = "path from FLASH to HUMAN ROBOT:\n";
		expected += "FLASH to HUMAN ROBOT via AVF 4\n";

		String actual = mp.findPath("FLASH", "HUMAN ROBOT");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testLengthOnePath(){
		mp.createNewGraph("hw5/data/ShortPath.csv");
		
		String expected = "path from Toast and Jam to Eggs:\n";
		expected += "Toast and Jam to Eggs via A\n";

		String actual = mp.findPath("Toast and Jam", "Eggs");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testReverseLengthOnePath(){
		mp.createNewGraph("hw5/data/ShortPath.csv");
		
		String expected = "path from Eggs to Toast and Jam:\n";
		expected += "Eggs to Toast and Jam via A\n";

		String actual = mp.findPath("Eggs", "Toast and Jam");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testLengthTwoPath(){
		mp.createNewGraph("hw5/data/ShortPath2.csv");
		
		String expected = "path from Eggs to OJ:\n";
		expected += "Eggs to Bacon via B\n";
		expected += "Bacon to OJ via E\n";

		String actual = mp.findPath("Eggs", "OJ");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testLengthThreePath(){
		mp.createNewGraph("hw5/data/LongPath.csv");
		
		String expected = "path from Bacon to Milkshake:\n";
		expected += "Bacon to Hamburger via C\n";
		expected += "Hamburger to Fries via B\n";
		expected += "Fries to Milkshake via D\n";

		String actual = mp.findPath("Bacon", "Milkshake");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testLengthFourPath(){
		mp.createNewGraph("hw5/data/LongPath2.csv");
		
		String expected = "path from Toast and Jam to Milkshake:\n";
		expected += "Toast and Jam to Bacon via A\n";
		expected += "Bacon to OJ via C\n";
		expected += "OJ to Fries via E\n";
		expected += "Fries to Milkshake via D\n";

		String actual = mp.findPath("Toast and Jam", "Milkshake");

		assertEquals(expected, actual);
	}
	
	@Test
	public void testReverseLengthFourPath(){
		mp.createNewGraph("hw5/data/LongPath2.csv");
		
		String expected = "path from Milkshake to Toast and Jam:\n";
		expected += "Milkshake to Fries via D\n";
		expected += "Fries to OJ via E\n";
		expected += "OJ to Bacon via C\n";
		expected += "Bacon to Toast and Jam via A\n";

		String actual = mp.findPath("Milkshake", "Toast and Jam");

		assertEquals(expected, actual);
	}
}
