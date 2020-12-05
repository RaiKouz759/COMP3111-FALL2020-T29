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

public class Activity3JavaFXTest extends ApplicationTest {
	
	private Scene s;
	private TableView<Map> table;
	private TextField topN, start, end;
	private RadioButton male;
	
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
		topN = (TextField)s.lookup("#t3_topN");
		start = (TextField)s.lookup("#task3_year_start");
		end = (TextField)s.lookup("#task3_year_end");
		table = (TableView<Map>)s.lookup("#task3_table");
	}
	
	@Test
	public void testQuery1() {
		clickOn("#tabReport3");
		topN.setText("4");
		clickOn("#t3_f");
		start.setText("1939");
		end.setText("1944");
		clickOn("#task3_report");
		sleep(1000);
		assertTrue(table.getColumns().get(0).getCellObservableValue(0).getValue().equals("Mary"));
		assertTrue(table.getColumns().get(1).getCellObservableValue(0).getValue().equals("1944"));
		assertTrue(table.getColumns().get(2).getCellObservableValue(0).getValue().equals("1"));
		assertTrue(table.getColumns().get(3).getCellObservableValue(0).getValue().equals("1944"));
		assertTrue(table.getColumns().get(4).getCellObservableValue(0).getValue().equals("1"));
		assertTrue(table.getColumns().get(5).getCellObservableValue(0).getValue().equals("FLAT"));

		assertTrue(table.getColumns().get(0).getCellObservableValue(1).getValue().equals("Barbara"));
		assertTrue(table.getColumns().get(1).getCellObservableValue(1).getValue().equals("1944"));
		assertTrue(table.getColumns().get(2).getCellObservableValue(1).getValue().equals("2"));
		assertTrue(table.getColumns().get(3).getCellObservableValue(1).getValue().equals("1944"));
		assertTrue(table.getColumns().get(4).getCellObservableValue(1).getValue().equals("2"));
		assertTrue(table.getColumns().get(5).getCellObservableValue(1).getValue().equals("FLAT"));

		assertTrue(table.getColumns().get(0).getCellObservableValue(2).getValue().equals("Patricia"));
		assertTrue(table.getColumns().get(1).getCellObservableValue(2).getValue().equals("1944"));
		assertTrue(table.getColumns().get(2).getCellObservableValue(2).getValue().equals("4"));
		assertTrue(table.getColumns().get(3).getCellObservableValue(2).getValue().equals("1943"));
		assertTrue(table.getColumns().get(4).getCellObservableValue(2).getValue().equals("3"));
		assertTrue(table.getColumns().get(5).getCellObservableValue(2).getValue().equals("DOWN"));
	}
	
	@Test
	public void testStartYearBoundary() {
		clickOn("#tabReport3");
		topN.setText("4");
		clickOn("#t3_f");
		start.setText("1879");
		end.setText("1944");
		clickOn("#task3_report");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Starting year must be")).query();
	}
	
	@Test
	public void testEndYearBoundary() {
		clickOn("#tabReport3");
		topN.setText("4");
		clickOn("#t3_f");
		start.setText("1943");
		end.setText("2020");
		clickOn("#task3_report");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Ending year")).query();
	}
	
	@Test
	public void testStartEndYearBoundary() {
		clickOn("#tabReport3");
		topN.setText("4");
		clickOn("#t3_f");
		start.setText("1879");
		end.setText("2020");
		clickOn("#task3_report");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Both starting")).query();
	}
	
	@Test
	public void testStartEndOrder() {
		clickOn("#tabReport3");
		topN.setText("4");
		clickOn("#t3_f");
		start.setText("1956");
		end.setText("1938");
		clickOn("#task3_report");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Starting year is ahead")).query();
	}
	
	@Test
	public void topN_Boundary() {
		clickOn("#tabReport3");
		topN.setText("0");
		clickOn("#t3_f");
		start.setText("1940");
		end.setText("1947");
		clickOn("#task3_report");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("TopN must")).query();
	}
	
}
