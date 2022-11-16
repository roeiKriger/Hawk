package Model;

import java.util.List;

/*
 * Class of Question
 */

public class Question {

	/**
	 * Level of a question
	 */
	private int questionLevel;

	/**
	 * The text of the question
	 */
	private String questionContent;

	/**
	 * List of question
	 */
	private List<String> answers;

	/**
	 * Index of correct answer in list
	 */
	private int correctAnswerId;
	
	// Constructor
	public Question(int questionLevel, String questionContent, List<String> answers, int correctAnswerId)
	{
		this.questionLevel = questionLevel;
		this.questionContent = questionContent;
		this.answers = answers;
		this.correctAnswerId = correctAnswerId;
	}

	/*
	 * Getters and Setters
	 */
	
	public int getQuestionLevel() {
		return questionLevel;
	}

	public void setQuestionLevel(int questionLevel) {
		this.questionLevel = questionLevel;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public int getCorrectAnswerId() {
		return correctAnswerId;
	}

	public void setCorrectAnswerId(int correctAnswerId) {
		this.correctAnswerId = correctAnswerId;
	}
	
	
	/*
	 *  Methods
	 */
	
	/**
	 *  Adding a new answer to the list answers 
	 * @param answer answer to add
	 */
	public void addAnswer(String answer)
	{
		answers.add(answer);
	}
	
	/**
	 *  Remove an answer from the list of answers
	 * @param answer answer to remove
	 */
	public void deleteAnswer(String answer)
	{
		answers.remove(answer);
	}
	
	/**
	 * Checking if the player answered correct the question
	 * @param answer answer to check
	 * @return True if answer is correct
	 */
	public boolean checkCorrectAnswer(String answer)
	{
		for (int i = 0; i < answers.size(); i++)
		{
			if (answers.get(i).equals(answer))
			{
				if (i == correctAnswerId)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Question [questionLevel=" + questionLevel + ", content of question=" + questionContent + ", answers=" + answers
				+ ", correctanswerIdx=" + correctAnswerId +"]";
	}
	
}
