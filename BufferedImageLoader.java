
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

// https://www.youtube.com/watch?v=sxBknKI2BfQ

public class BufferedImageLoader {

	public BufferedImage loadImage(String pathRelativeToThis) throws IOException {

		URL url = this.getClass().getResource(pathRelativeToThis);
		BufferedImage img = ImageIO.read(url);
	
		return img;
	}
}
