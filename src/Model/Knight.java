package Model;

public class Knight extends Piece{

	public Knight(int row, int col, Square[][] possiblemoves) 
	{
		super(row, col, possiblemoves);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Method will calculate the possible moves for knight, per given level.
	 * The method will return a board of possible moves, which the type of the possible squares will be "possible".
	 */

	public Square[][] move(Square[][] board, int newRow, int newCol, int level) 
	{
		// initiate variables.
		Square[][] possibleMoves = new Square[8][8];
		int possibleNewRow[] = new int[8];
		int possibleNewCol[] = new int[8];

		// when the level is 2 then knight moves differently, due to game rules.
		if(level == 2)
		{
			possibleNewRow = possibleRowMovesLevelTwo(possibleNewRow, newRow);
			possibleNewCol = possibleColMovesTwo(possibleNewCol, newCol);
		}
		// in other levels the knight moves regularly 
		else
		{
			possibleNewRow = possibleRowMovesLevelDefault(possibleNewRow, newRow);
			possibleNewCol = possibleColMovesDefault(possibleNewCol, newCol);
		}

		
		for(int i=0;i<8;i++)
			if((possibleNewRow[i] >=0 && possibleNewRow[i] <8 && possibleNewCol[i] >=0 && possibleNewCol[i]<8))
			{
				// if the Square is not already used by the king or the queen we would like to allow the knight to move to there, if he would choose that
				if(!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("king") && (!board[possibleNewRow[i]][possibleNewCol[i]].getSquareType().equals("queen")))
				{
					possibleMoves[possibleNewRow[i]][possibleNewCol[i]].setSquareType("possible"); 
				}
			}
		return possibleMoves;

	}

	@Override
	public Square[][] move(Square[][] board, int newRow, int newCol) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	// possible row moves according to chess regular rules
	public int[] possibleRowMovesLevelDefault(int possibleNewRow[], int newRow)
	{
		possibleNewRow[0]= newRow+1;
		possibleNewRow[1]= newRow+1;
		possibleNewRow[2]= newRow+2;
		possibleNewRow[3]= newRow+2;
		possibleNewRow[4]= newRow-1;
		possibleNewRow[5]= newRow-1;
		possibleNewRow[6]= newRow-2;
		possibleNewRow[7]= newRow-2;
		return possibleNewRow;
	}
	
	// possible column moves according to chess regular rules
	public int[] possibleColMovesDefault(int possibleNewCol[], int newCol)
	{
		possibleNewCol[0]= newCol-2;
		possibleNewCol[1]= newCol+2;
		possibleNewCol[2]= newCol-1;
		possibleNewCol[3]= newCol+1;
		possibleNewCol[4]= newCol-2;
		possibleNewCol[5]= newCol+2;
		possibleNewCol[6]= newCol-1;
		possibleNewCol[7]= newCol+1;
		return possibleNewCol;
	}

	// possible row moves according to level 2 rules
	public int[] possibleRowMovesLevelTwo(int possibleNewRow[], int newRow)
	{
		// Two rows straight then one diagonal (one diagonal is also an extra row)
		possibleNewRow[0]= newRow+3;
		possibleNewRow[1]= newRow+3;

		// Two rows back then one diagonal (one diagonal is also an extra row)
		possibleNewRow[2]= newRow-3;
		possibleNewRow[3]= newRow-3;

		// Two Diagonal forward and one straight
		possibleNewRow[4]= newRow+3;
		possibleNewRow[5]= newRow+3;

		// Two Diagonal back and one straight
		possibleNewRow[6]= newRow-3;
		possibleNewRow[7]= newRow-3;

		return possibleNewRow;
	}
	
	// possible column moves according to level 2 rules
	public int[] possibleColMovesTwo(int possibleNewCol[], int newCol)
	{
		// Two rows straight then one diagonal (one diagonal is also an extra row)
		possibleNewCol[0]= newCol-1;
		possibleNewCol[1]= newCol+1;

		// Two rows back then one diagonal (one diagonal is also an extra row)
		possibleNewCol[2]= newCol-1;
		possibleNewCol[3]= newCol+1;

		// Two Diagonal forward and one straight
		possibleNewCol[4]= newCol-2;
		possibleNewCol[5]= newCol+2;

		possibleNewCol[6]= newCol+2;
		possibleNewCol[7]= newCol-2;

		return possibleNewCol;
	}


}
