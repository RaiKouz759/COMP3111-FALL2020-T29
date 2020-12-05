package comp3111.popnames;
import java.util.*;  

/**
 * This class focuses on storing NamRecords for Activity1.
 * 
 * @author Alex
 *
 */
public class YearRecords {
	
	private ArrayList<NameRecord> nameRecordList;
	private int numRanks;
	private int year;
	
	/**
	 * Constructor for YearRecords.
	 * 
	 * @param numRanks numRanks to create the object.
	 * @param year year to create the object.
	 */
	public YearRecords(int numRanks, int year) {
		this.nameRecordList = new ArrayList<NameRecord>();
		this.numRanks = numRanks;
		this.year = year;
	}

	/**
	 * getter for numRanks. 
	 * 
	 * @return number of ranks.
	 */
	public int getNumRanks() {
		return numRanks;
	}

	/**
	 * setter for numRanks.
	 * 
	 * @param numRanks the number of ranks to set.
	 */
	public void setNumRanks(int numRanks) {
		this.numRanks = numRanks;
	}

	/**
	 * getter for year. 
	 * 
	 * @return the year of the YearRecords.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * setter for year.
	 * 
	 * @param year the year of the YearRecords.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * getting for arraylist of name record. 
	 * 
	 * @return arraylist of namerecords.
	 */
	public ArrayList<NameRecord> getNameRecordList() {
		return nameRecordList;
	}
	
	/**
	 * Function to add NameRecord to the arraylist. 
	 * 
	 * @param rec the NameRecord object to be added into the arraylist. 
	 */
	public void addNameRecord(NameRecord rec) {
		this.nameRecordList.add(rec);
	}
	
}




