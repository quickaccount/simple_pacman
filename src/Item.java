import java.awt.Rectangle;

import constants.ConstantVariables;

public class Item {

    private int xcoord;
    private int ycoord;
    private Rectangle box; //Hitboxes

    /**
    * Movable Objects can be the Avatar or Enemy's
    * Object Type can be 'A' or 'E', represented by true and false respectively
    * @param x initial x position
    * @param y initial y position
    * @param w width of object box
    * @param h height of object box
    */
    public  Item(int x, int y, int w, int h) {
        this.box = new Rectangle(x, y, w, h); //Set collision box
        this.xcoord = x;
        this.ycoord = y;
    }


    /**
    * Constructor that creates a moveable object at the specified location
    * @param x the initial x-coord
    * @param y the initial y-coord
    */
    public Item(int x, int y) {
        this.box = new Rectangle(x, y, ConstantVariables.WIDTH, ConstantVariables.HEIGHT);
        this.xcoord = x;
        this.ycoord = y;
    }


    /**
    * Returns the rectangular box around an object
    * @return a Rectangle around the object
    */
    public Rectangle getBox() {
        return this.box;
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


    /**
    * Returns the object's current location
    * @return an array with the current x and y coords
    */
    public int[] getObjectLoc() {
        // int[] ObjLoc = new int [] {(int)this.getBox().getX(), (int)this.getBox().getY()};
        int[] ObjLoc = {this.xcoord, this.ycoord};
        return ObjLoc;
    }
}
