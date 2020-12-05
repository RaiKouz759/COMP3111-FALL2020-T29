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
	 * Constructor for NameRecord.
	 * 
	 * @param name name of the person. 
	 * @param gender gender of person stored in int.
	 * @param numOfOccur number of times this name appears.
	 */
	public NameRecord(String name, int gender, int numOfOccur) {
		this.name = name;
		this.gender = gender;
		this.numOfOccur = numOfOccur;
	}


	/**
	 * getter for name. 
	 * 
	 * @return the name stored
	 */
	public String getName() {
		return name;
	}


	/**
	 * setter for name. 
	 * 
	 * @param name the name to be stored
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * getter for gender.
	 * 
	 * @return the gender stored
	 */
	public int getGender() {
		return gender;
	}


	/**
	 * setter for gender
	 * 
	 * @param gender the gender to be stored
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}


	/**
	 * getter for number of occurrences
	 * 
	 * @return the number of occurrence stored
	 */
	public int getNumOfOccur() {
		return numOfOccur;
	}


	/**
	 * @param numOfOccur the number of occurrence to be stored
	 */
	public void setNumOfOccur(int numOfOccur) {
		this.numOfOccur = numOfOccur;
	}
	
	
}
