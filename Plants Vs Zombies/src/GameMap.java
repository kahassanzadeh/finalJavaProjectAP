import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * this class created for making the map Ui
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class GameMap {
    //frame of the game
    private JFrame mainFrame;

    /**
     * constructor for setting some pictures and make the Game UI
     *
     * @throws IOException if the pictures Address Was Wrong
     */
    public GameMap() throws IOException {
        mainFrame = new JFrame();
        mainFrame.setLocation(200,20);
        mainFrame.setSize(1000,752);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png"));
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        BufferedImage sunflowerPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_sunflower.png"));
        BufferedImage peaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_peashooter.png"));
        BufferedImage freezePeaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png"));
        BufferedImage wallNutPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_wallnut.png"));
        BufferedImage cherryBombPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png"));
        BufferedImage lawnMower = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Lawn_Mower.png"));

        PlantCard sunflowerCard = new PlantCard(new ImageIcon(sunflowerPic).getImage());
        sunflowerCard.setLocation(110,8);
        mainFrame.add(sunflowerCard);

        PlantCard peaShooterCard = new PlantCard(new ImageIcon(peaShooterPic).getImage());
        peaShooterCard.setLocation(175,8);
        mainFrame.add(peaShooterCard);

        PlantCard freezePeaShooterCard = new PlantCard(new ImageIcon(freezePeaShooterPic).getImage());
        freezePeaShooterCard.setLocation(240,8);
        mainFrame.add(freezePeaShooterCard);

        PlantCard wallNutCard = new PlantCard(new ImageIcon(wallNutPic).getImage());
        wallNutCard.setLocation(305,8);
        mainFrame.add(wallNutCard);

        PlantCard cherryBombCard = new PlantCard(new ImageIcon(cherryBombPic).getImage());
        cherryBombCard.setLocation(370,8);
        mainFrame.add(cherryBombCard);



        mainFrame.add(imageLabel);
    }

    /**
     * showing the game map
     */
    public void showGameMap(){
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * closing the gameMap
     */
    public void closeGameMap(){
        mainFrame.setVisible(false);
    }

}
