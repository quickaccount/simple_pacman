public class Avatar extends MovableItem {

    private int score = 0;


    /**
    * Constructor, creates the player
    * @param x initial x-coordinate
    * @param y initial y-coordinate
    */
    public Avatar (int x, int y) {
        super(x, y);
    }


    /**
    * Used to add to the player's score
    */
    public void addScore() {
        this.score += 1;
    }

    public void setScore(int score) {
    	this.score = score;
    }    
    
    /**
     * Used to return the player's score
     */
    public int getScore() {
        return this.score;
    }


    /**
    * Takes user input and attempts to move the player
    * @param key the user's input, as a String
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

        itemList.allCollected(this);
        this.setNewCoord(this.getDir(0) + this.getXCoord(), this.getDir(1) + this.getYCoord()); //Apply movement
        return;
    }
}
