import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class Sun extends JPanel implements MouseListener, Serializable {

    private GameController gc;

    private int xPosition;

    private int yPosition;

    private int endingYPosition;

    private int secondCounter;

    private transient Image sunImage;

    private Timer countingSeconds = new Timer(1000, (ActionEvent e)->{
        secondCounter++;
    });

    public Sun(GameController gc,int xPosition,int yPosition,int endingYPosition){
        this.gc = gc;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.endingYPosition = endingYPosition;
        //setBackground(new Color(0,0,0,0));

        setSize(80,80);
        setOpaque(false);
        setLocation(xPosition,yPosition);
        sunImage = GameImages.getSunImage();
        addMouseListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage,0,0,null);
    }

    public void advance() {

        if(yPosition < endingYPosition){
            yPosition += 4;
        }
        else{
            countingSeconds.start();
            if(secondCounter > 4){
                gc.remove(this);
                gc.getAllOfSuns().remove(this);
                countingSeconds.stop();

            }
        }
        setLocation(xPosition,yPosition);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gc.setSunScore(gc.getSunScore()+25);
        gc.remove(this);
        gc.removeSun(this);
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
