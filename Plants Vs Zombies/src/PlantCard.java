import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * this class created for managing each plant Cards
 * this class extends JPanel and implements MouseListener
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class PlantCard extends JPanel implements MouseListener, Serializable {
    //image of the card
    private ImageIcon image;
    //saving the action happens to the card
    private transient ActionListener actionListener;

    private boolean cardIsAvailable = true;

    private Timer secondCounter;

    private int counter;

    private int coolDown;

    private Timer pausedTimer;

    /**
     * constructor of the card
     * @param image image of the card
     */
    public PlantCard(ImageIcon image,int seconds){
        setSize(64,90);
        this.image = image;
        addMouseListener(this);
        this.coolDown = seconds;
        secondCounter = new Timer(500,(ActionEvent e)->{
            counter += 500;
            if(counter == coolDown){
                cardIsAvailable = true;
                counter = 0;
                secondCounter.stop();
            }
        });

    }

    /**
     * setting the main action for this panel
     * @param actionListener actionListener
     */
    public void setAction(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * getting the action that performed
     * @return actionListener
     */
    public ActionListener getActionListener() {
        return actionListener;
    }

    /**
     * painting the image to the panel using graphic
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        g.drawImage(image.getImage(),0,0,null);
    }

    /**
     * this method will add an Action Event to the end of the Actions of the main game Handler and will announce that
     * this card is clicked
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*Timer pausedSecondCounter = null;
        Timer finalPausedSecondCounter = pausedSecondCounter;*/

        secondCounter = new Timer(500,(ActionEvent m)->{
            counter += 500;
            if(counter == coolDown){
                cardIsAvailable = true;
                counter = 0;
                secondCounter.stop();
            }
        });
        if(counter != 0){
            cardIsAvailable = true;
        }

        if(actionListener != null && cardIsAvailable){
            actionListener.actionPerformed(new ActionEvent(this,ActionEvent.RESERVED_ID_MAX+1,""));
            cardIsAvailable = false;
            secondCounter.start();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
