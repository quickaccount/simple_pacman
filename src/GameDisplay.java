import java.io.FileInputStream;
import java.io.FileNotFoundException;

import constants.ConstantVariables;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameDisplay extends Application {

	AnimationApp items = new AnimationApp();
	Avatar avatar = new Avatar (ConstantVariables.INITIAL_X, ConstantVariables.INITIAL_Y);
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Pac Man");
		
		Group root = new Group();
	    Scene scene = new Scene(root, ConstantVariables.WINDOW_WIDTH-66, ConstantVariables.WINDOW_HEIGHT-70, Color.BLACK);
	    stage.setScene(scene);
	    
	    ImageView maze = new ImageView("maze.png");   /// 232 x 256
	    maze.setFitHeight(ConstantVariables.WINDOW_WIDTH);
	    maze.setFitWidth(ConstantVariables.WINDOW_HEIGHT);
	    maze.setPreserveRatio(true);
	    root.getChildren().add(maze);
	    
	    stage.show();
	}
	
	public void handleInput(String s) {
		/*System.out.println(s + " was pressed.");
		avatar.mvAttempt(s);
        //items.processMv(avatar, 'A');
        items.printDisplay();*/
	}
}
