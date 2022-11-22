package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Model.Constants;
import Model.Game;
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

public class GameScreen implements Initializable {
	
    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane boardWrapper;
    
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
		// TODO get nickname 
		Game currentGame = new Game("nickname", new Date());
		currentGame.createBoardLevelOne();
		Square[][] currentBoard = currentGame.getBoard();
		drawBoard(currentBoard, currentGame);
		initializeTimer();
	}
	
	private void drawBoard(Square[][] currentBoard, Game currentGame) {
		boardView = new Pane[8][8];
		
		// get position of game pieces
		int knightRow = -1, knightCol = -1, queenRow = -1, queenCol = -1, kingRow = -1, kingCol = -1;
		if (currentGame.getKnight() != null) {
			knightRow = currentGame.getKnight().getRow();
			knightCol = currentGame.getKnight().getCol();
		}
		if (currentGame.getQueen() != null) {
			queenRow = currentGame.getQueen().getRow();
			queenCol = currentGame.getQueen().getCol();
		}
		if (currentGame.getKing() != null) {
			kingRow = currentGame.getKing().getRow();
			kingCol = currentGame.getKing().getCol();
		}
		
		for (int row=0; row<8; row++) {
			for (int col=0; col<8; col++) {
				Paint color = getTileColor(row, col, currentBoard[row][col].getSquareType());
				StackPane tileView = new StackPane();
				tileView.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
				tileView.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
				
				// draw game pieces
				if (row == knightRow && col == knightCol) {
					drawGamePiece(tileView, "knight");
				}
				if (row == queenRow && col == queenCol) {
					drawGamePiece(tileView, "queen");
				}
				if (row == kingRow && col == kingCol) {
					drawGamePiece(tileView, "king");
				}
				
				// draw question tile
				if (currentBoard[row][col].getSquareType() == "question") {
					// TODO check what happens if there is override
					drawGamePiece(tileView, "question");
				}
				
				tileView.setLayoutX(Constants.TILE_SIZE * col);
				tileView.setLayoutY(Constants.TILE_SIZE * row);
				boardWrapper.getChildren().add(tileView);

				boardView[row][col] = tileView;
			}
		}
	}
	
	private Paint getTileColor(int i, int j, String tileType) {
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
	
	private Pane tileBeforeMove;
	private void drawGamePiece(StackPane tile, String pieceType) {
		ImageView actorImg = new ImageView(new Image("/Assets/" + pieceType + ".png"));
		actorImg.setFitWidth(Constants.GAME_PIECES_SIZE);
		actorImg.setFitHeight(Constants.GAME_PIECES_SIZE);
		
		if (pieceType == "knight") {
			
			actorImg.setStyle("-fx-cursor: hand");
			// TODO possible moves
			actorImg.setOnDragDetected(event -> {
				int oldCol = (int) (event.getSceneX() - boardWrapper.getLayoutX()) / Constants.TILE_SIZE;
				int oldRow = (int) (event.getSceneY() - boardWrapper.getLayoutY()) / Constants.TILE_SIZE;
				tileBeforeMove = boardView[oldRow][oldCol];
			});
			actorImg.setOnMouseReleased(event -> {
				int newCol = (int) (event.getSceneX() - boardWrapper.getLayoutX()) / Constants.TILE_SIZE;
				int newRow = (int) (event.getSceneY() - boardWrapper.getLayoutY()) / Constants.TILE_SIZE;
				
				// Handle Exceptions of position
				if ((event.getSceneX() < boardWrapper.getLayoutX()) || (event.getSceneX() > (boardWrapper.getLayoutX() + Constants.TILE_SIZE*8))
						|| (event.getSceneY() < boardWrapper.getLayoutY()) || (event.getSceneY() > boardWrapper.getLayoutY() + Constants.TILE_SIZE*8)) {
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
		}
		
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
    void openQuestionModal(ActionEvent event) throws IOException {
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
