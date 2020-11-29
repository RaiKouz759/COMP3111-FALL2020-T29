package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.lang.Float;
import java.util.*;  

import org.junit.Test;

public class Activity6QueryTest {
	@Test
	public void testNameLength() {
		assertFalse(Activity6Query.checkNameLength(""));
		assertFalse(Activity6Query.checkNameLength("A"));
		assertTrue(Activity6Query.checkNameLength("Hi"));
		assertTrue(Activity6Query.checkNameLength("Christiandaniel"));
		assertFalse(Activity6Query.checkNameLength("Christiandaniels"));
	}

	@Test
	public void testNameCharacter() {
		assertFalse(Activity6Query.checkNameCharacter(Character.toString((char)('a' - 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString('a')));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString((char)('a' + 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString((char)('z' - 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString('z')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString((char)('z' + 1))));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString((char)('A' - 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString('A')));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString((char)('A' + 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString((char)('Z' - 1))));
		assertTrue(Activity6Query.checkNameCharacter(Character.toString('Z')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString((char)('Z' + 1))));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString(' ')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString('0')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString(',')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString('-')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString('.')));
		assertFalse(Activity6Query.checkNameCharacter(Character.toString('_')));
	}

	@Test
	public void testYearBoundary() {
		assertFalse(Activity6Query.checkYear(1879));
		assertTrue(Activity6Query.checkYear(1880));
		assertTrue(Activity6Query.checkYear(2019));
		assertFalse(Activity6Query.checkYear(2020));
	}

	@Test
	public void testFindRankValid() {
		RankRecord record = Activity6Query.findRank("Olivia", 1, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == 1);
		assertTrue(record.getCount() == 18451);
		assertTrue(Float.compare(record.getPercentage(), (18451 / (float) 1665373)) == 0);
		
		record = Activity6Query.findRank("Zyrielle", 1, 2019);
		assertTrue(record.getYear() == 2019);
		//assertTrue(record.getRank() == 17905);
		assertTrue(record.getRank() == 15442);
		assertTrue(record.getCount() == 5);
		assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1665373)) == 0);

		record = Activity6Query.findRank("Liam", 0, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == 1);
		assertTrue(record.getCount() == 20502);
		assertTrue(Float.compare(record.getPercentage(), (20502 / (float) 1779948)) == 0);

		record = Activity6Query.findRank("Zyran", 0, 2019);
		assertTrue(record.getYear() == 2019);
		//assertTrue(record.getRank() == 14049);
		assertTrue(record.getRank() == 12080);
		assertTrue(record.getCount() == 5);
		assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1779948)) == 0);
	}

	@Test
	public void testFindRankInvalid() {
		RankRecord record = Activity6Query.findRank("Testing", 1, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == -1);
		assertTrue(record.getCount() == -1);
		assertTrue(Float.compare(record.getPercentage(), -1) == 0);

		record = Activity6Query.findRank("Testing", 0, 2019);
		assertTrue(record.getYear() == 2019);
		assertTrue(record.getRank() == -1);
		assertTrue(record.getCount() == -1);
		assertTrue(Float.compare(record.getPercentage(), -1) == 0);
	}

	@Test
	public void testExecuteQuery() {
		float score = Activity6Query.executeQuery("Daniel", 0, 1999, "Taylor", 1, true);
		assertTrue(Float.compare(score, (8 / (float) 9)) == 0);

		score = Activity6Query.executeQuery("Taylor", 1, 2000, "Daniel", 0, false);
		assertTrue(Float.compare(score, 0.9f) == 0);

		score = Activity6Query.executeQuery("Daniellll", 0, 1999, "Taylorrrrr", 1, true);
		assertTrue(Float.compare(score, 1.0f) == 0);
	}

	@Test
	public void testExecuteQueryInvalid() {
		float score;
		try{
			score = Activity6Query.executeQuery("", 0, 1999, "Taylor", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("D", 0, 1999, "Taylor", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Christiandaniels", 0, 1999, "Taylor", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Daniel*", 0, 1999, "Taylor", 1, true);
		} catch (Exception e) {}

		try{
			score = Activity6Query.executeQuery("Daniel", 0, 1999, "", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Daniel", 0, 1999, "T", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Daniel", 0, 1999, "Christiandaniels", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Daniel", 0, 1999, "Taylor*", 1, true);
		} catch (Exception e) {}

		try{
			score = Activity6Query.executeQuery("Daniel", 0, 1879, "Taylor", 1, true);
		} catch (Exception e) {}
		try{
			score = Activity6Query.executeQuery("Daniel", 0, 2020, "Taylor", 1, true);
		} catch (Exception e) {}
	}
}
