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

public class Activity6JavaFXTest extends ApplicationTest {

	private Scene s;
	private TextField name1, name2, year;
	private Label result;
	
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
		name1 = (TextField)s.lookup("#task6TextName1");
		name2 = (TextField)s.lookup("#task6TextName2");
		year = (TextField)s.lookup("#task6TextYear");
		result = (Label)s.lookup("#task6TextResult");
	}

    
	@Test
	public void testQuery() {	
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		sleep(500);
		assertTrue(result.getText().startsWith("Your score of compatibility is 0.8888889"));

		name1.setText("Taylor");
		clickOn("#task6RadioFemale1");
		year.setText("2000");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioOlder");
		clickOn("#task6ButtonReport");
		sleep(500);
		assertTrue(result.getText().startsWith("Your score of compatibility is 0.9"));

		name1.setText("Danielllll");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylorrrrr");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		sleep(500);
		assertTrue(result.getText().startsWith("Your score of compatibility is 1.0"));
	}

	@Test
	public void testName1Empty() {
		clickOn("#tabApp3");
		name1.setText("");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please enter your name")).query();
	}

	@Test
	public void testName2Empty() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please enter the name of your soulmate")).query();
	}

	@Test
	public void testName1Short() {
		clickOn("#tabApp3");
		name1.setText("D");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your name must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testName2Short() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("T");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("The name of your soulmate must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testName1Long() {
		clickOn("#tabApp3");
		name1.setText("DanielDanielDani");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your name must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testName2Long() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("TaylorTaylorTayl");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("The name of your soulmate must contain only 2 to 15 characters")).query();
	}

	@Test
	public void testName1Character() {
		clickOn("#tabApp3");
		name1.setText("Daniel*");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your name must contain only letters")).query();
	}

	@Test
	public void testName2Character() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999");
		name2.setText("Taylor*");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("The name of your soulmate must contain only letters")).query();
	}

	@Test
	public void testYOBInvlid() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1999a");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer.")).query();
	}

	@Test
	public void testYOBBoundaryBegin() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("1879");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer between")).query();
	}

	@Test
	public void testYOBBoundaryEnd() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year.setText("2020");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer between")).query();
	}
}
