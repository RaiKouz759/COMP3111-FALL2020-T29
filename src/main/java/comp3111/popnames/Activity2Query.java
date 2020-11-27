package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;  

public class Activity2Query {
	public static CSVParser getFileParser(int year) {
		FileResource file = new FileResource(String.format("dataset/yob%s.csv", year));
		return file.getCSVParser(false);
	}

	private static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	}
	
	public static RankRecord findRank(String name, int gender, int year) {
		RankRecord rankRecord = new RankRecord(year);
		String str_gender = Constants.genders[gender];
		CSVParser fileParser = getFileParser(year);
		int genderTotal = 0;
		int currentRank = 0;
		for (CSVRecord record : fileParser) {
			if(record.get(1).equals(str_gender)){
				++currentRank;
				genderTotal += Integer.parseInt(record.get(2));
				if (record.get(0).equals(name)) {
					rankRecord.set(currentRank, Integer.parseInt(record.get(2)));
				}
			}
		}
		rankRecord.setTotalCount(genderTotal);
		return rankRecord;
	}

	public static ArrayList<RankRecord> executeQuery(String name, int gender, int startPeriod, int endPeriod) {
		if(checkYear(startPeriod)) {
			if(!checkYear(endPeriod))
				throw new NumberFormatException("end"); 
		} else {
			if(checkYear(endPeriod))
				throw new NumberFormatException("start"); 
			else
				throw new NumberFormatException("start end"); 
		}			
		ArrayList<RankRecord> rankRecords = new ArrayList<RankRecord>();
		for(int year = startPeriod; year <= endPeriod; ++year) {
			RankRecord record = findRank(name, gender, year);
			rankRecords.add(record);
		}
		return rankRecords;
	}

}
