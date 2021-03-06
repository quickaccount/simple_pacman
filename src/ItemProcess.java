import java.io.*;
import java.util.ArrayList;
import constants.ConstantVariables;

import java.lang.Math;

/**
 * The ItemProcess class is an updated version of our text-based AnimationApp,
 * but it was renamed to better reflect its functionality. This class is most of
 * the logic behind the app. It loads in a text file and creates lists of
 * objects so that we can check collisions.
 */
public class ItemProcess {

	private ArrayList<Coin> coinList = new ArrayList<Coin>(); // ArrayList of Coin objects in the maze
	private ArrayList<Wall> wallList = new ArrayList<Wall>(); // ArrayList of Wall objects in the maze
	private Item[][] itemList = new Item[ConstantVariables.NUM_COL][ConstantVariables.NUM_ROWS]; // 2D array of Items in the maze
	private char[][] objList = new char[ConstantVariables.NUM_COL][ConstantVariables.NUM_ROWS]; // 2D array of char representations of
																								// Items (for textbased display in console)
	private boolean gameOn = false;
	private String[] loadedVals;
	private boolean win;

	/**
	 * Constructor that creates ItemProcess items and loads in data from the
	 * specified text file to populate wallList, coinList, objList and itemList.
	 * 
	 * @param file the name of the text file to load in and use to create lists of Items.
	 */
	public ItemProcess(String file) {
		this.setGameOn(true);

		// The name of the file containing the display template.
		String fileName = file;

		// Line Reference
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader template = new FileReader(fileName);
			BufferedReader bTemplate = new BufferedReader(template);
			int y = 0;

			if (fileName.equals("savedGame.txt")) {
				bTemplate.readLine(); // skip first
			}

			while ((line = bTemplate.readLine()) != null) {

				for (int x = 0; x < ConstantVariables.NUM_COL; x++) {

					char c = line.charAt(x);

					if (c == ConstantVariables.COIN_CHAR || c == ConstantVariables.EMPTY_CHAR) { // for now.. treat empty space as a deactivated coin

						Coin cCoin = new Coin(x, y);
						if (c == ConstantVariables.EMPTY_CHAR) {
							Avatar temp = new Avatar(0, 0);
							cCoin.setCoinOff(temp);
							this.objList[x][y] = ' '; // empty-coin
						} else {
							this.objList[x][y] = '.'; // text-based only
						}
						this.coinList.add(cCoin);
						this.itemList[x][y] = cCoin;

					} else if (c == ConstantVariables.WALL_CHAR) {

						this.objList[x][y] = 'X'; // text-based
						Wall w = new Wall(x, y);
						this.wallList.add(w);
						this.itemList[x][y] = w;
					} else {
						this.objList[x][y] = ' '; // empty-coin
					}
				}
				y++;
			}
			// Close default_display.txt
			bTemplate.close();
		}
		// Error checking
		catch (FileNotFoundException ex) {
			System.out.println("Cannot open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	/**
	 * Constructor for creating a game from a saved game text file.
	 * 
	 * @param file the name of the saved game text file.
	 * @param gd the GameDisplay object that we would like to apply the loaded values to.
	 */
	public ItemProcess(String file, GameDisplay gd) {
		this(file);

		String line = null;
		try {

			FileReader template = new FileReader(file);
			BufferedReader bTemplate = new BufferedReader(template);
			line = bTemplate.readLine();
			if (line != null) {
				loadedVals = line.split(" ");
			}
			bTemplate.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file '" + file + "'");
		} catch (IOException e) {
			System.out.println("Error reading file '" + file + "'");
		}

		gd.loadSavedValues(loadedVals);
	}
	
        /**
	 * Creates a text file to save game progress of score, collected coins and
	 * MovableItem coordinates.
	 * 
	 * @param gameName the name that you would like to save the game textfile as
	 * @param avatar to get XY Coordinates
	 * @param enemy to get XY Coordinates 
	 * @throws IOException
	 */
        public void saveToTextFile(String gameName, Avatar avatar, AI enemy) throws IOException {
	    PrintWriter writer = new PrintWriter(gameName);
	    String line = null;
	
	    writer.println(avatar.getScore() + " " + avatar.getXCoord() + " " + avatar.getYCoord() + " " + enemy.getXCoord() + " " + enemy.getYCoord());
	
	    for (int y = 0; y < ConstantVariables.NUM_ROWS; y++) {
	        for (int x = 0; x < ConstantVariables.NUM_COL; x++) {
	    	    if (itemList[x][y] instanceof Coin) {
		        if (((Coin) this.getItemList()[x][y]).getCoinIsOn()) {
			    writer.print(".");
		        } else {
		    	    writer.print(" ");
		        }
		    } else if (itemList[x][y] instanceof Wall) {
		        writer.print("X");
		    }
	        }
	        writer.println();
	    }
	    writer.close();
        }

	/**
	 * Return the full list of coins in the maze.
	 * 
	 * @return an ArrayList with all coins inside
	 */
	private ArrayList<Coin> getCoinList() {
		return this.coinList;
	}

	/**
	 * Return list of all walls in the maze.
	 * 
	 * @return an ArrayList with all the walls inside
	 */
	private ArrayList<Wall> getWallList() {
		return this.wallList;
	}

	/**
	 * Returns the 2D array of Items.
	 * 
	 * @return array of Items.
	 */
	public Item[][] getItemList() {
		return this.itemList;
	}

	/**
	 * Returns a printable list with char representations of objects
	 * 
	 * @return list of char represented objects
	 */
	public char[][] getObjList() {
		return this.objList;
	}

	/**
	 * Returns the char representations of the object at the specified index.
	 * 
	 * @return the char at the specified index in the objList array.
	 */
	public char getObjList(int x, int y) {
		return this.objList[x][y];
	}

	/**
	 * Collision checking of moving objects.
	 * Checks for MovableItem collision with coins and walls.
	 * 
	 * @param thing the MovableItem to process movement for.
	 */
	public void processMv(MovableItem thing) {
		if (this.wallCheck(thing) == true) {	// check for wall collision
			return;
		}

		// coin collision checking
		ArrayList<Coin> cL = this.getCoinList();
		Item item = this.getItemList()[thing.getXCoord() / 16][thing.getYCoord() / 16];

		// Is thing moving off coin or empty? display the position that the avatar moved off of
		if (thing.getOnCoin()) {
			thing.setOnCoin(false);
		}

		// check if MovableItem moving onto a coin turn coin off if appropriate, then move Movable item
		if (this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()] instanceof Coin) {
			Coin coinNewLoc = (Coin) this.getItemList()[thing.getNewXCoord()][thing.getNewYCoord()];
			if ((thing instanceof Avatar) && coinNewLoc.getCoinIsOn()) {
				coinNewLoc.setCoinOff((Avatar) thing);
				this.objList[thing.getXCoord()][thing.getYCoord()] = ' ';
			}
			thing.setOnCoin(true);
		}

		// update thing Coordinates
		thing.setXYCoord(thing.getNewXCoord(), thing.getNewYCoord());
	}

	/**
	 * Collision checking of moving objects and walls/boundaries.
	 * 
	 * @param thing the MovableItem we want to check for wall collisions.
	 * @return true if thing is at a wall, false if not at a wall.
	 **/
	public boolean wallCheck(MovableItem thing) {
		if ((thing.getNewXCoord() < 0) || (thing.getNewXCoord() > ConstantVariables.NUM_COL - 1)
				|| (thing.getNewYCoord() < 0) || (thing.getNewYCoord() > ConstantVariables.NUM_ROWS - 1)) {
			System.out.println("Attempted to leave boundary");
			return true;
		}
		ArrayList<Wall> wL = this.getWallList();
		for (Wall w : wL) {
			int xWall = w.getXCoord();
			int yWall = w.getYCoord();
			if ((thing.getNewXCoord() == xWall) && (thing.getNewYCoord() == yWall)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Change whether the game is on or off.
	 * 
	 * @param onOff the boolean value of whether the game is on or not.
	 */
	public void setGameOn(boolean onOff) {
		this.gameOn = onOff;
	}

	/**
	 * Get the boolean value of whether the game is on or off.
	 * 
	 * @return the value of the gameOn boolean.
	 */
	public boolean getGameOn() {
		return new Boolean(this.gameOn);
	}

	/**
	 * Sets the win condition to true. Turns game off.
	 */
	private void winCondition() {
		this.win = true;
		this.setGameOn(false);
	}

	/**
	 * Check whether a gameOver was caused by a win or a loss.
	 */
	public boolean getWin() {
		return this.win;
	}

	/**
	 * Checks for collision of an Avatar with an AI object.
	 * 
	 * @param enemy the enemy to check for collision with.
	 */
	public void avatarEnemyCollision(AI enemy) {
		// Enemy-Avatar collision check
		if (((Math.abs(enemy.getGoalDistanceX()) <= 1 && enemy.getGoalDistanceY() == 0)
				|| (Math.abs(enemy.getGoalDistanceY()) <= 1 && enemy.getGoalDistanceX() == 0))) {
			this.setGameOn(false);
			return;
		} else {
			return;
		}
	}

	/**
	 * End game condition. If all coins are collected, turns the game off.
	 * 
	 * @param player the Pac-Man avatar player that collects coins.
	 */
	public void allCollected(Avatar player) {
		if (player.getScore() >= this.coinList.size()-ConstantVariables.SPACES_IN_MID) {
			this.winCondition();
		}
	}
}
