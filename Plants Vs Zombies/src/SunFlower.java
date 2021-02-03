import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

/**
 * this class created for the sun flower
 * @author Mohammadreza hassanzadeh
 */
public class SunFlower extends Plant implements Serializable {
    //timer for deleting the suns
    private Timer pausedTimer;




    public SunFlower(GameController  gc, int row, int column,int time,int health,int startingTimer) {
        super(gc,row, column,time,health,startingTimer);
        taskTimer = new Timer(time,(ActionEvent e) -> {
            Sun sta = new Sun(gc,110 + this.column*100,110 + this.row*120,160 + this.row*120);
            gc.addSuns(sta);
        });
        taskTimer.start();
    }

    public void startPausedTimer(){
        int delay = time - ((this.stopingTimer - this.startingTimer) % time);
        pausedTimer = new Timer(delay,(ActionEvent e) -> {
            Sun sta = new Sun(gc,110 + this.column*100,110 + this.row*120,160 + this.row*120);
            gc.addSuns(sta);
        });
        pausedTimer.setRepeats(false);
        pausedTimer.start();
        taskTimer.setInitialDelay(time + delay);
        taskTimer.start();

    }


}
