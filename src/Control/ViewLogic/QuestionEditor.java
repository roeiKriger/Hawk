package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.Difficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuestionEditor implements Initializable{
	
	 @FXML
	 private AnchorPane mainPane;
	 
	 @FXML
	 private ComboBox<Utils.Difficulty> difficultyComboBox;
	
	 @Override
	//fill difficulty ComboBox 
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (difficultyComboBox != null)
			difficultyComboBox.getItems().addAll(Difficulty.values());
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