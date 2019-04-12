import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The AI class extends the abstract class MovableItem. An AI object
 * represents an enemy/ghost object at specific (x,y) coordinates in the
 * Pac-Man maze.
 */
public class AI extends MovableItem {

    private int [] dist = new int [2];
    private int [][] directions = new int [2][2]; // 2 sets of x, y coordinates ordered from longest to shortest
    private int [] currentDir = new int [2]; // sets the current direction for the enemy
    public ArrayList<int[]> mvDirQue = new ArrayList<int[]>();

    /**
    * Constructor that creates an AI ghost.
    * 
    * @param x initial x-coordinate.
    * @param y initial y-coordinate.
    * @param dist[0] the displacement between the enemy and avatar x-coordinates.
    * @param dist[1] the displacement between the enemy and avatar y-coordinates.
    */
    public AI (int x, int y) {
        super(x, y);
    }

    /**
    * Sets the distance between the current object and the player.
    * 
    * @param avatar the user/player: Pacman.
    */
    private void setGoalDistance(Avatar avatar) {
        this.dist[0] = this.getXCoord() - avatar.getXCoord();
        this.dist[1] = this.getYCoord() - avatar.getYCoord();
    }

    public int getGoalDistanceX() {
        return this.dist[0];
    }
    public int getGoalDistanceY() {
        return this.dist[1];
    }

    private void setDirections() { //added i-1 and 2->3
	// fill the first array position with the enemies best move
	if (Math.abs( (double)(this.getDistX()) ) >= Math.abs( (double)(this.getDistY()) )) {
	    // store the direction that would bring the enemy closest to avatar, into the first position of the directions array
	    if (this.getDistX() < 0) {
		this.directions[0][0] = 1;
		this.directions[1][0] = 0;
	    }
	    else {
		this.directions[0][0] = -1;
		this.directions[1][0] = 0;
	    }
	}
	

	else { // same as above, but y direction is preferred
	    if (this.getDistY() < 0) {
		this.directions[0][0] = 0;
		this.directions[1][0] = 1;
	    }
	    else {
		this.directions[0][0] = 0;
		this.directions[1][0] = -1;
	    }
	}
	
	    

    
	// fill second array position with the second best direction
	    
	// if a move in any direction other than the best direction results in a move away from the avatar
	if (this.getDistX() == 0 || this.getDistY() == 0) {
	    this.directions[0][1] = 0;
	    this.directions[1][1] = 0;
	}
	    
	// there exists a second best direction
	else {
	    if (this.directions[0][0] == 0) { // if y-axis move is best,  add the shortest direction in the x-axis to the second array location
		if (this.getDistX() < 0) {
		    this.directions[0][1] = 1;
		    this.directions[1][1] = 0;
		}
		else {
		    this.directions[0][1] = -1;
		    this.directions[1][1] = 0;
		}
	    }
	    else { // same as above but y move is second best
		if (this.getDistY() < 0) {
		    this.directions[1][1] = 1; // changed to -1
		    this.directions[0][1] = 0;
		}
		else {
		    this.directions[1][1] = -1;
		    this.directions[0][1] = 0;
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
        if (this.getDirection(0)[0] == this.getDirection(0)[1]) {
            dirAlt = 1;
        }


	// The wall index direction is used to check if the simuated enemy is adjacent to a wall 
	int [] wallIndexDir = Arrays.copyOf(this.getDirection(dirAlt), 2);


	// set the first item in the results and dirAttempts arrayLists as the last failed move that the AI would normally take
	results.add(0, 'f');
	dirAttempts.add(0, Arrays.copyOf(this.getDirection(dirAlt), 2)); //this.getSimDir());
	
	// set initial simulated Coordinates as the current Enemy coordinates
	int simXCoord = this.getXCoord();
	int simYCoord = this.getYCoord();
	
	// set initial simulated direction as perpendicular to the last failed direction
	int[] simDirection = perpDir(this.getDirection(dirAlt));

	
            do {
		simXCoord = simXCoord + simDirection[0];
                simYCoord = simYCoord + simDirection[1];
                if (items.getItemList()[simXCoord][simYCoord] instanceof Wall) { // check SimEnemyected enemy for wall collision
                    results.add(0, 'f');
                    dirAttempts.add(0, Arrays.copyOf(simDirection, 2)); // movement direction attempts
                    wallIndexDir = Arrays.copyOf(simDirection, 2);


                    simXCoord = simXCoord - simDirection[0]; // steps AI back a space
                    simYCoord = simYCoord - simDirection[1]; // steps AI back to non-collision space

                    // set new simulated direction given whether the last special condition was 'f' or 'S'
                    for (int i=1; i <= dirAttempts.size() -1;  i++) {
                        if (dirAttempts.get(0)[0] != dirAttempts.get(i)[0] && dirAttempts.get(0)[1] != dirAttempts.get(i)[1]) {

                            if (results.get(i) == 'f') { // last move that was different from current ended in 'f'

                                simDirection[0] = -dirAttempts.get(i)[0];
                                simDirection[1] = -dirAttempts.get(i)[1];
                                break;
                            }
                            else { // last move that was different from current ended in 'S'

                                // set wall index offset to previous direction
                                wallIndexDir = Arrays.copyOf(simDirection, 2);
                                simDirection[0] = dirAttempts.get(i)[0];
                                simDirection[1] = dirAttempts.get(i)[1];
                                break;
                            }
                        }
                    }
                }

                //has this been set? set SimEnemyXCoord?
                //else if (items.getItemList()[ (xyAttempts.get(0)[0]) ][ (xyAttempts.get(0)[1]) ] instanceof Wall) { // continue case   this.getSimEnemyXCoord() + wallIndexDir[0]
                else if (items.getItemList()[ (simXCoord + wallIndexDir[0]) ] [ (simYCoord + wallIndexDir[1]) ] instanceof Wall) {
		    results.add(0, 's');
		    System.out.println("simdir " + simDirection[0]+simDirection[1]);
		    if (simDirection[0] == this.getXDirection(0) && simDirection[1] == this.getYDirection(0)) {
			dirAttempts.add(0, Arrays.copyOf(simDirection, 2)); // movement direction attempts
			System.out.println("direction(dirAlt)" + this.getDirection(dirAlt)[0] + this.getDirection(dirAlt)[1]);
			System.out.println("s break");
			break;
			// end loop condition
		    }
		    else {
			dirAttempts.add(0, Arrays.copyOf(simDirection, 2)); // movement direction attempts
		    }
                }


                else { // empty case
                    results.add(0, 'S');

		    if (simDirection[0] == this.getXDirection(0) && simDirection[1] == this.getYDirection(0)) {
                        dirAttempts.add(0, Arrays.copyOf(simDirection, 2)); // movement direction attempts
			System.out.println("S break");
                        break;  // end loop condition
                    }
		    else {
                    dirAttempts.add(0, Arrays.copyOf(simDirection, 2)); // movement direction attempts
		    }

                    // add the move into adjacent space with no wall

                    simDirection[0] = wallIndexDir[0];
                    simDirection[1] = wallIndexDir[1];

                    // set new move direction and new wall check direction
                    wallIndexDir[0] = new Integer(-dirAttempts.get(0)[0]);
                    wallIndexDir[1] = new Integer(-dirAttempts.get(0)[1]);

                }

            } while (dirAttempts.size() < 17); // or initial attempted direction successfully plotted


            // remove simulated moves that will result in no move
            while (results.contains('f')) {
                dirAttempts.remove(results.indexOf('f'));
                results.remove(results.indexOf('f'));
            }

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
        return;
    }


    /**
     * Generates the enemy's move by checking which direction is furthest from the avatar
     * @param avatar the player
     * @param items a list of all tiles and the items that inhabit each tile
     */
    public void genMv(Avatar avatar, ItemProcess items) {
	this.setGoalDistance(avatar);
	
        // check if the enemy is stuck from a previous turn
        if (this.mvDirQue.size() > 0) {
            this.setNewCoord(this.getXCoord() + this.mvDirQue.get(this.mvDirQue.size()-1)[0], this.getYCoord() + this.mvDirQue.get(this.mvDirQue.size()-1)[1]);

            this.mvDirQue.remove(this.mvDirQue.size() -1); // removes the first entry of the move list

	    // process move: check wall collision if no wall then move
            items.processMv(this);
            return;
        }

        else {
            this.setDirections();
            for (int i=0; i<2; i ++) { // try to move in the two best directions
                this.setNewCoord(this.getXCoord() + this.getXDirection(i), this.getYCoord() + this.getYDirection(i));
                if (items.wallCheck(this) == false) {
                    items.processMv(this);
                    return;
                }
                if (this.getGoalDistanceX() == 0) {
                    break;
                }
                else if (this.getGoalDistanceY() == 0) {
                    break;
                }
            }
        }

        // if all else fails
        this.wallEscape(items);
        items.processMv(this);

        return;
    }
}
