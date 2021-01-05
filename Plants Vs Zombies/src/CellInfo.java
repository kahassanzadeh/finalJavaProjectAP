import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this class created for generating each cells in the map
 *
 * @author Mohammadreza Hassanzadeh
 * @version 1.0
 *
 */

public class CellInfo extends MouseAdapter {

    //plant inside the cell
    private Plant inCellPlant;
    //jPanel for the cell
    private JPanel panel;
    //check if in the cell had been performed any Actions
    private ActionListener actionListener;

    /**
     * constructor for this class
     * @param e action Listener
     */
    public CellInfo(ActionListener e){
        panel = new JPanel();
        panel.setBorder(new LineBorder(Color.blue));
        panel.setOpaque(true);
        panel.setSize(100,200);
        panel.addMouseListener(this);
        this.actionListener = e;
    }

    /**
     * setting a new plant to the cell
     * @param inCellPlant
     */
    public void setInCellPlant(Plant inCellPlant) {
        this.inCellPlant = inCellPlant;
    }

    /**
     * removing a plant inside the cell
     */
    public void removeInCellPlant()
    {
        inCellPlant.stop();
        inCellPlant = null;
    }

    /**
     * checking if an x coordinate is inside this cell
     * @param x int number of the x coordinate
     * @return true if x coordinate is inside this cell
     */
    public boolean isInsideCell(int x){
        return (x > panel.getX()) && (x < panel.getX() + 100);
    }

    /**
     * override method
     * it make an action if the player had clicked in the cell before
     * @param e event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        actionListener.actionPerformed(new ActionEvent(this,ActionEvent.RESERVED_ID_MAX + 1,"MouseClicked"));
    }
}
