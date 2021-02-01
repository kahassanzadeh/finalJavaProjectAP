import javax.swing.*;
import java.io.Serializable;

public class Plant implements Serializable {
    protected int health;
    //plant's location
    protected int row;
    protected int column;
    //timer for pant's task like : shoot pea or freezePea or appear sun
    protected Timer taskTimer = null;
    //The length of time the plant repeats its task
    protected int time;
    //tho object from GameController class that contain GameMap.
    protected GameController gc;

    protected int startingTimer;

    protected int stopingTimer;
    //constructor
    public Plant(GameController gc,int row,int column,int time,int health,int startingTimer){
        this.row = row;
        this.column = column;
        this.gc = gc;
        this.time = time;
        this.health = health;
        this.startingTimer = startingTimer;
    }


    /**
     * start shooting or appear sun
     */
    public void start()
    {
        taskTimer.start();
    }

    /**
     * stop shooting or appear sun
     */

    public void stop()
    {
        if(taskTimer != null){
            taskTimer.stop();
        }
    }

    /**
     *
     * @return true when plant was dead
     * @return false when plant is alive
     */
    public boolean isDead()
    {

        if(health<=0){
            if(taskTimer != null){
                taskTimer.stop();
            }
            return true;
        }
        else
            return false;
    }

    public void reduceHealth(int damage)
    {
        health-=damage;
    }



    public void setGp(GameController gc) {
        this.gc = gc;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Timer getTaskTimer() {
        return taskTimer;
    }

    public void setStopingTimer(int stopingTimer) {
        this.stopingTimer = stopingTimer;
    }
}
