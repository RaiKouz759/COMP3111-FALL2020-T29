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

public class Activity4JavaFXTest extends ApplicationTest{
	private Scene s;
	private TextField dName, dYOB, mName, mYOB, vYear;
	private RadioButton algorithm;
	private Label bHeader, gHeader, b1, b2, b3, g1, g2, g3;
	
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
		dName = (TextField)s.lookup("#t4_dname");
		dYOB = (TextField)s.lookup("#t4_dyob");
		mName = (TextField)s.lookup("#t4_mname");
		mYOB = (TextField)s.lookup("#t4_myob");
		vYear = (TextField)s.lookup("#t4_vyear");
		bHeader = (Label)s.lookup("#t4_boy_label");
		gHeader = (Label)s.lookup("#t4_girl_label");
		b1 = (Label)s.lookup("#t4_b1");
		b2 = (Label)s.lookup("#t4_b2");
		b3 = (Label)s.lookup("#t4_b3");
		g1 = (Label)s.lookup("#t4_g1");
		g2 = (Label)s.lookup("#t4_g2");
		g3 = (Label)s.lookup("#t4_g3");
	}
	
	@Test
	public void testQuery1() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_nkt4");
		clickOn("#t4_gr");
		
		assertTrue(bHeader.getText().equals("Boy Names"));
		assertTrue(gHeader.getText().equals("Girl Names"));
		assertTrue(b1.getText().equals("Jacob"));
		assertTrue(g1.getText().equals("Hannah"));
		assertTrue(b2.getText().equals(""));
		assertTrue(g2.getText().equals(""));
		assertTrue(b3.getText().equals(""));
		assertTrue(g3.getText().equals(""));
	}
	
	@Test
	public void testQuery2() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		
		assertTrue(bHeader.getText().equals("Boy names and scores"));
		assertTrue(gHeader.getText().equals("Girl names and scores"));
		assertTrue(b1.getText().equals("Jaymes : 0.577"));
		assertTrue(g1.getText().equals("Mayme : 0.574"));
		assertTrue(b2.getText().equals("Jayme : 0.549"));
		assertTrue(g2.getText().equals("Jarelys : 0.574"));
		assertTrue(b3.getText().equals("Jamey : 0.549"));
		assertTrue(g3.getText().equals("Marye : 0.56"));
	}
	
	@Test
	public void NaN_year1() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("a");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Period must be")).query();
	}
	
	@Test
	public void NaN_year2() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("a");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Period must be")).query();
	}
	
	@Test
	public void NaN_year3() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("a");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Period must be")).query();
	}
	
	@Test
	public void dYOB_lower_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1879");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Dad Year of Birth")).query();
	}
	
	@Test
	public void dYOB_upper_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("2020");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999";
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Dad Year of Birth")).query();
	}
	
	@Test
	public void mYOB_upper_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("2020");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Mom Year of Birth")).query();
	}
	
	@Test
	public void mYOB_lower_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1879");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Mom Year of Birth")).query();
	}
	
	@Test
	public void vYear_upper_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("2020");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Vintage Year must be")).query();
	}
	
	@Test
	public void vYear_lower_boundary() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1879");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Vintage Year must be")).query();
	}
	
	@Test
	public void dName_length() {
		clickOn("#tabApp1");
		dName.setText("J");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Dad's name must be")).query();
	}
	
	@Test
	public void mName_length() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("M");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Mom's name must be")).query();
	}
	
	@Test
	public void dName_char() {
		clickOn("#tabApp1");
		dName.setText("James*");
		dYOB.setText("1943");
		mName.setText("Mary");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Dad's name has")).query();
	}
	
	@Test
	public void dmName_char() {
		clickOn("#tabApp1");
		dName.setText("James");
		dYOB.setText("1943");
		mName.setText("Mary$");
		mYOB.setText("1947");
		vYear.setText("1999");
		clickOn("#t4_jaro");
		clickOn("#t4_gr");
		FxAssert.verifyThat("OK", NodeMatchers.isVisible());
		Node dialogPane = lookup(".dialog-pane").query();
		from(dialogPane).lookup((Text t) -> t.getText().startsWith("Mom's name has")).query();
	}
	
}
