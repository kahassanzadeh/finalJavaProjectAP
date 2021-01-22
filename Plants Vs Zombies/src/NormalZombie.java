/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

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
    public NormalZombie(GameMap gc, int zLain, int speed, int damage) {
        super(gc, zLain, speed, damage);
        health=200;
    }
}