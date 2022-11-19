package Model;

public class King extends Piece{

	/*
	 * King speed is increasing every 10 seconds
	 */
	private double speed;

	public King(int row, int col, Square[][] possibleMoves) 
	{
		super(row, col, possibleMoves);

	}

	@Override
	public Square[][] move(Square[][] board, int currentRow, int currentCol) 
	{

		//King can move only one step. So all the adjacent 8 cells have been considered.
		Square[][] possibleMoves = new Square[8][8];
		int possibleNewRow[]={currentRow,currentRow,currentRow+1,currentRow+1,currentRow+1,currentRow-1,currentRow-1,currentRow-1};
		int possibleNewCol[]={currentCol-1,currentCol+1,currentCol-1,currentCol,currentCol+1,currentCol-1,currentCol,currentCol+1};
		for(int i=0;i<8;i++)
			if((possibleNewRow[i] >= 0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i] <8))
			{
				// king can't step on blocked Square or on a Square where the Queen is right now
				if(!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("blocked") && (!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("queen")))
				{
					possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setSquareType("possible"); 
				}
			}			
		return possibleMoves;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}


}
