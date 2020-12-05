package comp3111.popnames;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.TreeMap;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

/**
 * This class deals mainly with Task 5 of the project. It contains only static functions that validate input and execute one of two types of queries.
 * Inputs that are validated include the name and the year of birth of the user. The basic algorithm is contained in the executeQueryNKT5 function, 
 * while the jaro algorithm is split into two functions as further input is needed from the user after the first step. 
 * 
 * @author Alex
 *
 */
public class Activity5Query {
	
	/**
	 * Input validation.
	 * 
	 * @param name name of the person querying.
	 * @return boolean checking if the name has at least one length and that it only contains letters. 
	 */
	public static boolean isNameCorrect(String name) {
		name = name.strip();
		if (name.length() == 0) {
			return false;
		}
		return name.chars().allMatch(Character::isLetter);
	}
	
	/**
	 * Input Validation. 
	 * 
	 * @param yob year of birth of the person querying.
	 * @return boolean if the year is within the range stated.
	 */
	public static boolean isYOBCorrect(int yob) {
		if (yob >=1880 && yob <= 2019) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * The function to execute NK algorithm. Displays the predicted name based on it.
	 * 
	 * @param name name of person querying. 
	 * @param yob year of birth of person querying.
	 * @param gender gender of person querying.
	 * @param prefGender preferred gender of soulmate.
	 * @param prefYounger whether the user wants his or her soulmate to be younger than him.
	 * @return the name of predicted soulmate. 
	 */
	public static String executeQueryNKT5(String name, int yob, int gender, int prefGender, boolean prefYounger) {
		/** magic number is 17 & assume that all inputs have been verified. 
		 */
		int magicNum = 17;
		String formatted_name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		int oRank = magicNum;
		String str_gender = Constants.genders[gender];
		String str_prefGender = Constants.genders[prefGender];
		
		// go through the csv file with the same yob as user
		CSVParser fileParser = AnalyzeNames.getFileParser(yob);
		int rank = 1;
		boolean matched = false;
		for (CSVRecord re : fileParser) {
			if (!re.get(1).equals(str_gender)) {
				continue;
			}
			if (re.get(0).equals(formatted_name)) {
				matched = true;
				oRank = rank;
				break;
			}
			rank++;
		}
		
		// calculate oYOB, take care about edge values
		int oYOB = yob;
		if (prefYounger && yob < 2019) {
			oYOB = yob + 1;
		} else if (!prefYounger && yob > 1880) {
			oYOB = yob - 1;
		}
		
		// get name of oRank in the year oYOB with preferred gender, if does not exist, get the highest rank
		CSVParser fileParser2 = AnalyzeNames.getFileParser(oYOB);
		int index = 1;
		String oName = "hello";
		String firstMatch = "kurisu";
		boolean firstMatched = false;
		for (CSVRecord re : fileParser2) {
			if (!re.get(1).equals(str_prefGender)) {
				continue;
			}
			if (re.get(1).equals(str_prefGender) && !firstMatched) {
				firstMatched = true;
				firstMatch = re.get(0);
			}
			if (index == oRank) {
				oName = re.get(0);
				break;
			}
			index++;
		}
		// in the case where there is no oRank name
		if (oName.equals("hello")) {
			oName = firstMatch;
		}
//		String name, int yob, int gender, int prefGender, boolean prefYounger
		int prefYoung = 0;
		if (!prefYounger) {
			prefYoung = 1;
		}
		String query = String.format("Task 5, app2YourName:%s;app2YourGenderM:%d;app2YOB:%d;app2SoulGenderM:%d;app2SoulYounger:%d;app2RadioNK:%d", name, gender, yob, prefGender, prefYoung, 1);
        
        //saving the query into the history file.
        try {
			History.storeHistory(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to store query history");
		}
		
		return oName;
		
	}
	
	/**
	 * The first step of a two step process. Find a prediction based on jaro distance, takes into account how similar sounding the person's name is to the names in the 
	 * database. It first shows 6 random names and asks the user to select one, the predicted oName will be the name that is closest to both
	 * the user and the chosen name. 
	 * 
	 * @param name name of user.
	 * @param yob year of birth of user.
	 * @param gender gender of user.
	 * @param prefGender preferred gender of soulmate.
	 * @param prefYounger if the user prefers to have a younger soulmate.
	 * @return returns an arraylist of random names to be passed to the second step.
	 */
	public static ArrayList<String> executeQueryJaroStepOne(String name, int yob, int gender, int prefGender, boolean prefYounger) { 
		ArrayList<String> oName = new ArrayList<>();
		String str_prefGender = Constants.genders[prefGender];
		Map<Integer, Integer> rand_map = new HashMap<>();
		
		int oYOB = yob;
		if (prefYounger && yob < 2019) {
			oYOB = yob + 1;
		} else if (!prefYounger && yob > 1880) {
			oYOB = yob - 1;
		}
		
		// get max number of ranks
		CSVParser fileParser = AnalyzeNames.getFileParser(oYOB);
		int numRanks = 0;
		for (CSVRecord re : fileParser) {
			if (re.get(1).equals(str_prefGender)) {
				numRanks++;
			}
		}
		
		// storing the random numbers
		for (int i=0; i<6; i++) {
			int random_value = ThreadLocalRandom.current().nextInt(1, numRanks + 1);
			while (rand_map.containsKey(random_value)) { 
				random_value = ThreadLocalRandom.current().nextInt(1, numRanks + 1);
			} 
			System.out.println(random_value);
			rand_map.put(random_value, 1);
			System.out.println(rand_map.get(random_value));
		}
		
		int curr_rank = 1;
		fileParser = AnalyzeNames.getFileParser(oYOB);
		for (CSVRecord re : fileParser) {
			if (re.get(1).equals(str_prefGender)) {
				curr_rank++; 
				if (rand_map.containsKey(curr_rank)) {
					oName.add(re.get(0));
				}				
			}
		}

		return oName;
	}
	
	/**
	 * The second step of the Jaro algorithm. It takes the chosen name of the user and searches against the database for the name that sounds most similar to both the user and 
	 * the chosen name.
	 * 
	 * @param chosenName chosen name from step one.
	 * @param name name of the user.
	 * @param gender gender of the user. 
	 * @param yob year of birth of the user. 
	 * @param prefYounger boolean of whether the user prefers a younger soulmate.
	 * @param prefGender whether the user prefers a male or female soulmate
	 * @return the name of the predicted soulamate. 
	 */
	public static String executeQueryJaroStepTwo(String chosenName, String name, int gender, int yob, boolean prefYounger, int prefGender) {
		String oName = "undefined";
		String formatted_name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		
		int oYOB = yob;
		if (prefYounger && yob < 2019) {
			oYOB = yob + 1;
		} else if (!prefYounger && yob > 1880) {
			oYOB = yob - 1;
		}
		String pref_Gender = Constants.genders[prefGender];
		//store the product of the similarities between the chosen name and name. 
		Map<Double, String> dscsortedMAP = new TreeMap<Double, String>(new Comparator<Double>() {
		
			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		});
		
		CSVParser fileParser = AnalyzeNames.getFileParser(oYOB);
		for (CSVRecord re : fileParser) {
			if (re.get(1).equals(pref_Gender)) {
				double prod_score = Algorithm.jaro_distance(chosenName, re.get(0)) * Algorithm.jaro_distance(formatted_name, re.get(0));
				dscsortedMAP.put(prod_score, chosenName);
			}
		}
		for (Entry<Double, String> mapData : dscsortedMAP.entrySet()) {
			oName =  mapData.getValue();
			break;
		}
		
		// getting inputs to store. gender - 0=male, prefgender -0=Male, app2radionk - 0=not using nk algo
		int prefYoung = 0;
		if (!prefYounger) {
			prefYoung = 1;
		}
		String query = String.format("Task 5, app2YourName:%s;app2YourGenderM:%d;app2YOB:%d;app2SoulGenderM:%d;app2SoulYounger:%d;app2RadioNK:%d;chosenName:%s", formatted_name, gender, yob, prefGender, prefYoung, 0, chosenName);
        
        //saving the query into the history file.
        try {
			History.storeHistory(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to store query history");
		}
        
		
		return oName;	
		


	}
	
	
}
