package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;  
import java.text.DecimalFormat;
import javafx.util.Pair;

public class Activity2Query {

	public static Pair<ArrayList<RankRecord>, String> executeQuery(String name, int gender, int startPeriod, int endPeriod) {
		if (!AnalyzeNames.checkNameLength(name)) {
			throw new RuntimeException("length"); 
		}
		if (!AnalyzeNames.checkNameCharacter(name)) {
			throw new RuntimeException("char"); 
		}
		if (AnalyzeNames.checkYear(startPeriod)) {
			if(!AnalyzeNames.checkYear(endPeriod))
				throw new RuntimeException("end"); 
		} else {
			if(AnalyzeNames.checkYear(endPeriod))
				throw new RuntimeException("start"); 
			else
				throw new RuntimeException("startend"); 
		}			
		String str_gender = Constants.genders[gender];
		ArrayList<RankRecord> rankRecords = new ArrayList<RankRecord>();
		
		boolean isEndValid = false, isValid = false, isFirstValid = true;
		RankRecord recordPopular = null;
		RankRecord record = null;
		for(int year = startPeriod; year <= endPeriod; ++year) {
			record = AnalyzeNames.getRankRecord(year, name, str_gender);
			rankRecords.add(record);
			if (!record.isValid())
				continue;
			isValid = true;
			if (isFirstValid || record.getPercentage() > recordPopular.getPercentage()) {
				recordPopular = record;
				isFirstValid = false;
			}
			if (year == endPeriod)
				isEndValid = true;
		}
		DecimalFormat df = new DecimalFormat("0");
		df.setMaximumFractionDigits(340);
		String result = "";
		if (isEndValid)
			result += "In the year " 
				+ String.valueOf(endPeriod) + ", the number of birth with name "
				+ name + " is "
				+ String.valueOf(record.getCount()) + ", which represents "
				+ df.format(AnalyzeNames.round(record.getPercentage() * 100, 5)) + " percent of total gender births in "
				+ String.valueOf(record.getYear()) + ". ";
		if (isValid)
			result += "The year when the name "
				+ name + " was most popular is "
				+ String.valueOf(recordPopular.getYear()) + ". In that year, the number of births is "
				+ String.valueOf(recordPopular.getCount()) + ", which represents "
				+ df.format(AnalyzeNames.round(recordPopular.getPercentage() * 100, 5)) + " percent of the total gender birth in "
				+ String.valueOf(recordPopular.getYear()) + ".";
		else
			result = "The name " + name + " does not appear in our dataset within the given range.";
		return new Pair<>(rankRecords, result);
	}

}
