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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private ToggleGroup answers; // will save the answers chosen from radio button 

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
    void onAnswer(ActionEvent event) 
    {
    	updateScoreAfterAnswer();
    	//move back to board
    	closeWindow();
    	
    }
    
    //This method check the answer and update score
    private void updateScoreAfterAnswer()
    {
    	Question question = sd.getCurrentQuestion(); //get correct question
		RadioButton selectedRadioButton = (RadioButton) answers.getSelectedToggle(); //get Object selected
		String toogleGroupValue = selectedRadioButton.getText(); // get text of question
    	int score = sd.getGame().getScore();
    	if(question.checkCorrectAnswer(toogleGroupValue))
    	{ // correct answer
    		if(question.getQuestionDifficulty() == 1)
    			score += Model.Constants.SUCSSED_EASY;
    		if(question.getQuestionDifficulty() == 2)
    			score += Model.Constants.SUCSSED_MIDDLE;
    		if(question.getQuestionDifficulty() == 3)
    			score += Model.Constants.SUCSSED_HARD;
    	}
    	else
    	{ //wrong answer
    		if(question.getQuestionDifficulty() == 1)
    			score += Model.Constants.WORNG_EASY;
    		if(question.getQuestionDifficulty() == 2)
    			score += Model.Constants.WORNG_MIDDLE;
    		if(question.getQuestionDifficulty() == 3)
    			score += Model.Constants.WORNG_HARD; 		
    	}
    	sd.getGame().setScore(score); // updating score
    	//Control.ViewLogic.GameScreen.changeScoreOnScreen();
    }
    
    @FXML
    private void closeWindow(){
        // get a handle to the stage
        Stage stage = (Stage) answer1.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
