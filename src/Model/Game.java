package Model;

import java.util.List;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

import org.omg.CORBA.PUBLIC_MEMBER;

import Control.SysData;
import Exceptions.EmptyNickNameException;
import Utils.Mode;
import javafx.animation.Timeline;
import javafx.scene.control.Alert.AlertType;

public class Game {

	/*
	 * Board made of Squares
	 */
	private Square[][] board;

	/*
	 * The score of the player at the moment
	 */
	private int score;

	/*
	 * The timer of the game
	 */
	private Timeline timer;

	/*
	 * The level of the game at the moment
	 */
	private int gameLevel;

	/*
	 * The player nickname
	 */
	private String nickname;

	/*
	 * Date of the game for game history
	 */
	private Date date;

	//game pieces
	private Knight knight;
	private Queen queen;
	private King king;
	
	private Utils.Mode mode;

	private int knightRowStarts = 0;
	private int knightColStarts = 0;

	private int queenRowStarts = 0;
	private int queenColStarts = 7;

	private int kingRowStarts = 0;
	private int kingColStarts = 7;
	
	//for factory pattern
	PieceFactory p = new PieceFactory();

	// Constructor
	public Game(String nickname, Date date) {
		this.board = new Square[8][8];
		this.score = 0;
		this.gameLevel = 1;
		this.nickname = nickname;
		this.date = date;
		this.mode = Mode.Default;
	}

	// Constructor
	public Game(String nickname) {
		this.board = new Square[8][8];
		this.score = 0;
		this.gameLevel = 1;
		this.nickname = nickname;

		this.date = new Date(System.currentTimeMillis());
		this.mode = Mode.Default;
	}
	
	// Constructor for Json Score
	public Game(String nickname, int score, Date date) {
		//this.board = new Square[8][8];
		this.score = score;
		//this.gameLevel = 1;
		this.nickname = nickname;

		this.date = date;
		//this.mode = Mode.Default;
	}

	public Game(Square[][] board, int score, Timeline timer, int gameLevel, String nickname, Date date, Knight knight,
			Queen queen, King king) {
		super();
		this.board = board;
		this.score = score;
		this.timer = timer;
		this.gameLevel = gameLevel;
		this.nickname = nickname;
		this.date = date;
		this.knight = knight;
		this.queen = queen;
		this.king = king;
		this.mode = Mode.Default;
	}

	/*
	 * Getters and Setters
	 */

	public Square[][] getBoard() {
		return board;
	}


	public void setBoard(Square[][] board) {
		this.board = board;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) 
	{
		this.score = score;
	}


	public Timeline getTimer() {
		return timer;
	}


	public void setTimer(Timeline timer) {
		this.timer = timer;
	}


	public int getGameLevel() {
		return gameLevel;
	}


	public void setGameLevel(int gameLevel) {
		if(gameLevel <= 4 && gameLevel > 0)
		{
			this.gameLevel = gameLevel;	
		}
		else 
		{
			this.gameLevel = 1;
		}
	}

	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Knight getKnight() {
		return knight;
	}

	public void setKnight(Knight knight) 
	{
		this.knight = knight;
	}

	public Queen getQueen() {
		return queen;
	}

	public void setQueen(Queen queen) {
		this.queen = queen;
	}

	public King getKing() {
		return king;
	}

	public void setKing(King king) {
		this.king = king;
	}

	public Utils.Mode getMode() {
		return mode;
	}

	public void setMode(Utils.Mode mode) {
		this.mode = mode;
	}

	public void createBoardLevelOne()
	{
		/*
		 * adding for DP Factory
		 */
		this.knight = (Knight) p.getPiece("KNIGHT", knightRowStarts, knightColStarts);
		this.queen =(Queen) p.getPiece("QUEEN",queenRowStarts, queenColStarts);
		
		this.board = createEmptyBoard(this.board);
		int numOfSquares = 0;

		// at level 1 we create at the start 3 Squares which are for random squares and 3 for questions
		while(numOfSquares < 3)
		{		
			this.board = createNewQuestionSquare(this.board, "question", numOfSquares + 1);
			numOfSquares++;
		}		

	}

	public void createBoardLevelTwo()
	{
		/*
		 * adding for DP Factory
		 */
		this.knight = (Knight) p.getPiece("KNIGHT", knightRowStarts, knightColStarts);
		this.queen =(Queen) p.getPiece("QUEEN",queenRowStarts, queenColStarts);

		this.board = createEmptyBoard(this.board);
		this.gameLevel = Constants.LEVEL_TWO;
		int numOfSquares = 0;

		// at level 1 we create at the start 3 Squares which are for forgetting and 3 for questions
		while(numOfSquares < 3)
		{
			this.board = createNewSquare(this.board, "forgetting");
			this.board = createNewQuestionSquare(this.board, "question", numOfSquares + 1);
			numOfSquares++;
		}		

	}

	public void createBoardLevelThree()
	{
		/*
		 * adding for DP Factory
		 */
		this.knight = (Knight) p.getPiece("KNIGHT", knightRowStarts, knightColStarts);
		this.king =(King) p.getPiece("KING",kingRowStarts, kingColStarts);
		
		this.board = createEmptyBoard(this.board);
		this.gameLevel = Constants.LEVEL_THREE;
		int numOfSquares = 0;

		// at level 1 we create at the start 2 Squares which are for random squares and 3 for questions
		while(numOfSquares < 2)
		{
			this.board = createNewSquare(this.board, "forgetting");
			this.board = createNewSquare(this.board, "randomSquare");
			this.board = createNewQuestionSquare(this.board, "question", numOfSquares + 1);
			numOfSquares++;
		}
		// we need three questions not only two, so extra question here
		this.board = createNewQuestionSquare(this.board, "question", numOfSquares + 1);
		this.queen = null;

	}

	public void createBoardLevelFour()
	{
		/*
		 * adding for DP Factory
		 */
		this.knight = (Knight) p.getPiece("KNIGHT", knightRowStarts, knightColStarts);
		this.king =(King) p.getPiece("KING",kingRowStarts, kingColStarts);
		
		this.board = createEmptyBoard(this.board);
		this.gameLevel = Constants.LEVEL_FOUR;
		int numOfSquares = 0;

		// at level 1 we create at the start 2 Squares which are for random squares and 3 for questions
		while(numOfSquares < 8)
		{
			this.board = createNewSquare(this.board, "blocked");
			if(numOfSquares < 3)
			{
				this.board = createNewQuestionSquare(this.board, "question", numOfSquares + 1);
			}
			numOfSquares++;
		}
		this.queen = null;

	}


	public Square[][] createEmptyBoard(Square[][] board)
	{
		for(int i =0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.board[i][j] = new Square("no-color", false);
				this.board[i][j].setSquareType("empty");
			}
		}
		return board;
	}

	// creating new type of squares in random locations, get the board and the type we want to create.
	public Square[][] createNewSquare(Square[][] board, String type)
	{
		Random rand = new Random();
		int randRow = rand.nextInt(8);
		int randCol = rand.nextInt(8);
		Boolean isDone = false;
		Piece piece = null;

		// always one of them is not null
		if(this.king!= null)
		{
			piece = this.king;
		}
		else 
		{
			piece = this.queen;
		}

		while(isDone == false)
		{
			if(board[randRow][randCol].getSquareType().equals("empty") && (randRow!= this.knight.getRow() && randCol!= this.knight.getCol()) && (randRow!= piece.getRow() && randCol != piece.getCol()))
			{
				board[randRow][randCol].setSquareType(type);			
				isDone = true;
			}
			else 
			{
				randRow = rand.nextInt(8);
				randCol = rand.nextInt(8);
			}
		}

		return board;
	}

	// creating new type of squares in random locations, get the board and the type we want to create.
	public Square[][] createNewQuestionSquare(Square[][] board, String type, int level)
	{
		Random rand = new Random();
		int randRow = rand.nextInt(8);
		int randCol = rand.nextInt(8);
		Boolean isDone = false;
		Piece piece = null;

		// always one of them is not null
		if(this.king!= null)
		{
			piece = this.king;
		}
		else 
		{
			piece = this.queen;
		}

		while(isDone == false)
		{
			if(board[randRow][randCol].getSquareType().equals("empty") && (randRow!= this.knight.getRow() && randCol!= this.knight.getCol()) && (randRow!= piece.getRow() && randCol != piece.getCol()))
			{
				board[randRow][randCol].setSquareType(type);	
				board[randRow][randCol].setQuestion(getOneQuestionByLevel(level));
				isDone = true;
			}
			else 
			{
				randRow = rand.nextInt(8);
				randCol = rand.nextInt(8);
			}
		}

		return board;
	}

	// The method gets a List of Questions (doesn't matter the difficulty and returns a random question from the list), needs to know what level in order to get it from SysData
	public Question getOneQuestionByLevel(int level)
	{
		List<Question> questions = null;
		// in Question 
		if(level == 1)
		{
			questions = SysData.getInstance().getQuestionsLevel1();
		}
		else if(level == 2)
		{
			questions = SysData.getInstance().getQuestionsLevel2();
		}
		else 
		{
			questions = SysData.getInstance().getQuestionsLevel3();
		}

		Random rand = new Random();
		int length = questions.size();
		int randQuestionIndex = rand.nextInt(length);
		Question question = questions.get(randQuestionIndex);
		return question;
	}
	
	public boolean checkIfSteppedOnRandomSquare()
	{
		//check if knight stepped on Random Square
		if(this.board[this.knight.getRow()][this.knight.getCol()].getSquareType().equals("randomSquare"))
		{
			// deleting the randomized Square from the board
			this.board[this.knight.getRow()][this.knight.getCol()].setSquareType("empty");
			
			//change the location of the knight
			changeKnightLocationRandomized();
			
			//create a new random square instead
			this.board = createNewSquare(this.board, "randomSquare");
			return true;
		}
		return false;
	}
	
	public void changeKnightLocationRandomized() 
	{
		Random rand = new Random();
		int randRow = rand.nextInt(8);
		int randCol = rand.nextInt(8);
		Boolean isDone = false;
		Piece piece = null;

		// always one of them is not null
		if(this.king!= null)
		{
			piece = this.king;
		}
		else 
		{
			piece = this.queen;
		}
	
		while(isDone == false)
		{
			if(board[randRow][randCol].getSquareType().equals("empty") && (randRow!= this.knight.getRow() && randCol!= this.knight.getCol()) && (randRow!= piece.getRow() && randCol != piece.getCol()))
			{
				this.knight.setRow(randRow);
				this.knight.setCol(randCol);
				isDone = true;
			}
			else 
			{
				randRow = rand.nextInt(8);
				randCol = rand.nextInt(8);
			}
		}
		
		
	}

	public Boolean checkIfSteppedOnforgettingSquare()
	{
		//check if knight stepped on Random Square
		if(this.board[this.knight.getRow()][this.knight.getCol()].getSquareType().equals("forgetting"))
		{
			// deleting the randomized Square from the board
			this.board[this.knight.getRow()][this.knight.getCol()].setSquareType("empty");
			
			//create a new forgetting square instead
			this.board = createNewSquare(this.board, "forgetting");
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Game [score=" + score + ", nickname=" + nickname + ", date=" + date + "]";
	}





}
