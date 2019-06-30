import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * It represents a hard level enemy with the max damage that an enemy can have
 * Has a healing feature
 *
 * @author (Keerthana, Pranav, Jane)
 * @version (2.0)
 */
public class HardEnemy extends Enemy
{
    private int seconds;
    private int loopTime;
    /**
     * Constructor for hard difficulty enemy
     */
    public HardEnemy(double x, double y, Image image)
    {
        super(x,y,80, 40,image,1,75,3);   
        seconds = 0;
    }
    
    /**
     * method update - moves the sprite based on velocity and time since last update
     * @param millis - time in milliseconds since last update
     * @param thl - Fighter, refers to the Player that is playing the game
     * this method is the heal version meant especially for medium
     * and hard level enemies
     * every time the location of the enemy updates, the health is increased 
     * by 2
     */
    public void update (int millis, Fighter thl)
    {
        // updates position based on velocity
        super.update(millis);
        seconds += millis;
        
        if (seconds / 1000 > 0)
        {
            setHealth(getHealth() + 2*(seconds/1000)) ; //do this for every __ units moved   
        }
        
        seconds %= 1000;
    } 
       
    @Override
    /**
     * method draw - displays the sprite on a graphics context object
     * @param   gc  graphics context object to use
     * @param   millis  time since last frame
     */
    public void draw(GraphicsContext gc, int millis)
    {
        // which image to get
        int pos = 0;
        
        //display the health
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY() - 15, getWidth(), 7);
        gc.setFill(Color.GREEN);
        gc.fillRect(getX(), getY() - 15, (double)getWidth() * getHealth() / getMaxHealth(), 7);

        // updates time of loop
        loopTime += millis;
        loopTime %= 450;
        if (getYvel() == 0 && Math.abs(getXvel()) < 10)
        {
            if (getXvel() < 0)
                pos = 4;
            else
                pos = 0;
        }
        else
        {
            if (getXvel() < 0)
                
                if (loopTime < 150)
                    pos = 1;
                else
                {
                    if (loopTime < 300)
                        pos = 5;
                    else
                        pos = 5;
                }
            else
                if (getXvel() > 0)
                {
                    if (loopTime < 150)
                        pos = 0;
                    else
                        if (loopTime < 300)
                            pos = 3;
                        else
                            pos = 3;
                }
            
        }
        gc.drawImage(getImage(), pos * getWidth(), 0, getWidth(), getHeight(),
            getX(), getY(), getWidth(), getHeight());
    }
}






