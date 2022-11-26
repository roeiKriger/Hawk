package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Control.SysData;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private Label nicknameLabel;
    
    @FXML
    private Label pointsLabel;
    
    @FXML
    private Label playPauseLabel;
    private String playPauseMode = "pause";
    
    public StackPane[][] boardView = null;
    
    private SysData sd = SysData.getInstance();
    
    private Game currentGame;
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		String nickname = sd.getNickname();
		nicknameLabel.setText("Hello " + nickname);
		currentGame = new Game(nickname, new Date());
		currentGame.createBoardLevelOne();
		Square[][] currentBoard = currentGame.getBoard();
		drawBoard(currentBoard);
		initializeTimer();
	}
	
	/*
	 *  draw board in fxml, in given board 
	 */
	private void drawBoard(Square[][] currentBoard) {
		boardView = new StackPane[8][8];
		
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
				Color color = getTileColor(row, col, currentBoard[row][col].getSquareType());
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
				
				// set position in fxml
				tileView.setLayoutX(Constants.TILE_SIZE * col);
				tileView.setLayoutY(Constants.TILE_SIZE * row);
				boardWrapper.getChildren().add(tileView);

				// save tileView in board that repressents the view
				boardView[row][col] = tileView;
			}
		}
	}
	
	/*
	 * get background color of tile by type or position 
	 */
	private Color getTileColor(int i, int j, String tileType) {
		Color color;
		// TODO - change color by tile type 
		if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
			color = Color.WHITE;
		}
		else {
			color = Color.BLACK;
		}
		return color;
	}
	
	/*
	 * function that add game piece to tile in view
	 * tileBeforeMove is global variable for save the initial tile before player move
	 */
	private void drawGamePiece(StackPane tile, String pieceType) {
		ImageView actorImg = new ImageView(new Image("/Assets/" + pieceType + ".png"));
		actorImg.setFitWidth(Constants.GAME_PIECES_SIZE);
		actorImg.setFitHeight(Constants.GAME_PIECES_SIZE);
		
		if (pieceType == "knight") {
			actorImg.setStyle("-fx-cursor: hand");
			Square[][] possibleMoves = currentGame.getKnight().move(currentGame.getBoard(), currentGame.getGameLevel(), currentGame.getKing(), currentGame.getQueen());

			actorImg.setOnMouseClicked(eventBefore -> {
				System.out.println("new turn");
				for (int i=0; i<8; i++) {
					for (int j=0; j<8; j++) {
						if (possibleMoves[i][j]!= null && possibleMoves[i][j].getCanVisit().equals(true)) {
						// case that this is possible tile
							int rowPossible = i;
							int colPossible = j;
							System.out.println(i + " " + j);
							
							// change color of tilePossible
							StackPane tilePossibleView = boardView[rowPossible][colPossible];
							tilePossibleView.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
							tilePossibleView.setStyle("-fx-cursor: hand");
														
							// add listener to press on possible move
							tilePossibleView.setOnMouseClicked(eventAfter -> {
								// TODO - add cases of question, random, etc;
								
								int newCol = (int) (eventAfter.getSceneX() - boardWrapper.getLayoutX()) / Constants.TILE_SIZE;
								int newRow = (int) (eventAfter.getSceneY() - boardWrapper.getLayoutY()) / Constants.TILE_SIZE;
								
								// update knight position in Model
								currentGame.getKnight().setRow(newRow);
								currentGame.getKnight().setCol(newCol);
																
								// TODO - pass to enemy turn 
								// TODO - update another data in Model?
								drawBoard(currentGame.getBoard());
							});	
						}						
					}
				}					
			});
		}
		
		tile.getChildren().clear();
		tile.getChildren().add(actorImg);
		StackPane.setAlignment(actorImg, Pos.CENTER);
	}
	
	
	/*
	 * initalize timer to 1 minute per round
	 * seconds and time are global variables for changing them after as subject
	 */
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
	
	/*
	 * function that responsible to play and pause the game
	 */
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
    
    /*
     * open question modal, without override the game screen
     */
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

    /*
     * exit the game
     */
    @FXML
    void returnToHomePage(ActionEvent event) throws IOException {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
    }
    
}
