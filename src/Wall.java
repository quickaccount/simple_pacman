//import javafx.scene.shape.*;
import java.awt.Rectangle;

import constants.ConstantVariables;

public class Wall {
    
    //Instance variables
    private Rectangle box; //Hitbox


    /**
    * Creates a wall at the specified location
    * @param x the x position
    * @param y the y position
    */
    public Wall (int x, int y) {
        this.box = new Rectangle(x, y, ConstantVariables.WIDTH, ConstantVariables.HEIGHT);
        /*
        Rectangle r = new Rectangle();
        r.setX(50);
        r.setY(50);
        r.setWidth(200);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);
        this.box = r;
        */
    }


    /**
    * Returns the hitbox of the wall
    * @return a Rectangle object for the current wall
    */
    public Rectangle getBox() {
        return this.box;
    }
}