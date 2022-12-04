package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Control.SysData;
import Model.Constants;
import Model.Game;
import Model.King;
import Model.Knight;
import Model.Queen;
import Model.Question;
import Model.Square;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

	/*  @FXML
    private static Label pointsLabel;*/

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
		sd.setGame(new Game(nickname, new Date()));
		currentGame = sd.getGame();
		//currentGame.createBoardLevelOne();
		currentGame.createBoardLevelThree();
		Square[][] currentBoard = currentGame.getBoard();
		initGamesArray(gamesArrayForForgettingSquareGames, currentGame);
		drawBoard(currentBoard);
		initializeTimer();
		initializeKingSpeed();


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
					int level = currentBoard[row][col].getQuestion().getQuestionDifficulty();
					drawGamePiece(tileView, "question" + level);
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
				//System.out.println("new turn");
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
								if (currentGame.checkIfSteppedOnRandomSquare()) {
									SysData.alert("Random Square", "You stood on a random square, the position of the knight will change randomly", AlertType.INFORMATION);
								}

								// update the games array for the forgetting square option
								updateGamesArray(gamesArrayForForgettingSquareGames, currentGame);

								if(currentGame.checkIfSteppedOnforgettingSquare())
								{
									goBackThreeSteps(gamesArrayForForgettingSquareGames);
									System.out.println(gamesArrayForForgettingSquareGames);
									currentGame = gamesArrayForForgettingSquareGames.get(gamesArrayForForgettingSquareGames.size()-1);
									System.out.println(currentGame);
									drawBoard(currentGame.getBoard());	
									SysData.alert("Forgetting Square", "You stood on a forget square, you go back 3 steps in the game", AlertType.INFORMATION);
								}
								else // if the player moves back three turns we want to let him play again, because he can fall right to the queen line of fire
								{
									//pass to automatic queen turn 
									//singleQueenTurn();
										

								}
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
	

	/*
	 * a single turn of a queen piece
	 */
	private void singleQueenTurn() {
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
	 * a single turn of a king piece
	 */
	private void singleKingTurn() {
		//get possible next moves for the king
		Square[][] possibleMoves = currentGame.getKing().move(currentGame.getBoard());

		//current knight location
		int knightRow = currentGame.getKnight().getRow();
		int knightCol = currentGame.getKnight().getCol();

		//System.out.println("new turn");
		double shortestDistance = Constants.LONGEST_DISTANCE_BETWEEN_TWO_PIECES;
		//row and col that will save closest tile to knight
		int bestRowToCatchKnight=0;
		int bestColToCatchKnight=0;

		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				if (possibleMoves[i][j]!= null && possibleMoves[i][j].getCanVisit().equals(true)
						&& !currentGame.getBoard()[i][j].getSquareType().equals("blocked")) {
					//case that this is possible tile
					int rowPossible = i;
					int colPossible = j;
					//System.out.println(i + " " + j);

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

					// update king position in Model
					currentGame.getKing().setRow(bestRowToCatchKnight);
					currentGame.getKing().setCol(bestColToCatchKnight);								
					drawBoard(currentGame.getBoard());
				}						
			}
		}	
		
		//System.out.println("played king turn - time: " + seconds);
	}

	
	//play given number of king moves automatically
	private static int count = 0;
	private void automaticKingMovement(int moves) {
		int i;
		Timer kingSpeedTimer = new Timer();	
		kingSpeedTimer.scheduleAtFixedRate( new TimerTask(){
			    public void run() {
			    	Platform.runLater(() -> {
		    	    count++;
		    	    
		    	    //stop timer after the number of desired moves was reached
		    	     if (count >= moves) {
		    	    	 kingSpeedTimer.cancel();
		    	    	 kingSpeedTimer.purge();
		    	         return;
		    	     }
			    	
		    	    //play a turn only if the game is not paused
			    	if(playPauseMode == "pause")
			    		singleKingTurn();	
			    		
			    	});
			    	
			    }
			  /*  divide number of moves in a 10 second span
			   *  for example: if moves = 5
			   *  10 seconds / 5 moves = king will move every 2 seconds
			   */
			 }, 0, (10*1000)/moves);
	
		count = 0;	
	}
		
	
	//increase king movement speed every 10 seconds
	private void initializeKingSpeed() {
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (seconds) {
				case Constants.FIRST_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;

				case Constants.SECOND_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;

				case Constants.THIRD_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;
					
				case Constants.FOURTH_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;
					
				case Constants.FIFTH_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;
					
				case Constants.SIXTH_SPEED:
					automaticKingMovement(currentGame.getKing().getSpeed());
					currentGame.getKing().setSpeed(currentGame.getKing().getSpeed()+1);
					break;
				}
			}
		});
		time.getKeyFrames().add(frame);
		time.playFromStart();
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
					//kingSpeedTimer.cancel();
				}
			}
		});
		time.getKeyFrames().add(frame);
		time.playFromStart();
	}

	// if at the beginning of the game (after one or two steps) you stepped on forgetting Square you will get back to the start of the game, because there were no more possible moves to get back to
	public void initGamesArray(ArrayList<Game> gamesArrayForForgettingSquareGames, Game currentGame) 
	{
		System.out.println("init array");
		Game g1 = createGameClone(currentGame);
		Game g2 = createGameClone(currentGame);
		Game g3 = createGameClone(currentGame);
		gamesArrayForForgettingSquareGames.add(g1);
		gamesArrayForForgettingSquareGames.add(g2);
		gamesArrayForForgettingSquareGames.add(g3);
	}

	public void updateGamesArray(ArrayList<Game> gamesArrayForForgettingSquareGames, Game currentGame) 
	{
		System.out.println("update array");

		Game newTempGame = createGameClone(currentGame);
		int index = gamesArrayForForgettingSquareGames.size()-2;
		System.out.println("current: " +currentGame);
		System.out.println("the last cell? "+ gamesArrayForForgettingSquareGames.get(index));
		//check for duplicates and ignore
		if(newTempGame.getKnight().getRow()!= (gamesArrayForForgettingSquareGames.get(index).getKnight().getRow()) && newTempGame.getKnight().getCol()!= (gamesArrayForForgettingSquareGames.get(index).getKnight().getCol()))
		{
			System.out.println(gamesArrayForForgettingSquareGames.size());
			gamesArrayForForgettingSquareGames.add(gamesArrayForForgettingSquareGames.size(), newTempGame);
		}
		for(int i=0; i< gamesArrayForForgettingSquareGames.size(); i++)
		{
			System.err.println("arr in index " + i +" is: " + gamesArrayForForgettingSquareGames.get(i));
		}
	}

	public void goBackThreeSteps(ArrayList<Game> gamesArrayForForgettingSquareGames) 
	{
		System.out.println("go back three steps");
		int index = gamesArrayForForgettingSquareGames.size();
		int counter = 0;
		if(index > 3) //if we have enough steps to remove
		{
			while (counter < 3)
			{
				// we are going back three steps, so we are removing the last two forward steps
				index --;
				gamesArrayForForgettingSquareGames.remove(index);
				counter++;
			}

		}
		else // if not enough steps, then it means we have only two steps done or one in our game, and we want to get back to the first one, also we recreate the 3 first cells to the first one
		{
			gamesArrayForForgettingSquareGames.add(0, gamesArrayForForgettingSquareGames.get(0));
			gamesArrayForForgettingSquareGames.add(1,gamesArrayForForgettingSquareGames.get(0));
			gamesArrayForForgettingSquareGames.add(2, gamesArrayForForgettingSquareGames.get(0));
		}

	}

	// in order to save the Game objects we need to create a brand new object from scratch, or else the arraylist of games will be duplicates of the same current games, which are all a refernce to the same object
	public Game createGameClone(Game currentGame)
	{
		Game newTempGame = new Game("Roei");
		Square[][] board1 = currentGame.getBoard();
		int score = currentGame.getScore(); 
		Timeline timer = currentGame.getTimer(); 
		int gameLevel = currentGame.getGameLevel();
		String nickname = currentGame.getNickname(); 
		Date date = currentGame.getDate(); 
		Knight knight = new Knight(currentGame.getKnight().getRow(), currentGame.getKnight().getCol());
		if(currentGame.getKing()!= null)
		{
			King king = new King(currentGame.getKing().getRow(), currentGame.getKing().getCol());
			newTempGame.setKing(king);
		}
		else 
		{
			Queen queen = new Queen(currentGame.getQueen().getRow(), currentGame.getQueen().getCol());
			newTempGame.setQueen(queen);
		}


		newTempGame.setBoard(board1);
		newTempGame.setScore(score);
		newTempGame.setTimer(timer);
		newTempGame.setGameLevel(gameLevel);
		newTempGame.setNickname(nickname);
		newTempGame.setDate(date);
		newTempGame.setKnight(knight);

		return newTempGame;
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

	/*   //Not Working yet
    @FXML
    public static void changeScoreOnScreen()
    {
    	pointsLabel.setText(SysData.getInstance().getGame().getScore() + " Points");
    }*/


}
