import java.io.IOException;

import constants.ConstantVariables;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

/**
 * GameDisplay is the main class of this program. It is a JavaFX Application and
 * is responsible for presenting the GUI.
 */
public class GameDisplay extends Application {

	// the scenes we switch between
	Scene mainMenu;
	Scene gamePlay;
	Scene pausedMenu;
	Scene gameEnd;

	// image arrays for pacman movement
	Image[] rightPacman = new Image[3];
	Image[] leftPacman = new Image[3];
	Image[] upPacman = new Image[3];
	Image[] downPacman = new Image[3];

	// image array for blinky movement
	Image[] rightBlinky = new Image[2];

	// audio games used in game
	AudioClip introMusic = new AudioClip(this.getClass().getResource("sounds/pacman_beginning.wav").toString());
	AudioClip endingSound = new AudioClip(this.getClass().getResource("sounds/pacman_death.wav").toString());
	AudioClip munchSound = new AudioClip(this.getClass().getResource("sounds/pacman_chomp.wav").toString());

	// keeps track of x coord of main menu Pac-Man animation
	private int menuAnimX = 0;

	// x and y for displaying Pac-Man in the game GUI
	private int pac_X = ConstantVariables.DISPLAY_INITIAL_X;
	private int pac_Y = ConstantVariables.DISPLAY_INITIAL_Y;

	private int blinky_X = ConstantVariables.DISPLAY_INITIAL_E; // x and y for blinky gui display
	private int blinky_Y = ConstantVariables.DISPLAY_INITIAL_E;

	private int mvRefreshCount; // counter to slow down movements
	private boolean gameStarted = false; // true when we leave main menu and go to game
	private boolean gamePaused = false; // keeps track of whether the game is paused or not
	ItemProcess items = new ItemProcess("maze.txt"); // ItemProcess instance to serve as logic behind game display
	AnimatedImage pacman = new AnimatedImage(); // generates pacman animation
	AnimatedImage blinky = new AnimatedImage(); // generates blinky animation
	Avatar avatar = new Avatar(ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y); // pacman avatar we use to
																							// process movements
	AI enemy = new AI(ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y); // an instance of a "ghost" used to
																						// process movements

	/**
	 * Constructs a GameDisplay object. Initializes Image arrays for Pac-Man and
	 * Blinky movement animations.
	 */
	public GameDisplay() {

		// initializing pacman movement image arrays
		for (int i = 0; i < 3; i++) {
			upPacman[i] = new Image("images/pacUp" + i + ".png");
		}

		for (int i = 0; i < 3; i++) {
			downPacman[i] = new Image("images/pacDown" + i + ".png");
		}

		for (int i = 0; i < 3; i++) {
			leftPacman[i] = new Image("images/pacLeft" + i + ".png");
		}

		for (int i = 0; i < 3; i++) {
			rightPacman[i] = new Image("images/pacRight" + i + ".png");
		}
		pacman.frames = rightPacman; // default to displaying images for pacman's rightward movement
		pacman.duration = 0.150; // set duration of one entire movement animation

		// initializing blinky movement image arrays
		for (int i = 0; i < 2; i++) {
			rightBlinky[i] = new Image("images/blinkyRight" + i + ".png");
		}
		blinky.frames = rightBlinky;
		blinky.duration = 0.150;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		// CREATION OF GAME PLAY SCENE !!!!!!!!
		VBox root = new VBox();

		Canvas scoreboard = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT); // canvas for displaying score
		Canvas canvas = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT); // canvas for displaying game play
		GraphicsContext score = scoreboard.getGraphicsContext2D();
		GraphicsContext gc = canvas.getGraphicsContext2D();

		root.getChildren().addAll(scoreboard, canvas);

		Image maze = new Image("images/maze.png");
		Image coin = new Image("images/coin.png");

		final long startNanoTime = System.nanoTime(); // start time in nano seconds

		// updates visual display approx 60 times/seconds
		AnimationTimer game = new AnimationTimer() {
			// handle is invoked every time a frame is rendered (by javafx default,
			// 60times/second)
			public void handle(long currentNanoTime) {
				double elapsedSeconds = (currentNanoTime - startNanoTime) / 1000000000.0; // convert the elapsed time in
																							// nanoseconds to seconds
				// background image essentially "clears" canvas
				gc.drawImage(maze, 0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);

				// display coins
				for (int y = 0; y < ConstantVariables.NUM_ROWS; y++) { // go through every row
					for (int x = 0; x < ConstantVariables.NUM_COL; x++) { // and column
						if (items.getItemList()[x][y] instanceof Coin) { // of the itemList and if the item is of type Coin
							if (((Coin) items.getItemList()[x][y]).getCoinIsOn()) { // and it is "on" (hasn't been collected yet)
								gc.drawImage(coin, x * ConstantVariables.WIDTH + ConstantVariables.COIN_OFFSET, y * ConstantVariables.HEIGHT - ConstantVariables.COIN_OFFSET);
							}
						}
					}
				}

				gc.drawImage(pacman.getFrame(elapsedSeconds), pac_X, pac_Y); // add pacman to display
				gc.drawImage(blinky.getFrame(elapsedSeconds), blinky_X, blinky_Y); // add blinky to display

				// display ScoreBoard
				score.setFont(Font.font("Verdana", 20));
				score.setFill(Color.BLACK);
				score.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT);
				score.setFill(Color.WHITE);
				String scoreString = "SCORE: " + avatar.getScore();
				String pressL = "[SPACE]: Pause-Game";
				score.fillText(scoreString, 10, 30);
				score.fillText(pressL, 226, 30);

				// display End Game and stop application
				if (items.getGameOn() == false) { // if pacman and the ghost intersect
					munchSound.stop();
					// endingSound.play();
					stage.setScene(gameEnd);
				}

				mvRefreshCount++; // adds one to the refresh count since last move

				// Calls the timedMove method
				if (mvRefreshCount > 18 && items.getGameOn() == true && gameStarted) { // increasing this number will slow the game
					timedMove("continue in current direction");
				}
			}
		};
		game.start();

		// instantiate game scene with the layout we just made
		gamePlay = new Scene(root, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);

		gamePlay.setOnKeyPressed(e -> {

			String input = "";
			if (items.getGameOn() == true) {
				switch (e.getCode()) {
				case W: // if pressed w
					if (!gamePaused) { // and game isn't paused
						input = "w";
						pacman.frames = upPacman; // change the direction of animation frames
						if (avatar.changeDirection(input, items) == true) { // if change in direction is made
							timedMove(input); // move pacman up (according to input)
						}
					}
					break;
				case A:
					if (!gamePaused) {
						input = "a";
						pacman.frames = leftPacman;
						if (avatar.changeDirection(input, items) == true) {
							timedMove(input);
						}

					}
					break;
				case S:
					if (!gamePaused) {
						input = "s";
						pacman.frames = downPacman;
						if (avatar.changeDirection(input, items) == true) {
							timedMove(input);
						}

					}
					break;
				case D:
					if (!gamePaused) {
						input = "d";
						pacman.frames = rightPacman;
						if (avatar.changeDirection(input, items) == true) {
							timedMove(input);
						}
					}
					break;
				case SPACE: // if space bar is pressed
					gamePaused = true;
					stage.setScene(pausedMenu); // change to the paused menu scene
					munchSound.stop(); // stop the gameplay sound
					game.stop(); // pause the animation timer (and consequently the game)
					break;

				}
			}
		});
		// END OF GAME PLAY SCENE

		// CREATION OF MAIN MENU SCENE !!!!!!!!!!!!!!!!!!!!!
		introMusic.play();
		VBox layout1 = new VBox(20);
		Canvas menuCanvas = new Canvas(ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
		layout1.getChildren().add(menuCanvas);

		GraphicsContext gcMenu = menuCanvas.getGraphicsContext2D();

		final long menuStartTime = System.nanoTime(); // start time in nano seconds
		// updates visual display approx 60 times/seconds
		Image title = new Image("images/title.png");
		new AnimationTimer() {

			public void handle(long currentNanoTime) {

				gcMenu.setFill(Color.BLACK);
				gcMenu.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT + 10); // black out the
																											// screen/clear canvas
				gcMenu.drawImage(title, 50, 100, 360, 84); // add the pacman logo to main menu

				gcMenu.setFont(Font.font("Verdana", 20));
				gcMenu.setFill(Color.ORANGE);
				gcMenu.fillText("Press [N] to start a new game.", 80, 400); // draw message strings
				gcMenu.fillText("Press [L] to load an existing game.", 60, 450);

				double elapsedSeconds = (currentNanoTime - menuStartTime) / 1000000000.0; // convert the elapsed time in
																							// nanoseconds to seconds
				gcMenu.drawImage(pacman.getFrame(elapsedSeconds), menuAnimX, pac_Y + 20); // add pacman to display
				if (!gameStarted) { // if the game has not started (still on main screen)
					if (menuAnimX < ConstantVariables.WINDOW_WIDTH) { // if not at right edge of screen
						menuAnimX += 2; // move to the right
					} else {
						menuAnimX = 0; // if at right edge, go back to left edge
					}
				}
			}
		}.start();

		// instantiate the main menu with the respective scene
		mainMenu = new Scene(layout1, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);
		
		mainMenu.setOnKeyPressed(e -> { // check for user input
			switch (e.getCode()) {
			case N: // if they press n key
				gameStarted = true; // game has begun
				stage.setScene(gamePlay); // change the scene to the game scene
				introMusic.stop();
				munchSound.setCycleCount(AudioClip.INDEFINITE);
				munchSound.play();
				break;

			case L: // if pressed l
				items = new ItemProcess("savedGame.txt", GameDisplay.this); // process the game with saved game text
																			// file
				gameStarted = true;
				stage.setScene(gamePlay);
				introMusic.stop();
				munchSound.setCycleCount(AudioClip.INDEFINITE);
				munchSound.play();
				break;
			}
		});
		// END OF MAIN MENU SCENE

		// CREATION OF PAUSED GAME MENU SCENE
		VBox layout2 = new VBox(20);
		Canvas pausedCanvas = new Canvas(ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
		layout2.getChildren().add(pausedCanvas);

		GraphicsContext gcPaused = pausedCanvas.getGraphicsContext2D();

		new AnimationTimer() {

			@Override
			public void handle(long currentNanoTime) {
				gcPaused.setFill(Color.BLACK);
				gcPaused.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT); // clears canvas
				gcPaused.setFont(Font.font("Verdana", 50));
				gcPaused.setFill(Color.LIGHTBLUE);
				gcPaused.fillText("GAME PAUSED", 50, 160); // draw message string
				gcPaused.setFont(Font.font("Verdana", 20));
				gcPaused.setFill(Color.ORANGE);
				gcPaused.fillText("Press [SHIFT] to resume your game", 55, 250); // draw message strings
				gcPaused.fillText("Press [N] to return to main menu", 70, 300);
				gcPaused.fillText("Press [S] to save your current\n            game progress", 70, 350);
			}

		}.start();

		pausedMenu = new Scene(layout2, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);

		pausedMenu.setOnKeyPressed(e -> {

			if (items.getGameOn() == true) {
				switch (e.getCode()) {
				case SHIFT:
					stage.setScene(gamePlay);
					game.start();
					munchSound.play();
					gamePaused = false;
					break;
				case S:
					try {
					    items.saveToTextFile("savedGame.txt", avatar, enemy);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				case N:
					stage.setScene(mainMenu);
					introMusic.play();
					game.start();
					restartGame();
					pacman.frames = rightPacman;
					break;
				}
			}
		});
		// END OF PAUSED MENU SCENE

		// CREATION OF GAME END SCENE
		VBox layout3 = new VBox(20);
		Canvas endCanvas = new Canvas(ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
		layout3.getChildren().add(endCanvas);

		GraphicsContext gcEnd = endCanvas.getGraphicsContext2D();

		new AnimationTimer() {
			// name animation timers and stop them when we switch scenes?
			@Override
			public void handle(long now) {
				gcEnd.setFont(Font.font("Verdana", 40));
				gcEnd.setFill(Color.BLACK);
				gcEnd.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT); // black out the
																										// screen
				if (items.getWin() == false) {
					// endingSound.play();
					gcEnd.setFill(Color.RED);
					gcEnd.fillText("GAME OVER!!", ConstantVariables.WINDOW_WIDTH / 2 - 140,
							ConstantVariables.WORLD_HEIGHT / 2 - 30); // display red "game over" string
				} else {
					gcEnd.setFill(Color.BLUE);
					gcEnd.fillText("You Win, kudos!", ConstantVariables.WINDOW_WIDTH / 2 - 160,
							ConstantVariables.WORLD_HEIGHT / 2 - 30); // display blue "You Win, kudos!" string
				}
				gcEnd.setFont(Font.font("Verdana", 20));
				gcEnd.setFill(Color.WHITE);
				gcEnd.fillText("Press [SPACE] to play again.", 90, 300);

			}

		}.start();

		gameEnd = new Scene(layout3, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);

		gameEnd.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case SPACE:
				stage.setScene(mainMenu);
				introMusic.play();
				game.start();
				restartGame();
				pacman.frames = rightPacman;
				break;
			}

		});
		
		// END OF GAME END SCENE
		stage.setScene(mainMenu); // start application on main menu
		stage.setTitle("Simple PacMan");
		stage.setResizable(false); // sets it so that the game window is not resizable
		stage.sizeToScene(); // gets rid of exra padding around maze image
		stage.show();
	}

	/**
	 * update gui location for the avatar and enemy
	 */
	private void moveMovableItem() {
		pac_Y = avatar.getYCoord() * ConstantVariables.MOVE_AMNT;
		pac_X = avatar.getXCoord() * ConstantVariables.MOVE_AMNT;
		blinky_X = enemy.getXCoord() * ConstantVariables.MOVE_AMNT;
		blinky_Y = enemy.getYCoord() * ConstantVariables.MOVE_AMNT;
	}


	/**
	 * auto-move function. Movement is based off of current player direction.
	 * 
	 * @param key
	 */
	private void timedMove(String key) {
	    mvRefreshCount = 0; // reset timed move counter
	    avatar.setNewCoord(avatar.getDir(0) + avatar.getXCoord(), avatar.getDir(1) + avatar.getYCoord());
	    items.processMv(avatar); // attempt avatar move to newCoord()
	    enemy.genMv(avatar, items); // generate enemy move

	    // update gui display
	    moveMovableItem(); // gui

	    // end game checks
	    items.avatarEnemyCollision(enemy); // check for Loss
	    items.allCollected(avatar); // check for Win
	    
	    // update display
	    printDisplay(avatar, enemy); //text-based
}

	/**
	 * keeps gui stay in sync with avatar
	 */
	private void handleInput() {}

	/**
	 * Prints the text-based display in the console.
	 * 
	 * @param avatar the pacman Avatar object.
	 * @param enemy  the ghost AI (blinky) object.
	 */
	private void printDisplay(Avatar avatar, AI enemy) {
		System.out.println("Score: " + avatar.getScore());
		String rowString = "";
		if (items.getGameOn() == true) {
			for (int y = 0; y < ConstantVariables.NUM_ROWS; y++) {
				for (int x = 0; x < ConstantVariables.NUM_COL; x++) {
					if (x == avatar.getXCoord() && y == avatar.getYCoord()) {
						rowString += ConstantVariables.AV_CHAR;
					} else if (x == enemy.getXCoord() && y == enemy.getYCoord()) {
						rowString += ConstantVariables.AI_CHAR;
					} else
						rowString += items.getObjList(x, y);
				}
				System.out.println(rowString);
				rowString = "";
			}
		} else {
			System.out.println("GAME OVER!");
			for (int y = 0; y < ConstantVariables.NUM_ROWS; y++) {
				for (int x = 0; x < ConstantVariables.NUM_COL; x++) {
					rowString += '#';
				}
				System.out.println(rowString);
				rowString = "";
			}
		}
	}

	/**
	 * Applies saved game values.
	 * 
	 * @param loadedVals an array of Strings that hold the saved game values
	 */
	public void loadSavedValues(String[] loadedVals) { // loads in and applies the saved score and movable item coords
		avatar.setScore(Integer.valueOf(loadedVals[0]));
		avatar.setXYCoord(Integer.valueOf(loadedVals[1]), Integer.valueOf(loadedVals[2]));
		pac_X = Integer.valueOf(Integer.valueOf(loadedVals[1])*16);
		pac_Y = Integer.valueOf((Integer.valueOf(loadedVals[2])*16));
		
		enemy.setXYCoord(Integer.valueOf(loadedVals[3]), Integer.valueOf(loadedVals[4]));
		blinky_X = Integer.valueOf(Integer.valueOf(loadedVals[3])*16);
		blinky_Y = Integer.valueOf((Integer.valueOf(loadedVals[4])*16));
	}

	/**
	 * Resets game values so you can play again.
	 */
	public void restartGame() {
		this.items = new ItemProcess("maze.txt");
		pac_X = ConstantVariables.DISPLAY_INITIAL_X;
		pac_Y = ConstantVariables.DISPLAY_INITIAL_Y;

		blinky_X = ConstantVariables.DISPLAY_INITIAL_E; // x and y for blinky gui display
		blinky_Y = ConstantVariables.DISPLAY_INITIAL_E;

		avatar = new Avatar(ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);
		enemy = new AI(ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y);

		mvRefreshCount = 0; // counter to slow down movements
		gameStarted = false; // true when we leave main menu and go to game
		gamePaused = false;
		System.out.println("RESTARTING GAME");
	}
}
