import javax.swing.*;

public class Plant {
    protected int health;
    //plant's location
    protected int row;
    protected int column;
    //timer for pant's task like : shoot pea or freezePea or appear sun
    protected Timer taskTimer;
    //The length of time the plant repeats its task
    protected int time;
    //tho object from GameController class that contain GameMap.
    protected GameMap gc;
    //constructor
    public Plant(GameMap gc,int row,int column,int time,int health){
        this.row = row;
        this.column = column;
        this.gc = gc;
        this.time=time;
        this.health = health;
    }


    /**
     * start shooting or appear sun
     */
    public void start()
    {

    }

    /**
     * stop shooting or appear sun
     */

    public void stop()
    {

    }

    /**
     *
     * @return true when plant was dead
     * @return false when plant is alive
     */
    public boolean isDead()
    {

        if(health<=0)
            return true;
        else
            return false;
    }

    public void reduceHealth(int damage)
    {
        health-=damage;
    }



    public void setGp(GameMap gc) {
        this.gc = gc;
    }
}
