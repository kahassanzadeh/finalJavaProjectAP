import java.io.Serializable;

/**
 * created by amirmahdi mirsharifi
 * version 1.0
 */

public class GiantWallNut extends  Plant implements Serializable {


    public GiantWallNut(GameController  gc, int row, int column,int time,int health,int startingTimer) {
        super(gc,row, column,time,health,startingTimer);
    }

}