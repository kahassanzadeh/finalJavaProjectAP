import javax.swing.*;
import java.awt.event.ActionEvent;

public class SunFlower extends Plant{
    public SunFlower(GameController  gc, int row, int column,int time,int health) {
        super(gc,row, column,time,health);
        taskTimer = new Timer(time,(ActionEvent e) -> {
            Sun sta = new Sun(gc,60 + this.row*100,110 + this.column*120,130 + this.column*120);
            gc.addSuns(sta);
        });
        taskTimer.start();
    }
}
