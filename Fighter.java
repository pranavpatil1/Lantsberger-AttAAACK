import javafx.scene.image.Image;
/**
 * Class Fighter extends class Sprite. Has data about the fighter like their health,
 * maximum health, damage and speed. 
 *
 * @author (Jane Wang)
 * @version (5202018)
 */
public class Fighter extends Sprite
{
    //health of the fighter
    private double myHealth;
    private double myMaxHealth;
    //speed of the player
    private double mySpeed;
    private double myDamage; //how much it takes from a Fighter after attack
    // time of the last shot (in seconds)
    private double lastShot;
    
     /**
      * constructor Fighter holds information about the fighter such as position, size 
     * of the image to display, and it's speed and health.
     */
    public Fighter(double x, double y, double width, double height, Image image, double speed, double maxHealth, double damage)
    {
        super (x, y, width, height, image);
        myMaxHealth = maxHealth;
        myHealth = myMaxHealth;
        mySpeed = speed;
        myDamage = damage;
        lastShot = System.currentTimeMillis()/1000.0;
    }

    /**
     * method getHealth gets the current health of the fighter
     * @return health of fighter
     */    
    public double getHealth()
    {
        return myHealth; 
    }
    /**
     * method getMaxHealth gets the max health of the fighter
     * @return Maxhealth of fighter
     */    
    public double getMaxHealth()
    {
        return myMaxHealth; 
    }
    /**
     * method getSpeed gets the speed of the fighter
     * @return speed of fighter
     */
    public double getSpeed()
    {
        return mySpeed;
    }
    /**
     * @return damage inflicted by the Enemy onto the Player
     */
    public double getDamage()
    {
        return myDamage;
    }
    /**
     * method setHealth changes the health
     * @param new health of the fighter
     */
    public void setHealth(double newH)
    { 
        myHealth = newH; 
    }
    /**
     * method setSpeed changes the speed of the fighter
     * @param new speed of the fighter
     */    
    public void setSpeed(double newS)
    {
        mySpeed = newS;
    }
    /**
     * method setDamage changes the damage of the fighter cab inflict
     * @param new damage of the fighter
     */    
    public void setDamage(double newD)
    {
        myDamage = newD;
    }
    /**
     * method jump changes the y-velocity of the fighter so it will jump
     * x-velocity remains the same
     */
    public void jump()
    {
        setVelocity(getXvel(), -450 * getSpeed());
    }
    /**
     * method moveLeft changes the x-velocity of the fighter so it will move left
     * y-velocity remains the same
     */
    public void moveLeft()
    {
        if (getXvel() > -160 * getSpeed())
            setVelocity(getXvel() - 20 * getSpeed(), getYvel());
    }
    /**
     * method moveRight changes the x-velocity of the fighter so it will move right
     * y-velocity remains the same
     */
    public void moveRight()
    {
        if (getXvel() < 160 * getSpeed())
            setVelocity(getXvel() + 20 * getSpeed(), getYvel());
    }
    /**
     * teleports back to starting position! x and y velocity become 0
     * @param x starting position
     * @param y starting position
     */    
    void reset(double x, double y)
    {
        setPosition(x,y);
        setVelocity(0,0); 
    }
    /**
     * method attack attacks the other fighter
     * @param fighter to attack
     */     
    public void attack(Fighter f)
    {
        if (System.currentTimeMillis()/1000.0 - lastShot > 0.5/getSpeed() && near(f))
        {
            lastShot = System.currentTimeMillis()/1000.0;
            f.setHealth(f.getHealth() - getDamage());
        }
    }
    
    /**
     * method near checks if the fighter is close to another fighter (by 10 pixel margin at least)
     * @param   f   fighter to check with
     * @return  if fighters are close
     */
    public boolean near(Fighter f)
    {
        // gets the greater of the two widths and heights
        double w = getWidth() > f.getWidth() ? getWidth() : f.getWidth();
        double h = getHeight() > f.getHeight() ? getHeight() : f.getHeight();
        // overlap of people with a margin of 10
        return Math.abs(f.getY()- getY()) <= h + 10 && (Math.abs(f.getX() - getX()) <= w + 10);
    }
    /**
     * method damage lowers health after being atacked based on the damage 
     * of the weapon or enemy
     * @param fighter to attack
     */ 
    void damage(Weapon b)
    {
        setHealth(getHealth() - b.getDestruction()); 
    }
}


