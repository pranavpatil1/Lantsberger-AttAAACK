import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
/**
 * Class QBlock creates a question block where you earn a power-up
 *
 * @author (JaneWang)
 * @version (06032018)
 */
public class QBlock extends Block
{
    /**
     * Constructor for objects of class QBlock 
     */
    public QBlock(double x, double y)
    {
        super(x, y, 60, 60, new Image("qBlock.png"));
    }
    
    /**
     * this draws the question block with graphics context 
     * @param gc    what you draw on
     * @param millis    not really needed for this method, but because it's an overrride
     */
    @Override
    public void draw(GraphicsContext gc, int millis)
    {
       gc.drawImage(new Image("qBlock.png"), getX(), getY());
    }
}
