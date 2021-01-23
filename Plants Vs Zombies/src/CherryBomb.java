/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class CherryBomb extends Plant{
    public CherryBomb(GameController gc, int row, int column, int time, int health) {
        super(gc, row, column, time, health);
    }

    @Override
    public void start() {

        for (int i=row-1;i<=row+1;i++)
        {
            for (int j=column-1;j<=column+1;j++)
            {
                //gc.getAllGameCells()[i][j].cellZombie.killZombie;
            }

        }
    }
}
