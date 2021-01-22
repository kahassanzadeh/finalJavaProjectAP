package com.company;

public class ConHeadZombie extends Zombie {
    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param speed
     * @param damage
     */
    public ConHeadZombie(GameController gc, int zLain, int speed, int damage) {
        super(gc, zLain, speed, damage);
        health=560;
    }
}
