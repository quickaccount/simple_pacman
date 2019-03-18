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


public class aiTest {

	//Constructor Tests


	@Test
	public void test_constructorRightBottom() {
		AI testAi = new AI(ConstantVariables.WORLD_WIDTH - 15, ConstantVariables.WORLD_HEIGHT - 15); //Spawn inside wall on right/bottom side

		assertEquals("Tried creating an AI at an invalid x position, too far right", 17, testAi.getXCoord());
		assertEquals("Tried creating an AI at an invalid y position, too low down", 17, testAi.getYCoord());
	}


	@Test
	public void test_constructorLeftTop() {
		AI testAi = new AI(15, 15); //Spawn inside wall on left/top side

		assertEquals("Tried creating an AI at an invalid x position, too far left", 17, testAi.getXCoord());
		assertEquals("Tried creating an AI at an invalid y position, too high up", ConstantVariables.WORLD_HEIGHT - 17, testAi.getYCoord());
	}

}