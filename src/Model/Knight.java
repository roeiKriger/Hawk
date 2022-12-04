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
		int numberOfPossibleSquares = Constants.SQUARES_DEFAULT;

		// when the level is 2 then knight moves differently, due to game rules.
		if(level == 2)
		{
			numberOfPossibleSquares = Constants.SQUARES_LEVEL_TWO;
			possibleNewRow = possibleRowMovesLevelTwo();
			possibleNewCol = possibleColMovesLevelTwo();
		}
		// in other levels the knight moves regularly 
		else
		{
			possibleNewRow = possibleRowMovesLevelDefault();
			possibleNewCol = possibleColMovesLevelDefault();
		}

		possibleNewRow = minusTurnsToPlusLocation(possibleNewRow);
		possibleNewCol = minusTurnsToPlusLocation(possibleNewCol);
		
		// now we have two arrays, each of them has locations of the placements, first array is the Row and the second is the Column, now we will check if the place is empty
		for(int i=0;i<numberOfPossibleSquares;i++) {
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
	public int[] possibleColMovesLevelDefault() {
		int currentCol = getCol();
		int possibleNewCol[] = {currentCol-2,currentCol-2,currentCol-1,currentCol-1,currentCol+1,
				currentCol+1,currentCol+2,currentCol+2};
		return possibleNewCol;
	}

	//possible row moves according to level 2 rules
	//two squares straight then one diagonal or two squares diagonal then one straight
	public int[] possibleRowMovesLevelTwo() {
		int currentRow = getRow();
		int possibleNewRow[] = {currentRow-2,currentRow-1,currentRow+1,currentRow+2,currentRow-3,currentRow+3,
				currentRow-3,currentRow+3,currentRow-3,currentRow+3,currentRow-3,currentRow+3,
				currentRow-2,currentRow-1,currentRow+1,currentRow+2};
	
		return possibleNewRow;
	}
	
	//possible column moves according to level 2 rules
	//two squares straight then one diagonal or two squares diagonal then one straight
	public int[] possibleColMovesLevelTwo() {		
		int currentCol = getCol();
		int possibleNewCol[] = {currentCol-3,currentCol-3,currentCol-3,currentCol-3,currentCol-2,currentCol-2,
				currentCol-1,currentCol-1,currentCol+1,currentCol+1,currentCol+2,currentCol+2,
				currentCol+3,currentCol+3,currentCol+3,currentCol+3};
		
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
