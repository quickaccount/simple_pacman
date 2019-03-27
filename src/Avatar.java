public class Avatar extends MovableItem {

    private int score = 0; //Player score


    /**
    * Constructor, creates the player
    * @param x initial x-coordinate
    * @param y initial y-coordinate
    */
    public Avatar(int x, int y) {
        super(x, y);
    }


    /**
    * Copy constructor
    * @param toCopy the Avatar to be cloned
    */
    public Avatar(Avatar toCopy) {
        this(toCopy.getXCoord(), toCopy.getYCoord());
        this.score = toCopy.getScore();
    }


    /**
    * Used to add to the player's score
    */
    public void addScore() {
        this.score += 1;
    }
    
    /**
     * Set the game score. 
     * @param score the value that the score is to be set to.
     */
    public void setScore(int score) {
    	this.score = score;
    }    
    
    /**
     * Used to return the player's score
     * @return the current score
     */
    public int getScore() {
        return this.score;
    }


    /**
    * Takes user input and attempts to move the player
    * @param key the user's input, as a String
    * @param itemList the list of all items on the screen
    */
    public void mvAttempt(String key, ItemProcess itemList) {

        char takeFirst = key.charAt(0); //Takes the first character from the user's input

        if (takeFirst == 'w') {
            this.setDir(0, -1); //Move up
        } else if (takeFirst == 's') {
            this.setDir(0, 1); //Move down
        } else if (takeFirst == 'a') {
            this.setDir(-1, 0); //Move left
        } else if (takeFirst == 'd') {
            this.setDir(1, 0); //Move right
        } else {
        }

        itemList.allCollected(this); //Check for all coins collected
        this.setNewCoord(this.getDir(0) + this.getXCoord(), this.getDir(1) + this.getYCoord()); //Apply movement
    }
}
