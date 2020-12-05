package comp3111.popnames;

import java.util.*;
import java.lang.Math;
import javafx.util.Pair;
import javafx.concurrent.Task;

/**
 * This is a thread task class for linear regression. This class should be used in the Controller and in a new thread. 
 *
 * @author James
 *
 */
public class LinearRegressionTask extends Task<Pair<Double, Double>> {
	 private final ArrayList<Pair<Double, Double>> points;

	 /** 
	 * Constructor. The class should created within a new thread.
	 * 
	 * @param points an array of points for regression
	 */
	public LinearRegressionTask(ArrayList<Pair<Double, Double>> points) {
		 this.points = points;
	 }

	 /**
	 * The actual execution call to generate the linear regression.
	 *
	 * @return a Pair object which contains the resulting slope and intercept of the line
	 */
	@Override
	 protected Pair<Double, Double> call() throws Exception {
	 	int n = points.size();
	 	
		if(n < 2)
			throw new RuntimeException("linear");

		double sumX = 0.0;
		double sumY = 0.0;
		double sumX2 = 0.0;
		double sumXY = 0.0;
		updateProgress(0, n);
		for (int i = 0; i < n; i++) {
			sumX  += points.get(i).getKey();
			sumY  += points.get(i).getValue();
			sumXY += points.get(i).getKey() * points.get(i).getValue();
			sumX2 += points.get(i).getKey() * points.get(i).getKey();
			updateProgress(i, n);
		}
		return new Pair<Double, Double>((n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX),
			(sumY * sumX2 - sumX * sumXY) / (n * sumX2 - sumX * sumX));
	 }
 }