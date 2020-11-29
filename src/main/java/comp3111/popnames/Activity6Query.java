package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;
import java.lang.Math;

public class Activity6Query {
	public static CSVParser getFileParser(int year) {
		FileResource file = new FileResource(String.format("dataset/yob%s.csv", year));
		return file.getCSVParser(false);
	}

	public static boolean checkNameLength(String name) {
		return (name.length() >= 2) && (name.length() <= 15);
	}

	public static boolean checkNameCharacter(String name) {
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	}
	
	public static RankRecord findRank(String name, int gender, int year) {
		RankRecord rankRecord = new RankRecord(year);
		String str_gender = Constants.genders[gender];
		CSVParser fileParser = getFileParser(year);
		int genderTotal = 0;
		int currentRank = 0;
		int currentLine = 0;
		int currentCount = 0;
		for (CSVRecord record : fileParser) {
			if (record.get(1).equals(str_gender)) {
				++currentLine;
				if(currentLine == 1 || Integer.parseInt(record.get(2)) < currentCount){
					currentRank = currentLine;
					currentCount = Integer.parseInt(record.get(2));
				}				
				genderTotal += Integer.parseInt(record.get(2));
				if (record.get(0).replaceAll("[^a-zA-Z]", "").equalsIgnoreCase(name)) {
					rankRecord.set(currentRank, Integer.parseInt(record.get(2)));
				}
			}
		}
		rankRecord.setTotalCount(genderTotal);
		return rankRecord;
	}

	public static float executeQuery(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, boolean isYounger) {
		if (!checkNameLength(iName)) {
			throw new NumberFormatException("length1"); 
		}
		if (!checkNameLength(iNameMate)) {
			throw new NumberFormatException("length2"); 
		}
		if (!checkNameCharacter(iName)) {
			throw new NumberFormatException("char1"); 
		}
		if (!checkNameCharacter(iNameMate)) {
			throw new NumberFormatException("char2"); 
		}
		if (!checkYear(iYOB)) {
			throw new NumberFormatException("year"); 
		}
		RankRecord record1 = findRank(iName, iGender, iYOB);
		int oYOB;
		if (isYounger)
			oYOB = iYOB + 1;
		else
			oYOB = iYOB - 1;
		RankRecord record2 = findRank(iNameMate, iGenderMate, oYOB);
		int oRank, oRankMate;
		if (record1.isValid())
			oRank = record1.getRank();
		else
			oRank = 1;
		if (record2.isValid())
			oRankMate = record2.getRank();
		else
			oRankMate = 1;
		return 1 - Math.abs(oRank - oRankMate) / (float)oRank;
	}
}
