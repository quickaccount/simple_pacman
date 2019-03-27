/**
* Test for AI class in Simple_Pacman
cd Desktop/CPSC 233/simple_pacman-demo3/src
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
		Avatar testAv = new AI(0, 1); //Spawn inside wall on left side

		assertEquals("Tried creating an AI at an invalid x position, too far left", 1, testAv.getXCoord());
	}


	@Test
	public void test_constructor_top() {
		Avatar testAv = new AI(1, 0); //Spawn inside wall along top row

		assertEquals("Tried creating an AI at an invalid y position, too high up", ConstantVariables.NUM_ROWS - 1, testAv.getYCoord());
	}


	@Test
	public void test_constructor_right() {
		Avatar testAv = new AI(ConstantVariables.NUM_COL, 1); //Spawn inside wall on right side

		assertEquals("Tried creating an AI at an invalid x position, too far right", 1, testAv.getXCoord());
	}


	@Test
	public void test_constructor_bottom() {
		Avatar testAv = new AI(1, ConstantVariables.NUM_ROWS); //Spawn inside wall along top row

		assertEquals("Tried creating an AI at an invalid y position, too far down", ConstantVariables.NUM_ROWS - 1, testAv.getYCoord());
	}


	@Test
	public void test_constructor_proper() {
		Avatar testAv = new AI(4, 3); //Acceptable spawn

		assertEquals("Tried creating a valid AI, [x, y]", "11", "" + testAv.getXCoord() + testAv.getYCoord());
	}


	//Getters and Setters
	/**
	@Test
	public void test_setXDir_doubleMove() {
		Avatar testAv = new AI(ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y); //Create at proper position
		testAv.setNewDir

		assertEquals("Tested for a move of more than one tile", 1, Math.abs(testAi.getNewXCoord() - testAi.getXCoord()));
	}


	//Wall collisions

	@Test
	public void test_wallCollision_up() {
		AI testAi = new AI(1, 1); //Create in corner, with east and south directions cut off
		ItemProcess items = new ItemProcess();
		testAi.genMv(null, items);

		assertEquals("Tried moving up into a wall", 0, Math.abs(testAi.getNewXCoord() - testAi.getXCoord()));
	}
	*/

}