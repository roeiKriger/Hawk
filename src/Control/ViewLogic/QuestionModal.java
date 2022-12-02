package Control.ViewLogic;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import Control.SysData;
import Model.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class QuestionModal implements Initializable{

    @FXML
    private RadioButton answer1;

    @FXML
    private RadioButton answer2;

    @FXML
    private RadioButton answer3;

    @FXML
    private RadioButton answer4;

    @FXML
    private ToggleGroup answers;

    @FXML
    private Label questionLabel;
    
    @FXML
    private Label levelLabel;
      

    private SysData sd = SysData.getInstance();
    public void initialize(URL arg0, ResourceBundle arg1) {
    	initQuestion();
	}
    
    private void initQuestion() {
    	Question question = sd.getCurrentQuestion();
    	questionLabel.setText(question.getQuestionContent());
    	levelLabel.setText("Level: " + question.getQuestionDifficulty());
    	
    	// TODO - maybe shuffle order
    	List<String> answers = question.getAnswers();
    	answer1.setText(answers.get(0));
    	answer2.setText(answers.get(1));
    	answer3.setText(answers.get(2));
    	answer4.setText(answers.get(3));
    }
    
    
    
    @FXML
    void onAnswer(ActionEvent event) {
    }

}
