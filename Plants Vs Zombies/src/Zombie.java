import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class Zombie {

    //The amount of zombie lives that set at subclasses.
    protected int health;
    // the zombie's speed Is defined in gameSetting fits game level(Normal,Hard) and is set at constructor.
    protected double speed;
    //zombie's location
    protected double posX=1000;
    //The amount that zombies reduce the life of the plant
    protected int damage;
    //this field show this zombie is at which lain of map.
    protected int lane;
    // zombie is moving or not.
    protected boolean isMoving=true;
    //The block where the plant is and the zombies have reached it
    protected CellInfo cell=null;
    //tho object from GamePanel class that contain GameMap.
    protected GameController gc;
    //checking if the object isSlowed
    protected boolean isSlowed = false;
    //returning to normal speed
    protected Timer countingIsSlowed = new Timer(2000,(ActionEvent e)->{
        if(isSlowed == true){
            returnToMainSpeed();
        }
    });


    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     */
    public Zombie(GameController gc,int zLain,int damage)
    {
        this.gc = gc;
        this.lane = zLain;
        this.damage = damage;
    }


    /**
     * At the beginning of this method we check whether the zombie is in contact with a plant or not
     *If it comes in contact with the plant, it will continue to reduce the plant life, and if the plant dies, the plant will be destroyed
     * If it does not come into contact with the plant, it will continue on its way to the end of the lain
     */
    public void onrush()
    {
        if (isMoving)
        {
            if (recognition())
            {
                cell.getInCellPlant().reduceHealth(damage);
                if (cell.getInCellPlant().isDead() )
                cell.removeInCellPlant();
            }

            else
                posX -= speed;
        }

        if (posX < 0)
        {
            isMoving = false;
            /*GameWindow.gw.dispose();
            GameWindow.gw = new GameWindow();*/

        }

    }

    /**
     * This method detects whether a plant is in the way of a zombie or not.
     * If it is located, it puts the specifications of the block in which the plant is located in the "Cell" field and
     * @return true
     */
    private boolean recognition ()
    {
        boolean isCells = false;
        cell=null;
        for (int i =0; i < 9; i++) {
            if (gc.getAllGameCells()[lane][i].getInCellPlant() != null && gc.getAllGameCells()[lane][i].isInsideCell((int) posX)) {
                isCells = true;
                cell = gc.getAllGameCells()[lane][i];
            }
        }

        return isCells;

    }

    public void slow()
    {
        isSlowed = true;
        speed=speed/2;
    }


    public boolean isDead()
    {
        if (health<=0)
            return true;
        else
            return false;
    }

    /**
     * this method reduce the zombie's health based on reduceNum of Pea or FrozenPea
     */
    public void reduceHealth(int reduceNum)
    {
        health -= reduceNum;
    }

    /**
     * this method destroyed zombie Although zombie has Enough healthy
     * for when first zombie arrives to end of lain
     */
    public void killZombie()
    {
        health=0;
    }

    public int getPosX() {
        return (int) posX;
    }

    public int getLane() {
        return lane;
    }

    public int getHealth() {
        return health;
    }
    public void returnToMainSpeed(){
        speed = speed * 2;
        isSlowed = false;
    }

}