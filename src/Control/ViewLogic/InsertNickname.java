package Control.ViewLogic;

import java.io.IOException;

import Control.SysData;
import Exceptions.EmptyNickNameException;
import Utils.Sound;
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

	private SysData sd = SysData.getInstance();

	@FXML
	void onStartGame(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.StartGame);			
		}
		
		try {
			String nick = nickname.getText();
			if (nick.length() == 0) {
				throw new EmptyNickNameException();
			}
			// save nick name in sysdata
			sd.setNickname(nick);

			// Move to game screen
			Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameScreen.fxml"));
			Stage primaryStage = (Stage) mainPane.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.setTitle("Knight's Move");
			primaryStage.show();
		} catch (EmptyNickNameException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty nickname");
			alert.setHeaderText("Please don't enter empty nickname");
			alert.showAndWait();
			return;
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
