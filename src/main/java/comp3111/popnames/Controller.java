/**
 * Building on the sample skeleton for 'ui.fxml' COntroller Class generated by SceneBuilder 
 */
package comp3111.popnames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ChoiceBox;
import java.lang.NumberFormatException;
import java.net.URL;

import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    private Tab tabTaskZero;

    @FXML
    private TextField textfieldNameF;

    @FXML
    private TextField textfieldYear;

    @FXML
    private Button buttonRankM;

    @FXML
    private TextField textfieldNameM;
    
    @FXML
    private Button task1Button;

    @FXML
    private Button buttonRankF;

    @FXML
    private Button buttonTopM;

    @FXML
    private Button buttonTopF;

    @FXML
    private Button buttonSummary;
    
    @FXML
    private Tab tabReport1;

    @FXML
    private ToggleGroup T1;

    @FXML
    private Tab tabReport2;

    @FXML
    private ToggleGroup T11;

    @FXML
    private Tab tabReport3;

    @FXML
    private ToggleGroup T111;

    @FXML
    private Tab tabApp1;

    @FXML
    private Tab tabApp2;

    @FXML
    private Tab tabApp3;

    @FXML
    private TextArea textAreaConsole;
    
    @FXML
    private TextField numRankTextField;
    
    @FXML
    private RadioButton maleRadioButton;
    
    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField endPeriodTextField;

    @FXML
    private TextField startPeriodTextField;
    
    @FXML
    private TableView<Map> report1Table;
    
    @FXML	
    private ChoiceBox<String> app2ChoiceBox;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// this part is to initialize the choiceBox in activity5
    	ArrayList<String> list = new ArrayList<String>();
    	list.add("NK-T5");
    	list.add("Levenshtein Distance");
    	ObservableList<String> obList = FXCollections.observableList(list);
        app2ChoiceBox.setItems(obList);
        app2ChoiceBox.setValue("NK-T5");
        // end of initialization of activity5
        
	}
    /**
     *  Task Zero
     *  To be triggered by the "Summary" button on the Task Zero Tab 
     *  
     */
    @FXML
    void doSummary() {
    	int year = Integer.parseInt(textfieldYear.getText());
    	String oReport = AnalyzeNames.getSummary(year);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankF() {
    	String oReport = "";
    	String iNameF = textfieldNameF.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameF, "F");
    	if (oRank == -1)
    		oReport = String.format("The name %s (female) has not been ranked in the year %d.\n", iNameF, iYear);
    	else
    		oReport = String.format("Rank of %s (female) in year %d is #%d.\n", iNameF, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }

  
    /**
     *  Task Zero
     *  To be triggered by the "Rank (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doRankM() {
    	String oReport = "";
    	String iNameM = textfieldNameM.getText();
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	int oRank = AnalyzeNames.getRank(iYear, iNameM, "M");
    	if (oRank == -1)
    		oReport = String.format("The name %s (male) has not been ranked in the year %d.\n", iNameM, iYear);
    	else
    		oReport = String.format("Rank of %s (male) in year %d is #%d.\n", iNameM, iYear, oRank);
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (female)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopF() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (female) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "F"));
    	textAreaConsole.setText(oReport);
    }


    /**
     *  Task Zero
     *  To be triggered by the "Top 5 (male)" button on the Task Zero Tab
     *  
     */
    @FXML
    void doTopM() {
    	String oReport = "";
    	final int topN = 5;
    	int iYear = Integer.parseInt(textfieldYear.getText());
    	oReport = String.format("Top %d most popular names (male) in the year %d:\n", topN, iYear);
    	for (int i=1; i<=topN; i++)
    		oReport += String.format("#%d: %s\n", i, AnalyzeNames.getName(iYear, i, "M"));
    	textAreaConsole.setText(oReport);
    }
    
    /**
     *  Task One
     *  To be triggered by the Generate Report Button in Reporting 1 tab.
     *  
     */
    @FXML
    void doTask1() {
    	int numRanks;
    	int startPeriod;
    	int endPeriod;
    	int gender;
    	try {
    		// input validation and catches any errors
    		 	numRanks = Integer.parseInt(numRankTextField.getText());
    		 	startPeriod = Integer.parseInt(startPeriodTextField.getText());
    		 	endPeriod = Integer.parseInt(endPeriodTextField.getText());
    		 	
    		if (maleRadioButton.isSelected()) {
    			gender = 0;
    		} else {
    			gender = 1;
    		}
    		
    	} catch(NumberFormatException e) {
    		// some error catching here
    		return;
    	}
    	ArrayList<YearRecords> yearRecordsList = Activity1Query.executeQuery(numRanks, gender, startPeriod, endPeriod);
    	// clear all the contents of the table view
    	report1Table.getColumns().clear();
    	report1Table.getItems().clear();
    	report1Table.refresh();

    	// initializing the year column
    	TableColumn<Map,String> yearColumn = new TableColumn<>("Year");
    	yearColumn.setCellValueFactory(new MapValueFactory<>("year"));
		report1Table.getColumns().add(yearColumn);
		
		int index = 1;
		for (; index <= numRanks; index++) {
			TableColumn<Map, String> topColumn = new TableColumn<>("Top " + index);
			topColumn.setCellValueFactory(new MapValueFactory<>("top" + index));
			report1Table.getColumns().add(topColumn);
		}
		ObservableList<Map<String, Object>> items =
			    FXCollections.<Map<String, Object>>observableArrayList();

    	for (YearRecords y : yearRecordsList) {
    		Map<String, Object> item = new HashMap<>();
    		item.put("year", y.getYear());
    		index = 1;
    		for (NameRecord nr : y.getNameRecordList()) {
    	    	item.put("top" + index, nr.getName());
    	    	index++;
    		}
    		items.add(item);
    	}
    	report1Table.getItems().addAll(items);
    	   	
    	
    }

}

