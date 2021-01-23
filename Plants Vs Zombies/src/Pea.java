import java.awt.*;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class Pea {

    public int posX;
    protected GameController gc;
    public int myLane;

    public Pea(GameController  gc,int lane,int startX){
        this.gc = gc;
        this.myLane = lane;
        posX = startX;
    }

    public void advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gc.getAllOfZombies().get(myLane).size(); i++) {
            Zombie z = gc.getAllOfZombies().get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.reduceHealth(30);
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");

                    gc.getAllOfZombies().get(myLane).remove(i);
                    //gc.setProgress(10);
                    exit = true;
                }
                gc.getAllOfPeas().get(myLane).remove(this);
                if(exit) break;
            }
        }

        posX += 15;
    }

}
