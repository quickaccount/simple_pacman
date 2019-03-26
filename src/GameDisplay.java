import java.io.IOException;
import java.io.PrintWriter;

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

public class GameDisplay extends Application {

	// the two scenes we switch on the stage
	Scene mainMenu;
	Scene gamePlay;
	
    // image arrays for pacman movement
    Image[] rightPacman = new Image[3];
    Image[] leftPacman = new Image[3];
    Image[] upPacman = new Image[3];
    Image[] downPacman = new Image[3];

    // image arrays for blinky movement
    Image[] rightBlinky = new Image[2];
    
    // keeps track of x coord of main menu pacman animation
    private int menuAnimX = 0;

    // x and y for displaying pacman in the gui
    private int pac_X = ConstantVariables.DISPLAY_INITIAL_X;
    private int pac_Y = ConstantVariables.DISPLAY_INITIAL_Y;

    private int blinky_X =	ConstantVariables.DISPLAY_INITIAL_E;	// x and y for blinky gui display
    private int blinky_Y = ConstantVariables.DISPLAY_INITIAL_E;

    private int mvRefreshCount;	// counter to slow down movements
    private boolean gameStarted = false;	// true when we leave main menu and go to game

    ItemProcess items = new ItemProcess("maze.txt");
    AnimatedImage pacman = new AnimatedImage();
    AnimatedImage blinky = new AnimatedImage();
    Avatar avatar = new Avatar (ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);	// pacman avatar we use to process movements
    AI enemy = new AI (ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y);	// an instance of a "ghost" used to process movements

    public GameDisplay() {
    	
        //initializing pacman movement image arrays
        for (int i = 0; i < 3; i++) {
            upPacman[i] = new Image( "pacUp" + i + ".png" );
        }

        for (int i = 0; i < 3; i++) {
            downPacman[i] = new Image( "pacDown" + i + ".png" );
        }

        for (int i = 0; i < 3; i++) {
            leftPacman[i] = new Image( "pacLeft" + i + ".png" );
    }

        for (int i = 0; i < 3; i++) {
            rightPacman[i] = new Image( "pacRight" + i + ".png" );
        }
        pacman.frames = rightPacman;	// default to displaying images for pacman's rightwards movement
        pacman.duration = 0.150;	// set duration of one entire movement animation

        // initializing blinky movement image arrays
 
        for(int i = 0; i < 2; i++) {
            rightBlinky[i] = new Image("blinkyRight" + i + ".png");
        }
        blinky.frames = rightBlinky;
        blinky.duration = 0.150;
    }

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {

    	// MAIN MENU SCENE !!!!!!!!!!!!!!!!!!!!!
		
    	VBox layout1 = new VBox(20);
    	Canvas menuCanvas = new Canvas(ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
    	layout1.getChildren().add(menuCanvas);

    	GraphicsContext gcMenu = menuCanvas.getGraphicsContext2D();
    	    	
    	final long menuStartTime = System.nanoTime();	// start time in nano seconds
        // updates visual display approx 60 times/seconds
        new AnimationTimer()
        {
   
        	public void handle(long currentNanoTime) {
        		
        		gcMenu.setFill(Color.BLACK);
                gcMenu.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);		// black out the screen/clear canvas
             	Image title = new Image("title.png");	// add the pacman logo
            	gcMenu.drawImage(title, 50 ,100, 360, 84);
            	
            	gcMenu.setFont(Font.font ("Verdana", 20));
            	gcMenu.setFill(Color.WHITE);
            	gcMenu.fillText("Press [N] to start a new game.", 80, 400);	// draw message strings
            	gcMenu.fillText("Press [L] to load an existing game.", 60, 450);
            	
                double elapsedSeconds = (currentNanoTime - menuStartTime) / 1000000000.0; // convert the elapsed time in nanoseconds to seconds
                gcMenu.drawImage(pacman.getFrame(elapsedSeconds), menuAnimX, pac_Y+20);	// add pacman to display
                if(!gameStarted) {	// if the game has not started (still on main screen)
                	if(menuAnimX < ConstantVariables.WINDOW_WIDTH) {	// if not at right edge of screen
                		menuAnimX +=2;;	// move to the right           	 	
                 	} else {
                	 	menuAnimX = 0;	// if at right edge, go back to left edge
                 	}
                }
            }
         }.start();
    			
    	mainMenu = new Scene(layout1, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);	// instantiate the main menu with the respective scene
    	mainMenu.setOnKeyPressed(e -> {	// check for user input
    		switch(e.getCode()) {
    		case N:	// if they press n key
    			gameStarted = true;	// game has begun
    			stage.setScene(gamePlay);	// change the scene to the game scene
    			break;
    			
    		case L:	// if pressed l 
    			items = new ItemProcess("savedGame.txt");	// process the game with saved game text file
    			gameStarted = true;
    			stage.setScene(gamePlay);
    			break;
    		}
    	});    	
    	
    	// GAME PLAY SCENE !!!!!!!!
        VBox root = new VBox();

        Canvas scoreboard = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT);
        Canvas canvas = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);
        root.getChildren().addAll(scoreboard, canvas);

        GraphicsContext score = scoreboard.getGraphicsContext2D();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image maze = new Image("maze.png");
        Image coin = new Image("coin.png");

        final long startNanoTime = System.nanoTime();	// start time in nano seconds

        // updates visual display approx 60 times/seconds
        new AnimationTimer()
        {
            // handle is invoked every time a frame is rendered (by javafx default, 60 times/second)
        	public void handle(long currentNanoTime) {
        		double elapsedSeconds = (currentNanoTime - startNanoTime) / 1000000000.0; // convert the elapsed time in nanoseconds to seconds

                // background image essentially "clears" canvas
                gc.drawImage(maze, 0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);

                // display coins
                for (int y=0; y < ConstantVariables.NUM_ROWS; y++) {	// go through every row
                    for (int x=0; x < ConstantVariables.NUM_COL; x++) {		// and column
                        if (items.getItemList()[x][y] instanceof Coin) {	// of the itemList and if the item is of type Coin
                        	if ( ((Coin)items.getItemList()[x][y]).getCoinIsOn() ) {	// and it is "on" (hasn't been collected yet)
                              // draw the coin
                                gc.drawImage(coin, x * ConstantVariables.WIDTH + ConstantVariables.COIN_OFFSET, y * ConstantVariables.HEIGHT - ConstantVariables.COIN_OFFSET);
                            }
                        }
                    }
                }

                gc.drawImage( pacman.getFrame(elapsedSeconds), pac_X, pac_Y);	// add pacman to display
                gc.drawImage( blinky.getFrame(elapsedSeconds), blinky_X, blinky_Y);	// add blinky to display

                // display ScoreBoard
                score.setFont(Font.font ("Verdana", 20));
                score.setFill(Color.BLACK);
                score.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT);
                score.setFill(Color.WHITE);
                String scoreString = "SCORE: " + avatar.getScore();
                score.fillText(scoreString, 10, 30);

                // display End Game and stop application
                if(avatar.intersects(enemy)) {	// if pacman and the ghost intersect
                  gc.setFont(Font.font ("Verdana", 20));
                  gc.setFill(Color.BLACK);
                  gc.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);		// black out the screen
                  gc.setFill(Color.RED);
                  gc.fillText("GAME OVER!!", ConstantVariables.WINDOW_WIDTH/2 -65, ConstantVariables.WORLD_HEIGHT/2 - 20);	// display red "game over" string
                  stop();	// stop the application
                }

                mvRefreshCount ++; // adds one to the refresh count since last move
                // Calls the timedMove method, which will be replaced by a separate main class with its own timer
                if (mvRefreshCount > 18 && gameStarted) { // change the number to slow the move timer
                    timedMove("continue in current direction");

                }
            }
        }.start();

      gamePlay = new Scene(root, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);	// instantiate game scene with the layout we just made
      
      stage.setScene(mainMenu);	// start application on main menu
      stage.setTitle("Pac Man");
      stage.setResizable(false);	// sets it so that the game window is not resizable
      stage.sizeToScene();	// gets rid of exra padding around maze image
      stage.show();

      gamePlay.setOnKeyPressed(new EventHandler<KeyEvent>() { //scene.setOnKeyReleased fixes holding key,

      @Override
      public void handle(KeyEvent event) {
        String input = "";
            switch(event.getCode()) {
            case W:
                input = "w";
                pacman.frames = upPacman;
                timedMove(input);
                break;
            case A:
                input = "a";
                pacman.frames = leftPacman;
                timedMove(input);
                break;
            case S:
                input = "s";
                pacman.frames = downPacman;
                timedMove(input);
                break;
            case D:
                input = "d";
                pacman.frames = rightPacman;
                timedMove(input);
                break;
            case P:
            	try {
            		saveToTextFile("savedGame.txt");
					stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
            	break;
            }
            
      }
    });
  }


  /**
   * Processes move for GUI pacman.
   * @param input The user input for movement.
   */
  private void movePac(String input) {

      handleInput(input);

      pac_Y = avatar.getYCoord() * ConstantVariables.MOVE_AMNT;
      pac_X = avatar.getXCoord() * ConstantVariables.MOVE_AMNT;
  }


    // temporary auto move function
    // movement based off of current player direction
    private void timedMove(String key) {

        mvRefreshCount = 0;
        avatar.mvAttempt(key);
        items.processMv(avatar);
        movePac(key);
        enemy.genMv(avatar, items);
        tempMoveAI();
    }


    // temporary function to update AI location after genMv
    private void tempMoveAI() {

        blinky_X = enemy.getXCoord() * ConstantVariables.MOVE_AMNT;
        blinky_Y = enemy.getYCoord() * ConstantVariables.MOVE_AMNT;
    }


  /**
   * Moves the pacman avatar/processes the move.
   * @param input The user input for movement.
   */
  private void handleInput(String input) {
      //System.out.println(input + " was pressed.");
      avatar.mvAttempt(input);

  }
  
  public void saveToTextFile(String gameName) throws IOException{
	  PrintWriter writer = new PrintWriter(gameName);
	  String line = null;
	  Item[][] itemList = items.getItemList();
	  
	  writer.println("score: " + avatar.getScore() + " avatar coords: " + avatar.getXCoord() + ", " + avatar.getYCoord() + " avatar display: " + pac_X + ", " + pac_Y +
			  " enemy coords: " + enemy.getXCoord() + ", " + enemy.getYCoord() + " enemy display: " + blinky_X + ", " + blinky_Y);
	  
	  for(int row = 0; row < itemList.length; row++) {
		  for(int col = 0; col < itemList[0].length; col++) {
			  
			  if(itemList[row][col] instanceof Coin) {
				  
				  if(((Coin)items.getItemList()[row][col]).getCoinIsOn()) {
					  writer.print(".");
				  } else {
					  writer.print(" ");
				  }
			  } else if(itemList[row][col] instanceof Wall) {
				  writer.print("X");
			  }	
		  }
		  writer.println();
	  }
  	
	  writer.close();
  }
}
