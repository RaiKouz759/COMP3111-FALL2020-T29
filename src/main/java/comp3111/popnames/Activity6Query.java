package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;
import java.lang.Math;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Map.Entry;
import java.io.IOException;
import javafx.util.Pair;

/**
 * This is the query class for Task 6 and it contains static functions which is a workflow to execute the query. The class should be used in the Controller class.
 *
 * @author James
 *
 */
public class Activity6Query {
	
	/**
	 * A helper function to validate user input and throw runtime exception for any invalid input.
	 * length1: invalid length for iName
	 * length2: invalid length for iNameMate
	 * char1: invalid characters for iName
	 * char2: invalid characters for iNameMate
	 * year1: invalid year iYOB
	 * year2: invalid year iYOBMate
	 *
	 * @param iName the name of first person
	 * @param iYOB the year of birth of the first person
	 * @param iNameMate the name of the second person
	 * @param iYOBMate the year of birth of the second person
	 * @throws Exception invalid inputs
	 */
	public static void validate(String iName, int iYOB, String iNameMate, int iYOBMate) throws Exception {
		if (!AnalyzeNames.checkNameLength(iName)) {
			throw new RuntimeException("length1"); 
		}
		if (!AnalyzeNames.checkNameLength(iNameMate)) {
			throw new RuntimeException("length2"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iName)) {
			throw new RuntimeException("char1"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iNameMate)) {
			throw new RuntimeException("char2"); 
		}
		if (!AnalyzeNames.checkYear(iYOB)) {
			throw new RuntimeException("year1"); 
		}
		if (!AnalyzeNames.checkYear(iYOBMate)) {
			throw new RuntimeException("year2"); 
		}
	}

	/**
	 * A helper function to find the most similar name of the same year and same gender using Jaro distance algorith.
	 * 
	 * @param year year of the preferred name
	 * @param name preferred name
	 * @param gender gender of the name
	 * @return the most similar name of the same gender in the year
	 */
	public static String mostSimilar(int year, String name, String gender) {
		
		// a max-map to store name and score pairs
		Map<Double, String> map = new TreeMap<Double, String>(new Comparator<Double>() {
			@Override
			public int compare(Double left, Double right) {
				return right.compareTo(left);
			}
		});

		// adding each name into map
		CSVParser fileParser = AnalyzeNames.getFileParser(year);
		for (CSVRecord record : fileParser) {
			if (record.get(1).equals(gender)) {
				map.put(Algorithm.jaro_distance(name, record.get(0)), record.get(0));
			}
		}

		// returning the first name
		for (Entry<Double, String> e : map.entrySet()) {
			return e.getValue();
		}
		
		// dataset is empty
		return null;
	}

	/**
	 * Execute task 6 query with the NK-T6 algorithms, standard or normalized, return the compatibility score and save query to history record.
	 * 
	 * @param iName the name of the first person
	 * @param iGender the gender of the first person
	 * @param iYOB the year of birth of the first person
	 * @param iNameMate the name of the second person
	 * @param iGenderMate the gender of the second person
	 * @param isYounger preferred age of the second person relative to the first person
	 * @param normalized indicate whether the score is normalized to the scale 0-1
	 * @return score, not yet converted to percentage
	 * @throws Exception invalid inputs
	 */
	public static float executeNKT6(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, boolean isYounger, boolean normalized) throws Exception {
		
		// input validation
		try {
			validate(iName, iYOB, iNameMate, 2000);
		} catch(Exception e) {
			throw e;
		}

		// setting calculation parameters
		String gender = Constants.genders[iGender];
		String genderMate = Constants.genders[iGenderMate];
		int oRank = AnalyzeNames.getRank(iYOB, iName, gender);
		int oYOB;
		if (isYounger)
            oYOB = iYOB == 2019 ? 2019 : iYOB + 1;
		else
			oYOB = iYOB == 1880 ? 1880 : iYOB - 1;
		
		int oRankMate = AnalyzeNames.getRank(oYOB, iNameMate, genderMate);
		// first name not found
		if (oRank == -1)
			oRank = AnalyzeNames.getRank(iYOB, mostSimilar(iYOB, iName, gender), gender);
		// second name not found
		if (oRankMate == -1)
			oRankMate = AnalyzeNames.getRank(oYOB, mostSimilar(oYOB, iNameMate, genderMate), genderMate);

		// calculate normalized NK-T6 score
		if (normalized) {
			CSVParser fileParser = AnalyzeNames.getFileParser(iYOB);
			int genderCount = 0;
			for (CSVRecord record : fileParser) {
				if (record.get(1).equals(gender)) {
					++genderCount;
				}
			}
			fileParser = AnalyzeNames.getFileParser(oYOB);
			int genderMateCount = 0;
			for (CSVRecord record : fileParser) {
				if (record.get(1).equals(genderMate)) {
					++genderMateCount;
				}
			}
			double percent = oRank / (double) genderCount;
			double percentMate = oRankMate / (double) genderMateCount;
			double diff = Math.abs(percent - percentMate);

			// save to history, algorithm is 1
			saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, (isYounger ? 0 : 1), oYOB, 1);
			return (float)(1 - Math.sqrt(1 - (diff - 1) * (diff - 1)));
		}
		// save to history, algorithm is 0
		saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, (isYounger ? 0 : 1), oYOB, 0);

		// calculate standard NK-T6 score
		return 1 - Math.abs(oRank - oRankMate) / (float)oRank;
	}

	/**
	 * Prepare for query of linear regression score, validate and save inputs.
	 * 
	 * @param iName the name of the first person
	 * @param iGender the gender of the first person
	 * @param iYOB the year of birth of the first person
	 * @param iNameMate the name of the second person
	 * @param iGenderMate the name of the second person
	 * @param preference the index of age preference, 0 for younger, 1 for older, 2 for custom year of birth
	 * @param iYOBMate the year of birth of the second person
	 * @return always return true for valid inputs, throw Exception for invalid inputs
	 * @throws Exception invalid inputs
	 */
	public static boolean prepareLinear(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, int preference, int iYOBMate) throws Exception {
		try {
			validate(iName, iYOB, iNameMate, iYOBMate);
		} catch(Exception e) {
			throw e;
		}
		// save to history, algorithm is 2
		saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, preference, iYOBMate, 2);
		return true;
	}

	/**
	 * Execute Task 6 query of linear regression score, return score and array of regression values
	 *
	 * @param result1 regression values for first name
	 * @param result2 regression values for second name
	 * @param iYOB the year of birth of the first person
	 * @param iYOBMate the year of birth of the second person
	 * @return a Pair object containing the score and an array list of four updated regression values for rendering
	 */
	public static Pair<Double, ArrayList<Double>> executeLinear(Pair<Double, Double> result1, Pair<Double, Double> result2, int iYOB, int iYOBMate) {
		// calculate ranks at the starting year
		double y1 = result1.getKey() * Math.max(iYOB, iYOBMate) + result1.getValue();
		double y2 = result2.getKey() * Math.max(iYOB, iYOBMate) + result2.getValue();
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(result1.getKey());
		result.add(y1);
		result.add(result2.getKey());
		result.add(y2);
		
		// calculating angle difference of two regression lines
		Double angle = Math.atan(result1.getKey()) - Math.atan(result2.getKey());
		// calculating normalized score
		Double score = Math.cos(angle) / 2 + 0.5;
		return new Pair<Double, ArrayList<Double>>(score, result);	
	}

	/**
	 * A helper function to save query history to the history log.
	 *
	 * @param name1 the name of the first person
	 * @param gender1 the gender of the first person
	 * @param year1 the year of birth of the first person
	 * @param name2 the name of the second person
	 * @param gender2 the gender of the second person
	 * @param preference the age preference indicating: 0 - younger, 1 - older, 2 - custom year of birth
	 * @param year2 the year of birth of the second person
	 * @param algorithm the algorithm used: 0 - NK-T6, 1 - Normalized NK-T6, 2 - Linear regression
	 * @throws Exception IO problems in history
	 */
	private static void saveHistory(String name1, int gender1, int year1, String name2, int gender2, int preference, int year2, int algorithm) throws Exception {
		String query = String.format("Task 6, task6TextName1:%s;task6Toggle1:%d;task6TextYear1:%d;task6TextName2:%s;task6Toggle2:%d;task6Toggle3:%d;task6TextYear2:%d;task6Toggle4:%d",
		 name1, gender1, year1, name2, gender2, preference, year2, algorithm);
		try {
			History.storeHistory(query);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to store query history.");
		}
	}
}
