/**
 * Building on the sample skeleton for 'ui.fxml' COntroller Class generated by SceneBuilder 
 */
package comp3111.popnames;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.lang.NumberFormatException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.io.File;



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
    private TextField task2TextName;

    @FXML
    private ToggleGroup task2Toggle;

    @FXML
    private RadioButton task2RadioMale;

    @FXML
    private RadioButton task2RadioFemale;

    @FXML
    private TextField task2TextStartPeriod;

    @FXML
    private TextField task2TextEndPeriod;

    @FXML
    private Button task2ButtonGenerate;

    @FXML
    private TableView<Map> task2TableResult;

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
    private TextField task6TextName1;

    @FXML
    private ToggleGroup task6Toggle1;

    @FXML
    private RadioButton task6RadioMale1;

    @FXML
    private RadioButton task6RadioFemale1;

    @FXML
    private TextField task6TextYear;

    @FXML
    private TextField task6TextName2;

    @FXML
    private ToggleGroup task6Toggle2;

    @FXML
    private RadioButton task6RadioMale2;

    @FXML
    private RadioButton task6RadioFemale2;

    @FXML
    private ToggleGroup task6Toggle3;

    @FXML
    private RadioButton task6RadioYounger;

    @FXML
    private RadioButton task6RadioOlder;

    @FXML
    private Button task6ButtonReport;

    @FXML
    private Label task6TextResult;

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
    
    @FXML
    private BarChart<String, Integer> rep1BarChart;
    
    @FXML
    private TextArea rep1Comment;
    
    @FXML
    private Label rep1Label;

    @FXML
    private Rectangle step2Cover;
    
    //activity 5 FXML objects
    @FXML
    private TextField app2YourName;

    @FXML
    private RadioButton app2YourGenderM;

    @FXML
    private RadioButton app2YourGenderF;

    @FXML
    private TextField app2YOB;

    @FXML
    private RadioButton app2SoulGenderM;

    @FXML
    private RadioButton app2SoulGenderF;

    @FXML
    private RadioButton app2SoulYounger;

    @FXML
    private RadioButton app2SoulOlder;
    
    @FXML
    private Label app2PredictSentence;

    @FXML
    private Label app2Answer;
    
    @FXML
    private Button app2Button;
    
    @FXML
    private RadioButton app2RadioNK;

    @FXML
    private ToggleGroup T3;

    @FXML
    private RadioButton app2RadioJaro;
    

    @FXML
    private RadioButton step2Radio1;

    @FXML
    private ToggleGroup T5;

    @FXML
    private RadioButton step2Radio2;

    @FXML
    private RadioButton step2Radio3;

    @FXML
    private Label step2Label;

    @FXML
    private Button step2Button;
    // end of activity5 objects

    @FXML
    private ChoiceBox<String> historyChoice;

    @FXML
    private TextArea historyText;
    private TextField task3_year_end;

    @FXML
    private TextField task3_year_start;

    @FXML
    private Button task3_report;
    
    @FXML
    private RadioButton t3_f;
    
    @FXML
    private RadioButton t3_m;
    
    @FXML
    private TextField t3_topN;
    
    @FXML
    private TextField t4_dname;

    @FXML
    private TextField t4_mname;

    @FXML
    private TextField t4_dyob;

    @FXML
    private TextField t4_myob;

    @FXML
    private TextField t4_vyear;

    @FXML
    private Button t4_gr;
    
    @FXML
    private RadioButton t4_nkt4;

    @FXML
    private ToggleGroup T4_algorithm;

    @FXML
    private RadioButton t4_jaro;
    
    
    //Task Four
    
    @FXML
    void t4_generate_recommendation() {
    	String dName = t4_dname.getText();
    	String mName = t4_mname.getText();
    	int dYOB = Integer.parseInt(t4_dyob.getText());
    	int mYOB = Integer.parseInt(t4_myob.getText());
    	int vYear = Integer.parseInt(t4_vyear.getText());
    	String choice = "";
    	if (this.T4_algorithm.getSelectedToggle().equals(this.t4_nkt4)){
    		choice = "NK-T4";
    	}
    	else if (this.T4_algorithm.getSelectedToggle().equals(this.t4_jaro)) {
    		choice = "Jaro";
    	}
    	String Report = Task4.recommendation(dName, dYOB, mName, mYOB, vYear, choice);
    	textAreaConsole.setText(Report);
    }
    
    
    // Task Three
    
    @FXML
    void task3_generate_summary() throws IOException {
    	int year_start = Integer.parseInt(task3_year_start.getText());
    	int year_end = Integer.parseInt(task3_year_end.getText());
    	int topN = Integer.parseInt(t3_topN.getText());
    	String gender = "";
    	if (this.T111.getSelectedToggle().equals(this.t3_m)) {
    		gender = "M";
    	}
    	else if (this.T111.getSelectedToggle().equals(this.t3_f)) {
    		gender = "F";
    	}
    	String Report = Task3.Summary(year_start, year_end, gender, topN);
    	textAreaConsole.setText(Report);
    }
    
    public ObservableList<String> log_obList;
    
    @FXML
    private Tab historyTab;

    @FXML
    private TabPane tabpane;

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
        
        tabpane.getSelectionModel().selectedItemProperty().addListener((ChangeListener<? super Tab>) new ChangeListener<Tab>() { 
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				// TODO Auto-generated method stub
				if(newValue.equals(historyTab)) {
			        // initialize contents of history tab
					String filePath = new File("").getAbsolutePath();
					filePath = filePath.concat("/src/main/resources/logs");
					
					// create directory if it does not exists
					new File(filePath).mkdirs();
					File dir = new File(filePath);
					File[] directoryListing = dir.listFiles();
					if (directoryListing != null) {
				        ArrayList<String> log_list = new ArrayList<String>();
					    for (File child : directoryListing) {
					      log_list.add(child.getName());
					    }
				        log_obList = FXCollections.observableList(log_list);
				        log_obList.addListener(new ListChangeListener<String> () {
			
							@Override
							public void onChanged(Change<? extends String> c) {
								// TODO Auto-generated method stub
								 System.out.println("adding" + c); 
							     historyChoice.getItems().setAll(log_obList);
							}
				        	
				            });
				        historyChoice.setItems(log_obList);
				        if (log_obList.size() > 0) {
				        	historyChoice.setValue(log_obList.get(0));
				        }
					  } else {
					    // Handle the case where dir is not really a directory.
					    // Checking dir.isDirectory() above would not be sufficient
					    // to avoid race conditions with another process that deletes
					    // directories.
						  showWarning("Error", "Error initializing the history tab");
					  }
				}
				
			}

        });

        
    }
    @FXML
    void clickHistory() {
    	if (historyTab.isSelected()) {
    		String filePath = new File("").getAbsolutePath();
    		filePath = filePath.concat("/src/main/resources/logs");
    		
    		// create directory if it does not exists
    		new File(filePath).mkdirs();
    		File dir = new File(filePath);
    		File[] directoryListing = dir.listFiles();
    		if (directoryListing != null) {
    	        ArrayList<String> log_list = new ArrayList<String>();
    		    for (File child : directoryListing) {
    		      log_list.add(child.getName());
    		    }
    	        log_obList = FXCollections.observableList(log_list);
    	        historyChoice.setItems(log_obList);
    	        if (log_obList.size() > 0) {
	        	historyChoice.setValue(log_obList.get(0));
	        }
    	}
    }
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
        	showWarning("Invalid Input Format", "Please only enter numbers.");
            return;
        }
        if (!Activity1Query.isNumOfResultsCorrect(numRanks)) {
        	showWarning("Invalid Input", "Please enter an N that is >= 1. ");
        	return;
        }
        if (!Activity1Query.isPeriodCorrect(startPeriod, endPeriod)) {
        	showWarning("Invalid Input", "Start and End periods must be within the boundaries stated.");
        	return;
        }
        
        ArrayList<YearRecords> yearRecordsList = Activity1Query.executeQuery(numRanks, gender, startPeriod, endPeriod);
        // clear all the contents of the table view & bar chart
        report1Table.getColumns().clear();
        report1Table.getItems().clear();
        report1Table.refresh();
        rep1BarChart.getData().clear();
        rep1BarChart.setVisible(true);

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
        
        // display bar chart with info. 
        rep1BarChart.setVisible(true);
        LinkedHashMap<String, Integer> top3Names = Activity1Query.top3Names;
        
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<String, Integer>();
        dataSeries.setName(Integer.toString(startPeriod) + " - " + Integer.toString(endPeriod));
        for (Entry<String, Integer> entry : top3Names.entrySet())
        {
            dataSeries.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
            
        }
        rep1BarChart.getData().add(dataSeries);
        rep1Comment.setVisible(true);
        rep1Comment.setText(Activity1Query.comment);
        rep1Label.setVisible(true);
        
        //testing stuff
        try {
            String done = Utility.storeHistory("testing storing function");
            if (!done.equals("error")) {
                System.out.println("saved file");
//                log_obList.add(done);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     *  Task Two
     *  To be triggered by the Generate Report Button in Reporting 2 tab.
     *  
     */
    @FXML
    void doTask2() {
        String name;
        int gender;
        int startPeriod;
        int endPeriod;
        name = task2TextName.getText();
        if(name.equals("")){
            showWarning("Invalid Name", "Please enter a name.");
            return;
        }
        try {
            startPeriod = Integer.parseInt(task2TextStartPeriod.getText());
            endPeriod = Integer.parseInt(task2TextEndPeriod.getText());
        } catch(NumberFormatException e) {
            showWarning("Invalid Period", "Period must be integers.");
            return;
        }      
        if (task2RadioMale.isSelected()) {
            gender = 0;
        } else {
            gender = 1;
        }
        ArrayList<RankRecord> rankRecords;
        try {
            rankRecords = Activity2Query.executeQuery(name, gender, startPeriod, endPeriod);
        } catch(NumberFormatException e) {
            if(e.getMessage().equals("length")) {
                showWarning("Invalid Name", "Name must contain only 2 to 15 characters.");
            } else if(e.getMessage().equals("char")) {
                showWarning("Invalid Name", "Name must contain only letters.");
            } else if(e.getMessage().equals("start")) {
                showWarning("Invalid Period", "Starting year must be an integer between 1880 and 2019.");
            } else if(e.getMessage().equals("end")) {
                showWarning("Invalid Period", "Ending year must be an integer between 1880 and 2019.");
            } else if(e.getMessage().equals("start end")) {
                showWarning("Invalid Period", "Both starting and ending years must be integers between 1880 and 2019.");
            }
            return;
        }

        task2TableResult.getColumns().clear();
        task2TableResult.getItems().clear();
        task2TableResult.refresh();

        TableColumn<Map,String> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new MapValueFactory<>("year"));
        task2TableResult.getColumns().add(yearColumn);
        
        TableColumn<Map, String> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new MapValueFactory<>("rank"));
        task2TableResult.getColumns().add(rankColumn);

        TableColumn<Map, String> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(new MapValueFactory<>("count"));
        task2TableResult.getColumns().add(countColumn);

        TableColumn<Map, String> percentageColumn = new TableColumn<>("Percentage");
        percentageColumn.setCellValueFactory(new MapValueFactory<>("percentage"));
        task2TableResult.getColumns().add(percentageColumn);
        
        ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();

        for (RankRecord record : rankRecords) {
            Map<String, Object> item = new HashMap<>();
            item.put("year", record.getYear());
            if(record.isValid()) {
                item.put("rank", record.getRank());
                item.put("count", record.getCount());
                item.put("percentage", record.getPercentage());
            } else {
                item.put("rank", "NULL");
                item.put("count", "NULL");
                item.put("percentage", "NULL");
            }            
            items.add(item);
        }
        task2TableResult.getItems().addAll(items);
    }

    /**
     *  Task Five
     *  To be triggered by the Generate Report Button in Application 2 tab.
     *  
     */
    @FXML
    void doTask5() {
        String name;
        int gender;
        int prefGender;
        int yob;
        boolean prefYounger;
        try {
            // get data from input fields
            name = app2YourName.getText();
            yob = Integer.parseInt(app2YOB.getText());
            if (app2YourGenderM.isSelected()) {
                gender = 0;
            } else {
                gender = 1;
            }
            if (app2SoulGenderM.isSelected()) {
                prefGender = 0;
            } else {
                prefGender = 1;
            }
            prefYounger = app2SoulYounger.isSelected();
            // input validation
            if (!Activity5Query.isNameCorrect(name)) {
            	showWarning("Invalid Input", "Please only enter letters for your name.");
                return;
            } 
            if (!Activity5Query.isYOBCorrect(yob)){
            	showWarning("Invalid Input", "Please only enter year of births that are within the boundaries stated.");
            	return;
            }
            
        } catch(NumberFormatException e) {
            //error catching logic here
        	showWarning("Invalid Input Format", "Please only enter numbers for YOB.");
            return;
        }
        String oName = "undefined";
        if(app2RadioNK.isSelected()) {
            oName = Activity5Query.executeQueryNKT5( name, yob, gender, prefGender, prefYounger);
            app2Answer.setText(oName);
        }else {
        	String[] list = new String[3];
        	list = Activity5Query.executeQueryJaroStepOne(name, yob, gender, prefGender, prefYounger);
        	app2Button.setDisable(true);
        	step2Button.setVisible(true);
        	step2Radio1.setText(list[0]);
        	step2Radio2.setText(list[1]);
        	step2Radio3.setText(list[2]);            
        	step2Radio1.setVisible(true);
        	step2Radio2.setVisible(true);
        	step2Radio3.setVisible(true);
        	step2Label.setVisible(true);
        	step2Cover.setVisible(true);
        }

    }

    @FXML
    void doTask5Part2() {
        String name;
        int gender;
        int prefGender;
        int yob;
        boolean prefYounger;
        String oName = "undefined";
    	
        name = app2YourName.getText();
        yob = Integer.parseInt(app2YOB.getText());
        prefYounger = app2SoulYounger.isSelected();
        if (app2YourGenderM.isSelected()) {
            gender = 0;
        } else {
            gender = 1;
        }
        if (app2SoulGenderM.isSelected()) {
            prefGender = 0;
        } else {
            prefGender = 1;
        }
    	String chosenName;
        if (step2Radio1.isSelected()) {
        	chosenName = step2Radio1.getText();
        } else if(step2Radio2.isSelected()) {
        	chosenName = step2Radio2.getText();
        } else {
        	chosenName = step2Radio3.getText();
        }
        
        oName = Activity5Query.executeQueryJaroStepTwo( chosenName, yob, prefYounger, prefGender);
    	//clean up
    	step2Radio1.setVisible(false);
    	step2Radio2.setVisible(false);
    	step2Radio3.setVisible(false);      
    	step2Button.setVisible(false);
    	app2Button.setDisable(false);
    	step2Cover.setVisible(false);
    	step2Label.setVisible(false);
        app2Answer.setText(oName);
    }

    /**
     *  Task Six
     *  To be triggered by the Generate Report Button in Reporting 2 tab.
     *  
     */
    @FXML
    void doTask6() {
        String name1, name2;
        int gender1, gender2;
        int year;
        boolean isYounger;
        name1 = task6TextName1.getText();
        name2 = task6TextName2.getText();
        if(name1.equals("")){
            showWarning("Invalid Name", "Please enter your name.");
            return;
        }
        if(name2.equals("")){
            showWarning("Invalid Name", "Please enter the name of your soulmate.");
            return;
        }
        try {
            year = Integer.parseInt(task6TextYear.getText());
        } catch(NumberFormatException e) {
            showWarning("Invalid Year of Birth", "Your year of birth must be an integer.");
            return;
        }      
        if (task6RadioMale1.isSelected()) {
            gender1 = 0;
        } else {
            gender1 = 1;
        }
        if (task6RadioMale2.isSelected()) {
            gender2 = 0;
        } else {
            gender2 = 1;
        }
        if (task6RadioYounger.isSelected()) {
            isYounger = true;
        } else {
            isYounger = false;
        }
        float score;
        try {
            score = Activity6Query.executeQuery(name1, gender1, year, name2, gender2, isYounger);
        } catch(NumberFormatException e) {
            if(e.getMessage().equals("length1")) {
                    showWarning("Invalid Name", "Your name must contain only 2 to 15 characters.");
            } else if(e.getMessage().equals("length2")) {
                showWarning("Invalid Name", "The name of your soulmate must contain only 2 to 15 characters.");
            } else if(e.getMessage().equals("char1")) {
                showWarning("Invalid Name", "Your name must contain only letters.");
            } else if(e.getMessage().equals("char2")) {
                showWarning("Invalid Name", "The name of your soulmate must contain only letters.");
            } else if(e.getMessage().equals("year")) {
                showWarning("Invalid Period", "Your year of birth must be an integer between 1880 and 2019.");
            }
            return;
        }
        task6TextResult.setText("Your score of compatibility is " + score);
    }

    private static void showWarning(String header, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}

