package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;  

public class Activity2Query {

	public static ArrayList<RankRecord> executeQuery(String name, int gender, int startPeriod, int endPeriod) {
		if (!AnalyzeNames.checkNameLength(name)) {
			throw new NumberFormatException("length"); 
		}
		if (!AnalyzeNames.checkNameCharacter(name)) {
			throw new NumberFormatException("char"); 
		}
		if (AnalyzeNames.checkYear(startPeriod)) {
			if(!AnalyzeNames.checkYear(endPeriod))
				throw new NumberFormatException("end"); 
		} else {
			if(AnalyzeNames.checkYear(endPeriod))
				throw new NumberFormatException("start"); 
			else
				throw new NumberFormatException("start end"); 
		}			
		String str_gender = Constants.genders[gender];
		ArrayList<RankRecord> rankRecords = new ArrayList<RankRecord>();
		for(int year = startPeriod; year <= endPeriod; ++year) {
			RankRecord record = AnalyzeNames.getRankRecord(year, name, str_gender);
			rankRecords.add(record);
		}
		return rankRecords;
	}

}
