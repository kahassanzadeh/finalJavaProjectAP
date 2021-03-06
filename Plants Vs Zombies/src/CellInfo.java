import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * this class created for generating each cells in the map
 *
 * @author Mohammadreza Hassanzadeh
 * @version 1.0
 *
 */

public class CellInfo extends JPanel implements MouseListener {

    //plant inside the cell
    private Plant inCellPlant;
    //check if in the cell had been performed any Actions
    private ActionListener actionListener;

    private InsideCellType insideCellType;

    /**
     * constructor for this class
     * @param e action Listener
     */
    public CellInfo(ActionListener e){
        this.setBorder(new LineBorder(Color.blue));
        this.setOpaque(true);
        this.setSize(100,120);
        this.addMouseListener(this);
        this.actionListener = e;
    }

    public CellInfo(){
        this.setBorder(new LineBorder(Color.blue));
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(true);
        this.setSize(100,120);
        this.addMouseListener(this);
    }

    /**
     * setting a new plant to the cell
     * @param inCellPlant plant in this cell
     */
    public void setInCellPlant(FreezePeaShooter inCellPlant) {
        this.inCellPlant = inCellPlant;
    }

    public void setInsideCellType(InsideCellType insideCellType) {
        this.insideCellType = insideCellType;
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
        return (x > this.getX()) && (x < this.getX() + 100);
    }

    public Plant getInCellPlant() {
        return inCellPlant;
    }

    public InsideCellType getInsideCellType() {
        return insideCellType;
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
