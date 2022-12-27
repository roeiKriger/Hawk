import java.io.IOException;

import org.json.simple.parser.ParseException;

import Control.SysData;
import Exceptions.JsonException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("/Assets/screens/horse_opacity.png"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Knight's Move");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException 
	{		
		SysData sd = SysData.getInstance();
		try {
		if (!sd.loadQuestions()) // import not successful
			throw new JsonException();
		} catch (JsonException e) {
			SysData.alert(e.getMessage(), e.getMessage(), AlertType.ERROR);
		}


		launch(args);
	}
		
}
