package hw7.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import hw7.Location;

import org.junit.BeforeClass;
import org.junit.Test;

public class LocationTest {
	
	private static Location location;
	
	/** holds objects for testing direction */
	Location brc = new Location("brc",1,204.0,1228.0);
	Location lrma = new Location("lrma",2,336.0,1275.0);
	Location wb = new Location("wb",3,345.0,1226.0);
	Location empac = new Location("empac",4,426.0,1453.0);
	Location fl = new Location("fl",5,525.0,1377.0);
	Location lh = new Location("lh",6,566.0,1276.0);
	Location tb = new Location("tb",7,660.0,1149.0);
	Location rb = new Location("rb",8,728.0,1149.0);
	Location gym = new Location("gym",9,816.0,1152.0);
	Location clab = new Location("clab",10,668.0,1491.0);
	Location rpiamb = new Location("rpiamb",11,738.0,1607.0);
	Location i94 = new Location(12,370.0,1199.0);
	Location i134 = new Location(13,584.0,1262.0);
	Location i99 = new Location(14,739.0,1569.0);
	
	Location center = new Location("Center",1,5.0,5.0);
	Location northeast = new Location("NorthEast",2,10.0,1.0);
	Location east = new Location("East",3,10.0,5.0);
	Location southeast = new Location("SouthEast",4,10.0,10.0);
	Location south = new Location("South",5,5.0,10.0);
	Location southwest = new Location("SouthWest",6,1.0,10.0);
	Location west = new Location("West",7,1.0,5.0);
	Location northwest = new Location("Center",8,1.0,1.0);
	Location north = new Location("North",9,5.0,1.0);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		location = new Location();
	}
	
	//TODO: TEST MORE EXCEPTIONS
	
	@Test
	public void testFourArgConstructorNullBuilding(){
		boolean thrown = false;

		try {
			new Location(null, 1, 34, 45);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testFourArgContructorBadID(){
		boolean thrown = false;

		try {
			new Location("B", -1, 34, 45);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testFourArgContructorBadXCor(){
		boolean thrown = false;

		try {
			new Location("B", 1, -1.0, 2.0);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testFourArgContructorBadYCor(){
		boolean thrown = false;

		try {
			new Location("B", 1, 5.0, -1.0);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testThreeArgContructorBadID(){
		boolean thrown = false;

		try {
			new Location(0, 1.0, 2.0);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testThreeArgContructorBadXCor(){
		boolean thrown = false;

		try {
			new Location(1, -1.0, 2.0);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testThreeArgContructorBadYCor(){
		boolean thrown = false;
		
		try {
			new Location(1, 1.0, -1.0);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testFourArgConstructorValid(){
		boolean thrown = false;

		try {
			new Location("building", 1, 34, 45);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertFalse(thrown);
	}
	
	@Test
	public void testThreeArgConstructorValid(){
		boolean thrown = false;

		try {
			new Location(1, 34, 45);
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertFalse(thrown);
	}
	
	@Test
	public void testGetDistance(){
		Location x = new Location("building", 1, 4.0,2.0);
		Location y = new Location("building", 1, 8.0,5.0);
		
		assertEquals(5.0, x.getDistanceTo(y), 0.0);
		assertEquals(0.0, x.getDistanceTo(x), 0.0);
	}
	
	@Test
	public void testGetDirectionNorth() {
		assertEquals("North", rpiamb.getDirectionTo(i99));
		assertEquals("North", center.getDirectionTo(north));
	
	}
	
	@Test
	public void testGetDirectionNorthEast() {
		assertEquals("NorthEast", empac.getDirectionTo(fl));
		assertEquals("NorthEast", wb.getDirectionTo(i94));
		assertEquals("NorthEast", lh.getDirectionTo(i134));
		assertEquals("NorthEast", center.getDirectionTo(northeast));
	}
	
	@Test
	public void testGetDirectionEast() {;		
		assertEquals("East", brc.getDirectionTo(lrma));
		assertEquals("East", tb.getDirectionTo(rb));
		assertEquals("East", rb.getDirectionTo(gym));
		assertEquals("East", center.getDirectionTo(east));
	}
	
	@Test
	public void testGetDirectionSouthEast() {
		assertEquals("SouthEast", center.getDirectionTo(southeast));
}
	
	@Test
	public void testGetDirectionSouth() {
		assertEquals("South", center.getDirectionTo(south));
}
	
	@Test
	public void testGetDirectionSouthWest() {
		assertEquals("SouthWest", center.getDirectionTo(southwest));
}
	
	@Test
	public void testGetDirectionWest() {
		assertEquals("West", center.getDirectionTo(west));
}
	
	@Test
	public void testGetDirectionNorthWest() {
		assertEquals("NorthWest", i99.getDirectionTo(clab));
		assertEquals("NorthWest", center.getDirectionTo(northwest));
	}
	
	@Test
	public void testToString(){
		Location x = new Location("building", 1, 4.0,2.0);
		
		assertEquals("<building, 1, 4, 2>", x.toString());
	}
	
	@Test
	public void testEqualsSymmetric() {
		Location x = new Location("building", 1, 34, 45);
		Location y = new Location("building", 1, 34, 45);
		assertTrue(x.equals(y));
		assertTrue(y.equals(x));
		assertEquals(x.hashCode(), y.hashCode());

	}

	@Test
	public void testEqualsReflexive() {
		Location x = new Location("building", 1, 34, 45);
		assertTrue(x.equals(x));
		assertEquals(x.hashCode(), x.hashCode());
	}

	@Test
	public void testEqualsTransitive() {
		Location x = new Location("building", 1, 34, 45);
		Location y = new Location("building", 1, 34, 45);
		Location z = new Location("building", 1, 34, 45);

		assertTrue("Transitive test fails x,y", x.equals(y));
		assertTrue("Transitive test fails y,z", y.equals(x));
		assertTrue("Transitive test fails x,z", x.equals(z));

		assertEquals("Hashcodes not equal: x,y ", x.hashCode(), y.hashCode());
		assertEquals("Hashcodes not equal: y,z ", y.hashCode(), z.hashCode());
		assertEquals("Hashcodes not equal: x,z ", x.hashCode(), z.hashCode());

	}
	
	@Test
	public void testLocationsDontEqual() {
		Location x = new Location("building", 1, 35, 40);
		Location y = new Location("building", 2, 35, 40);
		Location z = new Location("building", 1, 34, 40);
		Location w = new Location("building", 1, 35, 50);
		Location t = new Location("b", 1, 34, 40);
		
		assertFalse("Equals test failed x!=y : id's different", x.equals(y));
		assertFalse("Equals test failed x!=z : x-coords different", x.equals(z));
		assertFalse("Equals test failed x!=w : y-coords different", x.equals(w));
		assertFalse("Equals test failed x!=t : names different", x.equals(t));
		
		assertThat(x.hashCode(), is(not(y.hashCode())));
		assertThat(x.hashCode(), is(not(z.hashCode())));
		assertThat(x.hashCode(), is(not(w.hashCode())));
		assertThat(x.hashCode(), is(not(t.hashCode())));

	}

	@Test
	public void testEqualsNonLocationObject() {
		Location x = new Location("building", 2, 34, 45);
		assertFalse("Object is not an instance of Location", x.equals(new Double(5.0)));

	}

	@Test
	public void testCompareTo() {
		Location a = new Location("building", 1, 6, 6);
		Location b = new Location("building", 1, 8, 8);
		Location c = new Location("new building", 1, 5, 5);

		assertEquals(0, a.compareTo(a));
		assertEquals(-1, a.compareTo(b));
		assertEquals(1, b.compareTo(c));

	}

}
