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

    // image arrays for pacman movement
    Image[] rightPacman = new Image[3];
    Image[] leftPacman = new Image[3];
    Image[] upPacman = new Image[3];
    Image[] downPacman = new Image[3];

    // image arrays for blinky movement
    Image[] rightBlinky = new Image[2];
    Image[] leftBlinky = new Image[2];
    Image[] upBlinky = new Image[2];
    Image[] downBlinky = new Image[2];

    // x and y for displaying pacman in the gui
    private int pac_X = ConstantVariables.DISPLAY_INITIAL_X;
    private int pac_Y = ConstantVariables.DISPLAY_INITIAL_Y;

    private int blinky_X = 17;	// i just put a random position for now
    private int blinky_Y = 17;

    private int currScore = 0;

    private int mvRefreshCount;

    AnimationApp items = new AnimationApp();
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
            upBlinky[i] = new Image("blinkyUp" + i + ".png");
        }

        for(int i = 0; i < 2; i++) {
            downBlinky[i] = new Image("blinkyDown" + i + ".png");
        }

        for(int i = 0; i < 2; i++) {
            leftBlinky[i] = new Image("blinkyLeft" + i + ".png");
        }

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

        stage.setTitle("Pac Man");
        //Group root = new Group();
        VBox root = new VBox();

        Canvas scoreboard = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT);
        root.getChildren().add(scoreboard);

        GraphicsContext score = scoreboard.getGraphicsContext2D();

        Canvas canvas = new Canvas(ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image maze = new Image("maze.png");
        Image coin = new Image("coin.png");

        final long startNanoTime = System.nanoTime();	// start time in nano seconds

        // updates visual display approx 60 times/seconds
        new AnimationTimer()
        {
            // handle is invoked every time a frame is rendered (by javafx default, 60 times/second)
            public void handle(long currentNanoTime)
            {
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

                gc.drawImage( pacman.getFrame(elapsedSeconds), pac_X, pac_Y);	// add pacman
                gc.drawImage( blinky.getFrame(elapsedSeconds), blinky_X, blinky_Y);	// add blinky

                score.setFont(Font.font ("Verdana", 20));
                score.setFill(Color.BLACK);
                score.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.SCOREBOARD_HEIGHT);
                score.setFill(Color.WHITE);
                String scoreString = "SCORE: " + currScore;
                score.fillText(scoreString, 10, 30);

                // have to add currScore++; once merged with the branch that collects coins

                if(avatar.intersects(enemy)) {	// if pacman and the ghost intersect
                  gc.setFont(Font.font ("Verdana", 20));
                  gc.setFill(Color.BLACK);
                  gc.fillRect(0, 0, ConstantVariables.WORLD_WIDTH, ConstantVariables.WORLD_HEIGHT);		// black out the screen
                  gc.setFill(Color.RED);
                  gc.fillText("GAME OVER!!", ConstantVariables.WINDOW_WIDTH/2 -65, ConstantVariables.WORLD_HEIGHT/2 - 20);	// display red "game over" string
                  stop();	// stop the application
                }
                mvRefreshCount ++; // adds one to the refresh count since last move
                if (mvRefreshCount > 6) { //slows timer for a single move
                    timedMove("continue in current direction");
                }
            }
        }.start();

      Scene scene = new Scene(root, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);
      stage.setScene(scene);
      stage.setResizable(false);	// sets it so that the game window is not resizable
      stage.sizeToScene();	// gets rid of exra padding around maze image
      stage.show();

      scene.setOnKeyPressed(new EventHandler<KeyEvent>() { //scene.setOnKeyReleased fixes holding key,

      @Override
      public void handle(KeyEvent event) {
        String input = "";
            switch(event.getCode()) {
            case W:
                input = "w";
                pacman.frames = upPacman;
                timedMove(input);
                //movePac(input);
                break;
            case A:
                input = "a";
                pacman.frames = leftPacman;
                timedMove(input);
                //movePac(input);
                break;
            case S:
                input = "s";
                pacman.frames = downPacman;
                timedMove(input);
                //movePac(input);
                break;
            case D:
                input = "d";
                pacman.frames = rightPacman;
                timedMove(input);
                //movePac(input);
                break;
            }
      }
    });
  }


  /**
   * Processes move for GUI pacman.
   * @param input The user input for movement.
   */
  public void movePac(String input) {
      handleInput(input);

      pac_Y = avatar.getYCoord() * ConstantVariables.MOVE_AMNT;
      pac_X = avatar.getXCoord() * ConstantVariables.MOVE_AMNT;
  }


    // temporary auto move function
    // movement based off of current player direction
    public void timedMove(String key) {
        mvRefreshCount = 0;
        avatar.mvAttempt(key);
        items.processMv(avatar);
        movePac(key);
        enemy.genMv(avatar, items);
        tempMoveAI();
        items.printDisplay();
    }


    // temporary function to update AI location after genMv
    public void tempMoveAI() {
        blinky_X = enemy.getXCoord()*16;
        blinky_Y = enemy.getYCoord()*16;
    }


  /**
   * Moves the pacman avatar/processes the move.
   * @param input The user input for movement.
   */
  public void handleInput(String input) {
      System.out.println(input + " was pressed.");
      avatar.mvAttempt(input);

  }
}
