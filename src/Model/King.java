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


	//possible row moves according to king rules
	@Override
	int[] calculateRows(int level) {	
		int currRow = this.getRow();
		int possibleNewRow[]={currRow-1,currRow-1,currRow-1,currRow+1,currRow+1,currRow+1,currRow,currRow};
		return possibleNewRow;
	}
	
	//possible column moves according to king rules
	@Override
	int[] calculateCols(int level) {
		int currCol = this.getCol();
		int possibleNewCol[]={currCol-1,currCol,currCol+1,currCol-1,currCol,currCol+1,currCol-1,currCol+1};
		return possibleNewCol;
	}
	
	
	//create a set of all possible squares the king can step on
	@Override
	Square[][] pairAllPossibleMoves(int[] possibleNewRow, int[] possibleNewCol){
		Square[][] possibleMoves = new Square[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0; j<8; j++) {
				possibleMoves[i][j] = new Square();
			}
		}
		//now we have two arrays, each of them has locations of the placements, first array
		//is the Row and the second is the Column, we will match them to create a list of all possible squares
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
