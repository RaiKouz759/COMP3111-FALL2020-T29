package comp3111.popnames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class deals mainly with Task 3 and contains only static functions to validate data and execute the main query. There are 
 * are three functions that are used for validating inputs.
 * 
 * @author Amrutavarsh
 *
 */
public class Task3 {
	
	/**
	 * Determines trend given two years. 
	 * 
	 * @param high_year the year when the rank was the highest.
	 * @param low_year the year when the rank was the lowest.
	 * @return String value of trend by comparing high_year and low_year.
	 */
	public static String Trend(int high_year, int low_year) {
		if (high_year>low_year) {
			return "UP";
		}
		else if (high_year<low_year) {
			return "DOWN";
		}
		else if (high_year == low_year) {
			return "FLAT";
		}
		return null;
	}

	/**
	 * Input validation function. 
	 * 
	 * @param year the starting year of the period.
	 * @return the boolean if the period is correct and within the range.
	 */
	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	}
	
	/**
	 * Input validation function. 
	 * 
	 * @param start the starting year of the period.
	 * @param end the ending year of the period.
	 * @return the boolean if the period is correct and within the range.
	 */
	public static boolean checkYearPair(int start, int end) {
		return (start >= 1880 && end <= 2019 && start <= end);
	}
	
	/**
	 * Input validation function. 
	 * 
	 * @param TopN the top N ranks to check.
	 * @return the boolean whether topN is greater than or equal to 1.
	 */
	public static boolean validateTopN(int TopN) {
		return (TopN >= 1);
	}
	
	/**
	 * Main algorithm of task 3.
	 * 
	 * @param start_year the starting year in the period.
	 * @param end_year the ending year of the period.
	 * @param gender the gender of the person querying.
	 * @param TopN the top N ranks to be checked.
	 * @return the Arraylist containing records queried
	 */
	public static ArrayList<ArrayList<String>> TopNames(int start_year, int end_year, String gender, int TopN) {
		ArrayList<ArrayList<String>> Entries = new ArrayList<ArrayList<String>> ();
		for (int i=1;i<=TopN;i++) {
			String Name = AnalyzeNames.getName(start_year, i, gender);
			boolean exists = true;
			int low_rank_year = start_year;
			int high_rank_year = start_year;
			
			for(int j=start_year; j<=end_year;j++) {
				boolean exists_this_year = false;
				
				for (int k=1;k<=TopN;k++) {
					if (AnalyzeNames.getName(j, k, gender).equals(Name)) {
						exists_this_year = true;
						if(k>=AnalyzeNames.getRank(low_rank_year, Name, gender)) {
							low_rank_year = j;
						}
						if(k<=AnalyzeNames.getRank(high_rank_year, Name, gender)) {
							high_rank_year = j;
						}
					}
				}
				
				if(exists_this_year == false) {
					exists = false;
					break;
				}
				
			}
			if (exists == true) {
				ArrayList<String> Entry = new ArrayList<String> (Arrays.asList(Name, 
						Integer.toString(low_rank_year), Integer.toString(AnalyzeNames.getRank(low_rank_year, Name, gender)), 
						Integer.toString(high_rank_year), Integer.toString(AnalyzeNames.getRank(high_rank_year, Name, gender)), 
						Trend(high_rank_year, low_rank_year)));            
				Entries.add(Entry);
			}
		}
		
		return Entries;
	}
	
	/**
	 * Main query function of task 3. Validate and parses inputs to the algorithm. Saves logs.
	 * 
	 * @param start_year the starting year in the period.
	 * @param end_year the ending year of the period.
	 * @param gender the gender of the person querying.
	 * @param TopN the top N ranks to be checked.
	 * @return the Arraylist containing records queried
	 */
	public static ArrayList<ArrayList<String>> execute3Query(int start_year, int end_year, String gender, int TopN) {
		
		if (checkYear(start_year)) {
			if(!checkYear(end_year))
				throw new RuntimeException("end"); 
		} else {
			if(checkYear(end_year))
				throw new RuntimeException("start"); 
			else
				throw new RuntimeException("start end"); 
		}
		if (!checkYearPair(start_year, end_year)) {
			throw new RuntimeException("incorrect period order");
		}
		if (!validateTopN(TopN)) {
			throw new RuntimeException("incorrect TopN");
		}
		
		ArrayList<ArrayList<String>> Entries = TopNames(start_year, end_year, gender, TopN);
		
		String query = String.format("Task 3, start_year:%d;end_year:%d;gender:%s;topN:%d",
				 start_year, end_year, gender, TopN);
		try {
			History.storeHistory(query);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to store query history.");
		}
		return Entries;
	}
	
}
