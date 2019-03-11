import javafx.scene.image.Image;

/**
 * Ensures that frames in an animated image are displayed for equal periods of time.
 * @author https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/AnimatedImage.java
 *	this class was borrowed from the above github 
 */
public class AnimatedImage
{
    public Image[] frames;	// an array containing the frame Images of the animation
    public double duration;	// the amount of time that one full iteration of the animation will run
    
    /**
     * 
     * @param time the elapsed time in seconds
     * @return the image to be drawn to the canvas
     */
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}