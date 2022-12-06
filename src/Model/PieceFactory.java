package Model;
/*
 * Design Pattern - Factory Pattern
 */
public class PieceFactory 
{
	public Piece getPiece(String peiceType, int row, int col)
	{
	      if(peiceType == null){
	          return null;
	       }		
	       if(peiceType.equalsIgnoreCase("KING")){
	          return new King(row,col);
	          
	       } else if(peiceType.equalsIgnoreCase("KNIGHT")){
	          return new Knight(row,col);
	          
	       } else if(peiceType.equalsIgnoreCase("QUEEN")){
	          return new Queen(row,col);
	       }
	       return null;
	}

}
