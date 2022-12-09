package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Control.SysData;
import Model.Question;
import Utils.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	titleLabel.setText(sd.getAddEditFlag() == "add" ? "Add Question" : "Edit Question");
    	addEditBtn.setText(sd.getAddEditFlag() == "add" ? "Add Question" : "Edit Question");
    	
    	// fill ComboBox
		difficultyComboBox.getItems().addAll(Difficulty.values());    		
    	correctAnsComboBox.getItems().addAll(1, 2, 3, 4);
    	
    	if (sd.getAddEditFlag() == "edit") {
    		setQuestionValues();
    	}
    }
    
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
    
    
    @FXML
    void addOrEdit(MouseEvent event) {
    	if (sd.getAddEditFlag() == "add") {
    		add();
    	} else {
    		edit();
    	}
    	
    	// TODO - update questions by level
    }
    
    private void add() {
    	
    }
    
    private void edit() {
    	
    }

    @FXML
    void returnToQuestionsManagement(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/QuestionEditor.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Questions Management");
		primaryStage.show();
    }

}
