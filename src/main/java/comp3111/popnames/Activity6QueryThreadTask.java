package comp3111.popnames;

import java.util.*;
import java.lang.Math;
import javafx.util.Pair;
import javafx.concurrent.Task;

/**
 * This is a thread task class for task 6 query, which prepares the two array of data points for linear regression. This class should be used in the Controller and in a new thread.
 * 
 * @author James
 *
 */
public class Activity6QueryThreadTask extends Task<Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>>> {

	private final String genderMate, gender, iName, iNameMate;
	private int iYOB, iYOBMate;

	/**
	 * Contructor for the class. The class should created within a new thread.
	 * 
	 * @param iName  the name of the first person
	 * @param iGender the gender of the first person
	 * @param iYOB the year of birth of the first person
	 * @param iNameMate the name of the second person
	 * @param iGenderMate the gender of the second person
	 * @param iYOBMate the year of birth of the second person
	 */
	public Activity6QueryThreadTask(String iName, int iGender, int iYOB, String iNameMate, int iGenderMate, int iYOBMate) {
		this.iName = iName;
		this.iNameMate = iNameMate;
		this.iYOBMate = iYOBMate;
		this.iYOB = iYOB;
		this.gender = Constants.genders[iGender];
		this.genderMate = Constants.genders[iGenderMate];
	 }

	/**
	 * The actual execution call to generate two arrays of data points for each person, representing the year and rank data points.
	 *
	 * @return a Pair object which contains the two arrays respectively
	 * @throws Exception not enough data points
	 */
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
		// looping through each year
		for(int year = start; year <= 2019; ++year) {
			record = AnalyzeNames.getRankRecord(year, iName, gender);
			recordMate = AnalyzeNames.getRankRecord(year, iNameMate, genderMate);

			// first name found
			if (record.isValid()) {
				++numFound;
				points.add(new Pair<Double, Double>((double)year, (double)record.getRank()));
			}

			// second name found
			if (recordMate.isValid()) {
				++numFoundMate;
				pointsMate.add(new Pair<Double, Double>((double)year, (double)recordMate.getRank()));
			}

			// update prograss for the thread
			updateProgress(year - start + 1, 2019 - start + 1);
		}

		// not enough points for regression
		if (numFound < 2 || numFoundMate < 2) {
			throw new RuntimeException("points"); 
		}
		
		return new Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>>(points, pointsMate);
	}
}
