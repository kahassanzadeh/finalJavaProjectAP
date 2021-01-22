package com.company;

public class SunFlower extends Plant{
    public SunFlower(GameController  gc, int row, int column,int time) {
        super(gc,row, column,time);
        health=50;
        taskTimer = new Timer(time,(ActionEvent e) -> {
            Sun sta = new Sun(gc,60 + gc.cellinfo[row][column].x*100,110 + gc.cellinfo[row][column].y*120,130 + gc.cellinfo[row][column].y*120);
            gc.activeSuns.add(sta);
            gc.add(sta,new Integer(1));
        });
        taskTimer.start();
    }
}
