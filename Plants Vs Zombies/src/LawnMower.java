import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class created for the lawn mowers
 * @author mohammadreza hassanzadeh
 * @version 1.1
 */
public class LawnMower extends JPanel implements Serializable {
    //image of the lawnMower
    private ImageIcon image;
    //game controller
    private GameController gc;
    //lane of the lawn mower
    private int lane;
    //initial position
    private int xPosition = -25;
    //if the lawn mowers should start
    private boolean shouldStart = false;

    public LawnMower(ImageIcon image,int lane,GameController gc){

        this.image = image;
        //this.image = this.image.getImage().getScaledInstance(80,68,Image.SCALE_SMOOTH);
        this.setSize(80,68);
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(true);
        this.lane = lane;
        this.gc = gc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(),0,0,null);
    }

    /**
     * if the lawn mower intersect with zombies
     */
    public void advance(){
        Rectangle lRect = new Rectangle(xPosition,109 + (lane)*120,100,120);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(lane).iterator();

        while (zombieIterator.hasNext()) {
            try{
                Zombie temp = zombieIterator.next();
                Rectangle zRect = new Rectangle((int) temp.posX, 109 + lane * 120, 400, 120);
                if (lRect.intersects(zRect)) {
                    shouldStart = true;
                }
            }catch(Exception ignored){

            }
        }
    }

    /**
     * if the lawn mower should tart
     */
    public void start(){

        Rectangle lRect = new Rectangle(xPosition,109 + (lane)*120,100,120);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(lane).iterator();

        while(xPosition <= 1000){
            try{
                Zombie temp = zombieIterator.next();
                Rectangle zRect = new Rectangle((int) temp.posX,109 + lane*120,400,120);
                if(lRect.intersects(zRect)){
                    temp.reduceHealth(2000);
                    zombieIterator.remove();
                }
            }catch(Exception ignored){

            }finally {
                xPosition += 10;
                break;
            }
        }

    }

    public Image getImage() {
        return image.getImage();
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getLane() {
        return lane;
    }

    public boolean getShouldStart() {
        return shouldStart;
    }
}
