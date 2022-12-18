import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import Control.SysData;
import Model.Game;
import Model.Question;

class TestClass 
{
	
	private List<String> answers;
	private Question testQuestion;
	private Game g;
	
	
	/*
	 * This test is testing if game is initialize in score of 0
	 */
	@Test
	public void testScoreInit()
	{
		g = new Game("jUnit");
		int score_actual = g.getScore();
		assertEquals(0, score_actual);
	}
	
	/*
	 * This test is testing if the Question JSON File is read successfully
	 */
	@Test
	public void testReadQuestionsJson() throws IOException, ParseException
	{
		assertTrue(SysData.getInstance().load_questions());
	}
	
	/*
	 * This test is testing if the Scores JSON File is read successfully
	 */
	@Test
	public void testReadScoresJson() throws java.text.ParseException {
		assertTrue(SysData.getInstance().import_scores());
	}
	
	/*
	 * This method trying to add new Question 
	 * Testing if just one of the answers is correct
	 * if the question content is not null
	 * check if difficulty is correct
	 */
	@Before
	public List<String> initializeAnswers()
	{
		answers = new ArrayList<String>();
		answers.add("A");
		answers.add("B");
		answers.add("C");
		answers.add("D");
		return answers;
	}

	@Test
	public void addQuestionTest() throws IOException
	{
		testQuestion = new Question(1, "Whay you prefer?", initializeAnswers(), 1);
		assertNotNull(testQuestion.getQuestionContent());
		assertEquals(1, testQuestion.getQuestionDifficulty());
		assertTrue(testQuestion.checkCorrectAnswer("A"));
		assertFalse(testQuestion.checkCorrectAnswer("B"));
		assertFalse(testQuestion.checkCorrectAnswer("C"));
		assertFalse(testQuestion.checkCorrectAnswer("D"));
	}
	
	/*
	 * Checking if score is add to Scores JSON
	 */
	
	@Test
	public void addScoreTest() 
	{
		g = new Game("jUnit");
		g.setScore(100);
		SysData sd = SysData.getInstance();
		sd.setGame(g);
		assertTrue(sd.add_score());	
	}
	
	


}
