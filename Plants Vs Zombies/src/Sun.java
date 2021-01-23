import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Sun extends JPanel implements MouseListener {

    private GameController gc;

    private int xPosition;

    private int yPosition;

    private int endingYPosition;

    private static final int deletingTime = 4;

    public Sun(GameController gc,int xPosition,int yPosition,int endingYPosition){
        this.gc = gc;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.endingYPosition = endingYPosition;
        setBackground(new Color(0,0,0,0));
        setLocation(xPosition,yPosition);
        setSize(80,80);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GameImages.getSunImage(),0,0,null);
    }

    public void advance() throws InterruptedException {
        int counter = 0;
        if(yPosition < endingYPosition){
            yPosition -= 5;
        }
        else{
            counter += 1;
            Thread.sleep(1000);
            if(counter == deletingTime){
                gc.remove(this);
                gc.getAllOfSuns().remove(this);
            }
        }
        setLocation(xPosition,yPosition);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gc.setSunScore(gc.getSunScore()+25);
        gc.remove(this);
        gc.getAllOfSuns().remove(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
