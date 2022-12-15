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
	
	abstract int[] calculateRows(int level);
	abstract int[] calculateCols(int level);
	abstract Square[][] pairAllPossibleMoves(int[] possibleNewRow, int[] possibleNewCol);
	
	
	
	/* move function returns possible moves according to each of the pieces movement rules
	* This is a template method, based on the design
	* pattern we learned in practice 5 -> the template pattern 
	*/
	public final Square[][] move(int level){
		return pairAllPossibleMoves(calculateRows(level), calculateCols(level));	 
	}
	
	
	// method in case the piece needs to go from one side of the board to the other
	public int[] minusTurnsToPlusLocation(int possibleLoc[])
	{
		for(int i=0; i<possibleLoc.length; i++)
		{
			if(possibleLoc[i] < 0)
			{
				possibleLoc[i] = 8 + possibleLoc[i];
			}
			if (possibleLoc[i] > 7) 
			{
				possibleLoc[i] = possibleLoc[i] - 8;
			}
		}
		
		return possibleLoc;
	}
	
	@Override
	public String toString() {
		return "Piece [row=" + row + ", col=" + col + "]";
	}
	
	
}