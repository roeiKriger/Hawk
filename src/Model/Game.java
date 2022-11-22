package Model;

import java.util.Date;
import java.util.Random;
import java.util.Timer;

import javafx.animation.Timeline;

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
	
	private int knightRowStarts = 0;
	private int knightColStarts = 0;
	
	private int queenRowStarts = 0;
	private int queenColStarts = 7;
	
	private int kingRowStarts = 0;
	private int kingColStarts = 7;
	
	// Constructor
	public Game(String nickname, Date date) {
		this.board = new Square[8][8];
		this.score = 0;
		this.gameLevel = 1;
		this.nickname = nickname;
		this.date = date;
	}

	// Constructor
	public Game(String nickname) {
		this.board = new Square[8][8];
		this.score = 0;
		this.gameLevel = 1;
		this.nickname = nickname;
		this.date = new Date(System.currentTimeMillis());
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


	public void setScore(int score) {
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
	
	public void createBoardLevelOne()
	{
		knight = new Knight(kingRowStarts, kingColStarts);
		queen = new Queen(queenRowStarts, queenColStarts);
		this.board = createEmptyBoard(this.board);
		int numOfRandSquares = 0;

		// knight starts always at the same place
		//this.board[knightRowStarts][knightColStarts].setSquareType("knight");
		
		// queen always starts at the same place
		//this.board[queenRowStarts][queenColStarts].setSquareType("queen");
		
		// at level 1 we create at the start 3 Squares which are for random squares and 3 for questions
		while(numOfRandSquares < 3)
		{
			this.board = createNewSquare(this.board, "randomSquare");
			this.board = createNewSquare(this.board, "question");
			numOfRandSquares++;
		}		
		
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
	
		while(isDone == false)
		{
			if(board[randRow][randCol].getSquareType().equals("empty") && (randRow!=0 && randCol!=0))
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
	

}
