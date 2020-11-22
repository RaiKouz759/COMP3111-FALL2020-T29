package comp3111.popnames;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Activity1QueryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPeriodWithBoundary() {
		assertTrue(Activity1Query.isPeriodCorrect(1880, 2019));
	}
	
	@Test
	public void testPeriodWithValidRange() {
		assertTrue(Activity1Query.isPeriodCorrect(1950, 2000));
	}
	
	@Test
	public void testPeriodWithInvalidRange() {
		assertFalse(Activity1Query.isPeriodCorrect(200, 2000));
	}
	
	@Test
	public void testValidNumResults() {
		assertTrue(Activity1Query.isNumOfResultsCorrect(4));
	}
	@Test
	public void testBoundaryNumResults() {
		assertTrue(Activity1Query.isNumOfResultsCorrect(1));
	}
	@Test
	public void testInvalidNumResults() {
		assertFalse(Activity1Query.isNumOfResultsCorrect(-2));
	}

}
