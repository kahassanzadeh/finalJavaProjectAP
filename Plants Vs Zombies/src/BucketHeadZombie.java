import java.io.Serializable;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class BucketHeadZombie extends Zombie implements Serializable {
    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param damage
     */
    public BucketHeadZombie(GameController gc, int zLain, int damage,double speed) {
        super(gc, zLain,damage);
        this.speed = speed;
        health = 1300;
    }
}
