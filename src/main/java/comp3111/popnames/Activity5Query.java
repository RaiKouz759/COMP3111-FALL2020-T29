package comp3111.popnames;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
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
	public static String[] executeQueryJaroStepOne(String name, int yob, int gender, int prefGender, boolean prefYounger) { 
		String oName[] = new String[3];
		String formatted_name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		String str_gender = Constants.genders[gender];
		String str_prefGender = Constants.genders[prefGender];
		
		int oYOB = yob;
		if (prefYounger && yob < 2019) {
			oYOB = yob + 1;
		} else if (!prefYounger && yob > 1880) {
			oYOB = yob - 1;
		}
		
		// get csv of oYOB and store similarities of all names with user's names
		Map<Double, String> dscsortedMAP = new TreeMap<Double, String>(new Comparator<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		});
		CSVParser fileParser = getFileParser(oYOB);
		for (CSVRecord re : fileParser) {
			if (re.get(1).equals(Constants.genders[prefGender])) {
				dscsortedMAP.put(Algorithm.jaro_distance(formatted_name, re.get(0)), re.get(0));
			}
		}
		int index = 0;
		for (Entry<Double, String> mapData : dscsortedMAP.entrySet()) {
		    oName[index] = mapData.getValue();
		    index++;
		    if (index == 3) {
		    	break;
		    }
		  }
		return oName;
	}
	
	public static String executeQueryJaroStepTwo(String chosenName, int yob, boolean prefYounger, int prefGender) {
		
		int oYOB = yob;
		if (prefYounger && yob < 2019) {
			oYOB = yob + 1;
		} else if (!prefYounger && yob > 1880) {
			oYOB = yob - 1;
		}
		String gender = Constants.genders[prefGender];
		LocalDate date_today = java.time.LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
		String date_day = date_today.format(formatter);
		int day = Integer.parseInt(date_day, 10);
		int rank = AnalyzeNames.getRank(oYOB, chosenName, gender);
		return AnalyzeNames.getName(oYOB, ((rank - day) % rank) + 1, gender);
		
	}
	
	
}
