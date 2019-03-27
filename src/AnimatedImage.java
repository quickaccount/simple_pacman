/**
 * This is a class used for creating Animations/AnimatedImages. An AnimatedImage has an array of Images (frames) that
 * it loops through to create an animation and a duration of how long the animation is to last.
 * 
 * @author MichaelJW, Drademacher, Axelkennedal, Introduction-to-JavaFX-for-Game-Development/AnimatedImage.java (2016),
 * GitHub repository, https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/AnimatedImage.java (Cited Source)
 */
import javafx.scene.image.Image;

public class AnimatedImage
{
    public Image[] frames;
    public double duration;
    
    /**
     * Ensures that frames are each displayed for equal amount of time in the animation.
     * @param time the amount of time in seconds that the animation has been running for.
     * @return the image/frame to be shown in the animation.
     */
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}