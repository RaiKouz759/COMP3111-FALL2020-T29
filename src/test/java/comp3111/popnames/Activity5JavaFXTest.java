package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class focuses on testing the functions of the Activity5Query class and the responses of the controller to invalid inputs. 
 * 
 * 
 * @author Alex
 *
 */
public class Activity5JavaFXTest extends ApplicationTest{
	private Scene s;
	private TextField name, yob;
	private RadioButton male, prefGender, prefYounger, prefAlgoNK, prefAlgoJaro, radio1, radio2, radio3, radio4, radio5, radio6;
	private Label answer;
	
	/**
	 *Initializes the scene and references the ui elements that will be used during testing. 
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
		name = (TextField)s.lookup("#app2YourName");
		yob = (TextField)s.lookup("#app2YOB");
		male = (RadioButton)s.lookup("#app2YourGenderM");
		prefGender = (RadioButton)s.lookup("#app2SoulGenderM");
		prefYounger = (RadioButton)s.lookup("#app2SoulYounger");
		prefAlgoNK = (RadioButton)s.lookup("#app2RadioNK");
		radio1 = (RadioButton)s.lookup("#step2Radio1");
		radio2 = (RadioButton)s.lookup("#step2Radio2");
		radio3 = (RadioButton)s.lookup("#step2Radio3");
		radio4 = (RadioButton)s.lookup("#step2Radio4");
		radio5 = (RadioButton)s.lookup("#step2Radio5");
		radio6 = (RadioButton)s.lookup("#step2Radio6");
		prefAlgoJaro = (RadioButton)s.lookup("#app2RadioJaro");
		answer = (Label)s.lookup("#app2Answer");
	}
	
	/**
	 * Test for when the YOB field is empty. 
	 */
	@Test
	public void testYOBEmpty() {
		// test N 
		clickOn("#tabApp2");
		clickOn("#app2Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please only enter")).query();
	}
	/**
	 * Test for when non-letters are input into the name field.
	 */
	@Test
	public void testInvalidName() {
		// test invalid period
		clickOn("#tabApp2");
		name.setText("**");
		yob.setText("2020");
		clickOn("#app2Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please only enter letters for")).query();
	}
	/**
	 * Test for when the year of birth is out of the specified maximum. 
	 */
	@Test
	public void testOutOfRangeYOB() {
		// test invalid period
		clickOn("#tabApp2");
		name.setText("Alex");
		yob.setText("2200");
		clickOn("#app2Button");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Please only enter year of births")).query();
	}
	/**
	 * Test for a valid execution of the NK algorithm.
	 */
	@Test
	public void testValidNKQuery() {
		// test invalid period
		clickOn("#tabApp2");
		name.setText("Alex");
		yob.setText("2000");
		clickOn("#app2Button");
		sleep(50);
		assertTrue(answer.getText().equals("Caroline"));
	}
	
	/**
	 * Test for a valid jaro algorithm query. 
	 */
	@Test
	public void testExecuteJaroQuery() {
		clickOn("#tabApp2");
		name.setText("Alex");
		yob.setText("2000");
		prefAlgoJaro.setSelected(true);
		clickOn("#app2Button");
		sleep(50);
		clickOn("#step2Button");
		sleep(50);
		assertTrue(answer.getText().length() > 0);
	}
}
