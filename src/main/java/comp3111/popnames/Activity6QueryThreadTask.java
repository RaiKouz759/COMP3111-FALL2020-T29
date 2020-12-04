package comp3111.popnames;

import java.util.*;
import java.lang.Math;
import javafx.util.Pair;
import javafx.concurrent.Task;

public class Activity6QueryThreadTask extends Task<Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>>> {

	private final String genderMate, gender, iName, iNameMate;
	private int iYOB, iYOBMate;

	public Activity6QueryThreadTask(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, int iYOBMate) {
		this.iName = iName;
		this.iNameMate = iNameMate;
		this.iYOBMate = iYOBMate;
		this.iYOB = iYOB;
		this.gender = Constants.genders[iGender];
		this.genderMate = Constants.genders[iGenderMate];
	 }

	@Override
	 protected Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>> call() throws Exception {
		int numFound = 0;
		int numFoundMate = 0;
		RankRecord record = null;
		RankRecord recordMate = null;
		ArrayList<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
		ArrayList<Pair<Double, Double>> pointsMate = new ArrayList<Pair<Double, Double>>();
		updateProgress(0, 2019 - Math.max(iYOB, iYOBMate) + 1);
		int start = Math.max(iYOB, iYOBMate);
		for(int year = start; year <= 2019; ++year) {
			record = AnalyzeNames.getRankRecord(year, iName, gender);
			recordMate = AnalyzeNames.getRankRecord(year, iNameMate, genderMate);
			if (record.isValid()) {
				++numFound;
				points.add(new Pair<Double, Double>((double)year, (double)record.getRank()));
			}
			if (recordMate.isValid()) {
				++numFoundMate;
				pointsMate.add(new Pair<Double, Double>((double)year, (double)recordMate.getRank()));
			}
			updateProgress(year - start + 1, 2019 - start + 1);
		}
		if (numFound < 2 || numFoundMate < 2) {
			throw new RuntimeException("linear"); 
		}
		return new Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>>(points, pointsMate);
	}
}
