package comp3111.popnames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Task3 {
	
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

	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	}
	
	public static boolean checkYearPair(int start, int end) {
		return (start >= 1880 && end <= 2019 && start <= end);
	}
	
	public static boolean validateTopN(int TopN) {
		return (TopN >= 1);
	}
	
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
