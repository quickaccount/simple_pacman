package constants;

/**
 Collected constants of simple_pacman app.

All members of this class are immutable.
*/

public final class ConstantVariables {	// by declaring final, class is made non-extendable

	// AnimationApp constants
	public static final int WORLD_WIDTH = 656;
    public static final int WORLD_HEIGHT = 272; //864;
    public static final int NUM_COL = WORLD_WIDTH / 16;
    public static final int NUM_ROWS = WORLD_HEIGHT / 16;
    public static final int INITIAL_X =  (NUM_COL / 2) - 1;
    public static final int INITIAL_Y = (NUM_ROWS / 2) - 1;
    public static final int INITIAL_E_X =  (NUM_COL / 2) - 1;
    public static final int INITIAL_E_Y =  (NUM_ROWS / 2) - 5;
    
    // Coin constants
    public static final int C_DIM = 1;
    
    // MovableItem constants (also happen to be the constants for Wall)
    public static final int WIDTH = 1;
    public static final int HEIGHT = 1;
    
    // private constructor so class is non-instantiable
    private ConstantVariables() {
    	
    }
}

