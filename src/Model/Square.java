package Model;

public class Square {
	
	
	
	/**
	 * Square has few types, regular, special and more, "empty" , "special", "question", "blocked", "randomSquare", "forgetting"
	 */
	private String squareType;
	
	/**
	 * indicate if the player can visit this square now or not, for example if it is blocked square, or queen or king on it
	 */
	private Boolean canVisit; 
	
	/**
	 * Boolean which indicates if player stepped on this square or not
	 */
	private Boolean isVisited;
	
	/**
	 *  in case the square will need to contain a question then we will want to add a Question for it
	 */
	private Question question;

	// Constructor 
	public Square() {
		this.squareType = "empty";
		this.isVisited = false;
		this.canVisit = false;
		this.question = null;
	}
	
	

	public Square(Boolean isVisited , String squareType) 
	{
		super();
		this.isVisited = isVisited;
		this.squareType = squareType;
		this.canVisit = false;
		this.question = null;	
	}
	
	//for Question Square
	public Square(Boolean isVisited , String squareType, Question question) 
	{
		super();
		this.isVisited = isVisited;
		this.squareType = squareType;
		this.canVisit = false;
		this.question = question;	
	}
	
	public Square(String squareType) 
	{
		super();
		this.isVisited = false;
		this.squareType = squareType;
		this.canVisit = false;
		this.question = null;	
	}
	
	//for Question Square
	public Square(String squareType, Question question) 
	{
		super();
		this.isVisited = false;
		this.squareType = squareType;
		this.canVisit = false;
		this.question = question;	
	}
	

	/*
	 * Getters and Setters
	 */
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
	
	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}
	

	@Override
	public String toString() {
		return "Square [squareType=" + squareType + ", canVisit=" + canVisit + ", isVisited="
				+ isVisited + ", question=" + question + "]";
	}
	
	// Returns string which tells you on which type of square you stepped on
	public String steppedOn() {
		return "You stepped on a special square from " + squareType + "type!";
	}


}
