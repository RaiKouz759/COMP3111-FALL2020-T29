package comp3111.popnames;

import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DecimalFormat;

public class Task4 {
	
	public static boolean checkYear(int year) {
		return (year >= 1880) && (year <= 2019);
	}
	
	
	public static ArrayList<ArrayList<String>> NK_T4(String dName, int dYOB, String mName, int mYOB, int vYear){
		int dadRank = AnalyzeNames.getRank(dYOB, dName, "M");
		int momRank = AnalyzeNames.getRank(mYOB, mName, "F");
		if (dadRank == -1) { dadRank = 1;}
		if (momRank == -1) { momRank = 1;}
		ArrayList<ArrayList<String>> recommendation = new ArrayList<ArrayList<String>> ();
		ArrayList<String> boy = new ArrayList<String> (Arrays.asList(AnalyzeNames.getName(vYear, dadRank, "M")));
		ArrayList<String> girl = new ArrayList<String> (Arrays.asList(AnalyzeNames.getName(vYear, momRank, "F")));
		recommendation.add(boy);
		recommendation.add(girl);
		return recommendation;
	}
	
	public static ArrayList<ArrayList<String>> similarity_recommendation(String dName, int dYOB, String mName, int mYOB, int vYear) {
		double bScore1 = 0;
		double gScore1 = 0;
		String bName1 = "";
		String gName1 = "";
		
		double bScore2 = 0;
		double gScore2 = 0;
		String bName2 = "";
		String gName2 = "";
		
		double bScore3 = 0;
		double gScore3 = 0;
		String bName3 = "";
		String gName3 = "";
		
		ArrayList<ArrayList<String>> recommendation = new ArrayList<ArrayList<String>> ();
		
		for (CSVRecord rec : AnalyzeNames.getFileParser(vYear)) {
			if(rec.get(1).equals("M")) {
				double temp_bScore = Algorithm.jaro_distance(dName, rec.get(0)) * Algorithm.jaro_distance(mName, rec.get(0));
				if (temp_bScore > bScore1) {
					bScore3=bScore2;
					bName3=bName2;
					bScore2=bScore1;
					bName2=bName1;
					
					bScore1 = temp_bScore;
					bName1 = rec.get(0);
				} else if (temp_bScore > bScore2) {
					bScore3=bScore2;
					bName3=bName2;
					
					bScore2 = temp_bScore;
					bName2 = rec.get(0);
				} else if (temp_bScore > bScore3) {
					bScore3 = temp_bScore;
					bName3 = rec.get(0);
				}
			}
			else if(rec.get(1).equals("F")) {
				double temp_gScore = Algorithm.jaro_distance(dName, rec.get(0)) * Algorithm.jaro_distance(mName, rec.get(0));
				if (temp_gScore > gScore1) {
					gScore3=gScore2;
					gName3=gName2;
					gScore2=gScore1;
					gName2=gName1;
					
					gScore1 = temp_gScore;
					gName1 = rec.get(0);
				} else if (temp_gScore > gScore2) {
					gScore3=gScore2;
					gName3=gName2;
					
					gScore2 = temp_gScore;
					gName2 = rec.get(0);
				} else if (temp_gScore > gScore3) {
					gScore3 = temp_gScore;
					gName3 = rec.get(0);
				}
			}
		}
		
		DecimalFormat df = new DecimalFormat("#.###");
		
		String b1 = bName1 + " : " + df.format(bScore1);
		String b2 = bName2 + " : " + df.format(bScore2);
		String b3 = bName3 + " : " + df.format(bScore3);
		
		String g1 = gName1 + " : " + df.format(gScore1);
		String g2 = gName2 + " : " + df.format(gScore2);
		String g3 = gName3 + " : " + df.format(gScore3);
		
		ArrayList<String> boy = new ArrayList<String> (Arrays.asList(b1, b2, b3));
		ArrayList<String> girl = new ArrayList<String> (Arrays.asList(g1, g2, g3));
		recommendation.add(boy);
		recommendation.add(girl);
		return recommendation;
	}
	
	public static ArrayList<ArrayList<String>> recommendation(String dName, int dYOB, String mName, int mYOB, int vYear, String choice) {
		if(!checkYear(dYOB))
			throw new RuntimeException("invalid dYOB");
		if(!checkYear(mYOB))
			throw new RuntimeException("invalid mYOB");
		if(!checkYear(vYear))
			throw new RuntimeException("invalid vYear");
		if (!AnalyzeNames.checkNameLength(dName)) {
			throw new RuntimeException("dName length"); 
		}
		if (!AnalyzeNames.checkNameCharacter(dName)) {
			throw new RuntimeException("dName char"); 
		}
		if (!AnalyzeNames.checkNameLength(mName)) {
			throw new RuntimeException("mName length"); 
		}
		if (!AnalyzeNames.checkNameCharacter(mName)) {
			throw new RuntimeException("mName char"); 
		}
		
		ArrayList<ArrayList<String>> recommendation = new ArrayList<ArrayList<String>> ();
		if (choice == "NK-T4") {
			recommendation = NK_T4(dName, dYOB, mName, mYOB, vYear);
		}
		else if(choice == "Jaro") {
			recommendation = similarity_recommendation(dName, dYOB, mName, mYOB, vYear);
		}
		return recommendation;
	}
}
