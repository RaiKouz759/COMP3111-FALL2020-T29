package comp3111.popnames;

/**
 * A class for storing a rank record of a name in a year.
 * 
 * @author James
 *
 */
public class RankRecord {
	private final int year;
	private int rank;
	private int count;
	private float percentage;
	private boolean isValid;
	
	/** 
	 * Constructor.
	 * 
	 * @param year the year to be stored
	 */
	public RankRecord(int year) {
		this.year = year;
		rank = -1;
		count = -1;
		percentage = -1;
		isValid = false;
	}

	/**
	 * Setter for rank and count, also setting the object to be valid. 
	 *
	 * @param rank the rank to be stored
	 * @param count the count to be stored
	 */
	public void set(int rank, int count) {
		isValid = true;
		this.rank = rank;
		this.count = count;
	}

	/**
	 * Setter for the total count of the same gender in the year, calculates and store a percentage of counts.
	 * It will not update anything if the object is invalid.
	 * @param total the total number of name counts of the same gender in the year
	 */
	public void setTotalCount(int total) {
		if(isValid)
			percentage = count / (float) total;
	}


	/**
	 * Getter for the year
	 *
	 * @return year stored in object
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Getter for the rank
	 *
	 * @return rank stored in object, return -1 if it is invalid
	 */
	public int getRank() {
		if(!isValid)
			return -1;
		return rank;
	}

	/**
	 * Getter for the count
	 * @return count stored in object, return -1 if it is invalid
	 */
	public int getCount() {
		if(!isValid)
			return -1;
		return count;
	}

	/**
	 * Getter for the percentage
	 * @return percentage stored in object, return -1 if it is invalid or not total count not set
	 */
	public float getPercentage() {
		if(!isValid)
			return -1;
		return percentage;
	}

	/**
	 * Getter for the validity
	 * @return true if the object is valid, false if the object is invalid
	 */
	public boolean isValid() {
		return isValid;
	}
}
