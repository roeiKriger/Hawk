package Control.ViewLogic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class QuestionModal {

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
    
    public void initialize(URL arg0, ResourceBundle arg1)
	{
	}
    
    @FXML
    void onAnswer(ActionEvent event) {
    }

}
