import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Control.SysData;
import Model.Game;
import Model.Question;

class TestClass 
{
	
	private static List<String> answers = new ArrayList<String>() ;
	private static Question testQuestion;
	private Game g;
	
	/*
	 * Before all testing initialize new Question
	 */
	@BeforeAll
	public static void initializeQuestion()
	{
		answers.add("A");
		answers.add("B");
		answers.add("C");
		answers.add("D");
		testQuestion = new Question(1, "Whay you prefer?", answers, 1);
	}
	
	
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
	 * This test is testing if the Question has content not null
	 */
	@Test
	public void testContentQuestion()
	{
		assertNotNull(testQuestion.getQuestionContent());
	}
	
	/*
	 * This test is testing if the Question difficulty is true
	 */
	@Test
	public void testDiffucltyQuestion()
	{
		assertEquals(1, testQuestion.getQuestionDifficulty());
	}
	
	/*
	 * Testing if just one of the answers is correct
	 */
	@Test
	public void testAnsewrsOfQuestion() throws IOException
	{	
		assertTrue(testQuestion.checkCorrectAnswer("A"));
		assertFalse(testQuestion.checkCorrectAnswer("B"));
		assertFalse(testQuestion.checkCorrectAnswer("C"));
		assertFalse(testQuestion.checkCorrectAnswer("D"));
	}
	

}
