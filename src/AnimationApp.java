import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import constants.ConstantVariables;


public class AnimationApp {

    private ArrayList<Coin> coinList = new ArrayList<Coin>(); //Array of coins
    private ArrayList<Wall> wallList = new ArrayList<Wall>(); //Array of walls
   /* private static final int WORLD_WIDTH = 656;
    private static final int WORLD_HEIGHT = 272; //864;
    private static final int NUM_COL = WORLD_WIDTH / 16;
    private static final int NUM_ROWS = WORLD_HEIGHT / 16;
    private static final int INITIAL_X =  (NUM_COL / 2) - 1;
    private static final int INITIAL_Y = (NUM_ROWS / 2) - 1;
    private static final int INITIAL_E_X =  (NUM_COL / 2) - 1;
    private static final int INITIAL_E_Y =  (NUM_ROWS / 2) - 5; */
    private char[][] objList = new char [ConstantVariables.NUM_COL] [ConstantVariables.NUM_ROWS];
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
        String fileName = "default_display.txt";
        // Line Reference
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader template = new FileReader(fileName);

            BufferedReader bTemplate = new BufferedReader(template);
            int y = 0;
            while((line = bTemplate.readLine()) != null) {
                    for(int x=0; x<ConstantVariables.NUM_COL; x++) {
                        char c = line.charAt(x);
                        if (c == '.') {
                            //add getCoinList()?
                            this.coinList.add(new Coin(x, y)); //multiply by rectangle dimensions later
                            this.objList[x][y] = '.';
                        }

                        else if (c == 'X') {
                            //add getWallList()
                            this.wallList.add(new Wall(x, y)); //multiply y, x by Wall rectangle dimensions
                            this.objList[x][y] = 'X';
                        }
                        else
                            this.objList[x][y] = ' ';
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
        this.objList[ConstantVariables.INITIAL_X][ConstantVariables.INITIAL_Y] = 'A';
        //set default Enemy location
        this.objList[ConstantVariables.INITIAL_E_X][ConstantVariables.INITIAL_E_Y] = 'E';
    }


    // text-based print method
    public void printDisplay() {
        String rowString = "";

        for (int y=0; y < ConstantVariables.NUM_ROWS; y++) {
            for (int x=0; x < ConstantVariables.NUM_COL; x++) {
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
        char avatarEnemy = 'A';
        char displayCoin = ' ';
        ArrayList<Coin> cL = this.getCoinList();

        if (thing instanceof AI) {
            avatarEnemy = 'E';
            displayCoin = '.';
        }

        // Is thing moving off coin or empty?
        if (thing.getOnCoin() == true) {
            System.out.println(avatarEnemy + " is moving off a coin");
            this.setObjList(thing.getXCoord(), thing.getYCoord(), displayCoin);
        }
        else {
            System.out.println(avatarEnemy + " is moving off an empty space");
            this.setObjList(thing.getXCoord(), thing.getYCoord(), ' ');
        }

        // Is thing moving onto a Coin?
        for (Coin c: cL) {
            int xCoin = (int)c.getBox().getX();
            int yCoin = (int)c.getBox().getY();

            if ((thing.getNewXCoord() == xCoin) && (thing.getNewYCoord() == yCoin)) {
                System.out.println(avatarEnemy + " is moving onto a coin");

                if (thing instanceof Avatar) {
                    // coin on off conditions and increment avatar score + 1
                    c.setCoinOff((Avatar)thing);
                    System.out.println("Score: " + ((Avatar)thing).getScore());
                }

                thing.setOnCoin(true);
                break;
            }
            else
                thing.setOnCoin(false);
        }

        // update Coordinates
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


    // please note that this is a temporary main function
    public static void main(String[] args) {
        AnimationApp items = new AnimationApp();
        Avatar avatar = new Avatar (ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);
        AI enemy = new AI (ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y);
        items.printDisplay();
        items.setGameOnOff(true);
        while (items.gameOnOff == true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter wasd");
            String it = userInput.next();
            if ((it.equals("w")) || (it.equals("a")) || (it.equals("s")) || (it.equals("d"))) {
                System.out.println(it);
                avatar.mvAttempt(it);
                items.processMv(avatar);
                enemy.genMv(avatar, items);
                items.printDisplay();
            }
        }
        // endgame print board
        System.out.println();
        System.out.println();
        System.out.println("Game Over!!!!!");
        System.out.println("Score: " + avatar.getScore());
        for (int i=0; i < ConstantVariables.NUM_ROWS; i++) {
            String finished = "";
            for (int k=0; k < ConstantVariables.NUM_COL; k++) {
                finished += "#";
            }
            System.out.println(finished);
        }
    }
}
