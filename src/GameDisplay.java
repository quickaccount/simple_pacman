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

	AnimationApp items = new AnimationApp();
	Avatar avatar = new Avatar (ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);
	
	Image[] rightPacman = new Image[3];
	Image[] leftPacman = new Image[3];
	Image[] upPacman = new Image[3];
	Image[] downPacman = new Image[3];
	
	private int pac_X = ConstantVariables.INITIAL_X;
	private int pac_Y = ConstantVariables.INITIAL_Y;
	
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
	    
	    AnimatedImage pacman = new AnimatedImage();
        for (int i = 0; i < 3; i++)
            rightPacman[i] = new Image( "pacRight" + i + ".png" );
        pacman.frames = rightPacman;
        pacman.duration = 0.100;
	    
	    final long startNanoTime = System.nanoTime();
	    
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	 
	            // background image clears canvas
	            gc.drawImage(maze, 0, 0, ConstantVariables.WINDOW_WIDTH, ConstantVariables.WINDOW_HEIGHT);
	            gc.drawImage( pacman.getFrame(t), pac_X, pac_Y);
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
				//String input = "";
				switch(event.getCode()) {
				case W:
					//input = "w";
					//handleInput(input);
					pac_Y -= ConstantVariables.MOVE_AMNT;
					break;
				case A:
					//input = "a";
					//handleInput(input);
					pac_X -= ConstantVariables.MOVE_AMNT;
					break;
				case S:
					//input = "s";
					//handleInput(input);
					pac_Y += ConstantVariables.MOVE_AMNT;
					break;
				case D:
					//input = "d";
					//handleInput(input);
					pac_X += ConstantVariables.MOVE_AMNT;
					break;
				}
			}
		});
	}
	
	public void handleInput(String s) {
		/*System.out.println(s + " was pressed.");
		avatar.mvAttempt(s);
        //items.processMv(avatar, 'A');
        items.printDisplay();*/
	}
}
