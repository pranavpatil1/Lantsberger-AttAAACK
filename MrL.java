import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Constructor for objects of class MrL holds information about the player Mr Lanstburger,
 * like the image and the weapon it's using
 *
 * @author (JaneWang)
 * @version (05212018)
 */
public class MrL extends Player
{
    private static Weapon puck = new Weapon(7, 0, 0, 15, 15, new Image("puck.png"));
    private static Weapon w2 = new Weapon(10, 0, 0, 15, 15, new Image("puck.png"));
    private static Weapon w3 = new Weapon(13, 0, 0, 15, 15, new Image("puck.png"));
    
    // how far into animation (milliseconds)
    private int loopTime;
    
    /**
     * Constructor for objects of class MrL holds information about the player Mr Lanstburger,
     * like the image and the weapon it's using
     */
    public MrL(double x, double y)
    {
        super(x, y, 20, 75, new Image("lantsberger.png"), 1, puck,w2, w3);
    }
    /**
     * method draw - shows Mr L animated and with an awesome health bar
     * @param   gc  what to draw on
     * @param   millis  time since the last draw
     */
    @Override
    public void draw(GraphicsContext gc, int millis)
    {
        // which image to get
        int pos = 0;
        int[] order = {1, 3, 4, 5};
        
        // updates time of loop
        loopTime += millis;
        loopTime %= 140 * order.length;
        if (getYvel() != 0)
                pos = 5;
        else 
            if (Math.abs(getXvel()) < 10)
                pos = 0;
            else
                pos = order[loopTime / 140];
        
        //display the health
        gc.setFill(Color.RED);
        gc.fillRect(getX() - 10, getY() - 15, 40, 10);
        gc.setFill(Color.GREEN);
        gc.fillRect(getX() - 10, getY() - 15, 40.0 * getHealth() / getMaxHealth(), 10);

        int shift = 0;
        if (pos != 0)
            shift = 7;
        
        if (getXvel() >= 0)
            gc.drawImage(getImage(), pos * 45, 0, 45, getHeight(),
                getX() - 4 - shift, getY() + 5, 45, getHeight());
        else
            gc.drawImage(getImage(), pos * 45, 0, 45, getHeight(),
                getX() + 45 - 20 + shift, getY() + 5, -45, getHeight());
    }
}





