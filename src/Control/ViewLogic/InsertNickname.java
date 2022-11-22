package Control.ViewLogic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InsertNickname {

    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private TextField nickname;

    @FXML
    void onStartGame(ActionEvent event) throws IOException {
    	String nick = nickname.getText();
    	if (nick.length() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty nickname");
			alert.setHeaderText("Please don't enter enpty nickname");
			alert.showAndWait();
			return;
    	}
    	
    	// Move to game screen
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameScreen.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
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
