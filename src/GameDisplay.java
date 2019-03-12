import constants.ConstantVariables;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

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

    private int mvRefreshCount;

    AnimationApp items = new AnimationApp();
    AnimatedImage pacman = new AnimatedImage();
    AnimatedImage blinky = new AnimatedImage();
    Avatar avatar = new Avatar (ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);	// pacman avatar we use to process movements
    AI enemy = new AI (ConstantVariables.INITIAL_E_X, ConstantVariables.INITIAL_E_Y);

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
        pacman.frames = rightPacman;
        pacman.duration = 0.150;

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
        Group root = new Group();

        Canvas canvas = new Canvas(ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image maze = new Image("maze.png");
        Image coin = new Image("coin.png");

        final long startNanoTime = System.nanoTime();	// start time in nano seconds

        new AnimationTimer()
        {
            // handle is invoked every time a frame is rendered (by javafx default, 60 times/second)
            public void handle(long currentNanoTime)
            {
                double elapsedSeconds = (currentNanoTime - startNanoTime) / 1000000000.0; // convert the elapsed time in nanoseconds to seconds

                // background image clears canvas
                gc.drawImage(maze, 0, 0, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);

                // display coins
                for (int y=0; y < ConstantVariables.NUM_ROWS; y++) {
                    for (int x=0; x < ConstantVariables.NUM_COL; x++) {
                        if (items.getItemList()[x][y] instanceof Coin) {
                            if ( ((Coin)items.getItemList()[x][y]).getCoinIsOn() ) {
                                gc.drawImage(coin, x * ConstantVariables.WIDTH + ConstantVariables.COIN_OFFSET, y * ConstantVariables.HEIGHT - ConstantVariables.COIN_OFFSET);
                            }
                        }
                    }
                }
                gc.drawImage( pacman.getFrame(elapsedSeconds), pac_X, pac_Y);
                gc.drawImage( blinky.getFrame(elapsedSeconds), blinky_X, blinky_Y);
                mvRefreshCount ++; // adds one to the refresh count since last move
                if (mvRefreshCount > 6) { //slows timer for a single move
                    timedMove("continue in current direction");
                }
            }
        }.start();

      Scene scene = new Scene(root, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT, Color.BLACK);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.sizeToScene();
      stage.show();

      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

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
      /*
    if(items.wallCheck(avatar)) {
            //if touching a wall, don't move
        } else {

          if(input.equals("w")) {
            pac_Y -= ConstantVariables.MOVE_AMNT;
          } else if(input.equals("a")) {
            pac_X -= ConstantVariables.MOVE_AMNT;
          } else if(input.equals("s")) {
            pac_Y += ConstantVariables.MOVE_AMNT;
          } else if(input.equals("d")) {
            pac_X += ConstantVariables.MOVE_AMNT;
          }
          else {
              if(avatar.getDir(0) == 0) {
                  pac_Y += avatar.getDir(1) * ConstantVariables.MOVE_AMNT;
              }
              else {
                  pac_X += avatar.getDir(0) * ConstantVariables.MOVE_AMNT;
              }
          }
    }
    */
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
