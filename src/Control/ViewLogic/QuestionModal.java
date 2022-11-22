package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Control.SysData;
import Model.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
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
      

    
    public void initialize(URL arg0, ResourceBundle arg1) throws IOException, ParseException
	{
    	
    	
	}
    
    @FXML
    void onAnswer(ActionEvent event) {
    }

}
