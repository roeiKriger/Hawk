package Control.ViewLogic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage {	

    @FXML
    private AnchorPane mainPane;
	

    @FXML
    void onGameHistory(ActionEvent event) throws IOException {
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameHistory.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Rules");
		primaryStage.show();
    }

    @FXML
    void onGameRules(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameRules.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Rules");
		primaryStage.show();
    }

    @FXML
    void onQuestionsManagement(ActionEvent event) throws IOException {
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/View/SignInAsAdmin.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Insert Nickname");
		primaryStage.show();

    }

    @FXML
    void onStartGame(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/InsertNickname.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Insert Nickname");
		primaryStage.show();
    }

}
