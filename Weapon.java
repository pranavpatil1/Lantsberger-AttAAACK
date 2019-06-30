
/**
 * the Weapons class contains a variable that 
 * stores destruction levels of weapons
 *
 * @Aruna Sudarshan
 * @5/20/2018
 */
import javafx.scene.image.Image;
public class Weapon extends Sprite
{
    private int destruction; //integer that represents destruction level
    
    /**
     * Constructor for objects of class Weapons
     */
    public Weapon(int level, double x, double y, double width, double height, Image image)
    {
        super(x, y,width,height,image);
        destruction = level;
    }
    /**
     * Constructor for objects of class Weapons
     */
    public Weapon(Weapon other)
    {
        this(other.getDestruction(), other.getX(), other.getY(), other.getWidth(), other.getHeight(), other.getImage());
    }
    /**
     * @return destruction, the amount of damage that a weapon can inflict
     */
    public int getDestruction()
    {
        return destruction;
    }
}