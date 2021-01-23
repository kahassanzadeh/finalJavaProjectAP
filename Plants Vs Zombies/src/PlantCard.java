import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this class created for managing each plant Cards
 * this class extends JPanel and implements MouseListener
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class PlantCard extends JPanel implements MouseListener {
    //image of the card
    private Image image;
    //saving the action happens to the card
    private ActionListener actionListener;

    /**
     * constructor of the card
     * @param image image of the card
     */
    public PlantCard(Image image){
        setSize(64,90);
        this.image = image;
        addMouseListener(this);
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
        g.drawImage(image,0,0,null);
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
        if(actionListener != null){
            actionListener.actionPerformed(new ActionEvent(this,ActionEvent.RESERVED_ID_MAX+1,""));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
