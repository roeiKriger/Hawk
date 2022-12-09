package Control.ViewLogic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private AnchorPane mainPane;

    @FXML
    private TextField question;

    @FXML
    private Label titleLabel;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// TODO Auto-generated method stub
    	
    }
    
    
    @FXML
    void addOrEdit(MouseEvent event) {

    }

    @FXML
    void returnToQuestionsManagement(ActionEvent event) {

    }


}
