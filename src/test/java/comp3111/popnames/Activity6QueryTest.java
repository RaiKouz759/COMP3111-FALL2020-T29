package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static junit.framework.TestCase.fail;
import java.lang.Float;
import java.util.*;  

import javafx.util.Pair;

import org.junit.Test;

/**
 * This class focuses on testing Activity6Query class methods. 
 * 
 * @author James
 *
 */
public class Activity6QueryTest {
	
	/**
	 * Tests validate function with success. 
	 */
	@Test
	public void testValidateInputSuccess() {
		try{
			Activity6Query.validate("Daniel", 1999, "Taylor", 2000);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Tests validate function with failure due to short first name.
	 */
	@Test
	public void testValidateInputFailLength1() {
		try{
			Activity6Query.validate("D", 1999, "Taylor", 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length1"));
		}
	}

	/**
	 * Tests validate function with failure due to short second name.
	 */
	@Test
	public void testValidateInputFailLength2() {
		try{
			Activity6Query.validate("Daniel", 1999, "T", 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length2"));
		}
	}

	/**
	 * Tests validate function with failure due to first name containing non-alphabetical characters.
	 */
	@Test
	public void testValidateInputFailChar1() {
		try{
			Activity6Query.validate("Daniel*", 1999, "Taylor", 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("char1"));
		}
	}

	/**
	 * Tests validate function with failure due to second name containing non-alphabetical characters.
	 */
	@Test
	public void testValidateInputFailChar2() {
		try{
			Activity6Query.validate("Daniel", 1999, "Taylo_r", 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("char2"));
		}
	}

	/**
	 * Tests validate function with failure due to first year out of range.
	 */
	@Test
	public void testValidateInputFailYear1() {
		try{
			Activity6Query.validate("Daniel", 2020, "Taylor", 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("year1"));
		}
	}

	/**
	 * Tests validate function with failure due to second year out of range.
	 */
	@Test
	public void testValidateInputFailYear2() {
		try{
			Activity6Query.validate("Daniel", 1999, "Taylor", 1879);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("year2"));
		}
	}

	/**
	 * Tests mostSimilar function finding the same name.
	 */
	@Test
	public void testMostSimilarIdentical() {
		assertTrue(Activity6Query.mostSimilar(1999, "Daniel", "M").equals("Daniel"));
	}

	/**
	 * Tests mostSimilar function finding correct similar name.
	 */
	@Test
	public void testMostSimilarDifferent() {
		assertTrue(Activity6Query.mostSimilar(1999, "Danieli", "M").equals("Daniel"));
	}

	/**
	 * Tests executeNKT6 function with invalid inputs.
	 */
	@Test
	public void testExecuteQueryNKT6Invalid() {
		try{
			Activity6Query.executeNKT6("D", 0, 1999, "Taylor", 1, true, false);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length1"));
		}
	}

	/**
	 * Tests executeNKT6 function with younger age chosen.
	 */
	@Test
	public void testExecuteQueryNKT6Younger() {
		float score = Activity6Query.executeNKT6("Daniel", 0, 1999, "Taylor", 1, true, false);
		assertTrue(Float.compare(score, (8 / (float) 9)) == 0);
	}

	/**
	 * Tests executeNKT6 function with older age chosen.
	 */
	@Test
	public void testExecuteQueryNKT6Older() {
		float score = Activity6Query.executeNKT6("Taylor", 1, 2000, "Daniel", 0, false, false);
		assertTrue(Float.compare(score, 0.9f) == 0);
	}

	/**
	 * Tests executeNKT6 function with first name not found in dataset.
	 */
	@Test
	public void testExecuteQueryNKT6NotFound1() {
		float score = Activity6Query.executeNKT6("Danieli", 0, 1999, "Taylor", 1, true, false);
		assertTrue(Float.compare(score, (8 / (float) 9)) == 0);
	}

	/**
	 * Tests executeNKT6 function with second name not found in dataset.
	 */
	@Test
	public void testExecuteQueryNKT6NotFound2() {
		float score = Activity6Query.executeNKT6("Taylor", 1, 2000, "Danieli", 0, false, false);
		assertTrue(Float.compare(score, 0.9f) == 0);
	}

	/**
	 * Tests executeNKT6 function normalized, and younger age chosen.
	 */
	@Test
	public void testExecuteQueryNormalized1() {
		float score = Activity6Query.executeNKT6("Emma", 1, 2019, "Emma", 1, true, true);
		assertTrue(Float.compare(score, 1) == 0);
	}

	/**
	 * Tests executeNKT6 function normalized, and older age chosen.
	 */
	@Test
	public void testExecuteQueryNormalized2() {
		float score = Activity6Query.executeNKT6("Mary", 1, 1880, "Mary", 1, false, true);
		assertTrue(Float.compare(score, 1) == 0);
	}
	

	/**
	 * Tests prepareLinear function with failure.
	 */
	@Test
	public void testPrepareLinearInvalid() {
		try{
			Activity6Query.prepareLinear("D", 0, 1999, "Taylor", 1, 2, 2000);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length1"));
		}
	}

	/**
	 * Tests prepareLinear function with success.
	 */
	@Test
	public void testPrepareLinearValid() {
		try{
			Activity6Query.prepareLinear("Daniel", 0, 1999, "Taylor", 1, 2, 2000);
		} catch (Exception e) {
			assertTrue(false);
		}
		assertTrue(true);
	}

	/**
	 * Tests executeLinear function to generate correct regression results.
	 */
	@Test
	public void testExecuteLinear () {
		Pair<Double, Double> result1 = new Pair<Double, Double>(1.0, 2.0);
		Pair<Double, Double> result2 = new Pair<Double, Double>(5.0, 6.0);
		Pair<Double, ArrayList<Double>> ret = Activity6Query.executeLinear(result1, result2, 2000, 1990);
		assertTrue(ret.getKey() >= 0 && ret.getKey() <= 100);
	}
}
