import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;
/**
 * class EnemyAI controls many enemies
 * makes it move back and forth on a block WITHOUT falling off
 *
 * @author Pranav Patil
 * @version 05.28.18
 */
public class EnemyAI
{
    // list of enemies to handle
    private List<Enemy> myEnemies;
    private List<Block> myBlocks;
    private Fighter myTarget;
    private static Sprite coll = new Sprite(0, 0, 2, 2, new Image("qBlock.png"));
    private static Sprite coll2 = new Sprite(0, 0, 2, 2, new Image("qBlock.png"));

    /**
     * constructor EnemyAI sets the list of enemies to handle
     * @param enemies - list of enemies in the level
     * @param blocks - list of blocks within the level including qBlocks
     * @param target - the Fighter that the enemies are going to attack
     */
    public EnemyAI(List<Enemy> enemies, List<Block> blocks, Fighter target)
    {
        myEnemies = enemies;
        myBlocks = blocks;
        myTarget = target;
    }

    /**
     * method setTarget - changes the target that the enemies are all attacking :P
     * @param target - the Fighter that the enemies are going to attack
     */
    public void setTarget (Fighter target)
    {
        myTarget = target;
    }
    /**
     * method update
     * changes the location of the enemy based on the time and the gravity (PhysicsBox)
     * 
     * @param millis - amount of time passed
     */
    public void update(int millis)
    {
        // checks if the enemy has space to the left
        boolean hit = false;
        // checks if the enemy has space way over to the right
        boolean hit2 = false;
        // check if the enemy is on the ground
        boolean down = false;
        for (Enemy e : myEnemies)
        {
            hit = false;
            hit2 = false;
            down = false;
            coll.setPosition(e.getX(), e.getY() + e.getHeight() + 1);
            //if the enemy collides with the ground
            for (Block b : myBlocks)
            {
                if (coll.isCollision(b))
                    down = true;
            }
            //for every enemy in the field, moves it around based on the Enemy class
            if (e.isLeft())
            {
                e.moveLeft();
                //if the player is on the same block and the enemy is close to him
                if (down)
                {
                    coll.setPosition(e.getX() - 20, e.getY() + e.getHeight() + 1);
                    coll2.setPosition(e.getX() - 170, e.getY() + e.getHeight() + 1);
                    for (Block b : myBlocks)
                    {
                        //confirms that there is space for the enemy to move on either side
                        if (coll.isCollision(b))
                            hit = true;
                        if (coll2.isCollision(b))
                            hit2 = true;
                    }
                    //if it reaches the end of the block on the left then it'll jump, or the right
                    if (!hit && hit2 && Math.random() < 1)
                        e.jump();
                    else
                        if (!hit)
                            e.setLeft(false);
                }
            }
            else //when the enemy should be moving right
            {
                e.moveRight();
                if (down) //if touching the ground
                {
                    coll.setPosition(e.getX() + 20 + e.getWidth(), e.getY() + e.getHeight() + 1);
                    coll2.setPosition(e.getX() + 170 + e.getWidth(), e.getY() + e.getHeight() + 1);
                    for (Block b : myBlocks)
                    {
                        //confirms that there is space for the enemy to move on either side
                        if (coll.isCollision(b))
                            hit = true;
                        if (coll2.isCollision(b))
                            hit2 = true;
                    }
                    //jumps right or left if there isn't enough room on a block
                    if (!hit && hit2 && Math.random() < 0.5)
                        e.jump();
                    else
                        if (!hit)
                            e.setLeft(true);
                }
            }
            //flips directions once in a while
            if (Math.random() < 0.001)
                e.setLeft(!e.isLeft());
            //if there's a legit target on the loose, go kill it
            if (myTarget != null)
                e.attack(myTarget);
        }
    }
}