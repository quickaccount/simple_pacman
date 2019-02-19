
import java.awt.image.BufferedImage;

// https://www.youtube.com/watch?v=sxBknKI2BfQ

public class SpriteSheet {

	public BufferedImage spriteSheet;

	public SpriteSheet(BufferedImage ss) {

		this.spriteSheet = ss;
	}

	public BufferedImage grabSprite(int x, int y, int width, int height) {

		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
	
}
