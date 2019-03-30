import constants.ConstantVariables;

/**
 * The Item class is the superclass of Wall, Coin and all MovableItems (Avatar and AI).
 * An Item is any item that would be found in the maze.
 *
 */
public abstract class Item {

    private int xcoord;    // x coordinate of the Item
    private int ycoord;    // y coordinate of the Item

    /**
     * Constructor that creates an Item at the specified location.
     * 
     * @param x the initial x-coord.
     * @param y the initial y-coord.
     */
    protected Item(int x, int y) {
        /* THIS IS FOR IF WE USE PIXELS FOR CREATION
        if (this instanceof AI || this instanceof Avatar) { //Ensure proper creation for ghosts and pacman only
            if (x > ConstantVariables.WORLD_WIDTH - (ConstantVariables.WIDTH * 2) || x < ConstantVariables.WIDTH) { //Invalid x, uses movable items' width and accounts for walls
                x = 16; //Create along left-hand side
                y = ConstantVariables.WORLD_HEIGHT - (ConstantVariables.HEIGHT * 2); //Ensure it is created along the bottom row, as there are no walls that could cause problems in a spawn there
            } else if (y > ConstantVariables.WORLD_HEIGHT - 32 || y < 16) { //Only invalid y
                y = ConstantVariables.WORLD_HEIGHT - (ConstantVariables.HEIGHT * 2); //Create somewhere along bottom wall, as no problems can occur there (x coord does not matter there)
            }
        }
        */

        //This is for if we use columns and rows for initial positions
        if (this instanceof Avatar) { //Ensure proper creation for ghosts and pacman only
            if (x > ConstantVariables.NUM_COL - 1 || x < 1) { //Invalid x, uses movable items' width and accounts for walls
                x = ConstantVariables.INITIAL_X; //Create along left-hand side
                y = ConstantVariables.INITIAL_Y; //Ensure it is created along the bottom row, as there are no walls that could cause problems in a spawn there
            } else if (y > ConstantVariables.NUM_ROWS - 1 || y < 1) { //Only invalid y
                x = ConstantVariables.INITIAL_X;
                y = ConstantVariables.INITIAL_Y; //Create somewhere along bottom wall, as no problems can occur there (x coord does not matter there)
            }
        }
        
        this.setXCoord(x);
        this.setYCoord(y);
    }


    /**
    * Change only the x coordinate of the Item.
    * 
    * @param xNew the new x position.
    */
    protected void setXCoord(int xNew){
        this.xcoord = xNew;
    }


    /**
    * Change only the y coordinate of the Item.
    * 
    * @param yNew the new y position.
    */
    protected void setYCoord(int yNew){
        this.ycoord = yNew;
    }


    /**
    * Returns the object's current x coordinate.
    * 
    * @return an integer value for the x coordinate.
    */
    public int getXCoord() {
        return this.xcoord;
    }


    /**
    * Returns the object's current y coordinate.
    * 
    * @return an integer value for the y coordinate.
    */
    public int getYCoord() {
        return this.ycoord;
    }
}
