/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */
package com.company;

public class PeaShooter extends Plant{


    public PeaShooter(GameController  gc, int row, int column,int time) {
        super(gc,row, column,time);
        health=70;
    }

    @Override
    public void start() {
        taskTimer = new Timer(time,(ActionEvent e) -> {
            if(gc.laneZombies.get(row).size() > 0) {
                gc.lanePeas.get(row).add(new Pea(gc, row, 103 + gc.cellinfo[row][column].x * 100));
            }
        });
        taskTimer.start();

    }


    @Override
    public void stop() {
        taskTimer.stop();
    }
}
