package comp3111.popnames;
import java.util.*;  

public class YearRecords {
	
	private ArrayList<NameRecord> nameRecordList;
	private int numRanks;
	private int year;
	
	public YearRecords(int numRanks, int year) {
		this.nameRecordList = new ArrayList<NameRecord>();
		this.numRanks = numRanks;
		this.year = year;
	}

	public int getNumRanks() {
		return numRanks;
	}

	public void setNumRanks(int numRanks) {
		this.numRanks = numRanks;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ArrayList<NameRecord> getNameRecordList() {
		return nameRecordList;
	}
	
	public void addNameRecord(NameRecord rec) {
		this.nameRecordList.add(rec);
	}
	
}




