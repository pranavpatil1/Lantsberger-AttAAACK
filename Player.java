import javafx.scene.image.Image;
/**
 * class Player holds information about the player such as position, size of
 * the image to display, and it's speed and health.
 *
 * @author (JaneWang)
 * @version (05212018)
 */
public class Player extends Fighter
{
    //List of possible weapons to use
    private int[] ammo;
    private int myWeapon;//weapon currently in use
    private Weapon[] arsenal;
    
    /**
     * constructor Player holds information about the fighter such as position, size of
     * the image to display, and it's speed and health.
     */
    public Player(double x, double y, double width, double height, Image image, double speed, Weapon one, Weapon two, Weapon three )
    {
        super (x, y, width, height, image, speed, 100, 10);
        // defaults array to size 3
        arsenal = new Weapon[3];
        ammo = new int [3];
        
        // starts weapon at size 0
        myWeapon = 0; 
        
        // nearly infinite ammo
        ammo[0] = Integer.MAX_VALUE;
        
        // sets arsenal to defaults
        arsenal[0] = one;
        arsenal[1] = two;
        arsenal[2] = three;
    }
    /**
     * getAmmo returns the ammo to a certain weapon
     * @param index of the weapon to return ammo
     * @return ammo of certain weapon
     */ 
    public int getAmmo(int index)
    {
        return ammo[index];
    }

    /**
     * addAmmo adds the ammo to a certain weapon
     * @param index of the weapon to add ammo
     */ 
    public void addAmmo(int index)
    {
        ammo[index] += 5;
    }
    /**
     * deleteAmmo decreases the ammo as the weapon is used
     * @param index of weapon to delete
     */ 
    public void deleteAmmo(int index)
    {
        ammo[index] --;
    }
    /**
     * setWeapon sets the index to the new weapon being used
     * @param index of weapon to set to
     */ 
    public boolean setWeapon(int index)
    {
        if(ammo[index] > 0)
        {
            myWeapon = index; 
            return true;
        }
        else
            return false;
    }

    /**
     * method shoot generates a weapon
     * @param x,y   the location of the click
     */   
    public Weapon shoot(int x, int y)
    {
        double angle;//angle of the position to attack based on coordinates 
        Weapon shoot = null;
        if (ammo[myWeapon] > 0)
        {
            //creating new weapon
            shoot = new Weapon(arsenal[myWeapon]);
            shoot.setPosition(getX() + getWidth()/2, getY() + getHeight()/2);//set to new position
            //finding the new velocity with the angle
            angle = Math.atan2((y-(getY() + getHeight()/2)),(x-(getX() + getWidth())));
            shoot.setVelocity(1000 * Math.cos(angle), 1000 * Math.sin(angle));//set velocity
            deleteAmmo(myWeapon);
        }
        return shoot;
    }
}





