import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Iterator;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */
public class CherryBomb extends Plant implements Serializable {

    private Rectangle cRect = new Rectangle(44 + ((column - 1) * 100),109 + ((row - 1) * 120),300,360);

    public CherryBomb(GameController gc, int row, int column, int time, int health,int startingTimer) {
        super(gc, row, column, time, health,startingTimer);
    }

    @Override
    public void start() {
        taskTimer = new Timer(2000,(ActionEvent e) -> {
            cRect = new Rectangle(44 + ((column - 1) * 100),109 + ((row - 1) * 120),300,360);

            Iterator<Zombie> z1 = gc.getAllOfZombies().get(row).iterator();
            Iterator<Zombie> z2 = null;
            Iterator<Zombie> z3 = null;
            try{
                if(gc.getAllOfZombies().get(row - 1) != null){
                    z2 = gc.getAllOfZombies().get(row - 1).iterator();
                }

            }catch( Exception ignored){

            }
            try{
                if(gc.getAllOfZombies().get(row + 1) != null){
                    z3 = gc.getAllOfZombies().get(row + 1).iterator();
                }

            }catch (Exception ignored){

            }
            while(z1.hasNext()){
                Zombie temp = z1.next();
                Rectangle zRect = new Rectangle((int) temp.posX,109 + temp.lane * 120,100,120);
                if(cRect.intersects(zRect)){
                    temp.reduceHealth(3000);
                    z1.remove();
                }
            }
            if(z2 != null){
                while(z2.hasNext()){
                    Zombie temp = z2.next();
                    Rectangle zRect = new Rectangle((int) temp.posX,109 + temp.lane * 120,100,120);
                    if(cRect.intersects(zRect)){
                        temp.reduceHealth(3000);
                        z2.remove();
                    }
                }
            }
            if(z3 != null){
                while(z3.hasNext()){
                    Zombie temp = z3.next();
                    Rectangle zRect = new Rectangle((int) temp.posX,109 + temp.lane * 120,100,120);
                    if(cRect.intersects(zRect)){
                        temp.reduceHealth(3000);
                        z3.remove();
                    }
                }
            }
            this.stop();
        });
        taskTimer.start();


    }

    @Override
    public void stop() {
        taskTimer.stop();
        this.reduceHealth(1000);
        gc.getAllGameCells()[row][column].setCellInfoPlantToNull();
    }

    @Override
    public int getColumn() {
        return super.getColumn();
    }

    @Override
    public int getRow() {
        return super.getRow();
    }
}
