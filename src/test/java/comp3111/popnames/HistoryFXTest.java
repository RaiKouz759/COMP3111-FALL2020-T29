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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HistoryFXTest extends ApplicationTest{
	private Scene s;
	private TextArea comments;
	private TableView<Map> table, table2;
	private TextField n, start, end;
	private RadioButton male;
	private ChoiceBox historyChoice;

	
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
		comments = (TextArea)s.lookup("#rep1Comment");
		n = (TextField)s.lookup("#numRankTextField");
		start = (TextField)s.lookup("#startPeriodTextField");
		end = (TextField)s.lookup("#endPeriodTextField");
		table = (TableView<Map>)s.lookup("#historyTableView");
		historyChoice = (ChoiceBox)s.lookup("#historyChoice");
		table2 = (TableView<Map>)s.lookup("#report1Table");
	}
	
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
		long fileLength = file.length();
		file.seek(fileLength);
		file.writeBytes("2020, Task 1, numRankTextField:10;maleRadioButton:0;startPeriodTextField:1941;endPeriodTextField:1945" + "\n");
		lock.release();
		
		file.close();
		channel.close();
		// finish writing sample queries into the test file. 
		// navigate to history tab and select first row
		clickOn("#historyTab");
		sleep(20);
//		historyChoice.getSelectionModel().selectFirst();
		sleep(50);
		table.getSelectionModel().select(0);
		clickOn("#historyRerun");	
		sleep(20);
		assertTrue(table2.getColumns().get(1).getCellObservableValue(0).getValue().equals("James"));
//		
//		//navigate back
		clickOn("#historyTab");
		table.getSelectionModel().select(1);
		sleep(20);
	}
	
	
}
