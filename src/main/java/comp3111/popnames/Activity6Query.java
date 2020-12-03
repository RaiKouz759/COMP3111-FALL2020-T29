package comp3111.popnames;

import org.apache.commons.csv.*;

import edu.duke.FileResource;
import java.util.*;
import java.lang.Math;

public class Activity6Query {
	
	public static float executeQuery(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, boolean isYounger) {
		if (!AnalyzeNames.checkNameLength(iName)) {
			throw new NumberFormatException("length1"); 
		}
		if (!AnalyzeNames.checkNameLength(iNameMate)) {
			throw new NumberFormatException("length2"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iName)) {
			throw new NumberFormatException("char1"); 
		}
		if (!AnalyzeNames.checkNameCharacter(iNameMate)) {
			throw new NumberFormatException("char2"); 
		}
		if (!AnalyzeNames.checkYear(iYOB)) {
			throw new NumberFormatException("year"); 
		}
		String gender = Constants.genders[iGender];
		String genderMate = Constants.genders[iGenderMate];
		int oRank = AnalyzeNames.getRank(iYOB, iName, gender);
		int oYOB;
		if (isYounger)
			oYOB = iYOB + 1;
		else
			oYOB = iYOB - 1;
		int oRankMate = AnalyzeNames.getRank(oYOB, iNameMate, genderMate);
		if (oRank == -1)
			oRank = 1;
		if (oRankMate == -1)
			oRankMate = 1;
		return 1 - Math.abs(oRank - oRankMate) / (float)oRank;
	}
}
