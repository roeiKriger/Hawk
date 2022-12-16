package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Control.SysData;
import Model.Question;
import Utils.Difficulty;
import Utils.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionAddEdit implements Initializable {

    @FXML
    private Label addEditBtn;

    @FXML
    private TextField answer1;

    @FXML
    private TextField answer2;

    @FXML
    private TextField answer3;

    @FXML
    private TextField answer4;

    @FXML
    private ComboBox<Integer> correctAnsComboBox;
    
    @FXML
    private ComboBox<Difficulty> difficultyComboBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField question;

    @FXML
    private Label titleLabel;

    
	private SysData sd = SysData.getInstance();
	
	// values for add/edit question
    private int difficultNum;
    private String questionStr;
    private ArrayList<String> answers;
    int correctAns;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	titleLabel.setText(sd.getAddEditFlag() == "add" ? "Add Question" : "Edit Question");
    	addEditBtn.setText(sd.getAddEditFlag() == "add" ? "Add Question" : "Edit Question");
    	
    	// fill ComboBox
		difficultyComboBox.getItems().addAll(Difficulty.values());    		
    	correctAnsComboBox.getItems().addAll(1, 2, 3, 4);
    	
    	// in case of edit, set values of selected question in form
    	if (sd.getAddEditFlag() == "edit") {
    		setQuestionValues();
    	}
    }
    
    /*
     * in case of edit, set values of selected question in form
     */
    private void setQuestionValues() {
    	// get question from sysdata
    	Question editedQuestion = sd.getEditedQuestion();
    	
    	question.setText(editedQuestion.getQuestionContent());
    	answer1.setText(editedQuestion.getAnswers().get(0));
    	answer2.setText(editedQuestion.getAnswers().get(1));
    	answer3.setText(editedQuestion.getAnswers().get(2));
    	answer4.setText(editedQuestion.getAnswers().get(3));
    	
    	correctAnsComboBox.setValue(editedQuestion.getCorrectAnswerId());
    	
    	// set value of difficulty
    	if (editedQuestion.getQuestionDifficulty() == 1) {
    		difficultyComboBox.setValue(Difficulty.Easy);
    	} else if (editedQuestion.getQuestionDifficulty() == 2) {
    		difficultyComboBox.setValue(Difficulty.Medium);
    	} else {
    		difficultyComboBox.setValue(Difficulty.Hard);
    	}
    }
    
    /*
     * function that perform 'add' or 'edit' by relevant flag
     */
    @FXML
    void addOrEdit(MouseEvent event) throws IOException {
    	if (sd.getAddEditFlag() == "add") {
    		add();
    	} else {
    		edit();
    	}   
    	sd.write_questions(sd.get_questions());
    }
      
    /*
     * add new question to questions array in sysdata and present this
     */
    private void add() throws IOException {
    	if (!validateRequiredFields()) {
    		return;
    	}
    	
    	getFormValues();
    	
    	Question newQuestion = new Question(difficultNum, questionStr, answers, correctAns);
    	
    	sd.get_questions().add(newQuestion);
    	
    	// update questions by level
    	sd.filterQuestionsByLevels();
    	
    	returnToQuestionsManagement(null);
    }
    
    /*
     * edit selected question  in sysdata and present this
     */
    private void edit() throws IOException {
    	if (!validateRequiredFields()) {
    		return;
    	}

    	getFormValues();
    	
    	Question editedQuestion = sd.getEditedQuestion();
    	
    	editedQuestion.setAnswers(answers);
    	editedQuestion.setCorrectAnswerId(correctAns);
    	editedQuestion.setQuestionContent(questionStr);
    	editedQuestion.setQuestionDifficulty(difficultNum);
    	
    	// update questions by level
    	sd.filterQuestionsByLevels();
    	
    	returnToQuestionsManagement(null);
    }
    
    /*
     * get values of question from form
     */
    private void getFormValues() {
    	questionStr = question.getText();
    	
    	// answers
    	String ans1Str = answer1.getText();
    	String ans2Str = answer2.getText();
    	String ans3Str = answer3.getText();
    	String ans4Str = answer4.getText();
    	answers = new ArrayList<String>();
    	answers.add(ans1Str);
    	answers.add(ans2Str);
    	answers.add(ans3Str);
    	answers.add(ans4Str);
    	
    	correctAns = correctAnsComboBox.getValue();
    	
    	// difficulty of question
    	Difficulty difficult = difficultyComboBox.getValue();
    	if (difficult.equals(Difficulty.Easy)) {
    		difficultNum = 1;
    	} else if (difficult.equals(Difficulty.Medium)) {
    		difficultNum = 2;
    	} else { // Hard
    		difficultNum = 3;
    	}

    }
    
    /*
     * A function that verifies that the required fields have been entered
     */
    private boolean validateRequiredFields() {
    	if (question.getText().equals("")) {
    		SysData.alert("Required Field", "Please enter a non-empty question", AlertType.WARNING);
    		return false;
    	}
    	if (answer1.getText().equals("") || answer2.getText().equals("") || answer3.getText().equals("") || answer4.getText().equals("")) {
    		SysData.alert("Required Field", "Please enter a non-empty answers", AlertType.WARNING);
    		return false;
    	}
    	if (difficultyComboBox.getValue() == null) {
    		SysData.alert("Required Field", "Please enter a difficulty level", AlertType.WARNING);
    		return false;
    	}
    	if (correctAnsComboBox.getValue() == null) {
    		SysData.alert("Required Field", "Please enter a correct answer", AlertType.WARNING);
    		return false;
    	}
    	return true;
    }
    

    @FXML
    void returnToQuestionsManagement(ActionEvent event) throws IOException {
    	sd.playSound(Sound.Menu);
    	
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/QuestionEditor.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Questions Management");
		primaryStage.show();
    }

}
