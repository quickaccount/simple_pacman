//Import
import java.awt.Rectangle;

import constants.ConstantVariables;

public class Coin {

    //Instance variables
    private boolean isOn = true;
    private Rectangle box;


    /**
    * Creates a new coin at the specified location
    * @param x x position
    * @param y y position
    */
    public Coin (int x, int y) {
        this.box = new Rectangle(x, y, ConstantVariables.C_DIM, ConstantVariables.C_DIM); //x, y, length, width
    }


    /**
    * Used to get the position and hitbox of the coin
    * @return Rectangle object that is referred to as 'box'
    */
    public Rectangle getBox() {
        return this.box;
    }


    /**
    * When a coin is picked up by the player, it gets removed and the player's score increases
    * @param avatar the user/player
    */
    public void setCoinOff(Avatar avatar) {
        this.isOn = false; //Deactivates the coin that has been picked up
        avatar.addScore(); //+1 to the player's score
    }


    /**
    * Used to check if the coin is active
    * @return true of the coin still exists, otherwise false
    */
    public boolean getCoinIsOn () {
        return this.isOn;
    }

}
