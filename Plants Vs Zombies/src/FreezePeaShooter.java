import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class FreezePeaShooter extends Plant {


    public FreezePeaShooter(GameMap  gc, int row, int column,int time,int health) {
        super(gc,row, column,time,health);

    }


    @Override
    public void start() {
        taskTimer = new Timer(time,(ActionEvent e) -> {
            if(gc.getAllOfZombies().get(row).size() > 0) {
                gc.getAllOfPeas().get(row).add(new FreezePea(gc, row, 103 + this.row * 100));
            }
        });
        taskTimer.start();

    }


    @Override
    public void stop() {
        taskTimer.stop();
    }
}