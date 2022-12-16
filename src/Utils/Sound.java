package Utils;

public enum Sound {
	Menu("MenuButtons.wav"), CorrectAnswer("CorrectAnswer.wav"), 
	Error("Error.wav"), QuestionPopup("QuestionPopup.wav"), 
	StartGame("StartGame.wav"), WrongAnswer("WrongAnswer.wav") ;
	
	// the value is the file name in folder 'sounds'
	private final String value;

	Sound(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
