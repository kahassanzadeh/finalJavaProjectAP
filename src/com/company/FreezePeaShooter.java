package com.company;

public class FreezePeaShooter extends Plant {


    public FreezePeaShooter(GameController  gc, int row, int column,int time) {
        super(gc,row, column,time);
        health=170;
    }


    @Override
    public void start() {
        taskTimer = new Timer(time,(ActionEvent e) -> {
            if(gc.laneZombies.get(row).size() > 0) {
                gc.lanePeas.get(row).add(new FreezePea( gc.cellinfo[row][column].y, 103 + gc.cellinfo[row][column].x * 100));
            }
        });
        taskTimer.start();

    }


    @Override
    public void stop() {
        taskTimer.stop();
    }
}
