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

public class Activity2JavaFXTest extends ApplicationTest {

	private Scene s;
	private TextField name, start, end;
    private TableView<Map> table;
	
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
		name = (TextField)s.lookup("#task2TextName");
		start = (TextField)s.lookup("#task2TextStartPeriod");
		end = (TextField)s.lookup("#task2TextEndPeriod");
		table = (TableView<Map>)s.lookup("#task2TableResult");
	}

    
	@Test
	public void testQuery() {	
		clickOn("#tabReport2");
		name.setText("Margaret");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("1882");
		//sleep(1000);
		clickOn("#task2ButtonReport");
		sleep(500);
		assertTrue(table.getColumns().get(0).getCellObservableValue(0).getValue().equals(1880));
		assertTrue(table.getColumns().get(1).getCellObservableValue(0).getValue().equals(6));
		assertTrue(table.getColumns().get(2).getCellObservableValue(0).getValue().equals(1578));
		assertTrue(table.getColumns().get(3).getCellObservableValue(0).getValue().equals(1578 / (float) 90993));

		assertTrue(table.getColumns().get(0).getCellObservableValue(1).getValue().equals(1881));
		assertTrue(table.getColumns().get(1).getCellObservableValue(1).getValue().equals(5));
		assertTrue(table.getColumns().get(2).getCellObservableValue(1).getValue().equals(1658));
		assertTrue(table.getColumns().get(3).getCellObservableValue(1).getValue().equals(1658 / (float) 91954));

		assertTrue(table.getColumns().get(0).getCellObservableValue(2).getValue().equals(1882));
		assertTrue(table.getColumns().get(1).getCellObservableValue(2).getValue().equals(6));
		assertTrue(table.getColumns().get(2).getCellObservableValue(2).getValue().equals(1821));
		assertTrue(table.getColumns().get(3).getCellObservableValue(2).getValue().equals(1821 / (float) 107850));

		name.setText("David");
		clickOn("#task2RadioMale");
		start.setText("1941");
		end.setText("1943");
		//sleep(1000);
		clickOn("#task2ButtonReport");
		sleep(500);
		assertTrue(table.getColumns().get(0).getCellObservableValue(0).getValue().equals(1941));
		assertTrue(table.getColumns().get(1).getCellObservableValue(0).getValue().equals(7));
		assertTrue(table.getColumns().get(2).getCellObservableValue(0).getValue().equals(30551));
		assertTrue(table.getColumns().get(3).getCellObservableValue(0).getValue().equals(30551 / (float) 1227941));

		assertTrue(table.getColumns().get(0).getCellObservableValue(1).getValue().equals(1942));
		assertTrue(table.getColumns().get(1).getCellObservableValue(1).getValue().equals(6));
		assertTrue(table.getColumns().get(2).getCellObservableValue(1).getValue().equals(35892));
		assertTrue(table.getColumns().get(3).getCellObservableValue(1).getValue().equals(35892 / (float) 1380774));

		assertTrue(table.getColumns().get(0).getCellObservableValue(2).getValue().equals(1943));
		assertTrue(table.getColumns().get(1).getCellObservableValue(2).getValue().equals(6));
		assertTrue(table.getColumns().get(2).getCellObservableValue(2).getValue().equals(37237));
		assertTrue(table.getColumns().get(3).getCellObservableValue(2).getValue().equals(37237 / (float) 1426846));

		name.setText("Dddddddd");
		clickOn("#task2RadioMale");
		start.setText("1941");
		end.setText("1942");
		//sleep(1000);
		clickOn("#task2ButtonReport");
		sleep(500);
		assertTrue(table.getColumns().get(0).getCellObservableValue(0).getValue().equals(1941));
		assertTrue(table.getColumns().get(1).getCellObservableValue(0).getValue().equals("NULL"));
		assertTrue(table.getColumns().get(2).getCellObservableValue(0).getValue().equals("NULL"));
		assertTrue(table.getColumns().get(3).getCellObservableValue(0).getValue().equals("NULL"));

		assertTrue(table.getColumns().get(0).getCellObservableValue(1).getValue().equals(1942));
		assertTrue(table.getColumns().get(1).getCellObservableValue(1).getValue().equals("NULL"));
		assertTrue(table.getColumns().get(2).getCellObservableValue(1).getValue().equals("NULL"));
		assertTrue(table.getColumns().get(3).getCellObservableValue(1).getValue().equals("NULL"));

	}

	@Test
	public void testNameEmpty() {
		clickOn("#tabReport2");
		name.setText("");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("1882");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please enter a")).query();
	}

	@Test
	public void testNameShort() {
		clickOn("#tabReport2");
		name.setText("M");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("1882");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Name must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testNameLong() {
		clickOn("#tabReport2");
		name.setText("MargaretMargaret");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("1882");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Name must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testNameCharacter() {
		clickOn("#tabReport2");
		name.setText("Margaret*");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("1882");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Name must contain only letters")).query();
	}

	@Test
	public void testPeriodInvlid() {
		clickOn("#tabReport2");
		name.setText("Margaret");
		clickOn("#task2RadioFemale");
		start.setText("1880a");
		end.setText("1882a");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Period must")).query();
	}

	@Test
	public void testStartPeriodBoundary() {
		clickOn("#tabReport2");
		name.setText("Margaret");
		clickOn("#task2RadioFemale");
		start.setText("1879");
		end.setText("1882");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Starting year")).query();
	}

	@Test
	public void testEndPeriodBoundary() {
		clickOn("#tabReport2");
		name.setText("Margaret");
		clickOn("#task2RadioFemale");
		start.setText("1880");
		end.setText("2020");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Ending year")).query();
	}

	@Test
	public void testStartEndPeriodBoundary() {
		clickOn("#tabReport2");
		name.setText("Margaret");
		clickOn("#task2RadioFemale");
		start.setText("1879");
		end.setText("2020");
		clickOn("#task2ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Both starting")).query();
	}
}
