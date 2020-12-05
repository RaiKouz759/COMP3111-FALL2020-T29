package comp3111.popnames;

/**
 * Helper class to store name records for activity 1. 
 * 
 * @author Alex
 *
 */
public class NameRecord {
	
	private String name;
	private int gender; //0 - male 1 - female
	private int numOfOccur;
	
	
	/**
	 * @param name
	 * @param gender
	 * @param numOfOccur
	 */
	public NameRecord(String name, int gender, int numOfOccur) {
		this.name = name;
		this.gender = gender;
		this.numOfOccur = numOfOccur;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public int getNumOfOccur() {
		return numOfOccur;
	}


	public void setNumOfOccur(int numOfOccur) {
		this.numOfOccur = numOfOccur;
	}
	
	
}
