
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener {

	private int score = 0;
	
	private static final int WORLD_WIDTH = 672;
	private static final int WORLD_HEIGHT = 864;
	private static final int INITIAL_X = WORLD_WIDTH/2;
	private static final int INITIAL_Y = WORLD_HEIGHT/2;
	private static final int BORDER_WIDTH = 3;
	private static final int FONT_SIZE = 25;
	private static final int SCORE_X = 540;
	private static final int SCORE_Y = 40;
	private static final int MOVE_AMOUNT = 10;
	private static final int RESIZE_FACTOR = 2;
	private static final int LEFT_THRESHOLD = 18;
	private static final int RIGHT_THRESHOLD = WORLD_WIDTH-35;
	private static final int UP_THRESHOLD = 21;
	private static final int DOWN_THRESHOLD = WORLD_HEIGHT-65;
	private static final int SPRITE_DIM = 16;
	private static final int NUM_ANIMATIONS = 3;
	private static final int MOVE_TIMER = 1;

	private int playerX = INITIAL_X;
	private int playerY = INITIAL_Y;
	private int leftMoveCounter = 0;
	private int leftMoveIndex = 0;
	private int rightMoveCounter = 0;
	private int rightMoveIndex = 0;
	private int upMoveCounter = 0;
	private int upMoveIndex = 0;
	private int downMoveCounter = 0;
	private int downMoveIndex = 0;

	BufferedImage sprite;
	SpriteSheet ss;
	
	public Gameplay() {

		addKeyListener(this);
		setFocusable(true);
		loadPac();
	}
	
	// this method is from https://www.youtube.com/watch?v=sxBknKI2BfQ
	private void loadPac() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = loader.loadImage("pacman_movement.png");		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		ss = new SpriteSheet(spriteSheet);

		sprite = resize(ss.grabSprite(0, 0, 16, 16));
	}

	public void paint(Graphics g) {

		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, WORLD_WIDTH, WORLD_HEIGHT);

		// borders
		g.setColor(Color.blue);
		g.fillRect(0, 0, BORDER_WIDTH, WORLD_HEIGHT);
		g.fillRect(0, 0, WORLD_WIDTH, BORDER_WIDTH);
		g.fillRect(WORLD_WIDTH-9, 0, BORDER_WIDTH, WORLD_HEIGHT);
		g.fillRect(0, WORLD_HEIGHT-38, WORLD_WIDTH, BORDER_WIDTH);

		// score
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));
		g.drawString("score: " + score, SCORE_X, SCORE_Y);

		// pacman
		g.drawImage(sprite, playerX, playerY, null);
		repaint();

		g.dispose();
	}

	// this method from https://github.com/paulkr/Flappy-Bird/blob/master/lib/Sprites.java
	private static BufferedImage resize(BufferedImage img) {
		
		int newWidth = (int) (img.getWidth() * RESIZE_FACTOR);
		int newHeight = (int) (img.getHeight() * RESIZE_FACTOR);
		
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(img, 0, 0, newWidth, newHeight, null);
		g.dispose();

		return resizedImage;
		
	}
	
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < LEFT_THRESHOLD) {
				playerX = LEFT_THRESHOLD;
			} else {
				moveLeft();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= RIGHT_THRESHOLD) {
				playerX = RIGHT_THRESHOLD;
			} else {
				moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (playerY < UP_THRESHOLD) {
				playerY = UP_THRESHOLD;
			} else {
				moveUp();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (playerY >= DOWN_THRESHOLD) {
				playerY = DOWN_THRESHOLD;
			} else {
				moveDown();
			}
		}
	}

	public void moveLeft() {
		
		playerX -= MOVE_AMOUNT;
		if(leftMoveIndex < NUM_ANIMATIONS) {
			
			if(leftMoveCounter > MOVE_TIMER) {
				sprite = resize(ss.grabSprite(SPRITE_DIM*leftMoveIndex, SPRITE_DIM, SPRITE_DIM, SPRITE_DIM));
				leftMoveIndex++;
				leftMoveCounter=0;
			}
			else {
				leftMoveCounter++;
			}
			
		}
		else {
			leftMoveIndex=0;
		}
	}

	public void moveRight() {
		
		playerX += MOVE_AMOUNT;
		if(rightMoveIndex < NUM_ANIMATIONS) {
			
			if(rightMoveCounter > MOVE_TIMER) {
				sprite = resize(ss.grabSprite(SPRITE_DIM*rightMoveIndex, 0, SPRITE_DIM, SPRITE_DIM));
				rightMoveIndex++;
				rightMoveCounter=0;
			}
			else {
				rightMoveCounter++;
			}
			
		}
		else {
			rightMoveIndex=0;
		}
	}

	public void moveUp() {
		
		playerY -= MOVE_AMOUNT;
		if(upMoveIndex < NUM_ANIMATIONS) {
			
			if(upMoveCounter > MOVE_TIMER) {
				sprite = resize(ss.grabSprite(SPRITE_DIM*upMoveIndex, SPRITE_DIM*2, SPRITE_DIM, SPRITE_DIM));
				upMoveIndex++;
				upMoveCounter=0;
			}
			else {
				upMoveCounter++;
			}
			
		}
		else {
			upMoveIndex=0;
		}
	}

	public void moveDown() {
		
		playerY += MOVE_AMOUNT;
		if(downMoveIndex < NUM_ANIMATIONS) {
			
			if(downMoveCounter > MOVE_TIMER) {
				sprite = resize(ss.grabSprite(SPRITE_DIM*downMoveIndex, SPRITE_DIM*3, SPRITE_DIM, SPRITE_DIM));
				downMoveIndex++;
				downMoveCounter=0;
			}
			else {
				downMoveCounter++;
			}
			
		}
		else {
			downMoveIndex=0;
		}
	}

	// abstract methods from key listener interface that have to be implemented
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
