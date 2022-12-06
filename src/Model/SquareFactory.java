package Model;

public class SquareFactory {
	/*
	 * Design Pattern - Factory Pattern
	 */
	public Square getSquare(String squareType, String color, Boolean isVisited) {
		if (squareType == null) {
			return null;
		}
		if (squareType.equalsIgnoreCase("QUESTION")) {
			return new Square(color, isVisited, "question");
		} else if (squareType.equalsIgnoreCase("SPECAIL")) {
			return new Square(color, isVisited, "specail");
		} else if (squareType.equalsIgnoreCase("BLOCKED")) {
			return new Square(color, isVisited, "blocked");
		} else if (squareType.equalsIgnoreCase("RANDOM")) {
			return new Square(color, isVisited, "random");
		} else if (squareType.equalsIgnoreCase("FORGETTING")) {
			return new Square(color, isVisited, "forgetting");
		} else if (squareType.equalsIgnoreCase("EMPTY")) {
			return new Square(color, isVisited, "empty");
		}
		return null;
	}// end getSquare

}
