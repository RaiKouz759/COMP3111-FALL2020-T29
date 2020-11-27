package comp3111.popnames;

import org.apache.commons.csv.CSVParser;

import edu.duke.FileResource;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	public static boolean storeHistory(String history) throws Exception {
		LocalDate date_today = java.time.LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String str_date = date_today.format(formatter);
		RandomAccessFile file;
		String filePath = new File("").getAbsolutePath();
		System.out.println(filePath);
		filePath = filePath.concat("/src/main/resources/logs");
		try {
			new File(filePath).mkdirs();
			file = new RandomAccessFile(filePath.concat("/" + str_date) + ".txt", "rw");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} 
		FileChannel channel = file.getChannel();
		FileLock lock = null;
		try {
			lock = channel.tryLock();
			
		}catch(final OverlappingFileLockException e) {
			file.close();
			channel.close();
			return false;
		}
		
		file.writeBytes(history + "\n");
		lock.release();
		
		file.close();
		channel.close();
		return true;
		
	}
}
