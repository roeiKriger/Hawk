package Model;

public class King extends Piece{

	/*
	 * King speed is increasing every 10 seconds
	 */
	private double speed;

	public King(int row, int col) 
	{
		super(row, col);

	}

	public Square[][] move(Square[][] board) 
	{
		//King can move only one step each turn. So all the possible 8 Squares have been considered
		Square[][] possibleMoves = new Square[8][8];
		int possibleNewRow[]={this.getRow(),this.getRow(),this.getRow()+1,this.getRow()+1,this.getRow()+1,this.getRow()-1,this.getRow()-1,this.getRow()-1};
		int possibleNewCol[]={this.getCol()-1,this.getCol()+1,this.getCol()-1,this.getCol(),this.getCol()+1,this.getCol()-1,this.getCol(),this.getCol()+1};
		for(int i=0;i<8;i++){
			possibleMoves[possibleNewRow[i]][possibleNewCol[i]] = new Square("noColor", false);
			if((possibleNewRow[i] >= 0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i] <8))
			{
				// king can't step on blocked Square
				if(!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("blocked"))
				{
					possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);
				}
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

	@Override
	public Square[][] move(Square[][] board, int currentRow, int currentCol) {
		// TODO Auto-generated method stub
		return null;
	}


}
