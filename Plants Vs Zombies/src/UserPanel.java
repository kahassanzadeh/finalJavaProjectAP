
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * this class created for UserPanel
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 11 2021
 * @version 1.0
 */
public class UserPanel {

    //main frame
    private JFrame frame;

    public UserPanel() throws IOException {

        //setting the main frame of the panel
        frame = new JFrame();
        frame.setLocation(100,100);
        frame.setSize(1150,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JLayeredPane wholePanel = new JLayeredPane();
        //JPanel imagePanel = new JPanel();
        //adding the main image
        BufferedImage image = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\first_screen.jpg"));
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        //wholePanel.add(imageLabel,1);

        //button for the game menu
        JButton newGame = new JButton("1.New Game");
        JButton loadGame = new JButton("2.Load Game");
        JButton ranking = new JButton("3.Ranking");
        JButton setting = new JButton("4.Setting");
        JButton quitGame = new JButton("5.Quit Game");

        newGame.setFont(fontOfTheButtons());
        loadGame.setFont(fontOfTheButtons());
        ranking.setFont(fontOfTheButtons());
        setting.setFont(fontOfTheButtons());
        quitGame.setFont(fontOfTheButtons());

        newGame.setSize(dimensionOfTheButtons());
        loadGame.setSize(dimensionOfTheButtons());
        ranking.setSize(dimensionOfTheButtons());
        setting.setSize(dimensionOfTheButtons());
        quitGame.setSize(dimensionOfTheButtons());

        newGame.setLocation(120,500);
        loadGame.setLocation(320,500);
        ranking.setLocation(520,500);
        setting.setLocation(720,500);
        quitGame.setLocation(920,500);

        frame.add(newGame);
        frame.add(loadGame);
        frame.add(ranking);
        frame.add(setting);
        frame.add(quitGame);



        frame.add(imageLabel);

    }

    /**
     * this method will make this panel visible
     */
    public void showUserPanel(){
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * this method will close this panel
     */
    public void closeUserPanel(){
        frame.setVisible(false);
    }

    /**
     * setting the fonts of the buttons
     * @return Font of the buttons
     */
    public Font fontOfTheButtons(){
        return new Font("Source Sans Pro",Font.BOLD,14);
    }

    /**
     * setting the dimensions of the button
     * @return Dimensions of the buttons
     */
    public Dimension dimensionOfTheButtons(){
        return new Dimension(150,70);
    }
}
