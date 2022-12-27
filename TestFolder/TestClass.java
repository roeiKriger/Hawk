import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Control.SysData;
import Exceptions.JsonException;
import Model.Game;
import Model.Knight;
import Model.Question;
import Model.Square;
import javafx.scene.control.Alert.AlertType;

class TestClass 
{
	
	private static List<String> answers = new ArrayList<String>() ;
	private static Question testQuestion;
	private static Game g;
	private static SysData sd;
	
	/*
	 * Before all testing initialize new Question
	 */
	@BeforeAll
	public static void initializeQuestionAndGame() throws IOException, ParseException
	{
		answers.add("A");
		answers.add("B");
		answers.add("C");
		answers.add("D");
		testQuestion = new Question(1, "What do you prefer?", answers, 1);
		
		
		sd = SysData.getInstance();
		try {
		if (!sd.loadQuestions()) // import not successful
			throw new JsonException();
		} catch (JsonException e) {
			SysData.alert(e.getMessage(), e.getMessage(), AlertType.ERROR);
		}
		sd.setGame(new Game("jUnit", new Date()));
		g = sd.getGame();
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
	
	
	/*
	 * Testing the knight starts at 0,0 tile in the beginning of each level
	 */
	@Test
	public void testKnightInitialLocation() throws IOException, ParseException
	{			

		g.createBoardLevelOne();
		assertEquals(g.getKnight().getRow(), 0);
		assertEquals(g.getKnight().getCol(), 0);
		
		g.createBoardLevelTwo();
		assertEquals(g.getKnight().getRow(), 0);
		assertEquals(g.getKnight().getCol(), 0);
		
		g.createBoardLevelThree();
		assertEquals(g.getKnight().getRow(), 0);
		assertEquals(g.getKnight().getCol(), 0);
		
		g.createBoardLevelFour();
		assertEquals(g.getKnight().getRow(), 0);
		assertEquals(g.getKnight().getCol(), 0);
	
	}
	
	
	

}
