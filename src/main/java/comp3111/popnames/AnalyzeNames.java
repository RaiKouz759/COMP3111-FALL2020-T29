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
	 * @param year
	 * @param name
	 * @param gender
	 * @return
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
	 * @param year
	 * @param name
	 * @param gender
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
	 * @param name
	 * @return
	 */
	public static boolean checkNameLength(String name) {
		return (name.length() >= 2) && (name.length() <= 15);
	}


	/**
	 * @param name
	 * @return
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
	 * @param year
	 * @return
	 */
	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	} 

	/**
	 * @param N
	 * @param sig
	 * @return
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