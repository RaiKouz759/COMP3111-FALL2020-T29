package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import java.awt.Choice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This test class focuses on testing the reading and rerun functionality of the history class. 
 * 
 * @author Alex
 *
 */
public class HistoryFXTest extends ApplicationTest{
	private Scene s;
	private TableView<Map> table, table2;
	private Label app2Answer;

	
	/**
	 * This function initializes the scene and references all the ui elements that will be used in the testing.
	 */
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Popular Names");
   		stage.show();
   		s = scene;
		table = (TableView<Map>)s.lookup("#historyTableView");
		table2 = (TableView<Map>)s.lookup("#report1Table");
		app2Answer = (Label)s.lookup("#app2Answer");
	}
	
	/**
	 * The main purpose of this function is to write a test log file, and to run the queries stored in there. The output is then compared. 
	 * 
	 * @throws IOException 
	 */
	@Test
	public void testRerunQueries() throws IOException {
		// create the test.txt
		RandomAccessFile file;
		String filePath = new File("").getAbsolutePath();
		System.out.println(filePath);
		filePath = filePath.concat("/src/main/resources/logs");
		try {
			new File(filePath).mkdirs();
			file = new RandomAccessFile(filePath.concat("/" + "test") + ".txt", "rw");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} 
		FileChannel channel = file.getChannel();
		FileLock lock = null;
		try {
			lock = channel.tryLock();
			
		}catch(final OverlappingFileLockException e) {
			file.close();
			channel.close();
			return;
		}
		// add your queries here and then select its index later in table.getSelectionModel().select(index); where index is position from the last write. 
		file.writeBytes("2020, Task 1, numRankTextField:10;maleRadioButton:0;startPeriodTextField:1941;endPeriodTextField:1945" + "\n");
		file.writeBytes("2020, Task 5, app2YourName:Zonia;app2YourGenderM:1;app2YOB:1910;app2SoulGenderM:1;app2SoulYounger:1;app2RadioNK:1" + "\n");
		lock.release();
		
		file.close();
		channel.close();
		// finish writing sample queries into the test file. 
		// navigate to history tab and select first row
		clickOn("#historyTab");
		sleep(20);
//		historyChoice.getSelectionModel().selectFirst();
		sleep(50);
		table.getSelectionModel().select(1);
		clickOn("#historyRerun");	
		sleep(20);
		assertTrue(table2.getColumns().get(1).getCellObservableValue(0).getValue().equals("James"));
//		
//		//navigate back
		clickOn("#historyTab");
		table.getSelectionModel().select(0);
		clickOn("#historyRerun");	
		sleep(20);
		assertTrue(app2Answer.getText().equals("Mary"));
		
	}
	
	
}
