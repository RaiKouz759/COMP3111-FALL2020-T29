package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;  

public class Activity1Query {
	
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
		}
	
	public static boolean isPeriodCorrect(int start, int end) {
		if(start >= 1880 && end <= 2019 && start <= end) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	public static boolean isNumOfResultsCorrect(int n) {
		if(n >= 1) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<YearRecords> executeQuery(int numRanks, int gender, int startPeriod, int endPeriod) {
		// assume that input parameters are valid and have been verified. 
		// can also get the top 3 results in the period range
		// what happens when numRanks is 1000? 
		
		ArrayList<YearRecords> yearRecordsList = new ArrayList<YearRecords>();
		String str_gender = Constants.genders[gender];
		for(int year=startPeriod; year<=endPeriod; year++) {
			CSVParser fileParser = getFileParser(year);
			YearRecords yearRecord = new YearRecords(numRanks, year);
			int counter = 0;
			for (CSVRecord re : fileParser) {
				if (counter >= numRanks) {
					break;
				}
				else {
					
					if (re.get(1).equals(str_gender)) {
					
					NameRecord nameRecord = new NameRecord(re.get(0), gender, Integer.parseInt(re.get(2)));
					yearRecord.addNameRecord(nameRecord);
					System.out.println("added nameRecord");
					counter++;
					}
				}
				

			}
			yearRecordsList.add(yearRecord);
			System.out.println("added a year record");
			System.out.println();
		}
		
		return yearRecordsList;
	}	
}
