package comp3111.popnames;

import org.apache.commons.csv.CSVParser;

import edu.duke.FileResource;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock; 
import java.nio.channels.OverlappingFileLockException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




public class Utility {
	
	
	/**Returns a CSVParser which you can iterate through. 
	 * 
	 * @param year the year for which you want to request the CSVParse of. 
	 * @return the CSVParser of the year you input. 
	 */
	public static CSVParser getFileParser(int year) {
	     FileResource fr = new FileResource(String.format("dataset/yob%s.csv", year));
	     return fr.getCSVParser(false);
		}
	
}
