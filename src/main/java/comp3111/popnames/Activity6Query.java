package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;
import java.lang.Math;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Map.Entry;
import java.io.IOException;
import javafx.util.Pair;

public class Activity6Query {
	
	public static void validate(String iName, int iYOB, String iNameMate, int iYOBMate) {
		if (!AnalyzeNames.checkNameLength(iName)) {
			throw new RuntimeException("length1"); 
		}
		if (!AnalyzeNames.checkNameLength(iNameMate)) {
			throw new RuntimeException("length2"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iName)) {
			throw new RuntimeException("char1"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iNameMate)) {
			throw new RuntimeException("char2"); 
		}
		if (!AnalyzeNames.checkYear(iYOB)) {
			throw new RuntimeException("year1"); 
		}
		if (!AnalyzeNames.checkYear(iYOBMate)) {
			throw new RuntimeException("year2"); 
		}
	}

	public static String mostSimilar(int year, String name, String gender) {
		Map<Double, String> map = new TreeMap<Double, String>(new Comparator<Double>() {
			@Override
			public int compare(Double left, Double right) {
				return right.compareTo(left);
			}
		});
		CSVParser fileParser = AnalyzeNames.getFileParser(year);
		for (CSVRecord record : fileParser) {
			if (record.get(1).equals(gender)) {
				map.put(Algorithm.jaro_distance(name, record.get(0)), record.get(0));
			}
		}
		for (Entry<Double, String> e : map.entrySet()) {
			return e.getValue();
		}
		return null;
	}

	public static float executeNKT6(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, boolean isYounger, boolean normalized) {
		try {
			validate(iName, iYOB, iNameMate, 2000);
		} catch(Exception e) {
			throw e;
		}
		String gender = Constants.genders[iGender];
		String genderMate = Constants.genders[iGenderMate];
		int oRank = AnalyzeNames.getRank(iYOB, iName, gender);
		int oYOB;
		if (isYounger)
            oYOB = iYOB == 2019 ? 2019 : iYOB + 1;
		else
			oYOB = iYOB == 1880 ? 1880 : iYOB - 1;
		int oRankMate = AnalyzeNames.getRank(oYOB, iNameMate, genderMate);
		if (oRank == -1)
			oRank = AnalyzeNames.getRank(iYOB, mostSimilar(iYOB, iName, gender), gender);
		if (oRankMate == -1)
			oRankMate = AnalyzeNames.getRank(oYOB, mostSimilar(oYOB, iNameMate, genderMate), genderMate);
		if (normalized) {
			CSVParser fileParser = AnalyzeNames.getFileParser(iYOB);
			int genderCount = 0;
			for (CSVRecord record : fileParser) {
				if (record.get(1).equals(gender)) {
					++genderCount;
				}
			}
			fileParser = AnalyzeNames.getFileParser(oYOB);
			int genderMateCount = 0;
			for (CSVRecord record : fileParser) {
				if (record.get(1).equals(genderMate)) {
					++genderMateCount;
				}
			}
			double percent = oRank / (double) genderCount;
			double percentMate = oRankMate / (double) genderMateCount;
			double diff = Math.abs(percent - percentMate);
			saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, (isYounger ? 0 : 1), oYOB, 1);
			return (float)(1 - Math.sqrt(1 - (diff - 1) * (diff - 1)));
		}
		saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, (isYounger ? 0 : 1), oYOB, 0);
		return 1 - Math.abs(oRank - oRankMate) / (float)oRank;
	}

	public static boolean prepareLinear(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, int preference, int iYOBMate) {
		try {
			validate(iName, iYOB, iNameMate, iYOBMate);
		} catch(Exception e) {
			throw e;
		}
		saveHistory(iName, iGender, iYOB, iNameMate, iGenderMate, preference, iYOBMate, 2);
		return true;
	}

	public static Pair<Double, ArrayList<Double>> executeLinear(Pair<Double, Double> result1, Pair<Double, Double> result2, int iYOB, int iYOBMate) {
		double y1 = result1.getKey() * Math.max(iYOB, iYOBMate) + result1.getValue();
		double y2 = result2.getKey() * Math.max(iYOB, iYOBMate) + result2.getValue();
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(result1.getKey());
		result.add(y1);
		result.add(result2.getKey());
		result.add(y2);
		Double angle = Math.atan(result1.getKey()) - Math.atan(result2.getKey());
		Double score = Math.cos(angle) / 2 + 0.5;
		return new Pair<Double, ArrayList<Double>>(score, result);	
	}

	private static void saveHistory(String name1, int gender1, int year1, String name2, int gender2, int preference, int year2, int algorithm) {
		String query = String.format("Task 6, task6TextName1:%s;task6Toggle1:%d;task6TextYear1:%d;task6TextName2:%s;task6Toggle2:%d;task6Toggle3:%d;task6TextYear2:%d;task6Toggle4:%d",
		 name1, gender1, year1, name2, gender2, preference, year2, algorithm);
		try {
			History.storeHistory(query);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to store query history.");
		}
	}
}
