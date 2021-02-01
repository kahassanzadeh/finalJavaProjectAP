import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class PeaShooter extends Plant implements Serializable {


    public PeaShooter(GameController  gc, int row, int column,int time,int health,int startingTimer) {
        super(gc,row, column,time,health,startingTimer);
    }

    @Override
    public void start() {
        this.taskTimer = new Timer(time,(ActionEvent e) -> {
            if(gc.getAllOfZombies().get(row).size() > 0) {
                gc.getAllOfPeas().get(row).add(new Pea(gc, row, 103 + this.column * 100));
            }
        });
        taskTimer.start();

    }


    @Override
    public void stop() {
        taskTimer.stop();
    }
}
