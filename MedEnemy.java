import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * it describes a medium level enemy which is medium in terms of damage and stuff
 * 
 * @author (Keerthana, Pranav, Jane) 
 * @version (1.0)
 */
public class MedEnemy extends Enemy
{
    private int seconds;
    private int loopTime;
    /**
     * Constructor for objects of class TwoPointers
     */
    public MedEnemy(double x, double y, Image image)
    {
        super(x, y, 36, 40,image, 0.85,40, 3);
    }
    
    /**
     * method update - moves the sprite based on velcoity and time since last update
     * @param millis - time in millseconds since last update
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
                pos = 2;
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
                        pos = 3;
                    else
                        pos = 3;
                }
            else
                if (getXvel() > 0)
                {
                    if (loopTime < 150)
                        pos = 0;
                    else
                        if (loopTime < 300)
                            pos = 2;
                        else
                            pos = 2;
                }
            
        }
        gc.drawImage(getImage(), pos * 40, 0, 40, getHeight(),
            getX() - 2, getY(), 40, getHeight());
    }
}






