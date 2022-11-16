package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;



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
	private Timer timer;
	
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
	
	// Constructor
	public Game(Square[][] board, int score, Timer timer, int gameLevel, String nickname, Date date) {
		this.board = board;
		this.score = score;
		this.timer = timer;
		this.gameLevel = gameLevel;
		this.nickname = nickname;
		this.date = date;
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


	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public int getGameLevel() {
		return gameLevel;
	}


	public void setGameLevel(int gameLevel) {
		this.gameLevel = gameLevel;
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
	
	
	

}
