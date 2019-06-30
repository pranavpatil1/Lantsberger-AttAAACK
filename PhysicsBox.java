import java.util.List;
import java.util.ArrayList;
/**
 * class PhysicsBox - controls the system of gravity and interaction
 * of the blocks and fighters, so the fighters can interact with the blocks
 * like platforms and all fighters are affected by gravity
 *
 * @author Pranav Patil
 * @version 05.29.18
 */
public class PhysicsBox
{
    private List<Fighter> myFighters; //list for fighters in the game
    private List<Block> myBlocks; //list for blocks
    /**
     * constructor PhysicsBox - creates a physics system default to no fighters 
     * or blocks
     */
    public PhysicsBox()
    {
        myFighters = new ArrayList<>();
        myBlocks = new ArrayList<>();
    }
    /**
     * constructor PhysicsBox - creates a physics system with the specified list
     * of fighters and blocks
     * @param   fighters    list of fighters to handle
     * @param   blocks      list of blocks as boundaries
     */
    public PhysicsBox(List<Fighter> fighters, List<Block> blocks)
    {
        myFighters = fighters;
        myBlocks = blocks;
    }
    /**
     * method setFighters changes the list of fighters to the specified list
     * @param   fighters    list of fighters to handle
     */
    public void setFighters(List<Fighter> fighters)
    {
        myFighters = fighters;
    }
    /**
     * method setBlocks changes the list of blocks to the specified list
     * @param   fighters    list of block as boundaries
     */
    public void setBlocks(List<Block> blocks)
    {
        myBlocks = blocks;
    }

    /**
     * method update interacts the fighters with the blocks and applies
     * gravity based on time
     * @param   millis  time since last update
     */
    public void update(int millis)
    {
        // the lower edge of the fighter
        double lower;
        double lower2;
        for (Fighter f : myFighters)
        {
            // gets the lower boundary based on bottom edge AND velocity
            lower = f.getY() + f.getHeight() + f.getYvel() * millis/1000.0;
            // gets the lower boundary based on bottom edge WITHOUT vel
            lower2 = f.getY() + f.getHeight();
            // adds gravity and resistance based on time
            f.setVelocity(f.getXvel() * (1 - 2 * millis/1000.0),
                f.getYvel() + 850 * millis / 1000.0);
            
            // go through all the blocks for each fighter
            for (Block block : myBlocks)
            {
                // checks if its the top 10px of a block
                if (f.getYvel() > 0 && lower >= block.getY() &&
                    lower <= block.getY() + 10 && 
                    f.getX() + f.getWidth() > block.getX() && 
                    f.getX() < block.getX() + block.getWidth() ||
                    
                    // lower2 checks without adding velocity of next frame
                    f.getYvel() > 0 && lower2 >= block.getY() &&
                    lower2 <= block.getY() + 10 && 
                    f.getX() + f.getWidth() > block.getX() && 
                    f.getX() < block.getX() + block.getWidth())
                {
                    // not moving up or down
                    f.setVelocity(f.getXvel(), 0);
                    // position resets to exactly the right y position
                    f.setPosition(f.getX(), block.getY() - f.getHeight());
                }
            }
        }
    }
}