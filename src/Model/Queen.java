package Model;

public class Queen extends Piece{

	public Queen(int row, int col) {
		super(row, col);
	}


	//possible row moves according to queen rules in horizontal and vertical direction
	@Override
	int[] calculateRows(int level) {
		int currRow = this.getRow();
		int possibleNewRow[]={currRow+1,currRow+2,currRow+3,currRow+4,currRow+5,currRow+6,currRow+7,
				currRow,currRow,currRow,currRow,currRow,currRow,currRow};
		return possibleNewRow;	
	}

	//possible column moves according to queen rules in horizontal and vertical direction
	@Override
	int[] calculateCols(int level) {
		int currCol = this.getCol();
		int possibleNewCol[]={currCol,currCol,currCol,currCol,currCol,currCol,currCol,
				currCol+1,currCol+2,currCol+3,currCol+4,currCol+5,currCol+6,currCol+7};
		return possibleNewCol;
	}

	
	//create a set of all possible squares the queen can step on
	@Override
	Square[][] pairAllPossibleMoves(int[] possibleNewRow, int[] possibleNewCol) {
		Square[][] possibleMoves = new Square[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0; j<8; j++) {
				possibleMoves[i][j] = new Square("noColor", false);
			}
		}
		
		//fix indexes that are out of bound
		possibleNewRow = minusTurnsToPlusLocation(possibleNewRow);
		possibleNewCol = minusTurnsToPlusLocation(possibleNewCol);

		//now we have two arrays, each of them has locations of the placements, first array
		//is the Row and the second is the Column, we will match them to create a list of all possible squares
		for(int i=0;i<14;i++){
			if((possibleNewRow[i] >= 0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i] <8))
				possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);			
		}	
		
		
		// add possible moves in diagonal direction 
		
		int newRow = this.getRow()+1;
		int newCol = this.getCol()-1;
		while(newRow < 8 && newCol >= 0){
			possibleMoves[newRow][newCol].setCanVisit(true);
			newRow++;
			newCol--;
		}
		newRow = this.getRow()-1;
		newCol = this.getCol()+1;
		while(newRow >= 0 && newCol < 8){
			possibleMoves[newRow][newCol].setCanVisit(true);
			newRow--;
			newCol++;
		}
		
		
		//add possible moves in other diagonal direction
		
		newRow = this.getRow()-1;
		newCol = this.getCol()-1;
		while(newRow >= 0 && newCol >= 0){
			possibleMoves[newRow][newCol].setCanVisit(true);
			newRow--;
			newCol--;
		}
		newRow = this.getRow()+1;
		newCol = this.getCol()+1;
		while(newRow < 8 && newCol < 8){
			possibleMoves[newRow][newCol].setCanVisit(true);
			newRow++;
			newCol++;
		}

		return possibleMoves;	
	}
	
}
