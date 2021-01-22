/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

package com.company;

public class FreezePea extends Pea {

    public FreezePea(GameController  gc,int lane,int startX){
        super(gc,lane,startX);
    }

    @Override
    public void advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gc.laneZombies.get(myLane).size(); i++) {
            Zombie z = gc.laneZombies.get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.reduceHealth(35);
                z.slow();
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    GameController.setProgress(10);
                    gc.laneZombies.get(myLane).remove(i);
                    exit = true;
                }
                gc.lanePeas.get(myLane).remove(this);
                if(exit) break;
            }
        }

        posX += 15;
    }

}
