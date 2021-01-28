/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class ConHeadZombie extends Zombie {
    /**
     * constructor
     * set GameController , zombie's lane,speed and damage
     *
     * @param gc
     * @param zLain
     * @param damage
     */
    public ConHeadZombie(GameController gc, int zLain, int damage,double speed) {
        super(gc, zLain, damage);
        this.speed = speed;
        health = 560;
    }
}
