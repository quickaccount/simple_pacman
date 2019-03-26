import java.io.*;
import java.util.ArrayList;
import constants.ConstantVariables;
import java.lang.Math;


/**
 * The ItemProcess class is an updated version of out text-based AnimationApp, but it was renamed to better reflect its functionality
 * This class is most of the logic behind the app. It loads in a text file and creates lists of objects
 * so that we can check collisions.
 */
public class ItemProcess {

    private ArrayList<Coin> coinList = new ArrayList<Coin>(); //Array of coins
    private ArrayList<Wall> wallList = new ArrayList<Wall>(); //Array of walls
    private Item[][] itemList = new Item [ConstantVariables.NUM_COL] [ConstantVariables.NUM_ROWS];
    private char[][] objList = new char [NUM_COL] [NUM_ROWS];
    private boolean gameOn = false;


    /**
    * Return the full list of coins.
    * @return an ArrayList with all coins inside
    */
    private ArrayList<Coin> getCoinList() {
        return this.coinList;
    }


    /**
    * Return list of all walls.
    * @return an ArrayList with all the walls inside
    */
    private ArrayList<Wall> getWallList() {
        return this.wallList;
    }


    /**
     * Constructor that creates AnimationApp items and populates wallList, coinList, objList
     */
    public ItemProcess() {
        this.setGameOn(true);

        // The name of the file containing the display template.
        String fileName = "maze.txt";
        // Line Reference
        String line = null;

      try {
        // FileReader reads text files in the default encoding.
        FileReader template = new FileReader(fileName);
        BufferedReader bTemplate = new BufferedReader(template);
        int y = 0;

        while((line = bTemplate.readLine()) != null) {

          for(int x = 0; x < ConstantVariables.NUM_COL; x++) {

            char c = line.charAt(x);

            if (c == ConstantVariables.COIN_CHAR) {

                this.objList[x][y] = '.'; // text-based only
                Coin cCoin = new Coin(x, y);
                this.coinList.add(cCoin);
                this.itemList[x][y] = cCoin;


            } else if (c == ConstantVariables.WALL_CHAR) {

                this.objList[x][y] = 'X'; //text-based
                Wall w = new Wall(x, y);
                this.wallList.add(w);
                this.itemList[x][y] = w;
            }
            else {
                this.objList[x][y] = ' '; // empty-coin
            }
          }
            y++;
        }
          // Close default_display.txt
          bTemplate.close();
      }
      // Error checking
      catch(FileNotFoundException ex) {
        System.out.println("Cannot open file '" + fileName + "'");
      }
      catch(IOException ex) {
        System.out.println("Error reading file '" + fileName + "'");
      }

      //set default Avatar location
      this.objList[INITIAL_X][INITIAL_Y] = 'A';
      //set default Enemy location
      this.objList[INITIAL_E_X][INITIAL_E_Y] = 'E';
    }


    public Item[][] getItemList() {
        return this.itemList;
    }


    /**
     * Returns a printable list with char representations of objects
     * @return list of char represented objects
     */
    public char[][] getObjList() {
        return this.objList;
    }


    /**
     * Returns a printable list with char representations of objects
     * @return list of char represented objects
     */
    public char[][] getObjList(int x, int y) {
        return this.objList[x][y];
    }


    /**
    * Collision checking of moving objects
    * @param thing the object to test
    */
    public void processMv(MovableItem thing) {
        if (this.wallCheck(thing) == true) {
            //System.out.println("hit wall");
            return;
        }

        //coin collision checking
        ArrayList<Coin> cL = this.getCoinList();
        Item item = this.getItemList()[thing.getXCoord() / 16][thing.getYCoord() /16];

        // Is thing moving off coin or empty? display the position that the avatar moved off of
        if (thing.getOnCoin()) {
            thing.setOnCoin(false);
        }

      // check if MovableItem moving onto a coin turn coin off if appropriate, then move moveable item
        if (this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()] instanceof Coin) {
            Coin coinNewLoc = (Coin)this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()];
            if ((thing instanceof Avatar) && coinNewLoc.getCoinIsOn()) {
                coinNewLoc.setCoinOff((Avatar)thing);
            }
            thing.setOnCoin(true);
        }


        // update thing Coordinates
        thing.setXYCoord(thing.getNewXCoord(), thing.getNewYCoord());
        // set new avatar location in printable object list
    }


    /**
     * Collision checking of moving objects and walls / boundaries
     * @param thing the object to test
     **/
    public boolean wallCheck(MovableItem thing) {
        if ((thing.getNewXCoord() < 0) || (thing.getNewXCoord() > ConstantVariables.NUM_COL - 1) || (thing.getNewYCoord() < 0) || (thing.getNewYCoord() > ConstantVariables.NUM_ROWS - 1)) {
            System.out.println("Attempted to leave boundary");
            return true;
        }
        ArrayList<Wall> wL = this.getWallList();
        for (Wall w: wL) {
            int xWall = w.getXCoord();
            int yWall = w.getYCoord();
            if ((thing.getNewXCoord() == xWall) && (thing.getNewYCoord() == yWall)) {
                return true;
            }
        }
        return false;
    }


    private void setGameOn(boolean onOff) {
        this.gameOn = onOff;
    }


    public boolean getGameOn() {
        return new Boolean(this.gameOn);
    }


    public void avatarEnemyCollision(AI enemy) {
        // Enemy-Avatar collision check
        if (((Math.abs(enemy.getGoalDistanceX()) <= 1 && enemy.getGoalDistanceY() == 0) || (Math.abs(enemy.getGoalDistanceY()) <= 1 && enemy.getGoalDistanceX() == 0)))  {
            System.out.println("game over");
            System.out.println("game over");
            System.out.println("game over");
            System.out.println("game over");
            this.setGameOn(false);
            return;
        }
        else {
            return;
        }
    }


    // temporary text-based display --> maybe create a simple class to call?
    // text-based print method
    public void printDisplay() {
        String rowString = "";

        for (int y=0; y < NUM_ROWS; y++) {
            for (int x=0; x < NUM_COL; x++) {
                rowString += this.getObjList(x, y);
            }
            System.out.println(rowString);
            rowString = "";
        }
    }
}
