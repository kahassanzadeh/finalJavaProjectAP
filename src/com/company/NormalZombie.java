package com.company;

public class NormalZombie extends Zombie{
    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param speed
     * @param damage
     */
    public NormalZombie(GameController gc, int zLain, int speed, int damage) {
        super(gc, zLain, speed, damage);
        health=200;
    }
}
