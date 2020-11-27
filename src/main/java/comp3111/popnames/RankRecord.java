package comp3111.popnames;

public class RankRecord {
	private final int year;
	private int rank;
	private int count;
	private float percentage;
	private boolean isValid;
	
	public RankRecord(int year) {
		this.year = year;
		rank = -1;
		count = -1;
		percentage = -1;
		isValid = false;
	}

	public void set(int rank, int count) {
		isValid = true;
		this.rank = rank;
		this.count = count;
	}

	public void setTotalCount(int total) {
		if(isValid)
			percentage = count / (float) total;
	}


	public int getYear() {
		return year;
	}

	public int getRank() {
		if(!isValid)
			return -1;
		return rank;
	}

	public int getCount() {
		if(!isValid)
			return -1;
		return count;
	}

	public float getPercentage() {
		if(!isValid)
			return -1;
		return percentage;
	}

	public boolean isValid() {
		return isValid;
	}
}
