package comp3111.popnames;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This class focuses on testing the functions of the Activity5Query class. Its input validation functions are tested with negative, valid and boundary values.
 * The execution of the two algorithms are also tested with valid inputs and the output is compared. 
 * 
 * @author Alex
 *
 */
public class Activity5QueryTest {

	/**
	 * Test for valid names, including all caps and having leading spaces.
	 */
	@Test
	public void testNameValid() {
		assertTrue(Activity5Query.isNameCorrect("JANE"));
		assertTrue(Activity5Query.isNameCorrect("John"));
		assertTrue(Activity5Query.isNameCorrect("Mary"));
		assertTrue(Activity5Query.isNameCorrect("   Kurisu"));
		assertTrue(Activity5Query.isNameCorrect("Bond   "));
	}
	
	/**
	 * Test for when name is invalid, such as non-letters included in the name.
	 */
	@Test
	public void testNameInvalid() {
		assertFalse(Activity5Query.isNameCorrect(""));
		assertFalse(Activity5Query.isNameCorrect("*"));
		assertFalse(Activity5Query.isNameCorrect("-"));
		assertFalse(Activity5Query.isNameCorrect("0-"));
		assertFalse(Activity5Query.isNameCorrect("324tggs"));
		assertFalse(Activity5Query.isNameCorrect("-feioj"));
		assertFalse(Activity5Query.isNameCorrect("/"));
		assertFalse(Activity5Query.isNameCorrect("@"));
		assertFalse(Activity5Query.isNameCorrect(" "));
	}
	
	/**
	 * Test for valid year of births with some boundary values.
	 */
	@Test
	public void testYOBValid() {
		assertTrue(Activity5Query.isYOBCorrect(1880));
		assertTrue(Activity5Query.isYOBCorrect(2019));
		assertTrue(Activity5Query.isYOBCorrect(2018));
		assertTrue(Activity5Query.isYOBCorrect(1881));
		assertTrue(Activity5Query.isYOBCorrect(1960));
		
	}
	/**
	 * Test for invalid year of births, including negative and out of range values.
	 */
	@Test
	public void testYOBInvalid() {
		assertFalse(Activity5Query.isYOBCorrect(1879));
		assertFalse(Activity5Query.isYOBCorrect(1878));
		assertFalse(Activity5Query.isYOBCorrect(2020));
		assertFalse(Activity5Query.isYOBCorrect(-1879));
		assertFalse(Activity5Query.isYOBCorrect(3000));
		assertFalse(Activity5Query.isYOBCorrect(0));
	}
	
	/**
	 * Test of a run of the basic NKT5 algorithm and comparing the output in the UI.
	 */
	@Test
	public void testExecuteQueryNKT5() {
		// assumed that inputs are valid. validation done in controller
		
		//name is in record, yob is at boundary 1880, female, pref: female, pref: younger 
		
		String testName = Activity5Query.executeQueryNKT5("Anna", 1880, 1, 1, true);
		assertTrue(testName.equals("Anna"));
		
		//name is in record, yob is at boundary 1880, female, pref: female, pref: older 
		testName = Activity5Query.executeQueryNKT5("Anna", 1880, 1, 1, false);
		assertTrue(testName.equals("Anna"));
		
		testName = Activity5Query.executeQueryNKT5("Brandon", 1901, 0, 1, false);
		assertTrue(testName.equals("Grace"));
			
		//name is in record, yob is at normal value, female, pref: male, pref: younger 
		testName = Activity5Query.executeQueryNKT5("Emily", 1999, 1, 0, true);
		assertTrue(testName.equals("Jacob"));
		
		//name is not in record, yob is at normal value, female, pref: male, pref: younger 
		testName = Activity5Query.executeQueryNKT5("Kurisu", 1999, 1, 0, true);
		assertTrue(testName.equals("Anthony"));
		
		//name is not in record, yob is at normal value, male, pref: female, pref: younger 
		testName = Activity5Query.executeQueryNKT5("Okabe", 1999, 0, 1, true);
		assertTrue(testName.equals("Emma"));
		
		//testing when orank is not found in the case where name is in database
		testName = Activity5Query.executeQueryNKT5("Zonia", 1910, 1, 1, false);
		assertTrue(testName.equals("Mary"));
		
	}
	
	/**
	 * Test for the first part of the jaro algorithm. 
	 */
	@Test
	public void testExecuteQueryJaroStepOne() {
		// assume that inputs are valid.
		
//		String[] sup_out =  {"Alex", "Alexx", "Allexa"};
		ArrayList<String> test_out = Activity5Query.executeQueryJaroStepOne("Alex", 1999, 0, 1, true);
		assertTrue(test_out.size() == 6);
				
	}
	
	/**
	 * Test for the second part of the jaro algorithm, ensuring that the output is consistent. 
	 */
	@Test
	public void testExecuteQueryJaroStepTwo() {
		
		//executeQueryJaroStepTwo(String chosenName, String name, int yob, boolean prefYounger, int prefGender) John 1999 male female younger jaro starlett
		// assume that inputs are valid.

		//younger
		String test_out1 = Activity5Query.executeQueryJaroStepTwo("Starlett", "John", 0, 1999, true, 1);
		assertTrue(test_out1.equals("Starlett"));
		
		//older Mary Jake 1900 older male
		test_out1 = Activity5Query.executeQueryJaroStepTwo("Jake", "Mary", 1, 1900, false, 0);
		assertTrue(test_out1.equals("Jake"));
		
		
	}
	
}
