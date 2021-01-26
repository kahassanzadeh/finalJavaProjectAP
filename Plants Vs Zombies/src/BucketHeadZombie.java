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
     * @param damage
     */
    public BucketHeadZombie(GameController gc, int zLain, int damage) {
        super(gc, zLain,damage);
        this.speed = 0.6;
        health = 1300;
    }
}
