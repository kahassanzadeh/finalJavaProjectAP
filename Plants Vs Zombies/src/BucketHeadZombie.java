/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class BucketHeadZombie extends Zombie {
    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param speed
     * @param damage
     */
    public BucketHeadZombie(GameController gc, int zLain, int speed, int damage) {
        super(gc, zLain, speed, damage);
        health=1300;
    }
}
