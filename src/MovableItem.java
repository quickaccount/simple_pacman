import constants.ConstantVariables;

public abstract class MovableItem extends Item {

    private int[] dir = new int[] {0, 0};
    private int[] newCoord = new int[] {0, 0}; //Store coordinates as one item
    private boolean onCoin;


    /**
    * Constructor that creates a moveable object at the specified location
    * @param x the initial x-coord
    * @param y the initial y-coord
    */
    protected MovableItem (int x, int y) {
        super(x, y);
    }


    /**
     * Returns T or F depending on if the two MovableItems are occupying the same square of space.
     * @param item the MovableItem we want to check our intersection with
     * @return	either 'true' or 'false', depending on whether the two MovableItems are intersecting or not.
     */
    public boolean intersects(MovableItem item)
    {
      return (item.getXCoord()==this.getXCoord() && item.getYCoord()==this.getYCoord());
    }

    /**
     * Sets the onCoin variable. Depends on if the player is on a coin
     * @param onOrOff a boolean that is true if the player is currently on a coin, and false otherwise
     */
    protected void setOnCoin (boolean onOrOff) {
        this.onCoin = onOrOff;
    }


    /**
     * Returns T or F depending on if the player is currently on a coin
     * @return either 'true' or 'false', depending on what onCoin is currently set to
     */
    public boolean getOnCoin() {
        return this.onCoin;
    }


    /**
    * Updates the object's x and y coordinates
    * @param xNew the new x position
    * @param yNew the new y position
    */
    public void setXYCoord(int xNew, int yNew) {
        super.setXCoord(xNew);
        super.setYCoord(yNew);
    }


    /**
    * Change only the x coordinate of the object
    * @param xNew the new x position
    */
    @Override
    public void setXCoord(int xNew){
        super.setXCoord(xNew);
    }


    /**
    * Change only the y coordinate of the movable object
    * @param yNew the new y position
    */
    @Override
    public void setYCoord(int yNew){
        super.setYCoord(yNew);
    }


    /**
    * Returns the object's current x coordinate
    * @return an integer value for the x coordinate
    */
    @Override
    public int getXCoord() {
        return super.getXCoord();
    }


    /**
    * Returns the object's current y coordinate
    * @return an integer value for the y coordinate
    */
    @Override
    public int getYCoord() {
        return super.getYCoord();
    }


    /**
    * Returns int from first index of newCoord list
    * @return New x-coordinate
    */
    public int getNewXCoord() {
        return this.newCoord[0];
    }


    /**
     * Returns int from first index of newCoord list
     * @return New x-coordinate
     */
    public int getNewYCoord() {
        return this.newCoord[1];
    }


    /**
    * Change the x and y coordinates in the array of the object
    * @param xNew the new x position
    * @param yNew the new y position
    */
    protected void setNewCoord(int xNew, int yNew) {
        this.newCoord[0] = xNew;
        this.newCoord[1] = yNew;
    }


    /**
    * Returns the object's current location
    * @return an array with the current x and y coords
    */
    /*
    public int[] getObjectLoc() {
        // int[] ObjLoc = new int [] {(int)this.getBox().getX(), (int)this.getBox().getY()};
        int[] ObjLoc = {this.getXCoord(), this.getYCoord()};
        return ObjLoc;
    }
    */

    /**
    * Get the object's current direction
    * @param xy the index of the dir array to be accessed.
    * @return the direction of the object as an integer.
    */
    public int getDir(int xy) {
        return this.dir[xy];
    }


    /**
    * Set the direction of the object.
    * @param x the x direction: 1, 0, -1
    * @param y the y direction: 1, 0, -1
    */
    public void setDir(int x, int y) {
        this.dir[0] = x;
        this.dir[1] = y;
    }
}
