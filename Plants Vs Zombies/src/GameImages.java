import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class GameImages {

    private static final Image backgroundImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png").getImage();

    private static final Image peaShooterImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\peashooter.gif").getImage();

    private static final Image freezePeaShooterImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\freezepeashooter.gif").getImage();

    private static final Image sunFlowerImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\sunflower.gif").getImage();

    private static final Image peaImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Pea1.png").getImage();

    private static final Image freezePeaImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\freezepea.png").getImage();

    private static final  Image normalZombieImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\zombie_normal.gif").getImage();

    private static final Image  coneHeadZombieImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\coneheadzombie.gif").getImage();

    private static final Image bucketHeadZombieImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\StrongZombie.png").getImage();

    private static final Image wallNutImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\walnut_full_life.gif").getImage();


    private static Image cherryBomb = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\cherrybomb.png").getImage();


    private static Image sunImage = new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\sun.png").getImage();



    public static  Image getBackgroundImage() {
        return backgroundImage;
    }

    public static Image getPeaShooterImage() {
        return peaShooterImage;
    }

    public static Image getFreezePeaShooterImage() {
        return freezePeaShooterImage;
    }

    public static Image getSunFlowerImage() {
        return sunFlowerImage;
    }

    public static Image getPeaImage() {
        return peaImage;
    }

    public static Image getFreezePeaImage() {
        return freezePeaImage;
    }

    public static Image getNormalZombieImage() {
        return normalZombieImage;
    }

    public static Image getConeHeadZombieImage() {
        return coneHeadZombieImage;
    }

    public static Image getBucketHeadZombieImage() {
        return bucketHeadZombieImage;
    }

    public static Image getWallNutImage() {
        return wallNutImage;
    }

    public static Image getCherryBomb() {
        return cherryBomb;
    }

    public static Image getSunImage() {
        return sunImage;
    }
}
