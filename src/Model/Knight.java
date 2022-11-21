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

	public Square[][] move(Square[][] board, int level, King king, Queen queen) 
	{
		// initiate variables.
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
			possibleNewRow = possibleRowMovesLevelDefault(possibleNewRow, this.getRow());
			possibleNewCol = possibleColMovesDefault(possibleNewCol, this.getCol());
		}

		possibleNewRow = minusTurnsToPlusLocation(possibleNewRow);
		possibleNewCol = minusTurnsToPlusLocation(possibleNewCol);
		
		// now we have two arrays, each of them has locations of the placements, first array is the Row and the second is the Column, now we will check if the place is empty
		
		for(int i=0;i<8;i++)
			if((possibleNewRow[i] >=0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i]<8))
			{
				if(king != null)
				{
					if(possibleNewRow[i] != king.getRow() && possibleNewCol[i] != king.getCol() && (!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("blocked")))
					{
						possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);
					}
				}
				else 
				{
					if(possibleNewRow[i] != queen.getRow() && possibleNewCol[i] != queen.getCol() && (!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("blocked")))
					{
						possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setCanVisit(true);
					}
				}
			}
		
		return possibleMoves;

	}

	@Override
	public Square[][] move(Square[][] board, int currentRow, int currentCol) 
	{
		return null;
	}

	// possible row moves according to chess regular rules
	public int[] possibleRowMovesLevelDefault(int possibleNewRow[], int currentRow)
	{
		possibleNewRow[0]= currentRow+1;
		possibleNewRow[1]= currentRow+1;
		possibleNewRow[2]= currentRow+2;
		possibleNewRow[3]= currentRow+2;
		possibleNewRow[4]= currentRow-1;
		possibleNewRow[5]= currentRow-1;
		possibleNewRow[6]= currentRow-2;
		possibleNewRow[7]= currentRow-2;
		return possibleNewRow;
	}
	
	// possible column moves according to chess regular rules
	public int[] possibleColMovesDefault(int possibleNewCol[], int currentCol)
	{
		possibleNewCol[0]= currentCol-2;
		possibleNewCol[1]= currentCol+2;
		possibleNewCol[2]= currentCol-1;
		possibleNewCol[3]= currentCol+1;
		possibleNewCol[4]= currentCol-2;
		possibleNewCol[5]= currentCol+2;
		possibleNewCol[6]= currentCol-1;
		possibleNewCol[7]= currentCol+1;
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
