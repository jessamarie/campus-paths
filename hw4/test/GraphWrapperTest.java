package hw4.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.BeforeClass;
import org.junit.Test;

import hw4.*;

public class GraphWrapperTest {
	private static GraphWrapper graphWrapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		graphWrapper = new GraphWrapper();
		graphWrapper.addNode("Home");
		graphWrapper.addNode("School");
		graphWrapper.addNode("Work");
		graphWrapper.addEdge("Home", "School", "Sidewalk");
		graphWrapper.addEdge("Home", "Home", "Sidewalk");
		graphWrapper.addEdge("School", "School", "Sidewalk");
		graphWrapper.addEdge("Home", "Work", "Car");
	}

	@Test
	public void testlistNodesIterator() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Home");
		expected.add("School");
		expected.add("Work");

		ArrayList<String> result = new ArrayList<String>();
		Iterator<String> it = graphWrapper.listNodes();

		while (it.hasNext()) {
			result.add(it.next());
		}

		assertEquals(expected, result);

	}

	@Test
	public void testlistChildrenOfHome() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Home(Sidewalk)");
		expected.add("School(Sidewalk)");
		expected.add("Work(Car)");

		ArrayList<String> result = new ArrayList<String>();
		Iterator<String> it = graphWrapper.listChildren("Home");

		while (it.hasNext()) {
			result.add(it.next());
		}

		assertEquals(expected, result);
	}

	@Test
	public void testlistChildrenOfSchool() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("School(Sidewalk)");

		ArrayList<String> result = new ArrayList<String>();
		Iterator<String> it = graphWrapper.listChildren("School");

		while (it.hasNext()) {
			result.add(it.next());
		}

		assertEquals(expected, result);
	}

}
