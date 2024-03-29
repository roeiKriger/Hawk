package Control.ViewLogic;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Control.SysData;
import Model.Constants;
import Model.Game;
import Model.King;
import Model.Knight;
import Model.Queen;
import Model.Question;
import Model.Square;
import Utils.Mode;
import Utils.Sound;
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

	@FXML
    private Label pointsLabel;
	
	@FXML
	private ImageView background;

	@FXML
	private Label playPauseLabel;
	private String playPauseMode = "pause";
	
	public StackPane[][] boardView = null;

	private SysData sd = SysData.getInstance();

	ArrayList<Game> gamesArrayForForgettingSquareGames = new ArrayList<>();

	private Game currentGame;
	
	private int previousLevelScore;
	
	private boolean gameEndedFlag = false;

	public void initialize(URL arg0, ResourceBundle arg1)
	{
		//set current game
		String nickname = sd.getNickname();
		nicknameLabel.setText("Hello\n" + nickname + "!");
		sd.setGame(new Game(nickname, new Date()));
		currentGame = sd.getGame();
		//initialize board
		currentGame.createBoardLevelOne();	
		//mark the tile 0,0 of knight as visited
		currentGame.getBoard()[Constants.INITIAL_LOCATION][Constants.INITIAL_LOCATION].setIsVisited(true);
		//draw board
		Square[][] currentBoard = currentGame.getBoard();
		drawBoard(currentBoard);
		//initialize games array for the forgetting square
		gamesArrayForForgettingSquareGames.clear();
		initGamesArray();
		//start 60 seconds timer
		initializeTimer();
		//display current game level on screen
		levelLabel.setText("Level " + currentGame.getGameLevel());
		//set background
		background.setImage(new Image("/Assets/screens/blank.png"));
		previousLevelScore = 0;
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

				//draw visited tile
				if (currentBoard[row][col].getIsVisited() == true) {
					drawGamePiece(tileView, "visited");
				}

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
		ImageView actorImg = null;
		//get image of the specific screen mode 
		if(currentGame.getMode()==Mode.Default) 
			actorImg = new ImageView(new Image("/Assets/" + pieceType + ".png"));
		if(currentGame.getMode()==Mode.Christmas)
			actorImg = new ImageView(new Image("/Assets/christmas/" + pieceType + ".png"));
		if(currentGame.getMode()==Mode.Hanukkah)
			actorImg = new ImageView(new Image("/Assets/hanukkah/" + pieceType + ".png"));
			
		actorImg.setFitWidth(Constants.GAME_PIECES_SIZE);
		actorImg.setFitHeight(Constants.GAME_PIECES_SIZE);
	
		if (pieceType == "knight") {
			actorImg.setStyle("-fx-cursor: hand");
			Square[][] possibleMoves = currentGame.getKnight().move(currentGame.getGameLevel());

			actorImg.setOnMouseClicked(eventBefore -> {
				//start timer after a question was answered
				if (playPauseMode == "play")
					playPause(eventBefore);
				
				for (int i=0; i<8; i++) {
					for (int j=0; j<8; j++) {
						if (possibleMoves[i][j]!= null && possibleMoves[i][j].getCanVisit().equals(true)
								&& !currentGame.getBoard()[i][j].getSquareType().equals("blocked")) {
							// case that this is possible tile
							int rowPossible = i;
							int colPossible = j;

							// change color of tilePossible
							StackPane tilePossibleView = boardView[rowPossible][colPossible];
							if(currentGame.getMode()==Mode.Default) 
								tilePossibleView.setBackground(new Background(new BackgroundFill(Color.TEAL, CornerRadii.EMPTY, Insets.EMPTY)));
							if(currentGame.getMode()==Mode.Christmas) 
								tilePossibleView.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, Insets.EMPTY)));
							if(currentGame.getMode()==Mode.Hanukkah) 
								tilePossibleView.setBackground(new Background(new BackgroundFill(Color.	DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
							
							tilePossibleView.setStyle("-fx-cursor: hand");
							
							// add listener to press on possible move
							tilePossibleView.setOnMouseClicked(eventAfter -> {
								// TODO - add cases of question, random, etc;

								int newCol = (int) (eventAfter.getSceneX() - boardWrapper.getLayoutX()) / Constants.TILE_SIZE;
								int newRow = (int) (eventAfter.getSceneY() - boardWrapper.getLayoutY()) / Constants.TILE_SIZE;	
								
								//update score based on visited/unvisited square	
								if (currentGame.getBoard()[newRow][newCol].getIsVisited().equals(false)) {
									//set each tile the knight steps on as visited
									currentGame.getBoard()[newRow][newCol].setIsVisited(true);
									//if player chose an unvisited tile -> add one point to the score
									currentGame.setScore(currentGame.getScore()+Constants.POINT);
								}
								//if player chose a tile that was already visited -> subtract one point from the score
								else 
									currentGame.setScore(currentGame.getScore()-Constants.POINT);
								//update score on screen
								pointsLabel.setText("Points: " + currentGame.getScore());
								
								// case of question tile
								if (currentGame.getBoard()[newRow][newCol].getSquareType().equals("question")) {
									//stop the timer during a question
									playPause(eventBefore);
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
								
								//if the queen caught the knight
								if(currentGame.getQueen() != null) {
									if(currentGame.getQueen().getRow() == currentGame.getKnight().getRow()
											&& currentGame.getQueen().getCol() == currentGame.getKnight().getCol()) {
										time.stop();
										try {
											gameOver("The Knight was caught by the Queen. \nYou got to level " + currentGame.getGameLevel() 
											+ ", and earned " + currentGame.getScore() + " points. \nBetter luck next time!");
										} catch (IOException e) {
											e.printStackTrace();
										}
													
									}
								}
								else {
									//if the king caught the knight
									if(currentGame.getKing().getRow() == currentGame.getKnight().getRow()
											&& currentGame.getKing().getCol() == currentGame.getKnight().getCol()) {
										time.stop();
										try {
											gameOver("The Knight was caught by the King. \nYou got to level " + currentGame.getGameLevel() 
											+ ", and earned " + currentGame.getScore() + " points. \nBetter luck next time!");
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
								}
								

								// checking to see if the Knight stepped on a Random Square, if so he will be moved to a new location
								if (currentGame.checkIfSteppedOnRandomSquare()) {
									SysData.alert("Random Square", "You stood on a random square, the position of the knight will change randomly", AlertType.INFORMATION);
								}

								// add current game to the forgetting square game history
								updateGamesArray();

								//if knight got to a forgetting square
								if(currentGame.checkIfSteppedOnforgettingSquare()) {
									if(gamesArrayForForgettingSquareGames.size() > Constants.MAX_GAMES_FOR_FORGETTING_SQUARE) {
										//delete 3 last games from the game history
										goBackThreeSteps();
										//change current game back to the game from 3 steps ago
										Game currentGameTemp = null;
										currentGameTemp = createGameClone(gamesArrayForForgettingSquareGames.get(gamesArrayForForgettingSquareGames.size()-1));
										currentGameTemp.setKnight(currentGame.getKnight());
										
										if(currentGame.getQueen() != null)
										{
											currentGameTemp.setQueen(currentGame.getQueen());
										}
										else 
										{
											currentGameTemp.setKing(currentGame.getKing());
										}
										currentGame = currentGameTemp;
										drawBoard(currentGame.getBoard());	
										SysData.alert("Forgetting Square", "You stood on a forget square, going back 3 steps in the game", AlertType.INFORMATION);	
										
									}
								}
								else // if the player moves back three turns we want to let him play again, because he can fall right to the queen line of fire
								{
									//pass to automatic queen turn 
									if(currentGame.getQueen() != null)
										try {
											singleQueenTurn();
										} catch (Exception e) {
											e.printStackTrace();
										}
								}
								drawBoard(currentGame.getBoard());							

							});	
						}						
					}
				}
			});
		}
		

		if (pieceType.equals("knight")) {
			tile.getChildren().clear();
		}
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
	private void singleQueenTurn() throws IOException, ParseException {
		//get possible next moves for the queen
		Square[][] possibleMoves = currentGame.getQueen().move(currentGame.getGameLevel());

		//current knight location
		int knightRow = currentGame.getKnight().getRow();
		int knightCol = currentGame.getKnight().getCol();

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
		
		//if the queen caught the knight
		if(currentGame.getQueen().getRow() == currentGame.getKnight().getRow()
				&& currentGame.getQueen().getCol() == currentGame.getKnight().getCol()) {
			time.stop();
			gameOver("The Knight was caught by the Queen. \nYou got to level " + currentGame.getGameLevel() 
			+ ", and earned " + currentGame.getScore() + " points. \nBetter luck next time!");
						
		}
	}
	
	/*
	 * a single turn of a king piece
	 */
	private void singleKingTurn() throws IOException {
		//get possible next moves for the king
		Square[][] possibleMoves = currentGame.getKing().move(currentGame.getGameLevel());

		//current knight location
		int knightRow = currentGame.getKnight().getRow();
		int knightCol = currentGame.getKnight().getCol();

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
		
		//if the king caught the knight
		if(currentGame.getKing().getRow() == currentGame.getKnight().getRow()
				&& currentGame.getKing().getCol() == currentGame.getKnight().getCol()) {
			time.stop();
			gameOver("The Knight was caught by the King. \nYou got to level " + currentGame.getGameLevel() 
			+ ", and earned " + currentGame.getScore() + " points. \nBetter luck next time!");
		}
	}

	
	//play given number of king moves automatically
	private static int count = 0;
	private void automaticKingMovement(int moves) {
		Timer kingSpeedTimer = new Timer();	
		kingSpeedTimer.scheduleAtFixedRate( new TimerTask(){
			    public void run() {
			    	Platform.runLater(() -> {
		    	    count++;
		    	    
		    	    //stop timer after the number of desired moves was reached
		    	    //if the king caught the knight stop automatic movement
		    	     if (count >= moves || (currentGame.getKing().getRow() == currentGame.getKnight().getRow()
		    					&& currentGame.getKing().getCol() == currentGame.getKnight().getCol())) {
		    	    	 kingSpeedTimer.cancel();
		    	    	 kingSpeedTimer.purge();
		    	         return;
		    	     }
		    	    //play a turn only if the game is not paused
			    	if(playPauseMode == "pause")
						try {
							singleKingTurn();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
			    		
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
				if (seconds <= 0 && !gameEndedFlag) {
					time.stop();
					try {
						oneMinutePassedAndLevelEnded();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		time.getKeyFrames().add(frame);
		time.playFromStart();
	}

	// if at the beginning of the game (after one or two steps) you stepped on forgetting Square you will get back to the start of the game, because there were no more possible moves to get back to
	public void initGamesArray() {
		Game g1 = createGameClone(currentGame);
		Game g2 = createGameClone(currentGame);
		Game g3 = createGameClone(currentGame);
		gamesArrayForForgettingSquareGames.add(g1);
		gamesArrayForForgettingSquareGames.add(g2);
		gamesArrayForForgettingSquareGames.add(g3);
	}

	//add current game to array of games for the forgetting square
	public void updateGamesArray() {
		Game newTempGame = createGameClone(currentGame);
		gamesArrayForForgettingSquareGames.add(gamesArrayForForgettingSquareGames.size(), newTempGame);		
		
	}
	
	
	public void goBackThreeSteps() {
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
	public Game createGameClone(Game currentGame) {
		Game newTempGame = new Game("clone");
		//Square[][] board1 = currentGame.getBoard();
		Square[][] board1 = null;
		board1 = new Square[8][8];
		board1 = newTempGame.createEmptyBoard(board1);
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				board1[i][j] = new Square();
				if(board1[i][j]!=null)
				{
					board1[i][j].setIsVisited(currentGame.getBoard()[i][j].getIsVisited());
					board1[i][j].setCanVisit(currentGame.getBoard()[i][j].getCanVisit());
					board1[i][j].setQuestion(currentGame.getBoard()[i][j].getQuestion());
					board1[i][j].setSquareType(currentGame.getBoard()[i][j].getSquareType());
				}
				else {
					System.out.println("null");
				}
				
			}
		}
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
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.QuestionPopup);			
		}
		
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
	
	
	//start the next level or end the game, based on the score at the end of the level
	void oneMinutePassedAndLevelEnded() throws IOException {
		//calculate current level score
		int currentLevelScore = currentGame.getScore() - previousLevelScore;
		previousLevelScore = currentGame.getScore();
	
		//score is higher than 15 points
		if(currentLevelScore >= Constants.MIN_SCORE_TO_WIN_LEVEL) {
			
			//player won level 4
			if(currentGame.getGameLevel() == 4) 
				gameWon();
			//player won level 1/2/3, so the next level will open on screen
			else 
				switchToNextLevel();
		}
		//score is 15 points or less
		else {
			gameOver("You need to earn more than 15 points in each level, to be able to move to the next one. "
					+ "\nYou got to level " + currentGame.getGameLevel() + ", and earned " + currentLevelScore + " points."
					+ "\nYou earned " + currentGame.getScore() + " points total. Better luck next time!");
		}
	}
	
	//initialize next level's board
	void switchToNextLevel() {
		int previousLevel = currentGame.getGameLevel();
		int nextLevel = previousLevel+1;
		//alert user that the next level is starting
		SysData.alert("Level Completed", "You finished level " + previousLevel
				+ ", with " + currentGame.getScore() + " points.\n Level " + nextLevel +
				" starts now, good luck!", AlertType.WARNING);
		
		//create board of the next level
		switch (previousLevel) {
		case Constants.LEVEL_ONE:
			currentGame.createBoardLevelTwo();
			break;
		case Constants.LEVEL_TWO:
			currentGame.createBoardLevelThree();
			initializeKingSpeed();
			break;
		case Constants.LEVEL_THREE:
			currentGame.createBoardLevelFour();
			initializeKingSpeed();
			break;
		}
		
		levelLabel.setText("Level " + currentGame.getGameLevel());
		//reset the 60 seconds game timer
		seconds = Constants.ROUND_TIME;
		initializeTimer();
		//initialize game array for forgetting square
		gamesArrayForForgettingSquareGames.clear();
		initGamesArray();
		//display the new board
		drawBoard(currentGame.getBoard());
		//mark the tile 0,0 of knight as visited
		currentGame.getBoard()[Constants.INITIAL_LOCATION][Constants.INITIAL_LOCATION].setIsVisited(true);
		
	}
	
	//game over once the queen/king catches the knight, or a level ended with less than 15 points
	void gameOver(String reason) throws IOException{
		time.stop();
		gameEndedFlag = true;
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.WrongAnswer);			
		}
		sd.addGameToHistory();
		SysData.alert("Game Over", reason, AlertType.WARNING);
		returnToHomePage(null);
	}
	
	//once a player ends level 4 with over 15 points
	void gameWon() throws IOException{
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.CorrectAnswer);			
		}
		sd.addGameToHistory();
		if(currentGame.getScore() >= Constants.TROPHY)
			SysData.alert("Winner!", "You got over 200 points and won a trophy", AlertType.INFORMATION);
		else
			SysData.alert("Winner!", "Congrats, you won the game", AlertType.INFORMATION);
		returnToHomePage(null);
	}
	
	

	/*
	 * exit the game
	 */
	@FXML
	void returnToHomePage(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}

		time.stop();
		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Knight's Move");
		primaryStage.show();
	}
	
	void startANewGame(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}

		Parent newRoot = FXMLLoader.load(getClass().getResource("/View/InsertNickname.fxml"));
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.setTitle("Insert Nickname");
		primaryStage.show();
    }


	/*
	 * event listener that update the score
	 */
   @FXML
    void changeScoreOnScreen(MouseEvent event) {
		pointsLabel.setText("Points: " + currentGame.getScore());
    }
   
   
   //change screen mode to Christmas
   @FXML
	void christmasMode(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}
	   
	   if(currentGame.getMode()!=Mode.Christmas) {
		   currentGame.setMode(Mode.Christmas);
		   background.setImage(new Image("/Assets/christmas/christmas_bg.png"));
		   drawBoard(currentGame.getBoard());   
	   }
	   else 
		   defaultMode();
	}
   
   //change screen mode to Hanukkah
   @FXML
	void hanukkahMode(ActionEvent event) throws IOException {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}

	   if(currentGame.getMode()!=Mode.Hanukkah) {
		   currentGame.setMode(Mode.Hanukkah);
		   background.setImage(new Image("/Assets/hanukkah/hanukkah_bg.png"));
		   drawBoard(currentGame.getBoard());   
	   }
	   else 
		   defaultMode();
	}
   
   //change screen mode to default
   void defaultMode() {
		if (sd.isSoundFlag()) {
			sd.playSound(Sound.Menu);			
		}

	   currentGame.setMode(Mode.Default);
	   background.setImage(new Image("/Assets/screens/blank.png"));
	   drawBoard(currentGame.getBoard()); 
   }

}
