
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
/**
 * Class CBlock creates a car block
 *
 * @author (JaneWang)
 * @version (06032018)
 */
public class CBlock extends Block
{
    /**
     * Constructor for objects of class CBlock
     * @param   x   x position
     * @param   y   y position
     * @param   w   width
     * @param   h   height
     */
    public CBlock(double x, double y, double w, double h)
    {
        super(x, y, w, h, new Image("cBlock.png"));
    }

    /**
     * draw takes a graphics context and draws the car blocks
     */
    @Override
    public void draw(GraphicsContext gc, int millis)
    {
        // shift up 7 pixels
        int offset = -7;
        // layer for as many heights as needed
        for (int i = 0; i < getHeight(); i += 60)
        {
            // single block
            if (getWidth() == 60)
            {
                gc.drawImage(getImage(), 3 * 60, 0, 60, 60,
                    getX(), getY() + i + offset, 60, 60);
            }
            else
            {
                // left block
                gc.drawImage(getImage(), 0 * 60, 0, 60, 60,
                    getX(), getY() + i + offset, 60, 60);
                // middle blocks?
                for (int j = (int)getX() + 60; j < getX() + getWidth() - 60; j += 60)
                    gc.drawImage(getImage(), 1 * 60, 0, 60, 60,
                        j, getY() + i + offset, 60, 60);
                // right block
                gc.drawImage(getImage(), 2 * 60, 0, 60, 60,
                    getX() + getWidth() - 60, getY() + i + offset, 60, 60);
            }
        }
    }
}
