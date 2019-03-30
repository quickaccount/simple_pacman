//import constants.ConstantVariables;

/**
 * The Wall class extends the abstract class Item. A Wall object represents a
 * wall at specific (x,y) coordinates in the Pac-Man maze.
 */

public class Wall extends Item {

	/**
	 * Creates a wall at the specified location
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	public Wall(int x, int y) {
		super(x, y);
	}
}
