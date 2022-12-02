package Model;

public class Queen extends Piece{

	public Queen(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Move method, the queen can moves in
	 */

	@Override
	public Square[][] move(Square[][] board) 
	{
		Square[][] possibleMoves = new Square[8][8];

		// possible moves in vertical direction
		int newRow = this.getRow()-1;
		while(newRow >= 0)
		{
			possibleMoves[newRow][this.getCol()] = new Square("noColor", false);
			if(!possibleMoves[newRow][this.getCol()].getSquareType().equals("blocked"))
					possibleMoves[newRow][this.getCol()].setCanVisit(true);
			newRow--;
		}

		newRow = this.getRow()+1;
		while(newRow < 8)
		{
			possibleMoves[newRow][this.getCol()] = new Square("noColor", false);
			if(!possibleMoves[newRow][this.getCol()].getSquareType().equals("blocked"))
				possibleMoves[newRow][this.getCol()].setCanVisit(true);
			newRow++;
		}

		// possible moves in horizontal direction
		int newCol = this.getCol()-1;
		while(newCol >= 0)
		{
			possibleMoves[this.getRow()][newCol] = new Square("noColor", false);
			if(!possibleMoves[this.getRow()][newCol].getSquareType().equals("blocked"))
				possibleMoves[this.getRow()][newCol].setCanVisit(true);
			newCol--;
		}
		
		newCol = this.getCol()+1;
		while(newCol < 8)
		{
			possibleMoves[this.getRow()][newCol] = new Square("noColor", false);
			if(!possibleMoves[this.getRow()][newCol].getSquareType().equals("blocked"))
				possibleMoves[this.getRow()][newCol].setCanVisit(true);
			newCol++;
		}
		
		// possible moves in diagonal direction 
		
		newRow = this.getRow()+1;
		newCol = this.getCol()-1;
		while(newRow < 8 && newCol >= 0)
		{
			possibleMoves[newRow][newCol] = new Square("noColor", false);
			if(!possibleMoves[newRow][newCol].getSquareType().equals("blocked"))
				possibleMoves[newRow][newCol].setCanVisit(true);
			newRow++;
			newCol--;
		}
		newRow = this.getRow()-1;
		newCol = this.getCol()+1;
		while(newRow >= 0 && newCol < 8)
		{
			possibleMoves[newRow][newCol] = new Square("noColor", false);
			if(!possibleMoves[newRow][newCol].getSquareType().equals("blocked"))
				possibleMoves[newRow][newCol].setCanVisit(true);
			newRow--;
			newCol++;
		}
		
		// possible moves in other diagonal direction
		
		newRow = this.getRow()-1;
		newCol = this.getCol()-1;
		while(newRow >= 0 && newCol >= 0)
		{
			possibleMoves[newRow][newCol] = new Square("noColor", false);
			if(!possibleMoves[newRow][newCol].getSquareType().equals("blocked"))
				possibleMoves[newRow][newCol].setCanVisit(true);
			newRow--;
			newCol--;
		}
		newRow = this.getRow()+1;
		newCol = this.getCol()+1;
		while(newRow < 8 && newCol < 8)
		{
			possibleMoves[newRow][newCol] = new Square("noColor", false);
			if(!possibleMoves[newRow][newCol].getSquareType().equals("blocked"))
				possibleMoves[newRow][newCol].setCanVisit(true);
			newRow++;
			newCol++;
		}
		
		return possibleMoves;
	}

	
}
