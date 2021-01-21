import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class GameImages {

    private static Image backgroundImage;

    private static Image peaShooterImage;

    private static Image freezePeaShooterImage;

    private static Image sunFlowerImage;

    private static Image peaImage;

    private static Image freezePeaImage;

    private static Image normalZombieImage;

    private static Image coneHeadZombieImage;

    private static Image bucketHeadZombieImage;

    private static Image wallNutImage;

    private static Image cherryBomb;

    private static Image sunImage;

    public GameImages()  throws IOException {
        backgroundImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png"));
        peaShooterImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\peashooter.gif"));
        freezePeaShooterImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\freezepeashooter.gif"));
        sunFlowerImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\sunflower.gif"));
        peaImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Pea1.png"));
        freezePeaImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\freezepea.png"));
        normalZombieImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\zombie_normal.gif"));
        coneHeadZombieImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\coneheadzombie.gif"));
        bucketHeadZombieImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\StrongZombie.png"));
        wallNutImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\walnut_full_life.gif"));
        cherryBomb = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Gifs\\walnut_full_life.gif"));
        sunImage = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\sun.png"));

    }

    public static Image getBackgroundImage() {
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
