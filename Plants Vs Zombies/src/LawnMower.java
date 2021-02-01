import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class LawnMower extends JPanel implements Serializable {

    private ImageIcon image;

    private GameController gc;

    private int lane;

    private int xPosition = -25;

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
        /*Rectangle lRect = new Rectangle(xPosition,(lane + 1) * 120,80,68);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(lane).iterator();


        Zombie z = zombieIterator.next();
        Rectangle zRect = new Rectangle((int) z.posX,109 + lane*120,400,120);
        if(lRect.intersects(zRect)){
            gc.getAllOfZombies().get(lane).clear();
            gc.getAllOfLawnMowers().remove(this);
            gc.remove(this);
        }*/
            /*zombieIterator.remove();
            if(zombieIterator.hasNext())
                z = zombieIterator.next();
            else
                xPosition += 25;
            try{
                while(xPosition < 1000){
                    xPosition += 25;
                    lRect = new Rectangle(xPosition,(lane + 1) * 120,80,68);
                    zRect = new Rectangle((int) z.posX,109 + lane*120,400,120);
                    if(lRect.intersects(zRect)){
                        zombieIterator.remove();
                        if(zombieIterator.hasNext())
                            z = zombieIterator.next();
                    }
                }
            }catch (Exception e){
                xPosition += 25;
            }
        }*/

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
