package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;

import java.io.IOException;
import java.util.*;  
import java.util.Map.Entry;

import javax.print.attribute.HashAttributeSet;

public class Activity1Query {
	
	public static LinkedHashMap<String, Integer> top3Names;
	public static String comment = "";
	
	
	/**
	 * Input validation function. 
	 * 
	 * @param start the starting year of the period
	 * @param end the ending year of the period
	 * @return the boolean if the period is correct and within the range
	 */
	public static boolean isPeriodCorrect(int start, int end) {
		if(start >= 1880 && end <= 2019 && start <= end) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	/** 
	 * Input validation function. 
	 * 
	 * @param n the integer of the number of ranks that should be returned in each year
	 * @return boolean if the number of ranks is positive and at least 1
	 */
	public static boolean isNumOfResultsCorrect(int n) {
		if(n >= 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Main query function of task 1.
	 * 
	 * @param numRanks num of results per year that should be returned.
	 * @param gender the gender of the person querying.
	 * @param startPeriod the starting year of the period.
	 * @param endPeriod the ending year of the period.
	 * @return the arraylist containing the YearRecords classes containing the records queried. 
	 */
	public static ArrayList<YearRecords> executeQuery(int numRanks, int gender, int startPeriod, int endPeriod) {
		// assume that input parameters are valid and have been verified. 
		// can also get the top 3 results in the period range
		// what happens when numRanks is 1000? 
		
		ArrayList<YearRecords> yearRecordsList = new ArrayList<YearRecords>();
		String str_gender = Constants.genders[gender];
		for(int year=startPeriod; year<=endPeriod; year++) {
			CSVParser fileParser = AnalyzeNames.getFileParser(year);
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
		}
		// getting top 3 names from the list & most common starting letter of names
		
		top3Names = new LinkedHashMap<>();
		HashMap<Character, Integer> letterCounter = new HashMap<>();
		HashMap<String, Integer> counter = new HashMap<>();
		for (YearRecords year : yearRecordsList) {
			ArrayList<NameRecord> list = year.getNameRecordList();
			for (NameRecord re : list) {
				if (counter.containsKey(re.getName())){
					counter.put(re.getName(), counter.get(re.getName()) + re.getNumOfOccur());
				} else {
					counter.put(re.getName(), re.getNumOfOccur());
				}
				if (letterCounter.containsKey(re.getName().charAt(0))) {
					letterCounter.put(re.getName().charAt(0), letterCounter.get(re.getName().charAt(0)) + 1);
				} else {
					letterCounter.put(re.getName().charAt(0), 1);
				}
			}
		}
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(counter.entrySet());
		List<Entry<Character, Integer>> letterList = new LinkedList<Entry<Character, Integer>>(letterCounter.entrySet());
		

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2)
            {
                    return o2.getValue().compareTo(o1.getValue());
            }
            });
        Collections.sort(letterList, new Comparator<Entry<Character, Integer>>()
        {
            public int compare(Entry<Character, Integer> o1,Entry<Character, Integer> o2)
            {
                    return o2.getValue().compareTo(o1.getValue());
            }
            });
        
        // convert to the linkedList
        int topNum = 3;
        for (Entry<String, Integer> entry : list)
        {
            top3Names.put(entry.getKey(), entry.getValue());
            topNum--;
            if (topNum == 0) {
            	break;
            }
        }
        char mostCommon = letterList.get(0).getKey();
        int numOccur = letterList.get(0).getValue();
        comment = "From the year " + startPeriod + " to " + endPeriod + ", " + list.get(0).getKey() + " is the most common name with " +
        		list.get(0).getValue() + " occurences. \n";
        comment += "The most common first letter for the names in the top ranks is " + mostCommon + " with " + numOccur + " occurences throughout the years. \n";
        
        String query = String.format("Task 1, %d;%d;%d;%d", numRanks, gender, startPeriod, endPeriod);
        
        //saving the query into the history file.
        try {
			History.storeHistory(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to store query history");
		}
        
		return yearRecordsList;
	}	
}
