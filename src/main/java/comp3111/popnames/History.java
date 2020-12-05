package comp3111.popnames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class handles the reading and writing of the log files which stores the history of queries. 
 * 
 * @author Alex
 *
 */
public class History {
	/**
	 * @param query - assume that the query will be in the form of "Task #, name;age;prefgender;gender"
	 * @return string of current date if successful or error if unsuccessful. 
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String storeHistory(String query) throws IOException {
		LocalDateTime date_today = java.time.LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm:ss");
		String str_date = date_today.format(formatter);
		String str_datetime = date_today.format(datetimeFormat);
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
			return "error";
		} 
		FileChannel channel = file.getChannel();
		FileLock lock = null;
		try {
			lock = channel.tryLock();
			
		}catch(final OverlappingFileLockException e) {
			file.close();
			channel.close();
			return "error";
		}
		long fileLength = file.length();
		file.seek(fileLength);
		file.writeBytes(str_datetime + ", " + query + "\n");
		lock.release();
		
		file.close();
		channel.close();
		
		
		return str_date;
		
	}
	/**
	 * 
	 * @param filename - Assumes the filename with its .txt extension. 
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ArrayList<String> readHistory(String filename) throws FileNotFoundException {
		// assume that logs are stored in the logs folder. 
		String filePath = new File("").getAbsolutePath();
		String file_path = filePath.concat(Constants.log_path + "/" + filename);
		System.out.println(file_path);
		File f = new File(file_path);
		ArrayList<String> queries = new ArrayList<>();
		
		if(f.exists() && f.isFile()) { 
		    RandomAccessFile raFile = new RandomAccessFile(f, "r");
		    try {
				while(raFile.getFilePointer() < raFile.length()) {
				     queries.add(raFile.readLine());
				  }

				return queries;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error reading the file");
				return queries;
			}
		} else {
			return queries;
		}
	}
}
