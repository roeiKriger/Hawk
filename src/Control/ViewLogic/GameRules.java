package Control.ViewLogic;

import java.io.IOException;

import Control.SysData;
import Utils.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameRules {
	
    @FXML
    private AnchorPane mainPane;
    

    SysData sd = SysData.getInstance();
    
    @FXML
    void returnToHomePage(ActionEvent event) throws IOException {
    	sd.playSound(Sound.Menu);
    	
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();    }

}
