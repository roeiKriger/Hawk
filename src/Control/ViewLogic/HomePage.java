package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Control.SysData;
import Utils.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage implements Initializable{	

    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private ImageView soundImg;
	
    SysData sd = SysData.getInstance();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// sound image in initalize screen
		if (sd.isSoundFlag()) {
    		soundImg.setImage(new Image("/Assets/sound-on.png"));    		
    	} else {
    		soundImg.setImage(new Image("/Assets/sound-off.png")); 
    	}		
	}
	
	/*
	 * toggle image sound and flag sound is sysdata
	 */
    @FXML
    void toggleSound(MouseEvent event) {
    	if (sd.isSoundFlag()) {
    		soundImg.setImage(new Image("/Assets/sound-off.png"));    		
    	} else {
    		soundImg.setImage(new Image("/Assets/sound-on.png")); 
    		sd.playSound(Sound.Menu);			
    	}
    	sd.setSoundFlag(!sd.isSoundFlag());
    }


    @FXML
    void onGameHistory(ActionEvent event) throws IOException {
    	if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
    	
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameHistory.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Rules");
		primaryStage.show();
    }

    @FXML
    void onGameRules(ActionEvent event) throws IOException {
    	if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
    	
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/GameRules.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Rules");
		primaryStage.show();
    }

    @FXML
    void onQuestionsManagement(ActionEvent event) throws IOException {
    	if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
    	
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/View/SignInAsAdmin.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Insert Nickname");
		primaryStage.show();

    }

    @FXML
    void onStartGame(ActionEvent event) throws IOException {
    	if (sd.isSoundFlag()) {
			sd.playSound(Sound.StartGame);			
		}
    	
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/InsertNickname.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Insert Nickname");
		primaryStage.show();
    }

}
