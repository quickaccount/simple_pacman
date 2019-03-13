//Import
import constants.ConstantVariables;

public class Coin extends Item {

    //Instance variables
    private boolean isOn = true;


    /**
    * Creates a new coin at the specified location
    * @param x x position
    * @param y y position
    */
    public Coin (int x, int y) {
        super(x + ConstantVariables.COIN_OFFSET, y + ConstantVariables.COIN_OFFSET);
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
    public boolean getCoinIsOn() {
        return this.isOn;
    }
}
