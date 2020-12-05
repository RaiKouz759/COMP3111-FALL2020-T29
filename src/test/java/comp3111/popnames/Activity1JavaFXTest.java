package comp3111.popnames;

import static org.junit.Assert.*;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.api.FxAssert;
import java.util.*;  

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;

/**
 * This class focuses solely on FX tests for the Activity1Query class, focusing on input validation and ensuring that valid output is obtained. 
 * 
 * @author Alex
 *
 */
public class Activity1JavaFXTest extends ApplicationTest {
	
	private Scene s;
	private TextArea comments;
	private TableView<Map> table;
	private TextField n, start, end;
	private RadioButton male;

	
	/**
	 * Initializes the scene and references all the necessary ui elements for testing. 
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
		comments = (TextArea)s.lookup("#rep1Comment");
		n = (TextField)s.lookup("#numRankTextField");
		start = (TextField)s.lookup("#startPeriodTextField");
		end = (TextField)s.lookup("#endPeriodTextField");
		table = (TableView<Map>)s.lookup("#report1Table");
	}
	
	/**
	 * Tests for the response when wrong number of ranks is input. 
	 */
	@Test
	public void testNInvalid() {
		// test N 
		clickOn("#tabReport1");
		n.setText("0");
		start.setText("1880");
		end.setText("1882");
		clickOn("#task1Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please enter an N")).query();
	}
	/**
	 * Test for when the starting year is beyond the stated range. 
	 */
	@Test
	public void testInvalidPeriod() {
		// test invalid period
		clickOn("#tabReport1");
		n.setText("1");
		start.setText("1879");
		end.setText("1880");
		clickOn("#task1Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Start and End periods must be")).query();
	}
	
	/**
	 * Test for when non-numbers are input in the number of ranks field. 
	 */
	@Test
	public void testInvalidNInputFormat() {
		// test invalid period
		clickOn("#tabReport1");
		n.setText("*awffwaafw");
		start.setText("1889");
		end.setText("1882");
		clickOn("#task1Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please only enter")).query();
	}
	
	/**
	 * Test for a normal run of the query. 
	 */
	@Test
	public void testExecuteQuery() {
		clickOn("#tabReport1");
		n.setText("1");
		clickOn("#maleRadioButton");
		start.setText("1960");
		end.setText("1962");
		clickOn("#task1Button");
		sleep(500);
		assertTrue(table.getColumns().get(1).getCellObservableValue(0).getValue().equals("David"));
		assertTrue(table.getColumns().get(1).getCellObservableValue(1).getValue().equals("Michael"));
		assertTrue(table.getColumns().get(1).getCellObservableValue(2).getValue().equals("Michael"));
	}
	
	
}
