package comp3111.popnames;

/**
 * @author auyfg
 *
 */
public class RankRecord {
	private final int year;
	private int rank;
	private int count;
	private float percentage;
	private boolean isValid;
	
	/**
	 * @param year
	 */
	public RankRecord(int year) {
		this.year = year;
		rank = -1;
		count = -1;
		percentage = -1;
		isValid = false;
	}

	/**
	 * @param rank
	 * @param count
	 */
	public void set(int rank, int count) {
		isValid = true;
		this.rank = rank;
		this.count = count;
	}

	/**
	 * @param total
	 */
	public void setTotalCount(int total) {
		if(isValid)
			percentage = count / (float) total;
	}


	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return
	 */
	public int getRank() {
		if(!isValid)
			return -1;
		return rank;
	}

	/**
	 * @return
	 */
	public int getCount() {
		if(!isValid)
			return -1;
		return count;
	}

	/**
	 * @return
	 */
	public float getPercentage() {
		if(!isValid)
			return -1;
		return percentage;
	}

	/**
	 * @return
	 */
	public boolean isValid() {
		return isValid;
	}
}
