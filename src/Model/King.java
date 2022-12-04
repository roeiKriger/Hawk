package Model;

import java.awt.Frame;

public class King extends Piece{

	/*
	 * King speed is increasing every 10 seconds
	 */
	private int speed;

	public King(int row, int col) 
	{
		super(row, col);
		this.speed = 2;

	}

	@Override
	public Square[][] move(int level) {
		Square[][] possibleMoves = new Square[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0; j<8; j++) {
				possibleMoves[i][j] = new Square("noColor", false);
			}
		}
		int currRow = this.getRow();
		int currCol = this.getCol();
		
		//King can move only one step each turn. So all the possible 8 Squares have been considered
		int possibleNewRow[]={currRow-1,currRow-1,currRow-1,currRow+1,currRow+1,currRow+1,currRow,currRow};
		int possibleNewCol[]={currCol-1,currCol,currCol+1,currCol-1,currCol,currCol+1,currCol-1,currCol+1};
		for(int i=0;i<8;i++){
			if((possibleNewRow[i] >= 0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i] <8))
				possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);			
		}	
		
		return possibleMoves;
	}

	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


}
