package com.company;

public class Plant {
    protected static int health;
    //A number that show the amount of loss of  plant's life that set in setting Based on game level
    protected static int reduceNumber;
    //plant's location
    protected int row;
    protected int column;
    //timer for pant's task like : shoot pea or freezePea or appear sun
    protected Timer taskTimer;
    //tho object from GameController class that contain GameMap.
    public GameController gc;
    //constructor
    public Plant(GameController parent,int row,int column){
        this.row = row;
        this.column = column;
        gc = parent;
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

    public void reduceHealth()
    {
        health-=reduceNumber;
    }

    public void setReduceNumber(int reduceNumber) {
        this.reduceNumber = reduceNumber;
    }

    public void setGp(GameController gc) {
        this.gc = gc;
    }
}
