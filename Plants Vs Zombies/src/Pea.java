import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class Pea implements Serializable {
    //first position
    public int posX;
    //game controller
    protected GameController gc;
    //lane of the pea
    public int myLane;

    /**
     * constructor for the pea
     * @param gc game controller
     * @param lane lane of the pea
     * @param startX start position
     */
    public Pea(GameController  gc,int lane,int startX){
        this.gc = gc;
        this.myLane = lane;
        posX = startX;
    }

    /**
     * check if the pea has intrsect with a zombie
     * @return
     */
    public Pea advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(myLane).iterator();
        while (zombieIterator.hasNext()) {
            Zombie z = zombieIterator.next();
            Rectangle zRect = new Rectangle((int) z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.reduceHealth(30);
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    zombieIterator.remove();
                    //gc.setProgress(10);
                    exit = true;
                }
                if(exit) break;
                return this;
            }
        }
        posX += 10;
        return null;


    }

    public int getPosX() {
        return posX;
    }

    public int getMyLane() {
        return myLane;
    }
}
