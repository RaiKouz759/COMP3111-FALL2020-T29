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
	private TextField name1, name2, year1, year2;
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
		year1 = (TextField)s.lookup("#task6TextYear1");
		year2 = (TextField)s.lookup("#task6TextYear2");
		result = (Label)s.lookup("#task6TextResult");
	}

    
	@Test
	public void testNTK6Valid1() {	
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		assertTrue(result.getText().startsWith("Your score of compatibility is 88.889%"));
	}

	@Test
	public void testNTK6Valid2() {	
		clickOn("#tabApp3");
		name1.setText("Taylor");
		clickOn("#task6RadioFemale1");
		year1.setText("2000");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioOlder");
		clickOn("#task6ButtonReport");
		assertTrue(result.getText().startsWith("Your score of compatibility is 90%"));
	}

	@Test
	public void testName1Empty() {
		clickOn("#tabApp3");
		name1.setText("");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999");
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
		year1.setText("1999a");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer.")).query();
	}

	@Test
	public void testYOBBoundaryBegin1() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1879");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer between")).query();
	}

	@Test
	public void testYOBBoundaryBegin2() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1880");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioOlder");
		clickOn("#task6ButtonReport");
		assertTrue(result.getText().startsWith("Your score of compatibility is 100%"));
	}

	@Test
	public void testYOBBoundaryEnd1() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("2020");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Your year of birth must be an integer between")).query();
	}

	@Test
	public void testYOBBoundaryEnd2() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("2019");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioYounger");
		clickOn("#task6ButtonReport");
		assertTrue(result.getText().startsWith("Your score of compatibility is 100%"));
	}

	@Test
	public void testNKT6Custom() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioCustom");
		year2.setText("2000");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Either Younger or Older")).query();
	}

	@Test
	public void testNormCustom() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioCustom");
		year2.setText("2000");
		clickOn("#task6RadioNorm");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Either Younger or Older")).query();
	}

	@Test
	public void testLinearCustomInvalidYear() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioCustom");
		year2.setText("2000a");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("The year of birth of your soulmate")).query();
	}

	@Test
	public void testLinearCustomOutOfRange() {
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioCustom");
		year2.setText("2020");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("The year of birth of your soulmate")).query();
	}

	@Test
	public void testNormValid() {	
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		year2.setText("2000");
		clickOn("#task6RadioNorm");
		clickOn("#task6ButtonReport");
		assertTrue(result.getText().startsWith("Your score of compatibility is 97.956%"));
	}

	@Test
	public void testLinearValidMaleFemaleYounger() {	
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("1999");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		year2.setText("2000");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		sleep(5000);
		assertTrue(result.getText().startsWith("Your score of compatibility is 75.302%"));
	}

	@Test
	public void testLinearValidFemaleMaleOlder() {	
		clickOn("#tabApp3");
		name1.setText("Taylor");
		clickOn("#task6RadioFemale1");
		year1.setText("2000");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioOlder");
		year2.setText("1999");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		sleep(5000);
		assertTrue(result.getText().startsWith("Your score of compatibility is 75.302%"));
	}

	@Test
	public void testLinearValidCustom() {	
		clickOn("#tabApp3");
		name1.setText("Taylor");
		clickOn("#task6RadioFemale1");
		year1.setText("2000");
		name2.setText("Daniel");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioCustom");
		year2.setText("1999");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		sleep(5000);
		assertTrue(result.getText().startsWith("Your score of compatibility is 75.302%"));
	}

	@Test
	public void testLinearValidMissingPoints() {	
		clickOn("#tabApp3");
		name1.setText("Zo");
		clickOn("#task6RadioFemale1");
		year1.setText("2005");
		name2.setText("Kin");
		clickOn("#task6RadioMale2");
		clickOn("#task6RadioCustom");
		year2.setText("2005");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		sleep(5000);
		assertTrue(result.getText().startsWith("Your score of compatibility is 99.999%"));
	}

	@Test
	public void testLinearFail() {	
		clickOn("#tabApp3");
		name1.setText("Daniel");
		clickOn("#task6RadioMale1");
		year1.setText("2019");
		name2.setText("Taylor");
		clickOn("#task6RadioFemale2");
		clickOn("#task6RadioYounger");
		year2.setText("2000");
		clickOn("#task6RadioLinear");
		clickOn("#task6ButtonReport");
		sleep(5000);
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("There are not enough data points")).query();
	}
}
