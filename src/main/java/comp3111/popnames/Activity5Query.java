package comp3111.popnames;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

public class Activity5Query {
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
		}
	
	public static boolean isNameCorrect(String name) {
		name = name.strip();
		if (name.length() == 0) {
			return false;
		}
		return name.chars().allMatch(Character::isLetter);
	}
	
	public static boolean isYOBCorrect(int yob) {
		if (yob >=1880 && yob <= 2019) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String executeQueryNKT5(String name, int yob, int gender, int prefGender, boolean prefYounger) {
		/** magic number is 17 & assume that all inputs have been verified. 
		 */
		int magicNum = 17;
		String formatted_name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		int oRank = magicNum;
		String str_gender = Constants.genders[gender];
		String str_prefGender = Constants.genders[prefGender];
		
		// go through the csv file with the same yob as user
		CSVParser fileParser = getFileParser(yob);
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
		CSVParser fileParser2 = getFileParser(oYOB);
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
		
		return oName;
		
	}
	
	/**Find a prediction based on jaro distance, takes into account how similar sounding the person's name is to the names in the 
	 * database, then it shows 3 names and asks the user to select one, the predicted oRank will be that name's oRank - todays day % oRank.
	 * younger and older will differ by 1 year like before.  
	 * 
	 * @param name
	 * @param yob
	 * @param gender
	 * @param prefGender
	 * @param prefYounger
	 * @return
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
		CSVParser fileParser = getFileParser(oYOB);
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
		fileParser = getFileParser(oYOB);
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
	
	public static String executeQueryJaroStepTwo(String chosenName, String name, int yob, boolean prefYounger, int prefGender) {
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
		
		CSVParser fileParser = getFileParser(oYOB);
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
		return oName;		
	}
	
	
}
