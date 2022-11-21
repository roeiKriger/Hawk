package Model;

public class Queen extends Piece{

	public Queen(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Move method, the queen can moves in
	 */

	public Square[][] move(int currentRow, int currentCol) 
	{
		Square[][] possibleMoves = new Square[8][8];

		// possible moves in vertical direction
		int newRow = currentRow-1;
		while(newRow >= 0)
		{
			possibleMoves[newRow][currentCol].setSquareType("possible");
			newRow--;
		}

		newRow = currentRow+1;
		while(newRow < 8)
		{
			possibleMoves[newRow][currentCol].setSquareType("possible");
			newRow++;
		}

		// possible moves in horizontal direction
		int newCol = currentCol-1;
		while(newCol >= 0)
		{
				possibleMoves[currentRow][newCol].setSquareType("possible");
				newCol--;
		}
		
		newCol = currentCol+1;
		while(newCol < 8)
		{
			possibleMoves[currentRow][newCol].setSquareType("possible");
			newCol++;
		}
		
		// possible moves in diagonal direction 
		
		newRow = currentRow+1;
		newCol = currentCol-1;
		while(newRow < 8 && newCol >= 0)
		{
			possibleMoves[newRow][newCol].setSquareType("possible");
			newRow++;
			newCol--;
		}
		newRow = currentRow-1;
		newCol = currentCol+1;
		while(newRow >= 0 && newCol < 8)
		{
			possibleMoves[newRow][newCol].setSquareType("possible");
			newRow--;
			newCol++;
		}
		
		// possible moves in other diagonal direction
		
		newRow = currentRow-1;
		newCol = currentCol-1;
		while(newRow >= 0 && newCol >= 0)
		{
			possibleMoves[newRow][newCol].setSquareType("possible");
			newRow--;
			newCol--;
		}
		newRow = currentRow+1;
		newCol = currentCol+1;
		while(newRow < 8 && newCol < 8)
		{
			possibleMoves[newRow][newCol].setSquareType("possible");
			newRow++;
			newCol++;
		}
		
		return possibleMoves;
	}


	@Override
	public Square[][] move(Square[][] board, int currentRow, int currentCol) {
		return null;
	}



}
