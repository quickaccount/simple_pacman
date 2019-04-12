
/**
 * The Avatar class extends the abstract class MovableItem. An Avatar object represents a
 * user/player/pacman objects at specific (x,y) coordinates in the Pac-Man maze.
 */
public class Avatar extends MovableItem {

    private int score = 0; //Player score


    /**
    * Constructor, creates the player at specified coordinates.
    * 
    * @param x initial x-coordinate.
    * @param y initial y-coordinate.
    */
    public Avatar(int x, int y) {
        super(x, y);
    }


    /**
    * Copy constructor.
    * 
    * @param toCopy the Avatar to be cloned
    */
    public Avatar(Avatar toCopy) {
        this(toCopy.getXCoord(), toCopy.getYCoord());
        this.score = toCopy.getScore();
    }


    /**
    * Used to add to the player's score.
    */
    public void addScore() {
        this.score += 1;
    }
    
    /**
     * Set the game score. 
     * 
     * @param score the value that the score is to be set to.
     */
    public void setScore(int score) {
    	if (score >= 0 && score <= 606) { //If score is not negative and not greater than total # of coins
            this.score = score;
        } else { //Default to 0
            this.score = 0;
        }
    }    
    
    /**
     * Used to return the player's score.
     * 
     * @return the current score.
     */
    public int getScore() {
        return this.score;
    }


    /**
    * Takes user input and attempts to move the player accordingly.
    * 
    * @param key the user's input, as a String
    * @param itemList the list of all items on the screen
    */
    public boolean changeDirection(String key, ItemProcess items) {

	char takeFirst = key.charAt(0); //Takes the first character from the user's input
	int[] newDir = {0, 0};
	
	if (takeFirst == 'w') {
	    newDir[1] = -1;
	    // this.setDir(0, -1); //Move up
	} else if (takeFirst == 's') {
	    newDir[1] = 1;
	    // this.setDir(0, 1); //Move down
	} else if (takeFirst == 'a') {
	    newDir[0] = -1;
	    // this.setDir(-1, 0); //Move left
	} else if (takeFirst == 'd') {
	    newDir[0] = 1;
	    // this.setDir(1, 0); //Move right
	} else {}
	
    
	// test for a change in direction
	if (this.getDir(0) == newDir[0] && this.getDir(1) == newDir[1] ) { // "x = 0" y = 0
	    return false;
	}
	else {
	    if (newDir[0] == newDir[1]) { // zero case {0, 0}
		return false;
	    }
	    else { // new direction not previous direction
		this.setDir(newDir[0], newDir[1]);
		this.setNewCoord(this.getDir(0) + this.getXCoord(), this.getDir(1) + this.getYCoord()); //Apply movement
		return true;
	    }
	}
    }
}
