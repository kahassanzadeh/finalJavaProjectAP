import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * this class created for the menu panel in the game
 * @author Mohammadreza Hassanzadeh
 * @version 1.1
 */
public class InsideGameMenu extends JPanel implements MouseListener, Serializable {
    //image icon
    private final ImageIcon image;
    //action listener
    private ActionListener actionListener;

    /**
     * constructor for the panel
     * @param image
     */
    public InsideGameMenu(ImageIcon image){
        setSize(81,20);
        this.image = image;
        addMouseListener(this);
    }

    /**
     * setting the action for the panel
     * @param actionListener
     */
    public void setAction(ActionListener actionListener){
        this.actionListener = actionListener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try{
            g.drawImage(image.getImage(),0,0,null);
        }catch(Exception ignored){}
    }

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
