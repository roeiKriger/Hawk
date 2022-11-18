package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game implements Initializable {
	
    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane boardPane;
    
    @FXML
    private Label countDownLabel;
    
    @FXML
    private Label levelLabel;
    
    @FXML
    private Label pointsLabel;
    
    @FXML
    private Label playPauseLabel;
    private String playPauseMode = "pause";
    
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		drawInitialBoard();
		initializeTimer();
	}
	
	private void drawInitialBoard() {
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				Paint color = getTileColor(i, j);
				StackPane tile = new StackPane();
				tile.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
				tile.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
				
				if (i == 0 & j == 0) {
					drawGamePiece(tile, "knight");
				}
				else if (i == 0 && j == 7) { // TODO: change this by level.
					drawGamePiece(tile, "king");
					drawGamePiece(tile, "queen");
				}
				
				boardPane.add(tile, j, i);
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
	
	private void drawGamePiece(StackPane tile, String pieceType) {
		ImageView actorImg = new ImageView(new Image("/Assets/" + pieceType + ".png"));
		actorImg.setFitWidth(Constants.GAME_PIECES_SIZE);
		actorImg.setFitHeight(Constants.GAME_PIECES_SIZE);
		tile.getChildren().clear();
		tile.getChildren().add(actorImg);
		StackPane.setAlignment(actorImg, Pos.CENTER);
	}
	
	private Integer seconds = Constants.ROUND_TIME;
	private Timeline time;
	private void initializeTimer() {
		countDownLabel.setText("01:00");
		time = new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		if (time != null) {
			time.stop();
		}
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				seconds--;
				String secondsStr = (seconds >= 10) ? seconds.toString() : '0' + seconds.toString();
				countDownLabel.setText("00:" + secondsStr);
				if (seconds <= 0) {
					time.stop();
				}
			}
		});
		time.getKeyFrames().add(frame);
		time.playFromStart();
	}
	
    @FXML
    void playPause(MouseEvent event) {
    	if (playPauseMode == "pause") {
    		pause();
    		playPauseLabel.setText("Play");
    		playPauseMode = "play";
    	} else {
    		play();
    		playPauseLabel.setText("Pause");
    		playPauseMode = "pause";
    	}
    }
    private void pause() {
    	time.pause();
    }
    private void play() {
    	time.play();
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
