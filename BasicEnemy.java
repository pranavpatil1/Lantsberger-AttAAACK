import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * class BasicEnemy creates any type of low level enemy to fight
 * 
 * @author Keerthana
 * @version 05.28.28
 */
public class BasicEnemy extends Enemy
{
    private static String[] imagePaths = {"goto.png", "break.png"};
    private int loopTime;
    /**
     * Constructor for objects of class BasicEnemy
     */
    public BasicEnemy(double x, double y, int type)
    {
        super(x,y,30,30,new Image(imagePaths[type]),0.75,25,7);
        loopTime = 0;
    }
    
    @Override
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
                pos = 5;
        }
        else
        {
            if (getXvel() < 0)
                
                if (loopTime < 150)
                    pos = 1;
                else
                {
                    if (loopTime < 300)
                        pos = 4;
                    else
                        pos = 7;
                }
            else
                if (getXvel() > 0)
                {
                    if (loopTime < 150)
                        pos = 0;
                    else
                        if (loopTime < 300)
                            pos = 5;
                        else
                            pos = 6;
                }
        }
        gc.drawImage(getImage(), pos * getWidth(), 0, getWidth(), getHeight(),
            getX(), getY(), getWidth(), getHeight());
    }
}





