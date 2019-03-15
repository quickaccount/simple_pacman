import java.lang.Math;
import java.util.ArrayList;

public class AI extends MovableItem {


    private int [] dist = new int [2];
    private int [][] directions = new int [2][2]; // 2 sets of x, y coordinates ordered from longest to shortest
    private int [] currentDir = new int [2]; // sets the current direction for the enemy
    private ArrayList<int[]> mvDirQue = new ArrayList<int[]>();
    private int mvCount;

    /**
    * Constructor that creates an AI ghost
    * @param x initial x-coordinate
    * @param y initial y-coordinate
    * @param dist[0] the displacement between the enemy and avatar x-coordinates
    * @param dist[1] the displacement between the enemy and avatar y-coordinates
    */
    public AI (int x, int y) {
        super(x, y);
    }


    private void avatarCollision(ItemProcess items) {
        // Enemy-Avatar collision check
        if (((Math.abs(this.getDistX()) <= 1 && this.getDistY() == 0) || (Math.abs(this.getDistY()) <= 1 && this.getDistX() == 0)))  {
            items.setGameOnOff(false);
            return;
        }
        else {
            return;
        }
    }


    /**
    * Sets the distance between the current object and the player
    * @param avatar the user/player: Pacman
    */
    private void setDistance(Avatar avatar) {
        this.dist[0] = this.getXCoord() - avatar.getXCoord();
        this.dist[1] = this.getYCoord() - avatar.getYCoord();
    }

    private void setDirections() { //added i-1 and 2->3
        for (int i=0; i < 2; i++) {
            if (i==0) {
                // fill the first array position with the enemies best move
                if (Math.abs( (double)(this.getDistX()) ) >= Math.abs( (double)(this.getDistY()) )) {
                    // store the direction that would bring the enemy closest to avatar, into the first position of the directions array
                    if (this.getDistX() < 0) {
                        this.directions[0][i] = 1;
                        this.directions[1][i] = 0;
                    }
                    else {
                        this.directions[0][i] = -1;
                        this.directions[1][i] = 0;
                    }
                }


                else { // same as above, but y direction is preferred
                    if (this.getDistY() < 0) {
                        this.directions[1][i] = 1;
                        this.directions[0][i] = 0;
                    }
                    else {
                        this.directions[1][i] = -1;
                        this.directions[0][i] = 0;
                    }
                }
            }

            else if (i==1) {
                // fill second array position with the second best direction
                if (this.directions[0][0] == 0) { // if y-axis move is best,  add the shortest direction in the x-axis to the second array location
                    if (this.getDistX() < 0) {
                        this.directions[0][i] = 1;
                        this.directions[1][i] = 0;
                    }
                    else {
                        this.directions[0][i] = -1;
                        this.directions[1][i] = 0;
                    }
                }
                else { // same as above but y move is second best
                    if (this.getDistY() < 0) {
                        this.directions[1][i] = 1; // changed to -1
                        this.directions[0][i] = 0;
                    }
                    else {
                        this.directions[1][i] = -1;
                        this.directions[0][i] = 0;
                    }
                }
            }
        }
    }


    private int getXDirection(int rank) { // 0: best move, 1: 2nd best, 2: 3rd, 3: worst move
        return this.directions[0][rank];
    }


    private int getYDirection(int rank) { // 0: best move, 1: 2nd best, 2: 3rd, 3: worst move
        return this.directions[1][rank];
    }



    private void setCurrentDir(int x, int y) {
        this.currentDir[0] = x;
        this.currentDir[1] = y;
    }


    private int getCurrentXDir() {
        return this.currentDir[0];
    }


    private int getCurrentYDir() {
        return this.currentDir[1];
    }


    /**
     * Returns the distance between the player's x-coord and this object's x-coord
     * @return and integer that is the distance between the two x-coords
     */
    private int getDistX() {
        return this.dist[0];
    }


    /**
     * Returns the distance between the player's y-coord and this object's y-coord
     * @return and integer that is the distance between the two y-coords
     */
    private int getDistY() {
        return this.dist[1];
    }


    private void setMvCount(int movesInADir) { // sets amount of moves required for an escape
        this.mvCount = movesInADir;
    }


    private int getMvCount() {
        return this.mvCount;
    }


    private void wallEscape(ItemProcess items) {
        int wallCount1 = 0;
        int wallCount2 = 0;

        Item NextWallInDir = items.getItemList()[this.getNewXCoord()][this.getNewYCoord()]; // xcoord in shortest direction

        while (NextWallInDir instanceof Wall && wallCount1 < 10) { // checks for escape in direction opposite of second best move
            wallCount1 ++;
            System.out.println("wa;lly wall wall"+wallCount1);
            NextWallInDir = items.getItemList()[this.getNewXCoord() + this.getXDirection(0) - this.getXDirection(1) * wallCount1 ][ this.getNewYCoord() + this.getYDirection(0) - this.getYDirection(1) * wallCount1];
        }
        /*
        while (NextWallInDir instanceof Wall && wallCount2 < 10) { // checks for escape in direction opposite of best move
            wallCount2 ++;
            System.out.println("!!22 wa;lly wall wall"+wallCount2);
            NextWallInDir = items.getItemList()[this.getNewXCoord() +this.getXDirection(1) - this.getXDirection(0) * wallCount2][ this.getNewYCoord() + this.getYDirection(1) - this.getYDirection(0) * wallCount2];
        }
        */
        // if (wallCount1 > wallCount2) { // choose fewer moves
        //    System.out.println("wa;lly count " + wallCount1 + " " + wallCount2);
        //    this.setMvCount(wallCount2);
        //   this.setCurrentDir(this.getXDirection(0) * -1, this.getYDirection(0) * -1);
        // }

        // else {
            this.setMvCount(wallCount1);
            this.setCurrentDir(this.getXDirection(1) * -1, this.getYDirection(1) * -1);
            //}

        return;
    }


    /**
     * The object's next move - check collisions step
     * @param x the current x-position
     * @param y the current y-position
     * @param items a list of all tiles and the items that are on these tiles
     */

    /**
     * Generates the enemy's move by checking which direction is furthest from the avatar
     * @param avatar the player
     * @param items a list of all tiles and the items that inhabit each tile
     */
    public void genMv(Avatar avatar, ItemProcess items) {
        System.out.println("genny gen gen");
        System.out.println("mv count "+this.getMvCount());
        // check if the enemy is stuck from a previous turn
        if (this.getMvCount() > 0) {
            System.out.println("gen mv count >0");
            this.setMvCount(this.getMvCount() - 1);
            this.setNewCoord(this.getXCoord() + this.getCurrentXDir(), this.getYCoord() + this.getCurrentYDir());
            items.processMv(this);
            this.avatarCollision(items);
        }

        else {
            System.out.println("genny set dist set dir");
            this.setDistance(avatar);
            this.setDirections();
            System.out.println("These are the directions: " + this.getXDirection(0)+ ", " + this.getYDirection(0)+"|| "+this.getXDirection(1)+", "+this.getYDirection(1));

            for (int i=0; i<2; i ++) { // try to move in the two best directions
                System.out.println("attempt"+i);
                this.setNewCoord(this.getXCoord() + this.getXDirection(i), this.getYCoord() + this.getYDirection(i));

                if (items.wallCheck(this) == false) {
                    items.processMv(this);
                    this.avatarCollision(items);
                    return;
                }
            }

            // if all else fails
            this.wallEscape(items);
            System.out.println("wall escaped");
            items.processMv(this);
            this.avatarCollision(items);

            return;
        }
    }
}
