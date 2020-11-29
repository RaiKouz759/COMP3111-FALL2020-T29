package comp3111.popnames;

import java.util.ArrayList;

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
}
