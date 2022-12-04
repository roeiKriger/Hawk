package Model;

/*
 * This is an abstract class which King, Queen and Knight will inherit from
 */

public abstract class Piece {
	
	/**
	 * integer which indicates the player row location
	 */
	private int row;
	
	/**
	 * integer which indicates the player column location
	 */
	private int col;
	


	
	public Piece(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Getters and Setters
	 */
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}



	/**
	 * Move function returns possible moves according to each of the pieces movement rules
	 */
	public abstract Square[][] move(int level);
	
	
	
	@Override
	public String toString() {
		return "Piece [row=" + row + ", col=" + col + "]";
	}
	
	
}