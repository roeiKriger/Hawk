package Model;

public class Knight extends Piece{

	public Knight(int row, int col) 
	{
		super(row, col);
	
	}

	/*
	 * Method will calculate the possible moves for knight, per given level.
	 * The method will return a board of possible moves, which the type of the possible squares will be "possible".
	 */

	@Override
	public Square[][] move(int level) {
		// initiate variables
		Square[][] possibleMoves = new Square[8][8];
		int possibleNewRow[] = new int[8];
		int possibleNewCol[] = new int[8];

		// when the level is 2 then knight moves differently, due to game rules.
		if(level == 2)
		{
			possibleNewRow = possibleRowMovesLevelTwo(possibleNewRow, this.getRow());
			possibleNewCol = possibleColMovesTwo(possibleNewCol, this.getCol());
		}
		// in other levels the knight moves regularly 
		else
		{
			possibleNewRow = possibleRowMovesLevelDefault();
			possibleNewCol = possibleColMovesDefault();
		}

		possibleNewRow = minusTurnsToPlusLocation(possibleNewRow);
		possibleNewCol = minusTurnsToPlusLocation(possibleNewCol);
		
		// now we have two arrays, each of them has locations of the placements, first array is the Row and the second is the Column, now we will check if the place is empty
		for(int i=0;i<8;i++) {
			possibleMoves[possibleNewRow[i]][possibleNewCol[i]] = new Square("noColor", false);
			if((possibleNewRow[i] >=0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i]<8))
				possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);	
		}
		
		return possibleMoves;

	}

	// possible row moves according to chess regular rules
	public int[] possibleRowMovesLevelDefault() {
		int currentRow = getRow();
		int possibleNewRow[] = {currentRow-1,currentRow+1,currentRow-2,currentRow+2,currentRow-2,
				currentRow+2,currentRow-1,currentRow+1};
		return possibleNewRow;
	}
	
	// possible column moves according to chess regular rules
	public int[] possibleColMovesDefault() {
		int currentCol = getCol();
		int possibleNewCol[] = {currentCol-2,currentCol-2,currentCol-1,currentCol-1,currentCol+1,
				currentCol+1,currentCol+2,currentCol+2};
		return possibleNewCol;
	}

	// possible row moves according to level 2 rules
	public int[] possibleRowMovesLevelTwo(int possibleNewRow[], int currentRow)
	{
		// Two rows straight then one diagonal (one diagonal is also an extra row)
		possibleNewRow[0]= currentRow+3;
		possibleNewRow[1]= currentRow+3;

		// Two rows back then one diagonal (one diagonal is also an extra row)
		possibleNewRow[2]= currentRow-3;
		possibleNewRow[3]= currentRow-3;

		// Two Diagonal forward and one straight
		possibleNewRow[4]= currentRow+3;
		possibleNewRow[5]= currentRow+3;

		// Two Diagonal back and one straight
		possibleNewRow[6]= currentRow-3;
		possibleNewRow[7]= currentRow-3;

		return possibleNewRow;
	}
	
	// possible column moves according to level 2 rules
	public int[] possibleColMovesTwo(int possibleNewCol[], int currentCol)
	{
		// Two rows straight then one diagonal (one diagonal is also an extra row)
		possibleNewCol[0]= currentCol-1;
		possibleNewCol[1]= currentCol+1;

		// Two rows back then one diagonal (one diagonal is also an extra row)
		possibleNewCol[2]= currentCol-1;
		possibleNewCol[3]= currentCol+1;

		// Two Diagonal forward and one straight
		possibleNewCol[4]= currentCol-2;
		possibleNewCol[5]= currentCol+2;

		possibleNewCol[6]= currentCol+2;
		possibleNewCol[7]= currentCol-2;

		return possibleNewCol;
	}

	// method in case the knight wants to go from one side of the board to the other
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

}
