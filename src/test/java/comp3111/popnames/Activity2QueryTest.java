package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static junit.framework.TestCase.fail;
import java.lang.Float;
import java.util.*;  
import javafx.util.Pair;

import org.junit.Test;

/**
 * This class focuses on testing Activity2Query class methods. 
 * 
 * @author James
 *
 */
public class Activity2QueryTest {
	
	/**
	 * Tests executeQuery function with success, female and male names. 
	 */
	@Test
	public void testExecuteQuery() {
		// female name
		Activity2Query activity2 = new Activity2Query();
		ArrayList<RankRecord> rankRecords = activity2.executeQuery("Margaret", 1, 1880, 1882).getKey();
		assertTrue(rankRecords.size() == 3);
		
		assertTrue(rankRecords.get(0).getYear() == 1880);
		assertTrue(rankRecords.get(0).getRank() == 6);
		assertTrue(rankRecords.get(0).getCount() == 1578);
		assertTrue(Float.compare(rankRecords.get(0).getPercentage(), (1578 / (float) 90993)) == 0);

		assertTrue(rankRecords.get(1).getYear() == 1881);
		assertTrue(rankRecords.get(1).getRank() == 5);
		assertTrue(rankRecords.get(1).getCount() == 1658);
		assertTrue(Float.compare(rankRecords.get(1).getPercentage(), (1658 / (float) 91954)) == 0);

		assertTrue(rankRecords.get(2).getYear() == 1882);
		assertTrue(rankRecords.get(2).getRank() == 6);
		assertTrue(rankRecords.get(2).getCount() == 1821);
		assertTrue(Float.compare(rankRecords.get(2).getPercentage(), (1821 / (float) 107850)) == 0);

		// male name
		rankRecords = activity2.executeQuery("David", 0, 1941, 1943).getKey();
		assertTrue(rankRecords.size() == 3);
		
		assertTrue(rankRecords.get(0).getYear() == 1941);
		assertTrue(rankRecords.get(0).getRank() == 7);
		assertTrue(rankRecords.get(0).getCount() == 30551);
		assertTrue(Float.compare(rankRecords.get(0).getPercentage(), (30551 / (float) 1227941)) == 0);

		assertTrue(rankRecords.get(1).getYear() == 1942);
		assertTrue(rankRecords.get(1).getRank() == 6);
		assertTrue(rankRecords.get(1).getCount() == 35892);
		assertTrue(Float.compare(rankRecords.get(1).getPercentage(), (35892 / (float) 1380774)) == 0);

		assertTrue(rankRecords.get(2).getYear() == 1943);
		assertTrue(rankRecords.get(2).getRank() == 6);
		assertTrue(rankRecords.get(2).getCount() == 37237);
		assertTrue(Float.compare(rankRecords.get(2).getPercentage(), (37237 / (float) 1426846)) == 0);
	}

	/**
	 * Tests executeQuery function with failure.
	 */
	@Test
	public void testExecuteQueryInvalid() {
		Pair<ArrayList<RankRecord>, String> result;
		// empty name
		try{
			result = Activity2Query.executeQuery("", 1, 1880, 1882);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length"));            
		}

		// short name
		try{
			result = Activity2Query.executeQuery("M", 1, 1880, 1882);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length"));            
		}

		// long name
		try{
			result = Activity2Query.executeQuery("Christiandaniels", 1, 1880, 1882);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("length"));            
		}

		// invalid charaters in name
		try{
			result = Activity2Query.executeQuery("Margaret*", 1, 1880, 1882);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("char"));            
		}

		// starting year out of range
		try{
			result = Activity2Query.executeQuery("Margaret", 1, 1879, 1882);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("start"));            
		}

		// ending year out of range
		try{
			result = Activity2Query.executeQuery("Margaret", 1, 1880, 2020);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("end"));            
		}

		// starting and ending years out of range
		try{
			result = Activity2Query.executeQuery("Margaret", 1, 1879, 2020);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("startend"));
		}
	}
}
