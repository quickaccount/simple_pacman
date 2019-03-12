import java.awt.Rectangle;

import constants.ConstantVariables;

public class Item {

    private int xcoord;
    private int ycoord;

    /**
    * Constructor that creates a moveable object at the specified location
    * @param x the initial x-coord
    * @param y the initial y-coord
    */
    public Item(int x, int y) {
        this.setXCoord(x);
        this.setYCoord(y);
    }


    /**
    * Change only the x coordinate of the object
    * @param xNew the new x position
    */
    public void setXCoord(int xNew){
        this.xcoord = xNew;
    }


    /**
    * Change only the y coordinate of the movable object
    * @param yNew the new y position
    */
    public void setYCoord(int yNew){
        this.ycoord = yNew;
    }


    /**
    * Returns the object's current x coordinate
    * @return an integer value for the x coordinate
    */
    public int getXCoord() {
        return this.xcoord;
    }


    /**
    * Returns the object's current y coordinate
    * @return an integer value for the y coordinate
    */
    public int getYCoord() {
        return this.ycoord;
    }
}
