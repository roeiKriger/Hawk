import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Game;

/**
 * 
 */

/**
 * @author Matan
 *
 */
class TestClass {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
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

}
