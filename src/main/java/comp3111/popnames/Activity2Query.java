package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.io.IOException;
import java.util.*;  
import java.text.DecimalFormat;
import javafx.util.Pair;

/**
 * This is the query class for Task 2 and it only contains one static function to execute the query. The class should be used in the Controller class.
 * 
 * @author James
 *
 */
public class Activity2Query {

	/**
	 * Execute query for task 2 reporting popularity for a name, and save query to history record.
	 *
	 * @param name the name of the person under query
	 * @param gender the gender of the person under query
	 * @param startPeriod the starting year of the query period
	 * @param endPeriod the ending year of the query period
	 * @return a Pair object containing the list of ranking records for each year and the comment.
	 * @throws Exception
	 */
	public static Pair<ArrayList<RankRecord>, String> executeQuery(String name, int gender, int startPeriod, int endPeriod) throws Exception {
		
		// input validation
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

		// adding rank record from each year into an array list
		String str_gender = Constants.genders[gender];
		ArrayList<RankRecord> rankRecords = new ArrayList<RankRecord>();		
		boolean isEndValid = false, isValid = false, isFirstValid = true;
		RankRecord recordPopular = null;
		RankRecord record = null;
		for(int year = startPeriod; year <= endPeriod; ++year) {
			record = AnalyzeNames.getRankRecord(year, name, str_gender);
			rankRecords.add(record);

			// name not found in year
			if (!record.isValid())
				continue;

			isValid = true;

			// check for most popular year
			if (isFirstValid || record.getPercentage() > recordPopular.getPercentage()) {
				recordPopular = record;
				isFirstValid = false;
			}

			if (year == endPeriod)
				isEndValid = true;
		}

		// generatng comment
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

		// saving query to history
		String query = String.format("Task 2, task2TextName:%s;task2RadioMale:%d;task2TextStartPeriod:%d;task2TextEndPeriod:%d",
		 name, gender, startPeriod, endPeriod);
		try {
			History.storeHistory(query);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to store query history.");
		}

		return new Pair<>(rankRecords, result);
	}

}
