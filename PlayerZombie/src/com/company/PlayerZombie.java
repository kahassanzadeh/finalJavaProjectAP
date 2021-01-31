package com.company;

public class PlayerZombie extends Zombie{
    /**
     *(( امتیازی))
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param damage
     */
    public PlayerZombie(GameController gc, int zLain, int damage) {
        super(gc, zLain, damage);
        health=700;
        speed=0.6;
    }
}