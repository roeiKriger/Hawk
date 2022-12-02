package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Control.SysData;
import Model.Constants;
import Model.Game;
import Model.Question;
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
    
	ArrayList<Game> gamesArrayForForgettingSquareGames = new ArrayList<>();
    
    private Game currentGame;
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		String nickname = sd.getNickname();
		nicknameLabel.setText("Hello " + nickname);
		currentGame = new Game(nickname, new Date());
		currentGame.createBoardLevelOne();
		Square[][] currentBoard = currentGame.getBoard();
		initGamesArray(gamesArrayForForgettingSquareGames, currentGame);
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
					drawGamePiece(tileView, "question");
				}
				// draw question tile
				if (currentBoard[row][col].getSquareType() == "blocked") {
					drawGamePiece(tileView, "blocked");
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
//							System.out.println(i + " " + j);
							
							// change color of tilePossible
							StackPane tilePossibleView = boardView[rowPossible][colPossible];
							tilePossibleView.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
							tilePossibleView.setStyle("-fx-cursor: hand");
														
							// add listener to press on possible move
							tilePossibleView.setOnMouseClicked(eventAfter -> {
								// TODO - add cases of question, random, etc;
								
								int newCol = (int) (eventAfter.getSceneX() - boardWrapper.getLayoutX()) / Constants.TILE_SIZE;
								int newRow = (int) (eventAfter.getSceneY() - boardWrapper.getLayoutY()) / Constants.TILE_SIZE;
								
								// case of question tile
								if (currentGame.getBoard()[newRow][newCol].getSquareType().equals("question")) {
									try {
										// save current question in sysdata
										Question currentQuestion = currentGame.getBoard()[newRow][newCol].getQuestion();
										sd.setCurrentQuestion(currentQuestion);

										openQuestionModal(eventAfter);
									} catch (IOException e) {
										e.printStackTrace();
									}
									
									// replace position of question
									replaceQuestionPosition(newRow, newCol);
								}
								
								// update knight position in Model
								currentGame.getKnight().setRow(newRow);
								currentGame.getKnight().setCol(newCol);
															
								// checking to see if the Knight stepped on a Random Square, if so he will be moved to a new location
								currentGame.checkIfSteppedOnRandomSquare();
								
								//pass to automatic queen turn 
								queenTurn();
								
								drawBoard(currentGame.getBoard());
								
								// update the games array for the forgetting square option
								updateGamesArray(gamesArrayForForgettingSquareGames, currentGame);
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
	 * Changing the position of a question square after the knight stands on it
	 */
	private void replaceQuestionPosition(int oldRow, int oldCol) {
		Question currentQuestion = currentGame.getBoard()[oldRow][oldCol].getQuestion();
		int level = currentQuestion.getQuestionDifficulty();
		
		// new question square
		currentGame.createNewQuestionSquare(currentGame.getBoard(), "question", level);
				
		// reset current question square
		currentGame.getBoard()[oldRow][oldCol].setSquareType("empty");
		currentGame.getBoard()[oldRow][oldCol].setQuestion(null);
	}
	
	
	private void queenTurn() {
		//get possible next moves for the queen
		Square[][] possibleMoves = currentGame.getQueen().move(currentGame.getBoard());
		
		//current knight location
		int knightRow = currentGame.getKnight().getRow();
		int knightCol = currentGame.getKnight().getCol();
		
//		System.out.println("new turn");
		double shortestDistance = Constants.LONGEST_DISTANCE_BETWEEN_TWO_PIECES;
		//row and col that will save closest tile to knight
		int bestRowToCatchKnight=0;
		int bestColToCatchKnight=0;
			
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				if (possibleMoves[i][j]!= null && possibleMoves[i][j].getCanVisit().equals(true)) {
				// case that this is possible tile
					int rowPossible = i;
					int colPossible = j;
//					System.out.println(i + " " + j);
					
					//choose a possible tile that is closest to knight's location
					//compare tiles by euclidean distance from knight's tile 
					//sqrt((Ax-Bx)^2-(Ay-By)^2)
					double distanceRow = Math.pow(Double.valueOf(rowPossible-knightRow),2.0);
					double distanceCol = Math.pow(Double.valueOf(colPossible-knightCol),2.0);
					double distanceBetweenPossibleAndKnight = Math.sqrt(distanceCol + distanceRow);
					
					
					//if a closer tile than the one currently saved was found, save the new tile instead
					if (distanceBetweenPossibleAndKnight < shortestDistance) {
						bestRowToCatchKnight = rowPossible;
						bestColToCatchKnight = colPossible;
						shortestDistance = distanceBetweenPossibleAndKnight;
					}
						
					// update queen position in Model
					currentGame.getQueen().setRow(bestRowToCatchKnight);
					currentGame.getQueen().setCol(bestColToCatchKnight);								
					drawBoard(currentGame.getBoard());
				}						
			}
		}						
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
	
	// if at the beginning of the game (after one or two steps) you stepped on forgetting Square you will get back to the start of the game, because there were no more possible moves to get back to
	public void initGamesArray(ArrayList<Game> gamesArrayForForgettingSquareGames, Game currentGame) 
	{
		gamesArrayForForgettingSquareGames[0] = currentGame;
		gamesArrayForForgettingSquareGames[1] = currentGame;
		gamesArrayForForgettingSquareGames[2] = currentGame;
	}
	
	public void updateGamesArray(ArrayList<Game> gamesArrayForForgettingSquareGames, Game currentGame) 
	{
		// We assume that the first and oldest moves is in the first cell, cell 0
		// and the most new move and current board is in the last cell, cell 2
		gamesArrayForForgettingSquareGames[0] = gamesArrayForForgettingSquareGames[1];
		gamesArrayForForgettingSquareGames[1] = gamesArrayForForgettingSquareGames[2];
		gamesArrayForForgettingSquareGames[2] = currentGame;
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
    void openQuestionModal(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/QuestionModal.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
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
