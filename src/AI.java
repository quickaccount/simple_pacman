import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class AI extends MovableItem {


    private int [] dist = new int [2];
    private int [][] directions = new int [2][2]; // 2 sets of x, y coordinates ordered from longest to shortest
    private int [] currentDir = new int [2]; // sets the current direction for the enemy
    public ArrayList<int[]> mvDirQue = new ArrayList<int[]>();
    private int [] inputXY = new int[2];
    private int [] attemptDir = new int[2];
    private int projX;
    private int projY;

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

                // if a move in any direction other than the best direction results in a move away from the avatar
                if (this.getDistX() == 0 || this.getDistY() == 0) {
                    this.directions[0][i] = 0;
                    this.directions[1][i] = 0;
                }

                // there exists a second best direction
                else {
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


    private int [] getInputXY() {
        return this.inputXY;
    }
    private void setInputX(int x) {
        this.inputXY[0] = x;
    }
    private void setInputY(int y) {
        this.inputXY[1] = y;
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


    private int[] getAttemptDir() {
        return this.attemptDir;
    }
    private void setAttemptDir(int[] dir) {
        this.attemptDir = dir;
    }
    private void setAttemptXDir(int x) {
        this.attemptDir[0] = x;
    }
    private void setAttemptYDir(int y) {
        this.attemptDir[1] = y;
    }
    private int getProjX() {
        return this.projX;
    }
    private void setProjX(int x) {
        this.projX = x;
    }
    private int getProjY() {
        return this.projY;
    }
    private void setProjY(int y) {
        this.projY = y;
    }

    private int[] perpDir(int[] direction) { // alt changes left right/ up down (can be 1, -1, 0)
        int[] outputDir = {0, 0};

        if (direction[0] == 1) { // input right
            outputDir[1] = 1;
        }
        else if (direction[0] == -1) {
            outputDir[1] = -1;
        }
        else if (direction[1] == 1) {
            outputDir[0] = 1;
        }
        else if (direction[1] == -1) {
            outputDir[0] = -1;
        }
        else
            System.out.println("FAILURES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        return outputDir;
    }


    private void wallEscape(ItemProcess items) {
        this.mvDirQue.clear(); // -----------------??????????????????????????

        ArrayList<int[]> dirAttempts = new ArrayList<int[]>();
        ArrayList<int[]> xyAttempts = new ArrayList<int[]>();
        ArrayList<Character> results = new ArrayList<Character>();
        int dirAlt = 0;
        if (this.getDirection(0)[0] == 0 && this.getDirection(0)[1] == 0) {
            dirAlt = 1;
        }
        int alt = 1;
        // for (int alt = -1; alt<2; alt++) { // alternates the starting direction
            // simplyify all to end
             this.setAttemptDir(this.getDirection(dirAlt));
            int [] wallIndexDir = perpDir(this.getDirection(dirAlt));

            results.add(0, 'f');

            this.setInputX(this.getXCoord() + this.getDirection(dirAlt)[0]);
            this.setInputY(this.getYCoord() + this.getDirection(dirAlt)[1]);
            System.out.println("first call InputX: " + this.getInputXY()[0]  +" InputY: " + this.getInputXY()[1]);
            xyAttempts.add(0, Arrays.copyOf(this.getInputXY(), 2));

            // reset XY input coords
            this.setInputX(this.getXCoord());
            this.setInputY(this.getYCoord());

            dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); //this.getAttemptDir());
            this.setProjX(this.getXCoord()); //this.setProjX(xyAttempts.get(0)[0] - lastFailDir[0]);
            this.setProjY(this.getYCoord());

            wallIndexDir = Arrays.copyOf(this.getAttemptDir(), 2);


            // sets first direction either positive or negative
            this.setAttemptDir(perpDir(this.getDirection(dirAlt)));
            this.setAttemptXDir(alt * this.getAttemptDir()[0]); // alt
            this.setAttemptYDir(alt * this.getAttemptDir()[1]); // alt


            do {
                System.out.println("The first loop");
                System.out.println("dirAttsize" + dirAttempts.size());
                this.setProjX(this.getProjX() + this.getAttemptDir()[0]);
                this.setProjY(this.getProjY() + this.getAttemptDir()[1]);

                this.setInputX(this.getProjX() + wallIndexDir[0]);
                this.setInputY(this.getProjY() + wallIndexDir[1]);
                xyAttempts.add(0, Arrays.copyOf(this.getInputXY(), 2));




                System.out.println("projX: " + this.getProjX()  +" projY: " + this.getProjY());


                if (items.getItemList()[this.getProjX()][this.getProjY()] instanceof Wall) { // check projected enemy for wall collision

                    System.out.println("f ");
                    results.add(0, 'f');
                    dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); // movement direction attempts
                    wallIndexDir = Arrays.copyOf(this.getAttemptDir(), 2);

                    // this.setInputX(this.getInputXY()[0] + this.getAttemptDir()[0]);
                    // this.setInputY(this.getInputXY()[1] + this.getAttemptDir()[1]);

                    this.setProjX(this.getProjX() - this.getAttemptDir()[0]); // steps AI back a space
                    this.setProjY(this.getProjY() - this.getAttemptDir()[1]); // steps AI back to non-collision space
                    // fix
                    for (int i=1; i <= dirAttempts.size() -1;  i++) {
                        if (dirAttempts.get(0)[0] != dirAttempts.get(i)[0] && dirAttempts.get(0)[1] != dirAttempts.get(i)[1]) {
                            System.out.println();
                            System.out.println("diratt 0 fff "+dirAttempts.get(0)[0]+" y "+dirAttempts.get(0)[1]+" diratt i != " +dirAttempts.get(i)[0]+" y " +dirAttempts.get(i)[1]);
                            if (results.get(i) == 'f') {
                                System.out.println("last diff move: f");
                                this.setAttemptXDir(-dirAttempts.get(i)[0]);
                                this.setAttemptYDir(-dirAttempts.get(i)[1]);
                                break;
                            }
                            else {//if (results.get(i) == 'e') {
                                System.out.println("last diff move: S");
                                // set wall index offset to previous direction
                                wallIndexDir = Arrays.copyOf(this.getAttemptDir(), 2);
                                this.setAttemptXDir(dirAttempts.get(i)[0]);
                                this.setAttemptYDir(dirAttempts.get(i)[1]);
                                break;
                            }
                        }
                    }
                }

                //has this been set? set projX?
                else if (items.getItemList()[ (xyAttempts.get(0)[0]) ][ (xyAttempts.get(0)[1]) ] instanceof Wall) { // continue case

                    System.out.println("s ");
                    results.add(0, 's');
                    dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); // movement direction attempts

                    if (this.getAttemptDir() == this.getDirection(dirAlt)) {
                        dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); // movement direction attempts
                        break;
                        // end loop condition
                    }
                }


                else { // empty case

                    System.out.println("S");
                    results.add(0, 'S');
                    dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); // movement direction attempts

                    if (this.getAttemptDir() == this.getDirection(dirAlt)) {
                        dirAttempts.add(0, Arrays.copyOf(this.getAttemptDir(), 2)); // movement direction attempts
                        break;
                        // end loop condition
                    }

                    // add the move into adjacent space with no wall

                    // set new move direction and new wall check direction
                    wallIndexDir[0] = new Integer(-this.getAttemptDir()[0]);
                    wallIndexDir[1] = new Integer(-this.getAttemptDir()[1]);
                    this.setAttemptXDir(this.getInputXY()[0] - this.getProjX());
                    this.setAttemptYDir(this.getInputXY()[1] - this.getProjY());

                }
                System.out.println("new AttemprXdir: " + this.getAttemptDir()[0] + " y: " +  this.getAttemptDir()[1]);
                System.out.println("prev dirAttemptX0: " + dirAttempts.get(1)[0] + " y: " +  dirAttempts.get(1)[1]);
            }
            while (dirAttempts.size() < 16); // or initial attempted direction successfully plotted

            while (results.contains('f')) {
                dirAttempts.remove(results.indexOf('f'));
                xyAttempts.remove(results.indexOf('f'));
                results.remove(results.indexOf('f'));
            }
            /*
              for (int i = dirAttempts.size() -1; i >=0; i--){
              System.out.println("i: " + i);
              System.out.println(results.get(i)+"");
              System.out.println("dirx: "+dirAttempts.get(i)[0]+" y "+dirAttempts.get(i)[1]);
              System.out.println("Wallx: "+xyAttempts.get(i)[0]+" y "+xyAttempts.get(i)[1]);
              System.out.println("");
              }
            */

            if (this.mvDirQue.size() > 0 && this.mvDirQue.size() <= dirAttempts.size()) {
                System.out.println("run one shorter");
            }
            else {
                System.out.println("run two shorter");
                this.mvDirQue.clear();
                this.mvDirQue.addAll(dirAttempts);
            }
            dirAttempts.clear();
            results.clear();
            xyAttempts.clear();
            //    }
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
            System.out.println("dir[0] "+this.mvDirQue.get(this.mvDirQue.size()-1)[0]);
            System.out.println(" dir[1] "+this.mvDirQue.get(this.mvDirQue.size()-1)[1]);
            this.setNewCoord(this.getXCoord() + this.mvDirQue.get(this.mvDirQue.size()-1)[0], this.getYCoord() + this.mvDirQue.get(this.mvDirQue.size()-1)[1]);

            this.mvDirQue.remove(this.mvDirQue.size() -1); // removes the first entry of the move list
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
