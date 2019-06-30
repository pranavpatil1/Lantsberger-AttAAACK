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
public class EnchanterTim extends Player
{
    private static Weapon rock = new Weapon(7, 0, 0, 20, 20, new Image("rock.png"));
    private static Weapon w2 = new Weapon(10, 0, 0, 20, 20, new Image("rock.png"));
    private static Weapon w3 = new Weapon(13, 0, 0, 20, 20, new Image("rock.png"));
    
    // how far into animation (milliseconds)
    private int loopTime;
    
    /**
     * Constructor for objects of class MrL holds information about the player Mr Lanstburger,
     * like the image and the weapon it's using
     */
    public EnchanterTim(double x, double y)
    {
        super(x, y, 30, 65, new Image("tim.png"), 1.1, rock, w2, w3);
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
        int[] order = {1, 2, 3};
        
        // updates time of loop
        loopTime += millis;
        loopTime %= 140 * order.length;
        if (getYvel() != 0)
                pos = 2;
        else 
            if (Math.abs(getXvel()) < 10)
                pos = 0;
            else
                pos = order[loopTime / 140];
        
        //display the health
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY() - 15, 30, 10);
        gc.setFill(Color.GREEN);
        gc.fillRect(getX(), getY() - 15, 30.0 * getHealth() / getMaxHealth(), 10);

        if (getXvel() >= 0)
            gc.drawImage(getImage(), pos * 50, 0, 50, getHeight(),
                getX(), getY() + 5, 50, getHeight());
        else
            gc.drawImage(getImage(), pos * 50, 0, 50, getHeight(),
                getX() + 30, getY() + 5, -50, getHeight());
    }
}




