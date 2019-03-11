import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import constants.ConstantVariables;


public class AnimationApp {

    private ArrayList<Coin> coinList = new ArrayList<Coin>(); //Array of coins
    private ArrayList<Wall> wallList = new ArrayList<Wall>(); //Array of walls
    private char[][] objList = new char [ConstantVariables.NUM_COL] [ConstantVariables.NUM_ROWS];
    private Item[][] itemList = new Item [ConstantVariables.NUM_COL] [ConstantVariables.NUM_ROWS];
    private static boolean gameOnOff;


    /**
    * Return the full list of coins
    * @return an ArrayList with all coins inside
    */
    public ArrayList<Coin> getCoinList() {
        return this.coinList;
    }


    /**
    * Return list of all walls
    * @return an ArrayList with all the walls inside
    */
    public ArrayList<Wall> getWallList() {
        return this.wallList;
    }


    /**
     * Constructor that creates AnimationApp items and populates wallList, coinList, objList
     */
    public AnimationApp() {

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

                Coin cCoin = new Coin(x, y);
                this.coinList.add(cCoin);
                this.objList[x][y] = ConstantVariables.COIN_CHAR;
                this.itemList[x][y] = cCoin;

            } else if (c == ConstantVariables.WALL_CHAR) {

                Wall w = new Wall(x, y);
                this.wallList.add(w);
                this.itemList[x][y] = w;
                this.objList[x][y] = ConstantVariables.WALL_CHAR;

            } else {

                this.objList[x][y] = ConstantVariables.EMPTY_CHAR;
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
      //this.objList[ConstantVariables.INITIAL_X][ConstantVariables.INITIAL_Y] = 'A';
      //set default Enemy location
      //this.objList[ConstantVariables.INITIAL_E_X][ConstantVariables.INITIAL_E_Y] = 'E';
    }


    // text-based print method
    public void printDisplay() {
        String rowString = "";

        for (int y=0; y < ConstantVariables.NUM_ROWS; y++) {
            for (int x=0; x < ConstantVariables.NUM_COL; x++) {
                /*
                if (this.itemList[x][y] instanceof Coin) {
                    rowString += ConstantVariables.COIN_CHAR;
                }
                else if (this.itemList[x][y] instanceof Wall) {
                    rowString += ConstantVariables.WALL_CHAR;
                }
                else if (this.itemList[x][y] instanceof Avatar) {
                    rowString += ConstantVariables.AV_CHAR;
                }
                else if (this.itemList[x][y] instanceof AI) {
                    rowString += ConstantVariables.AI_CHAR;
                }
                else {
                    rowString += ConstantVariables.EMPTY_CHAR;
                }
                */
                rowString += this.objList[x][y];
            }
            System.out.println(rowString);
            rowString = "";
        }
    }


    /**
    * Returns a printable list with char representations of objects
    * @return list of char represented objects
    */
    public char[][] getObjList() {
        return this.objList;
    }


    public Item[][] getItemList() {
        return this.itemList;
    }


    /**
    * Allows for changing objects within the object list (ei - coin to no coin)
    * @param x the [x] index
    * @param y the [y] index
    * @param item new item to be placed at that specific index
    */
    public void setObjList(int x, int y, char item) {
        this.objList[x][y] = item;
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
            int xWall = (int)w.getBox().getX();
            int yWall = (int)w.getBox().getY();
            if ((thing.getNewXCoord() == xWall) && (thing.getNewYCoord() == yWall)) {
                return true;
            }
        }
        return false;
    }

    /**
    * Collision checking of moving objects
    * @param thing the object to test
    */
    public void processMv(MovableItem thing) {
        if (this.wallCheck(thing) == true) {
            System.out.println("hit wall");
            return;
        }

        //coin collision checking
        ArrayList<Coin> cL = this.getCoinList();
        char avatarEnemy = ConstantVariables.AI_CHAR;
        char displayCoin = ConstantVariables.COIN_CHAR;
        Item item = this.getItemList()[thing.getXCoord() / 16][thing.getYCoord() /16];

        if (thing instanceof AI) {
            avatarEnemy = ConstantVariables.AV_CHAR;
        }

        // Is thing moving off coin or empty? display the position that the avatar moved off of
        if (thing.getOnCoin() == true) {
            System.out.println(avatarEnemy + " is moving off a coin");
            if (item instanceof Coin) {
                if ( ((Coin)item).getCoinIsOn() == true) {
                    displayCoin = ConstantVariables.COIN_CHAR;
                }

                else {
                    displayCoin = ConstantVariables.EMPTY_CHAR;
                }
            }
            this.setObjList(thing.getXCoord(), thing.getYCoord(), displayCoin);
        }
        else {
            System.out.println(avatarEnemy + " is moving off an empty space");
            this.setObjList(thing.getXCoord(), thing.getYCoord(), ConstantVariables.EMPTY_CHAR);
        }

        // check if MovableItem moving onto a coin turn coin off if appropriate, then move moveable item
        /*
        if (this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()] instanceof Coin) {
            if (thing instanceof Avatar) {
                this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()].setCoinOff(thing);
            }
        }
        */

        // update thing Coordinates
        thing.setXYCoord(thing.getNewXCoord(), thing.getNewYCoord());
        // set new avatar location in printable object list
        this.setObjList(thing.getXCoord(), thing.getYCoord(), avatarEnemy);
    }


    /**
    * Enables ending and starting of the game
    * @param onOff either true or false
    */
    public void setGameOnOff (boolean onOff) {
        this.gameOnOff = onOff;
    }
}
