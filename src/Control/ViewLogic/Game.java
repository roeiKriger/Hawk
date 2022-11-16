package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game implements Initializable {
	
    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane boardPane;

	public void initialize(URL arg0, ResourceBundle arg1)
	{
		drawInitialBoard();
	}
	
	private void drawInitialBoard() {
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Paint color = getTileColor(i, j);
				Rectangle tile = new Rectangle(Constants.TILE_SIZE, Constants.TILE_SIZE, color);
				boardPane.add(tile, i, j);
			}
		}
	}
	
	private Paint getTileColor(int i, int j) {
		Paint color;
		if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
			color = Paint.valueOf(Constants.LIGHT_TILE);
		}
		else {
			color = Paint.valueOf(Constants.DARK_TILE);
		}
		return color;
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
