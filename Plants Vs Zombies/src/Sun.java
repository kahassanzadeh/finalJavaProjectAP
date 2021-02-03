import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * this class created for the Sun object
 * @author Mohammadreza hassanzadeh
 */
public class Sun extends JPanel implements MouseListener, Serializable {
    //game controller
    private GameController gc;
    //x position of the sun
    private int xPosition;
    //y position of the sun
    private int yPosition;
    //ending y position
    private int endingYPosition;
    //seconds counter
    private int secondCounter;
    //image of the sun
    private transient Image sunImage;
    //timer for removing the sun
    private Timer countingSeconds = new Timer(1000, (ActionEvent e)->{
        secondCounter++;
    });

    /**
     * constructor for the sun
     * @param gc game controller
     * @param xPosition x position of the sun
     * @param yPosition y position of the sun
     * @param endingYPosition ending y positions
     */
    public Sun(GameController gc,int xPosition,int yPosition,int endingYPosition){
        this.gc = gc;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.endingYPosition = endingYPosition;

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

    /**
     * this method advances the sun
     */
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
