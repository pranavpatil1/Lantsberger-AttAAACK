import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
/**
 * class Sprite holds data about something that needs to be displayed, such
 * as position, size, and what image to display
 *
 * @author Pranav Patil
 * @version 05.22.18
 */
public class Sprite
{
    // position data
    private double myX;
    private double myY;
    
    // velocity data
    private double myXvel;
    private double myYvel;
    
    // size data
    private double myWidth;
    private double myHeight;
    
    // image file being held
    private Image myImage;
    
    // time that the player is looping around
    private int displayTime;
    
    /**
     * constructor Display holds data about something that needs to be displayed, such
     * as position, size, and what image to display
     */
    public Sprite (double x, double y, double width, double height, Image image)
    {
        myX = x;
        myY = y;
        myXvel = 0;
        myYvel = 0;
        myWidth = width;
        myHeight = height;
        myImage = image;
    }    
    /**
     * method getX gets the x position
     * @return x position
     */
    public double getX()
    {
        return myX;
    }
    /**
     * method getY gets the y position
     * @return y position
     */
    public double getY()
    {
        return myY;
    }
    /**
     * method getXvel gets the x velocity
     * @return x velocity
     */
    public double getXvel()
    {
        return myXvel;
    }
    /**
     * method getYvel gets the y velocity
     * @return y velocity
     */
    public double getYvel()
    {
        return myYvel;
    }
    /**
     * method getWidth gets the image width
     * @return image width
     */
    public double getWidth()
    {
        return myWidth;
    }
    /**
     * method getHeight gets the image height
     * @return image height
     */
    public double getHeight()
    {
        return myHeight;
    }
    /**
     * method getImage gets the actual image data
     * @param   millis  time since last update
     * @return the image itself
     */
    public Image getImage()
    {
        return myImage;
    }
     /**
     * method setVelocity changes the velocity
     * @param xvel x velocity to change
     * @param yVel y velocity to change
     */
    public void setVelocity(double xVel, double yVel)
    {
        myXvel = xVel;
        myYvel = yVel;
    }
    /**
     * method setPosition changes the position
     * @param x x value to change
     * @param y y value to change
     */
    public void setPosition(double x, double y)
    {
        myX = x;
        myY = y;
    }
    /**
     * method isCollision - checks if two sprites overlap
     * @param other the other sprite to compare to
     * @return if the sprites are colliding
     */
    public boolean isCollision (Sprite other)
    {
        // checks if the sprites overlap
        return getX() < other.getX() + other.getWidth() &&
            getX() + getWidth() > other.getX() &&
            getY() < other.getY() + other.getHeight() &&
            getY() + getHeight() > other.getY();
    }
    /**
     * method update - moves the sprite based on velcoity and time since last update
     * @param millis - time in millseconds since last update
     */
    public void update (int millis)
    {
        // updates x based on velocity
        myX += myXvel * millis / 1000.0;
        myY += myYvel * millis / 1000.0;
    }
    /**
     * method draw - displays the sprite on a graphics context object
     * @param   gc  graphics context object to use
     * @param   millis  time since last frame
     */
    public void draw(GraphicsContext gc, int millis)
    {
        gc.drawImage(myImage, myX, myY);
    }
}
