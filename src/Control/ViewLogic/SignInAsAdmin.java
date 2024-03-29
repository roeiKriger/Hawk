package Control.ViewLogic;


import java.io.IOException;

import Control.SysData;
import Utils.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignInAsAdmin {
	
	 @FXML
	 private AnchorPane mainPane;
	 @FXML
	 private TextField usernameTextField;
	 @FXML
	 private PasswordField passwordField;
	 
	 String inputUsername;
	 String inputPassword;
	
	 Alert alert = new Alert(AlertType.WARNING);
	 
	 SysData sd = SysData.getInstance();
	  
	@FXML
    void onSignIn(ActionEvent event) throws IOException {
		
		//user has to fill username field and password field, or an alert will appear on screen
		if (usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			if (sd.isSoundFlag()) {
				sd.playSound(Sound.Error);			
			}
			
			 alert.setTitle("Warning Dialog");
			 alert.setContentText("Please fill both fields");
			 alert.showAndWait();
		}
		//get user's input for name and password
		else {	
			try {
				inputUsername = usernameTextField.getText();
				inputPassword = passwordField.getText();	
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//check username and password are both "admin"
			if (inputUsername.equals("admin") && inputPassword.equals("admin")) {
				if (sd.isSoundFlag()) {
					sd.playSound(Sound.Menu);			
				}
				
				Parent newRoot = FXMLLoader.load(getClass().getResource("/View/QuestionEditor.fxml"));
				Stage primaryStage = (Stage) mainPane.getScene().getWindow();
				primaryStage.getScene().setRoot(newRoot);
				primaryStage.setTitle("Questions Management");
				primaryStage.show();
				
			}
			//incorrect user name or passwprd alert on screen
			else {
				if (sd.isSoundFlag()) {
					sd.playSound(Sound.Error);			
				}				
				alert.setTitle("Warning Dialog");
				alert.setContentText("Incorrect username or password");
				alert.showAndWait();
			}
		}	
	}
	
	
	@FXML
    void returnToHomePage(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
		
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show(); 
 	}
	
}