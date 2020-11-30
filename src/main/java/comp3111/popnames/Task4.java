package comp3111.popnames;

import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVRecord;

public class Task4 {
	
	
	public static String NK_T4(String dName, int dYOB, String mName, int mYOB, int vYear){
		int dadRank = AnalyzeNames.getRank(dYOB, dName, "M");
		int momRank = AnalyzeNames.getRank(mYOB, mName, "F");
		if (dadRank == -1) { dadRank = 1;}
		if (momRank == -1) { momRank = 1;}
		String recommendation = "";
		recommendation += "boyName : " + AnalyzeNames.getName(vYear, dadRank, "M") + "\n";
		recommendation += "girlName : " + AnalyzeNames.getName(vYear, momRank, "F");
		return recommendation;
	}
	
	public static String similarity_recommendation(String dName, int dYOB, String mName, int mYOB, int vYear) {
		double bScore = 0;
		double gScore = 0;
		String bName = "";
		String gName = "";
		String recommendation = "";
		
		for (CSVRecord rec : AnalyzeNames.getFileParser(vYear)) {
			if(rec.get(1).equals("M")) {
				double temp_bScore = Algorithm.jaro_distance(dName, rec.get(0)) * Algorithm.jaro_distance(mName, rec.get(0));
				if (temp_bScore > bScore) {
					bScore = temp_bScore;
					bName = rec.get(0);
				}
			}
			else if(rec.get(1).equals("F")) {
				double temp_gScore = Algorithm.jaro_distance(dName, rec.get(0)) * Algorithm.jaro_distance(mName, rec.get(0));
				if (temp_gScore > gScore) {
					gScore = temp_gScore;
					gName = rec.get(0);
				}
			}
		}
		recommendation += "boyName : " + bName + "  score: " + bScore+ "\n";
		recommendation += "girlName : " + gName + "  score: " + gScore;
		return recommendation;
	}
	
	public static String recommendation(String dName, int dYOB, String mName, int mYOB, int vYear, String choice) {
		String recommendation = "";
		if (choice == "NK-T4") {
			recommendation = NK_T4(dName, dYOB, mName, mYOB, vYear);
		}
		else if(choice == "Jaro") {
			recommendation = similarity_recommendation(dName, dYOB, mName, mYOB, vYear);
		}
		recommendation += "\n Jack:" + Algorithm.jaro_distance("Jack", "Jack");
		return recommendation;
	}
}
