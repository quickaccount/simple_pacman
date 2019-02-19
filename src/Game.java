
import javax.swing.JFrame;

public class Game {

	// pacman world originally 224 x 288 but x3 for 672 x 864
	private static final int WORLD_WIDTH = 672;
	private static final int WORLD_HEIGHT = 864;
	private static final int INITIAL_X = 450;
	private static final int INITIAL_Y = 50;

	public static void main(String[] args) {

		JFrame game = new JFrame();
		Gameplay gamePlay = new Gameplay();
		game.setBounds(INITIAL_X, INITIAL_Y, WORLD_WIDTH, WORLD_HEIGHT);
		game.setTitle("PacMan");
		game.setResizable(false);
		game.setVisible(true);
		// when user closes frame, exit application
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add the Gameplay obj inside the JFrame obj
		game.add(gamePlay);
		
	}
}
