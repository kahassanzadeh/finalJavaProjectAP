import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * this class created for testing the options
 *
 */
public class TestClass implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LoginPanel lp = new LoginPanel();
        lp.showLoginPanel();

    }
    /*public static void main(String[] args) throws IOException {
        new TestClass().serializeInnerClass();
    }

    private void serializeInnerClass() throws IOException {
        File file = new File("test");
        InnerClass inner = new InnerClass();
        new ObjectOutputStream(new FileOutputStream(file)).writeObject(inner);
    }

    private class InnerClass implements Serializable {

        private long serialVersionUID = 1L;

        private int seconds = 0;


        private  BufferedImage d = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_sunflower.png"));

        private Timer m = new Timer(1000,new Mouse());

        private InnerClass() throws IOException {

        }

        private class Mouse implements ActionListener,Serializable{

            public Mouse(){

            }
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        }

    }*/
}
