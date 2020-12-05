package comp3111.popnames;

import org.apache.commons.csv.*;
import edu.duke.*;

/**
 * @author James, Codebase
 *
 */
public class AnalyzeNames {

	/**Returns a CSVParser which you can iterate through. 
	 * 
	 * @param year the year for which you want to request the CSVParse of. 
	 * @return the CSVParser of the year you input. 
	 */
	public static CSVParser getFileParser(int year) {
		FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
		return fr.getCSVParser(false);
	}
	
	/**
	 * Get a summary of the year ranking
	 *
	 * @param year the year of the query
	 * @return a string summary
	 */
	public static String getSummary(int year) {
		String oReport = "";	
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		int totalNames = 0;
		int uniqueBoys = 0;
		int uniqueGirls = 0;
		
		oReport = String.format("Summary (Year of %d):\n", year);
		for (CSVRecord rec : getFileParser(year)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			totalNames += 1;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				uniqueBoys++;
			}
			else {
				totalGirls += numBorn;
				uniqueGirls++;
			}
		}
		
		oReport += String.format("Total Births = %,d\n", totalBirths);
		oReport += String.format("***Baby Girls = %,d\n", totalGirls);
		oReport += String.format("***Baby Boys = %,d\n", totalBoys);
		
		oReport += String.format("Total Number of Unique Names = %,d\n", totalNames);
		oReport += String.format("***Unique Names (baby girls) = %,d\n", uniqueGirls);
		oReport += String.format("***Unique Names (baby boys) = %,d\n", uniqueBoys);
		
		return oReport;
	}
	
	
	/**
	 * Get a RankRecord object from the given year, name and gender of the person
	 * 
	 * @param year the year under query
	 * @param name the name of the person
	 * @param gender the gender of the person
	 * @return a RankRecord of the recond, an empty RankRecord for non-existing record in file
	 */
	public static RankRecord getRankRecord(int year, String name, String gender) {
		RankRecord rankRecord = new RankRecord(year);
		CSVParser fileParser = getFileParser(year);
		int genderTotal = 0;
		int currentRank = 0;
		int currentCount = 0;
		for (CSVRecord record : fileParser) {
			if (record.get(1).equals(gender)) {
				++currentRank;
				currentCount = Integer.parseInt(record.get(2));
				genderTotal += currentCount;
				if (record.get(0).replaceAll("[^a-zA-Z]", "").equalsIgnoreCase(name)) {
					rankRecord.set(currentRank, currentCount);
				}
			}
		}
		rankRecord.setTotalCount(genderTotal);
		return rankRecord;
	}

	 /**
	 * Get rank of the person in the year of query
	 * 
	 * @param year the year for the query
	 * @param name the name of the person
	 * @param gender the gender of the person
	 * @return
	 */
	public static int getRank(int year, String name, String gender) {
		return getRankRecord(year, name, gender).getRank();
		/*boolean found = false;
		int oRank = 0;
		int rank = 1;
		 for (CSVRecord rec : getFileParser(year)) {
			 // Increment rank if gender matches param
			 if (rec.get(1).equals(gender)) {
				 // Return rank if name matches param
				 if (rec.get(0).equals(name)) {
					found = true;
					oRank = rank;
					break;
				 }
				 rank++;
			 }
		 }
		 if (found)
			return oRank;
		 else
			return -1;*/
	 }
	 
 
	 /**
	 * Get the name of the given rank, year and gender
	 * 
	 * @param year the year of query
	 * @param rank the rank of the name popularity
	 * @param gender the gender of the person
	 * @return
	 */
	public static String getName(int year, int rank, String gender) {
		boolean found = false;
		 String oName = "";
		 int currentRank = 0;
		 
		 // For every name entry in the CSV file
		 for (CSVRecord rec : getFileParser(year)) {
			 // Get its rank if gender matches param
			 if (rec.get(1).equals(gender)) {
				 // Get the name whose rank matches param
				currentRank++;
				if (currentRank == rank) {
					found = true;
					oName = rec.get(0);
					break;
				}
			 }
		 }     
		 if (found)
			return oName;
		 else
			return "information on the name at the specified rank is not available";
	 }


	/**
	 * Validate the length of a name to be between 2 and 15 characters, return true if it is valid 
	 *
	 * @param name the name under query
	 * @return boolean indicating whether the name is valid
	 */
	public static boolean checkNameLength(String name) {
		return (name.length() >= 2) && (name.length() <= 15);
	}


	/**
	 * Validate the name only contains alphebet characters, return true if it is valid 
	 * 
	 * @param name the name under query
	 * @return boolean indicating whether the name is valid
	 */
	public static boolean checkNameCharacter(String name) {
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Validate the year is within 1880 and 2019
	 *
	 * @param year the year of query
	 * @return boolean indicating whether the year is valid
	 */
	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	} 

	/**
	 * A helper function, round off a number to a number of significant figures
	 * @param N the number to be rounded
	 * @param sig the number of significant figures needed
	 * @return the rounded number
	 */
	static double round(double N, double sig) 
	{ 
		int h;
		double l, a, b, c, d, e, i, j, m, f, g;
		b = N;
		c = Math.floor(N);
  
		for (i = 0; b >= 1; ++i)
			b = b / 10;

		d = sig - i;
		b = N;
		b = b * Math.pow(10, d);
		e = b + 0.5;
		if ((float)e == (float)Math.ceil(b)) {
			f = (Math.ceil(b));
			h = (int)(f - 2);
			if (h % 2 != 0) {
				e = e - 1;
			}
		}
		j = Math.floor(e);
		m = Math.pow(10, d);
		j = j / m;
		return j;
	} 

}