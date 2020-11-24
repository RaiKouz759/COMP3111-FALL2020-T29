package comp3111.popnames;

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
		if (prefYounger && yob > 1880) {
			oYOB = yob - 1;
		} else if (!prefYounger && yob < 2019) {
			oYOB = yob + 1;
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
			}
			index++;
		}
		// in the case where there is no oRank name
		if (oName.equals("hello")) {
			oName = firstMatch;
		}
		
		return oName;
		
	}
	
}
