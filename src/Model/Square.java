package Model;

public class Square {
	
	/**
	 * Each square has color depends on what type the square is
	 */
	private String color;
	
	/**
	 * Square has few types, regular, special and more, "empty" , "special", "question", "blocked", "random", "forgetting", "queen", "king", "knight"
	 */
	private String squareType;
	
	/**
	 * Square has few types, regular, special and more, "true", "false"
	 */
	private Boolean canVisit; 
	
	/**
	 * Boolean which indicates if player stepped on this square or not
	 */
	private Boolean isVisited;

	// Constructor 
	public Square(String color, Boolean isVisited) {
		this.color = color;
		this.squareType = "empty";
		this.isVisited = false;
		this.canVisit = false;
	}

	
	/*
	 * Getters and Setters
	 */
	
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSquareType() {
		return squareType;
	}

	public void setSquareType(String squareType) {
		this.squareType = squareType;
	}

	public Boolean getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(Boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public Boolean getCanVisit() {
		return canVisit;
	}


	public void setCanVisit(Boolean canVisit) {
		this.canVisit = canVisit;
	}

	@Override
	public String toString() {
		return "Square [color=" + color + ", squareType=" + squareType + ", isVisited=" + isVisited +", canVisit=" + canVisit + "]";
	}
	
	// Returns string which tells you on which type of square you stepped on
	public String steppedOn() {
		return "You stepped on a special square from " + squareType + "type!";
	}



	

}
