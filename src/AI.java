import java.lang.Math;

public class AI extends MovableItem {

    private int [] dist = new int [2];

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


    /**
    * Sets the distance between the current object and the player
    * @param avatar the user/player: Pacman
    */
    public void setDist(Avatar avatar) {
        this.dist[0] = this.getXCoord() - avatar.getXCoord();
        this.dist[1] = this.getYCoord() - avatar.getYCoord();
    }


    /**
    * Returns the distance between the player's x-coord and this object's x-coord
    * @return and integer that is the distance between the two x-coords
    */
    public int getDistX() {
        return this.dist[0];
    }


    /**
    * Returns the distance between the player's y-coord and this object's y-coord
    * @return and integer that is the distance between the two y-coords
    */
    public int getDistY() {
        return this.dist[1];
    }


    /**
    * The object's next move - check collisions step
    * @param x the current x-position
    * @param y the current y-position
    * @param items a list of all tiles and the items that are on these tiles
    */
    private void checkMv(int x, int y, AnimationApp items) {
        for (int i=0; i<4; i++) {
            this.setDir(x, y);
            this.setNewCoord(this.getDir(0) + this.getXCoord(), this.getDir(1) + this.getYCoord()); //Apply movement
            if (items.wallCheck(this) == false) {
                items.processMv(this);
                return;
            }

            if (i == 0) {
                if (y == 0) {
                    if (this.getDistY() < 0) {
                        x = 0;
                        y = 1;
                    }
                    else {
                        x = 0;
                        y = -1;
                    }
                }
                else if (x == 0) {
                    if (this.getDistX() < 0) {
                        x = 1;
                        y = 0;
                    }
                    else {
                        x = -1;
                        y = 0;
                    }
                }
            }
            else {
                if (Math.abs(x) > Math.abs(y)) {
                    x = 0;
                    y = x;
                }
                else {
                    x = y;
                    y = 0;
                }
            }
        }
    }


    /**
    * Generates the enemy's move by checking which direction is furthest from the avatar
    * @param avatar the player
    * @param items a list of all tiles and the items that inhabit each tile
    */
    public void genMv(Avatar avatar, AnimationApp items) {
        this.setDist(avatar);

        // Enemy-Avatar collision check
        if (((Math.abs(this.getDistX()) <= 1) && (this.getDistY() == 0)) || ((Math.abs(this.getDistY()) <= 1) && (this.getDistX() == 0)))  {
            //items.setGameOnOff(false);
            return;
        }

        else {
            if (Math.abs( (double)(this.getDistX()) ) >= Math.abs( (double)(this.getDistY()) )) {
                if (this.getDistX() < 0) {
                    this.checkMv(1, 0, items); //check wall collision in x direction
                }
                else {
                    this.checkMv(-1, 0, items);
                }
            }
            else {
                if (this.getDistY() < 0) {
                    this.checkMv(0, 1, items); //check wall collision in y directions
            }
                else {
                    this.checkMv(0, -1, items);
                }
            }
        }
    }
}
