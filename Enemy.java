import javafx.scene.image.Image;
/**
 * Describes an enemy in general terms
 * additional instance variable myDamage because it doesn't have a weapon
 * like a player does
 * has nearPlayer() method to see if it's within it's prey's bubble
 * attack() throws punches as long as its a certain distance from the dude
 * 
 * subclasses basic, medium, and hard enemies
 * 
 * @author (Keerthana, Pranav) 
 * @version (blah)
 */
public class Enemy extends Fighter
{
    private boolean goingLeft;
    
    /**
     * Constructor for objects of class Enemy
     * arguments same as Fighter's constructor except + damage
     */
    public Enemy(double x, double y, double width, double height, Image image, double speed, int health, int damage)
    {
        super(x, y, width, height, image, speed, health, damage);
        goingLeft = false;
    }
    
    /**
     * sets damage on its own, so argument list is same as Fighter
     * damage is twice the value of the speed
     */
    public Enemy(double x, double y, double width, double height, Image image, double ouch, double health)
    {
        super(x,y, width, height, image, ouch, health, ouch * 2);
        goingLeft = false;
    }
    /**
     * method setLeft - change if the enemy is going left
     *  (just for tracking purposes)
     * @param   left    if the enemy is going left
     */
    public void setLeft(boolean left)
    {
        goingLeft = left;
    }
    /**
     * method isLeft - check if the enemy is going left (just
     * for tracking purposes)
     * @return  if the enemy is going left
     */
    public boolean isLeft ()
    {
        return goingLeft;
    }
}

