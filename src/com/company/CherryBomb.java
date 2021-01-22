/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */
package com.company;

public class CherryBomb extends Plant{
    public CherryBomb(GameController gc, int row, int column, int time) {
        super(gc, row, column, time);
    }

    @Override
    public void start() {

        for (int i=row-1;i<=row+1;i++)
        {
            for (int j=column-1;j<=column+1;j++)
            {
                gc.cellinfo[i][j].cellZombie.killZombie;
            }

        }
    }
}
