/**
* Test for AI class in Simple_Pacman
javac AI.java
javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar *.java
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore aiTest
* @author T10-G02
*/

import static org.junit.Assert.*;
import org.junit.Test;
import constants.ConstantVariables;
import java.lang.Math;


public class aiTest {

	//Constructor Tests

	@Test
	public void test_constructor_left() {
		AI testAi = new AI(0, 1); //Spawn inside wall on left side

		assertEquals("Tried creating an AI at an invalid x position, too far left", 1, testAi.getXCoord());
	}


	@Test
	public void test_constructor_top() {
		AI testAi = new AI(1, 0); //Spawn inside wall along top row

		assertEquals("Tried creating an AI at an invalid y position, too high up", ConstantVariables.NUM_ROWS - 1, testAi.getYCoord());
	}


	@Test
	public void test_constructor_right() {
		AI testAi = new AI(ConstantVariables.NUM_COL, 1); //Spawn inside wall on right side

		assertEquals("Tried creating an AI at an invalid x position, too far right", 1, testAi.getXCoord());
	}


	@Test
	public void test_constructor_bottom() {
		AI testAi = new AI(1, ConstantVariables.NUM_ROWS); //Spawn inside wall along top row

		assertEquals("Tried creating an AI at an invalid y position, too far down", ConstantVariables.NUM_ROWS - 1, testAi.getYCoord());
	}


	@Test
	public void test_constructor_proper() {
		AI testAi = new AI(1, 1); //Acceptable spawn

		assertEquals("Tried creating a valid AI", ">11", ">" + testAi.getXCoord() + testAi.getYCoord());
	}


	//Getters and Setters
	/*
	@Test
	public void test_setXDir_doubleMove() {
		AI testAi = new AI(ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y); //Create at proper position
		testAi.setNewDir

		assertEquals("Tested for a move of more than one tile", 1, Math.abs(testAi.getNewXCoord() - testAi.getXCoord()));
	}
*/


	//Wall collisions
/*
	@Test
	public void test_wallCollision_up() {
		AI testAi = new AI(1, 1); //Create in corner, with east and south directions cut off
		Avatar testPlayer = new Avatar(28, 31);
		ItemProcess items = new ItemProcess();
		testAi.genMv(testPlayer, items);

		assertEquals("Tried moving up into a wall", 0, Math.abs(testAi.getNewXCoord() - testAi.getXCoord()));
	}
*/
}