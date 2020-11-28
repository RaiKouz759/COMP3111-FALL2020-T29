package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.lang.Float;
import java.util.*;  

import org.junit.Test;

public class Activity2QueryTest {
	@Test
	public void testNameLength() {
		assertFalse(Activity2Query.checkNameLength(""));
		assertFalse(Activity2Query.checkNameLength("A"));
		assertTrue(Activity2Query.checkNameLength("Hi"));
		assertTrue(Activity2Query.checkNameLength("Christiandaniel"));
		assertFalse(Activity2Query.checkNameLength("Christiandaniels"));
	}

	@Test
	public void testNameCharacter() {
		assertFalse(Activity2Query.checkNameCharacter(Character.toString((char)('a' - 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString('a')));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString((char)('a' + 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString((char)('z' - 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString('z')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString((char)('z' + 1))));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString((char)('A' - 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString('A')));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString((char)('A' + 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString((char)('Z' - 1))));
		assertTrue(Activity2Query.checkNameCharacter(Character.toString('Z')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString((char)('Z' + 1))));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString(' ')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString('0')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString(',')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString('-')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString('.')));
		assertFalse(Activity2Query.checkNameCharacter(Character.toString('_')));
	}

	@Test
	public void testYearBoundary() {
		assertFalse(Activity2Query.checkYear(1879));
		assertTrue(Activity2Query.checkYear(1880));
		assertTrue(Activity2Query.checkYear(2019));
		assertFalse(Activity2Query.checkYear(2020));
	}

	@Test
	public void testFindRankValid() {
		RankRecord record = Activity2Query.findRank("Olivia", 1, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == 1);
		assertTrue(record.getCount() == 18451);
		assertTrue(Float.compare(record.getPercentage(), (18451 / (float) 1665373)) == 0);
		
		record = Activity2Query.findRank("Zyrielle", 1, 2019);
		assertTrue(record.getYear() == 2019);
		//assertTrue(record.getRank() == 17905);
		assertTrue(record.getRank() == 15442);
		assertTrue(record.getCount() == 5);
		assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1665373)) == 0);

		record = Activity2Query.findRank("Liam", 0, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == 1);
		assertTrue(record.getCount() == 20502);
		assertTrue(Float.compare(record.getPercentage(), (20502 / (float) 1779948)) == 0);

		record = Activity2Query.findRank("Zyran", 0, 2019);
		assertTrue(record.getYear() == 2019);
		//assertTrue(record.getRank() == 14049);
		assertTrue(record.getRank() == 12080);
		assertTrue(record.getCount() == 5);
		assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1779948)) == 0);
	}

	@Test
	public void testFindRankInvalid() {
		RankRecord record = Activity2Query.findRank("Testing", 1, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == -1);
		assertTrue(record.getCount() == -1);
		assertTrue(Float.compare(record.getPercentage(), -1) == 0);

		record = Activity2Query.findRank("Testing", 0, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == -1);
		assertTrue(record.getCount() == -1);
		assertTrue(Float.compare(record.getPercentage(), -1) == 0);
	}

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
