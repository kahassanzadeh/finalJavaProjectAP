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
     * @param damage
     */
    public NormalZombie(GameController gc, int zLain, int damage) {
        super(gc, zLain, damage);
        this.speed = 0.38;
        this.health = 200;
    }
}
