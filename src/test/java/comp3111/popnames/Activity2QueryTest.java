package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.lang.Float;
import java.util.*;  

import org.junit.Test;

public class Activity2QueryTest {
	
	@Test
	public void testExecuteQuery() {
		ArrayList<RankRecord> rankRecords = Activity2Query.executeQuery("Margaret", 1, 1880, 1882);
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

		rankRecords = Activity2Query.executeQuery("David", 0, 1941, 1943);
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

	@Test
	public void testExecuteQueryInvalid() {
		ArrayList<RankRecord> rankRecords;
		try{
			rankRecords = Activity2Query.executeQuery("", 1, 1880, 1882);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("M", 1, 1880, 1882);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("Christiandaniels", 1, 1880, 1882);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("Margaret*", 1, 1880, 1882);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("Margaret", 1, 1879, 1882);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("Margaret", 1, 1880, 2020);
		} catch (Exception e) {}
		try{
			rankRecords = Activity2Query.executeQuery("Margaret", 1, 1879, 2020);
		} catch (Exception e) {}
	}
}
