import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class LawnMower extends JPanel {

    private Image image;
    private GameController gc;

    private int lane;

    private int xPosition = -25;

    public LawnMower(Image image,int lane,GameController gc){

        this.image = image;
        this.image = this.image.getScaledInstance(80,68,Image.SCALE_SMOOTH);
        this.setSize(80,68);
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(true);
        this.lane = lane;
        this.gc = gc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }

    public void start(){

        Rectangle lRect = new Rectangle(xPosition,(lane + 1) * 120,80,68);
        Iterator<Zombie> zombieIterator = gc.getAllOfZombies().get(lane).iterator();

        Zombie z = zombieIterator.next();
        Rectangle zRect = new Rectangle((int) z.posX,109 + lane*120,400,120);
        if(lRect.intersects(zRect)){
            gc.getAllOfZombies().get(lane).clear();
            gc.getAllOfLawnMowers().remove(this);
            gc.remove(this);
        }
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
        return image;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getLane() {
        return lane;
    }
}
