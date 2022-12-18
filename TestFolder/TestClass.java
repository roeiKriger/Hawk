import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.Test;
import Control.SysData;
import Model.Game;

/**
 * 
 */

/**
 * @author Matan
 *
 */
class TestClass 
{
	/*
	 * This test is testing if game is inisilaize in score of 0
	 */
	@Test
	public void testScoreInit()
	{
		Game g = new Game("jUnit");
		int score_actual = g.getScore();
		assertEquals(0, score_actual);
	}
	
	/*
	 * This test is testing if the Json File is read successfully
	 */
	@Test
	public void testReadQuestionsJson()
	{
		assertTrue(SysData.getInstance().load_questions());
	}
	
	/*
	 * 
	 */
	
	
}
