package Model;

public class Constants {

	public static final int TILE_SIZE = 65; // in px
	public static final int GAME_PIECES_SIZE = 40; // in px
	
	public static final int ROUND_TIME = 60; // in seconds	
	
	public static final int LONGEST_DISTANCE_BETWEEN_TWO_PIECES = 10;
	public static final int INITIAL_LOCATION = 0;
	public static final int POINT = 1;
	public static final int MIN_SCORE_TO_WIN_LEVEL = 15;
	public static final int TROPHY = 200;
	public static final int MAX_GAMES_FOR_FORGETTING_SQUARE = 3;
	
	
    //score adding after answer a question
	public static final int SUCSSED_EASY = 1;
	public static final int SUCSSED_MIDDLE = 2;
	public static final int SUCSSED_HARD = 3;
	public static final int WORNG_EASY = -2;
	public static final int WORNG_MIDDLE = -3;
	public static final int WORNG_HARD = -4;
	
	//change king speed at the x seconds mark
	public static final int FIRST_SPEED = 59;
	public static final int SECOND_SPEED = 49;
	public static final int THIRD_SPEED = 39;
	public static final int FOURTH_SPEED = 29;
	public static final int FIFTH_SPEED = 19;
	public static final int SIXTH_SPEED = 9;
	
	//number of knight possible squares
	public static final int SQUARES_DEFAULT = 8;
	public static final int SQUARES_LEVEL_TWO = 16;
	
	//levels
	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;
	public static final int LEVEL_FOUR = 4;
	

}
