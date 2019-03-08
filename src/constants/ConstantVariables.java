package constants;

/**
 Collected constants of simple_pacman app.

All members of this class are immutable.
*/

public final class ConstantVariables {	// by declaring final, class is made non-extendable

    // GameDisplay constants
    public static final int WINDOW_WIDTH = 696;  // tiles will be 24 x 24? to go with maze.txt dimensions
	public static final int WINDOW_HEIGHT = 768;
	public static final int DISPLAY_INITIAL_X = WINDOW_WIDTH/2;
	public static final int DISPLAY_INITIAL_Y = WINDOW_HEIGHT/2 - 30;
	
	// AnimationApp constants
	public static final int WORLD_WIDTH = 656;
    public static final int WORLD_HEIGHT = 272; //864;
    public static final int NUM_COL = 29;	// was WORLD_WIDTH / 16
    public static final int NUM_ROWS = 32;	// was WORLD_HEIGHT / 16
    public static final int INITIAL_X = (NUM_COL / 2);  // was (NUM_COL / 2)-1
    public static final int INITIAL_Y = (NUM_ROWS / 2) - 1;
    public static final int INITIAL_E_X =  (NUM_COL / 2) - 1;
    public static final int INITIAL_E_Y =  (NUM_ROWS / 2) - 5;
    public static final char WALL_CHAR = 'X';
    public static final char COIN_CHAR = '.';
    public static final char EMPTY_CHAR = ' ';
    public static final int MOVE_AMNT = 24;
    
    // Coin constants
    public static final int C_DIM = 1;
    
    // MovableItem constants (also happen to be the constants for Wall)
    public static final int WIDTH = 1;
    public static final int HEIGHT = 1;
    
    // private constructor so class is non-instantiable
    private ConstantVariables() {
    
    }
}

