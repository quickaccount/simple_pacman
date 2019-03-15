import java.lang.Math;
import java.util.ArrayList;

public class AI extends MovableItem {


    private int [] dist = new int [2];
    private int [][] directions = new int [2][2]; // 2 sets of x, y coordinates ordered from longest to shortest
    private int [] currentDir = new int [2]; // sets the current direction for the enemy
    private ArrayList<int[]> mvDirQue = new ArrayList<int[]>();

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
            //items.setGameOnOff(false);
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

    private int getDistanceX() {
        return this.dist[0];
    }
    private int getDistanceY() {
        return this.dist[1];
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

    private int[] getDirection(int rank) {
        return this.directions[rank];
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


    private void wallEscape(ItemProcess items) {
        int [] inputList1 = new int [2];
        int [] inputList2 = new int [2];
        ArrayList<int[]> wallList1= new ArrayList<int[]>();
        ArrayList<int[]> wallList2 = new ArrayList<int[]>();
        int q;
        this.mvDirQue.clear();

        Item NextWallInDir = items.getItemList()[this.getXCoord() + this.getXDirection(0)][this.getYCoord() + this.getYDirection(0)]; // xcoord in shortest direction
        do {
            System.out.println("The first loop");
            System.out.println("XC: "+this.getXCoord()+" XD(1): "+this.getXDirection(0)+"XD(0): "+this.getXDirection(1)+"p: "+wallList1.size()+" YC: "+this.getYCoord()+" YD(1): "+this.getYDirection(0)+"YD(0): "+this.getYDirection(1)+" p: "+ wallList1.size());
            NextWallInDir = items.getItemList()[this.getXCoord() + this.getXDirection(0) - this.getXDirection(1) * (wallList1.size() + 1) ][ this.getYCoord() + this.getYDirection(0) - this.getYDirection(1) * (wallList1.size() + 1)];
            inputList1[0] = -this.getXDirection(1);
            inputList1[1] = -this.getYDirection(1);
            wallList1.add(inputList1);
        }
        while (NextWallInDir instanceof Wall && wallList1.size() < 10); // checks for escape in direction opposite of second best move
        wallList1.add(this.getDirection(0));



        NextWallInDir = items.getItemList()[this.getXCoord() + this.getXDirection(1)][this.getYCoord() + this.getYDirection(1)]; // xcoord in shortest direction
        do {
            System.out.println("The second loop");
            NextWallInDir = items.getItemList()[this.getXCoord() + this.getXDirection(1) - this.getXDirection(0) * wallList2.size()][ this.getYCoord() + this.getYDirection(1) - this.getYDirection(0) * wallList2.size() ];
            inputList2[0] = -this.getXDirection(0);
            inputList2[1] = -this.getYDirection(0);
            wallList2.add(inputList2);
        }
        while (NextWallInDir instanceof Wall && wallList2.size() < 10);  // checks for escape in direction opposite of best move
        wallList2.add(this.getDirection(1));




        if (wallList1.size() > wallList2.size()) { // choose fewer moves
            this.mvDirQue.addAll(wallList2);
            this.setCurrentDir(-this.getXDirection(0), -this.getYDirection(0));
         }

        else {
            this.mvDirQue.addAll(wallList1);
            this.setCurrentDir(-this.getXDirection(1), -this.getYDirection(1));
        }
        wallList1.clear();
        wallList2.clear();
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
        // check if the enemy is stuck from a previous turn
        if (this.mvDirQue.size() > 0) {
            System.out.println("gen move: mvDirQue > 0");

            this.setNewCoord(this.getXCoord() + this.mvDirQue.get(0)[0], this.getYCoord() + this.mvDirQue.get(0)[1]);

            this.mvDirQue.remove(0); // removes the first entry of the move list
            // process move: check wall collision if no wall then move
            items.processMv(this);
            // check for collision with avatar
            this.avatarCollision(items);
            return;
        }

        else {
            System.out.println("genny set dist set dir");
            this.setDistance(avatar);
            this.setDirections();
            for (int i=0; i<2; i ++) { // try to move in the two best directions
                this.setNewCoord(this.getXCoord() + this.getXDirection(i), this.getYCoord() + this.getYDirection(i));
                if (items.wallCheck(this) == false) {
                    items.processMv(this);
                    this.avatarCollision(items);
                    return;
                }
                if (this.getDistanceX() == 0) {
                    break;
                }
                else if (this.getDistanceY() == 0) {
                    break;
                }
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
