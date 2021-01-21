import javax.swing.*;
import java.awt.*;

public class LawnMower extends JPanel {

    private Image image;

    public LawnMower(Image image){

        this.image = image;
        this.image = this.image.getScaledInstance(80,68,Image.SCALE_SMOOTH);
        this.setSize(80,68);
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }
}
