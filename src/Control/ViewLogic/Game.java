package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Model.Constants;
import Model.Square;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game implements Initializable {
	
    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane boardPane;
    
    @FXML
    private Label countDownLabel;
    
    @FXML
    private Label levelLabel;
    
    @FXML
    private Label pointsLabel;
    
    @FXML
    private Label playPauseLabel;
    private String playPauseMode = "pause";
    
    public Pane[][] boardView = null;
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		Model.Game currentGame = new Model.Game("name", null);
		currentGame.createBoardLevelOne();
		drawInitialBoard(currentGame.getBoard());
		initializeTimer();
		
		
	
	}
	
	private void drawInitialBoard(Square[][] currentBoard) {
		boardView = new Pane[8][8];
		
		for (int row=0; row<8; row++) {
			for (int col=0; col<8; col++) {
				Paint color = getTileColor(row, col);
				StackPane tile = new StackPane();
				tile.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
				tile.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
				
				if (row == 0 & col == 0) {
					drawGamePiece(tile, "knight");
				}
				else if (row == 0 && col == 7) { // TODO: change this by level.
					drawGamePiece(tile, "king");
					drawGamePiece(tile, "queen");
				}
				
				tile.setLayoutX(Constants.TILE_SIZE * col);
				tile.setLayoutY(Constants.TILE_SIZE * row);
				boardPane.getChildren().add(tile);
				boardView[row][col] = tile;
			}
		}
	}
	
	private Paint getTileColor(int i, int j) {
		// TODO - change color by tile type
		Paint color;
		if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
			color = Paint.valueOf(Constants.LIGHT_TILE);
		}
		else {
			color = Paint.valueOf(Constants.DARK_TILE);
		}
		return color;
	}
	
	Pane tileBeforeMove;
	private void drawGamePiece(StackPane tile, String pieceType) {
		ImageView actorImg = new ImageView(new Image("/Assets/" + pieceType + ".png"));
		actorImg.setFitWidth(Constants.GAME_PIECES_SIZE);
		actorImg.setFitHeight(Constants.GAME_PIECES_SIZE);
		actorImg.setStyle("-fx-cursor: hand");
		actorImg.setOnDragDetected(event -> {
//			System.out.println("setOnDragDetected");
			int oldCol = (int) (event.getSceneX() - boardPane.getLayoutX()) / Constants.TILE_SIZE;
			int oldRow = (int) (event.getSceneY() - boardPane.getLayoutY()) / Constants.TILE_SIZE;
//			System.out.println("oldCol: " + oldCol + ", oldRow: " + oldRow);
			tileBeforeMove = boardView[oldRow][oldCol];
		});
		actorImg.setOnMouseReleased(event -> {
//			System.out.println("setOnMouseReleased");
			int newCol = (int) (event.getSceneX() - boardPane.getLayoutX()) / Constants.TILE_SIZE;
			int newRow = (int) (event.getSceneY() - boardPane.getLayoutY()) / Constants.TILE_SIZE;
//			System.out.println("newCol: " + newCol + ", newRow: " + newRow);
			
			// Handle Exceptions of position
			if ((event.getSceneX() < boardPane.getLayoutX()) || (event.getSceneX() > (boardPane.getLayoutX() + Constants.TILE_SIZE*8))
					|| (event.getSceneY() < boardPane.getLayoutY()) || (event.getSceneY() > boardPane.getLayoutY() + Constants.TILE_SIZE*8)) {
				this.pause();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Out of bounds location");
				alert.setHeaderText("Please select a location within the board");
				alert.showAndWait();
				this.play();
				return;
			}
			tileBeforeMove.getChildren().clear();
			Pane tileAfterMove = boardView[newRow][newCol];
			tileAfterMove.getChildren().clear();
			tileAfterMove.getChildren().add(actorImg);
		});
		
		// TODO - try to move the game piece while dragging
//		actorImg.setOnMouseDragged(event -> {
//			System.out.println("setOnMouseDragged: " + event.getSceneX() + " " + event.getSceneY());
//			actorImg.toFront();
//			actorImg.setX(event.getX());
//			actorImg.setY(event.getY());
//		});
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
    void openModal(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionModal.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Question");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
            ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
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
