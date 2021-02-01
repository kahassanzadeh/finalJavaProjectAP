import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

public class Squash extends Plant {

    /**
     * created by amirMahdi mirsharifi & mohammadReza hasanZadeh
     * ((امتیازی))
     * @param gc
     * @param row
     * @param column
     * @param time
     * @param health
     */
    public Squash(GameController gc, int row, int column, int time, int health) {
        super(gc, row, column, time, health);
    }

    // rectangle for down cell
    private Rectangle dcRect = new Rectangle(44 + (column * 100),109 + ((row - 1) * 120),200,120);
    // rectangle for up cell
    private Rectangle ucRect = new Rectangle(44 + (column * 100),109 + ((row - 1) * 120),200,120);


    /**
     * in this method we check upCell and downCell and if we have zombie at on of them plants jumps on the zombies on the cell
     */
    @Override
    public void start() {
        taskTimer = new Timer(0,(ActionEvent e) -> {
            Iterator<Zombie> z = null;

            try{
                if(gc.getAllOfZombies().get(row - 1) != null){
                    z = gc.getAllOfZombies().get(row - 1).iterator();
                }
                else
                {
                    z = gc.getAllOfZombies().get(row + 1).iterator();
                }

            }catch( Exception ignored){ }


            while(z.hasNext()){
                Zombie temp = z.next();
                Rectangle zRect = new Rectangle((int) temp.posX,109 + temp.lane * 120,100,120);
                if(ucRect.intersects(zRect)||dcRect.intersects(zRect)){
                    temp.reduceHealth(3000);
                    z.remove();
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
