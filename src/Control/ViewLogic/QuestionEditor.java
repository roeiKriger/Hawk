package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Control.SysData;
import Model.Question;
import Utils.Difficulty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionEditor implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private ComboBox<Utils.Difficulty> difficultyComboBox;

	@FXML
	private TableView<Question> questionsTable;

	/*
	 * Column of the Table of Questions
	 */
	@FXML
	public TableColumn<Question, String> numb; // id of the question

	@FXML
	public TableColumn<Question, String> diff; // difficulty of the question
	@FXML
	public TableColumn<Question, String> cont;// text of the question
	@FXML
	public TableColumn<Question, String> correct; // correct answer
	
	
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
	

	private SysData sd = SysData.getInstance();
	private ObservableList<Question> questions;

	
	/*
	 * initialize comboBox with difficulty and table with values of Question
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// fill difficulty ComboBox
		if (difficultyComboBox != null)
			difficultyComboBox.getItems().addAll(Difficulty.values());
		
		// fill Question table
			questions = FXCollections.observableArrayList(FXCollections.observableArrayList(sd.get_questions()));
			numb.setCellValueFactory(new PropertyValueFactory<>("questionId"));
			diff.setCellValueFactory(new PropertyValueFactory<>("questionDifficulty"));
			cont.setCellValueFactory(new PropertyValueFactory<>("questionContent"));
			correct.setCellValueFactory(new PropertyValueFactory<>("correctAnswerId"));
			questionsTable.setItems(questions);
			
			editBtn.setDisable(true);
			deleteBtn.setDisable(true);
			
			// event listener that save the selected question
			questionsTable.setRowFactory(ev -> {
				return rowSelectedHandler();
			});
	}
	
	/*
	 * event listener of selected row in table
	 * the function saves the selected question in sysdata 
	 */
	private TableRow<Question> rowSelectedHandler() {
		TableRow<Question> rowSelectedQuestion = new TableRow<>();
		
		rowSelectedQuestion.setOnMousePressed(eventClicked -> {
			 if (eventClicked.isPrimaryButtonDown() && !rowSelectedQuestion.isEmpty()) {
		            // get the selected item and save this in sys data
		            sd.setEditedQuestion(rowSelectedQuestion.getItem());
		            
		            editBtn.setDisable(false);
		            deleteBtn.setDisable(false);
		        }
		});
		return rowSelectedQuestion;
	}
	
	/*
	 * Filter for question difficulty
	 */
	@FXML
    void doingFilter(ActionEvent event)
    {
		if(difficultyComboBox.getValue().equals(Difficulty.Easy)) // choosing easy in the filter
			questionsTable.setItems(FXCollections.observableArrayList(sd.getQuestionsLevel1()));
		if(difficultyComboBox.getValue().equals(Difficulty.Medium)) // choosing medium in the filter
			questionsTable.setItems(FXCollections.observableArrayList(sd.getQuestionsLevel2()));
		if(difficultyComboBox.getValue().equals(Difficulty.Hard)) // choosing hard in the filter
			questionsTable.setItems(FXCollections.observableArrayList(sd.getQuestionsLevel3()));	
    }
	
	/*
	 *	function that change addEditFlag and move to add screen 
	 */
    @FXML
    void onAddQuestion(ActionEvent event) throws IOException {
    	// change addEditFlag in sysdata
    	sd.setAddEditFlag("add");
    	
    	moveToAddEditScreen();
    }

    /*
     * function that change addEditFlag and move to edit screen 
     */
    @FXML
    void onEditQuestion(ActionEvent event) throws IOException {
    	// change addEditFlag in sysdata
    	sd.setAddEditFlag("edit");
    	
    	moveToAddEditScreen();
    }
    
    @FXML
    void onDeleteQuestion(ActionEvent event) {

    }
    
    void moveToAddEditScreen() throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/QuestionAddEdit.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle(sd.getAddEditFlag() == "add" ? "Add Question" : "Edit Question");
		primaryStage.show();
    }


	@FXML
	void returnToHomePage(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
	}

}
