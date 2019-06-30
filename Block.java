import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
/**
 * class Block simulates a boundary that fighters can run on
 * and essentially is a sprite that displays weirdly
 *
 * @author Pranav Patil
 * @version 05.28.18
 */
public class Block extends Sprite
{
    /**
     * Constructor for objects of class Block
     */
    public Block(double x, double y, double width, double height, Image img)
    {
        super (x, y, width, height, img); 
    }
    
    /**
     * method draw loops through the size of the block to draw
     * the individual parts based on the spritesheet rather than
     * just displaying the spritesheet
     * @param   gc  graphics context to draw on
     * @param   millis  time since last frame (not used, for overriding)
     */
    @Override
    public void draw(GraphicsContext gc, int millis)
    {
        for (int i = 0; i < getHeight(); i += 60)
        {
            if (getWidth() == 60)
            {
                gc.drawImage(getImage(), 3 * 60, 0, 60, 60,
                    getX(), getY() + i, 60, 60);
            }
            else
            {
                gc.drawImage(getImage(), 0 * 60, 0, 60, 60,
                    getX(), getY() + i, 60, 60);
                for (int j = (int)getX() + 60; j < getX() + getWidth() - 60; j += 60)
                    gc.drawImage(getImage(), 1 * 60, 0, 60, 60,
                        j, getY() + i, 60, 60);
                gc.drawImage(getImage(), 2 * 60, 0, 60, 60,
                    getX() + getWidth() - 60, getY() + i, 60, 60);
            }
        }
    }
}