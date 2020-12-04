package comp3111.popnames;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class focuses on testing the input validation with negative, boundary and valid values. This class also tests valid query executions. 
 * 
 * @author Alex
 *
 */
public class Activity1QueryTest {
	
	public static final String UTF8_BOM = "\uFEFF";

	/**
	 * Test period range close to boundary values.
	 */
	@Test
	public void testPeriodWithBoundary() {
		assertTrue(Activity1Query.isPeriodCorrect(1880, 2019));
		assertFalse(Activity1Query.isPeriodCorrect(1879, 2019));
		assertFalse(Activity1Query.isPeriodCorrect(1879, 2020));
		assertFalse(Activity1Query.isPeriodCorrect(1880, 2020));
		assertTrue(Activity1Query.isPeriodCorrect(1881, 2018));
	}
	
	/**
	 * Test valid period ranges.
	 */
	@Test
	public void testPeriodWithValidRange() {
		assertTrue(Activity1Query.isPeriodCorrect(1950, 2000));
		assertTrue(Activity1Query.isPeriodCorrect(1970, 1980));
	}
	
	/**
	 * Test invalid period ranges such as negative or very large numbers. 
	 */
	@Test
	public void testPeriodWithInvalidRange() {
		assertFalse(Activity1Query.isPeriodCorrect(200, 2000));
		assertFalse(Activity1Query.isPeriodCorrect(-200, 2000));
		assertFalse(Activity1Query.isPeriodCorrect(500, 30000));
		assertFalse(Activity1Query.isPeriodCorrect(1800, 2500));
		assertFalse(Activity1Query.isPeriodCorrect(200, -2000));
	}
	
	/**
	 * Test for number of ranks that are positive and more than 0. 
	 */
	@Test
	public void testValidNumResults() {
		assertTrue(Activity1Query.isNumOfResultsCorrect(4));
		assertTrue(Activity1Query.isNumOfResultsCorrect(200));
	}
	/**
	 * Test for boundary values for the number of ranks.
	 */
	@Test
	public void testBoundaryNumResults() {
		assertTrue(Activity1Query.isNumOfResultsCorrect(1));
		assertFalse(Activity1Query.isNumOfResultsCorrect(0));
		assertTrue(Activity1Query.isNumOfResultsCorrect(2));
	}
	/**
	 * Test for negative inputs for the number of ranks.
	 */
	@Test
	public void testInvalidNumResults() {
		assertFalse(Activity1Query.isNumOfResultsCorrect(-2));
		assertFalse(Activity1Query.isNumOfResultsCorrect(-500));

	}
	/**
	 * Test for a full execution of the query.
	 */
	@Test
	public void testExecuteQuery() {
		// input validation is done at controller. 
		// Single  boundary year & Male

		ArrayList<YearRecords> testSingleYear = Activity1Query.executeQuery(3, 0, 1880, 1880);
		ArrayList<NameRecord> testListNames = testSingleYear.get(0).getNameRecordList();
		assertTrue(testListNames.get(0).getName().equals("John"));
		assertTrue(testListNames.get(1).getName().equals("William"));
		assertTrue(testListNames.get(2).getName().equals("James"));
		assertTrue(testListNames.get(0).getGender() == 0);
		assertTrue(testListNames.get(1).getGender() == 0);
		assertTrue(testListNames.get(2).getGender() == 0);
		assertTrue(testListNames.get(0).getNumOfOccur() == 9655);
		assertTrue(testListNames.get(1).getNumOfOccur() == 9532);
		assertTrue(testListNames.get(2).getNumOfOccur() == 5927);
		
		// Single boundary year & Female
		ArrayList<YearRecords> testFSingleYear = Activity1Query.executeQuery(3, 1, 2019, 2019);
		testListNames = testFSingleYear.get(0).getNameRecordList();
		String firstBOM = testListNames.get(0).getName();
		
		if (firstBOM.startsWith(UTF8_BOM)) {
			firstBOM = firstBOM.substring(1);
		}
		assertTrue(firstBOM.equals("Olivia"));
		assertTrue(testListNames.get(1).getName().equals("Emma"));
		assertTrue(testListNames.get(2).getName().equals("Ava"));
		assertTrue(testListNames.get(0).getGender() == 1);
		assertTrue(testListNames.get(1).getGender() == 1);
		assertTrue(testListNames.get(2).getGender() == 1);
		assertTrue(testListNames.get(0).getNumOfOccur() == 18451);
		assertTrue(testListNames.get(1).getNumOfOccur() == 17102);
		assertTrue(testListNames.get(2).getNumOfOccur() == 14440);
	
		// a range of years & 1 numRank & Male
		ArrayList<YearRecords> testRangeYears = Activity1Query.executeQuery(1, 0, 1960, 1962);
		ArrayList<NameRecord> firstNameList = testRangeYears.get(0).getNameRecordList();
		ArrayList<NameRecord> secondNameList = testRangeYears.get(1).getNameRecordList();
		ArrayList<NameRecord> thirdNameList = testRangeYears.get(2).getNameRecordList();
		assertTrue(firstNameList.get(0).getName().equals("David"));
		assertTrue(secondNameList.get(0).getName().equals("Michael"));
		assertTrue(thirdNameList.get(0).getName().equals("Michael"));
		assertTrue(firstNameList.get(0).getGender() == 0);
		assertTrue(secondNameList.get(0).getGender() == 0);
		assertTrue(thirdNameList.get(0).getGender() == 0);
		assertTrue(firstNameList.get(0).getNumOfOccur() == 85931);
		assertTrue(secondNameList.get(0).getNumOfOccur() == 86916);
		assertTrue(thirdNameList.get(0).getNumOfOccur() == 85046);

		// a range of years & 1 numRank & Female
		testRangeYears = Activity1Query.executeQuery(1, 1, 1960, 1962);
		firstNameList = testRangeYears.get(0).getNameRecordList();
		secondNameList = testRangeYears.get(1).getNameRecordList();
		thirdNameList = testRangeYears.get(2).getNameRecordList();
		assertTrue(firstNameList.get(0).getName().equals("Mary"));
		assertTrue(secondNameList.get(0).getName().equals("Mary"));
		assertTrue(thirdNameList.get(0).getName().equals("Lisa"));
		assertTrue(firstNameList.get(0).getGender() == 1);
		assertTrue(secondNameList.get(0).getGender() == 1);
		assertTrue(thirdNameList.get(0).getGender() == 1);
		assertTrue(firstNameList.get(0).getNumOfOccur() == 51479);
		assertTrue(secondNameList.get(0).getNumOfOccur() == 47667);
		assertTrue(thirdNameList.get(0).getNumOfOccur() == 46083);
	}

}
