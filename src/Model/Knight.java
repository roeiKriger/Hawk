package Model;

public class Knight extends Piece{

	public Knight(int row, int col) 
	{
		super(row, col);	
	}
	
	


	@Override
	public String toString() {
		return "row: " + getRow() + ", col: " + getCol();
	}




	//possible row moves according to knight rules
	@Override
	int[] calculateRows(int level) {
		int currentRow = getRow();
		int possibleNewRow[];
		
		//possible row moves according to chess regular rules
		if(level==1) {
			possibleNewRow = new int[] {currentRow-1,currentRow+1,currentRow-2,currentRow+2,currentRow-2,
					currentRow+2,currentRow-1,currentRow+1};	
		}
		//possible row moves according to level 2 rules
		//two squares straight then one diagonal or two squares diagonal then one straight
		else {
			possibleNewRow = new int[] {currentRow-2,currentRow-1,currentRow+1,currentRow+2,currentRow-3,currentRow+3,
					currentRow-3,currentRow+3,currentRow-3,currentRow+3,currentRow-3,currentRow+3,
					currentRow-2,currentRow-1,currentRow+1,currentRow+2};
		}		
			
		return possibleNewRow;

	}

	//possible column moves according to knight rules
	@Override
	int[] calculateCols(int level) {
		int currentCol = getCol();
		int possibleNewCol[];
		
		//possible column moves according to chess regular rules 
		if(level==1) {
			possibleNewCol = new int[] {currentCol-2,currentCol-2,currentCol-1,currentCol-1,currentCol+1,
					currentCol+1,currentCol+2,currentCol+2};
		}
		//possible column moves according to level 2/3/4 rules
		//two squares straight then one diagonal or two squares diagonal then one straight
		else {
			possibleNewCol = new int[] {currentCol-3,currentCol-3,currentCol-3,currentCol-3,currentCol-2,currentCol-2,
					currentCol-1,currentCol-1,currentCol+1,currentCol+1,currentCol+2,currentCol+2,
					currentCol+3,currentCol+3,currentCol+3,currentCol+3};
		}
		
		return possibleNewCol;
	}
	
	
	//create a set of all possible squares the knight can step on
	@Override
	Square[][] pairAllPossibleMoves(int[] possibleNewRow, int[] possibleNewCol) {
		// initiate variables
		Square[][] possibleMoves = new Square[8][8];
		int numberOfPossibleSquares;
		
		if (possibleNewRow.length == Constants.SQUARES_LEVEL_TWO)
			numberOfPossibleSquares = Constants.SQUARES_LEVEL_TWO;
		else 
			numberOfPossibleSquares = Constants.SQUARES_DEFAULT;
	
		//fix indexes that are out of bound
		possibleNewRow = minusTurnsToPlusLocation(possibleNewRow);
		possibleNewCol = minusTurnsToPlusLocation(possibleNewCol);
		
		//now we have two arrays, each of them has locations of the placements, first array
		//is the Row and the second is the Column, we will match them to create a list of all possible squares
		for(int i=0;i<numberOfPossibleSquares;i++) {
			possibleMoves[possibleNewRow[i]][possibleNewCol[i]] = new Square("noColor", false);
			if((possibleNewRow[i] >=0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i]<8))
				possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);	
		}
		
		return possibleMoves;
		
	}
	
}
