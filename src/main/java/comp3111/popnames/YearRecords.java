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
	 * @param numRanks 
	 * @param year
	 */
	public YearRecords(int numRanks, int year) {
		this.nameRecordList = new ArrayList<NameRecord>();
		this.numRanks = numRanks;
		this.year = year;
	}

	/**
	 * getter for numRanks. 
	 * 
	 * @return
	 */
	public int getNumRanks() {
		return numRanks;
	}

	/**
	 * setter for numRanks.
	 * 
	 * @param numRanks
	 */
	public void setNumRanks(int numRanks) {
		this.numRanks = numRanks;
	}

	/**
	 * getter for year. 
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * setter for year.
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * getting for arraylist of name record. 
	 * 
	 * @return
	 */
	public ArrayList<NameRecord> getNameRecordList() {
		return nameRecordList;
	}
	
	/**
	 * Function to add NameRecord to the arraylist. 
	 * 
	 * @param rec
	 */
	public void addNameRecord(NameRecord rec) {
		this.nameRecordList.add(rec);
	}
	
}




