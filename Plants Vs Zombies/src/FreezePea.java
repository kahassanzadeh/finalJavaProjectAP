import java.awt.*;
import java.util.Iterator;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */


public class FreezePea extends Pea {

    public FreezePea(GameController  gc,int lane,int startX){
        super(gc,lane,startX);
    }

    @Override
    public Pea advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(myLane).iterator();

        while (zombieIterator.hasNext()) {
            Zombie z = zombieIterator.next();
            Rectangle zRect = new Rectangle((int) z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.reduceHealth(30);
                if(!z.isSlowed){
                    z.slow();
                    z.countingIsSlowed.start();
                }
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");

                    zombieIterator.remove();
//                    gc.setProgress(10);
                    exit = true;
                }

                if(exit) break;
                return this;
            }

        }
        posX += 5;
        return null;
    }

}
