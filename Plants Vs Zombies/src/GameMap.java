
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

/**
 * this class created for making the map Ui
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class GameMap extends JFrame{


    private GameController gameController;

    private JLabel sunScoreLabel;

    private int sunScore = 300;

    private int score;

    private String gameMode ;

    private Timer sunFlowerCardTimer;

    private Timer peaShooterCardTimer;

    private Timer freezePeaShooterCardTimer;

    private Timer wallNutCardTimer;

    private Timer cherryBombCardTimer;





    /**
     * constructor for setting some pictures and make the Game UI
     *
     * @throws IOException if the pictures Address Was Wrong
     */
    public GameMap(String gameMode) throws IOException {
        this.gameMode = gameMode;
        /*changingScreen = new Timer(1000,(ActionEvent e)->{
            if(gameController.getSeconds() == 5){
                getLayeredPane().add(GameImages.getSecondWavePic(),JLayeredPane.DEFAULT_LAYER);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }else if(gameController.getSeconds() == 331){
                getLayeredPane().add(GameImages.getThirdWave(),JLayeredPane.DEFAULT_LAYER);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }else if(gameController.getSeconds() == 481){
                changingScreen.stop();
            }
        });
        changingScreen.start();*/
        initFrame();
        initCardsAndLawns();

    }

    /**
     * showing the game map
     */
    public void showGameMap(){
        pack();
        setVisible(true);
    }

    /**
     * closing the gameMap
     */
    public void closeGameMap(){
        this.setVisible(false);
    }

    private void initCardsAndLawns() throws IOException {

        BufferedImage sunflowerPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_sunflower.png"));
        BufferedImage peaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_peashooter.png"));
        BufferedImage freezePeaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png"));
        BufferedImage wallNutPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_wallnut.png"));
        BufferedImage cherryBombPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png"));
        BufferedImage lawnMower = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Lawn_Mower.png"));

        PlantCard sunflowerCard = new PlantCard(new ImageIcon(sunflowerPic).getImage(),coolDownSecondsGetter("SunFlower"));
        sunflowerCard.setLocation(110, 8);
        sunflowerCard.setAction((ActionEvent e) -> {

            gameController.setClickedCellType(InsideCellType.SunFlower);
        });
        getLayeredPane().add(sunflowerCard,JLayeredPane.MODAL_LAYER);

        PlantCard peaShooterCard = new PlantCard(new ImageIcon(peaShooterPic).getImage(),coolDownSecondsGetter("peaShooter"));
        peaShooterCard.setLocation(175, 8);
        peaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.PeaShooter);
        });
        getLayeredPane().add(peaShooterCard,JLayeredPane.MODAL_LAYER);

        PlantCard freezePeaShooterCard = new PlantCard(new ImageIcon(freezePeaShooterPic).getImage(),coolDownSecondsGetter("freezePeaShooter"));
        freezePeaShooterCard.setLocation(240, 8);
        freezePeaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.FreezePeaShooter);
        });
        getLayeredPane().add(freezePeaShooterCard,JLayeredPane.MODAL_LAYER);

        PlantCard wallNutCard = new PlantCard(new ImageIcon(wallNutPic).getImage(),coolDownSecondsGetter("wallNut"));
        wallNutCard.setLocation(305, 8);
        wallNutCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.WallNut);
        });
        getLayeredPane().add(wallNutCard,JLayeredPane.MODAL_LAYER);

        PlantCard cherryBombCard = new PlantCard(new ImageIcon(cherryBombPic).getImage(),coolDownSecondsGetter("cherryBomb"));
        cherryBombCard.setLocation(370, 8);
        cherryBombCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.CherryBomb);
        });
        getLayeredPane().add(cherryBombCard,JLayeredPane.MODAL_LAYER);

        for (int i = 1; i <= 5; i++) {
            LawnMower lw = new LawnMower(new ImageIcon(lawnMower).getImage(),i - 1,gameController);
            gameController.getAllOfLawnMowers().add(lw);
        }

        getLayeredPane().add(sunScoreLabel,JLayeredPane.MODAL_LAYER);



    }

    private void initFrame() throws IOException {

        setLocation(200,20);
        setSize(1012,785);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel mainBG = new JLabel(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png"));
        add(mainBG);

        sunScoreLabel = new JLabel(String.valueOf(sunScore));
        sunScoreLabel.setLocation(50,80);
        sunScoreLabel.setSize(60,20);
        sunScoreLabel.setFont(new Font("Times New Roman",Font.BOLD,17));

        gameController = new GameController(sunScoreLabel,sunScore,gameMode,this);
        gameController.setLocation(0,0);
        getLayeredPane().add(gameController,JLayeredPane.DEFAULT_LAYER);




    }

    public JLabel getSunScoreLabel() {
        return sunScoreLabel;
    }

    public int getSunScore() {
        return sunScore;
    }

    public int getScore() {
        return score;
    }

    public int coolDownSecondsGetter(String cardType){
        if(cardType.equals("SunFlower")){
            return 7500;
        }
        if(cardType.equals("peaShooter")){
            return 7500;
        }
        if(cardType.equals("wallNut")){
            return 30000;
        }
        if(gameMode.equals("Normal")){
            if(cardType.equals("freezePeaShooter")){
                return 7500;
            }
            if(cardType.equals("cherryBomb")){
                return 30000;
            }
        }
        if(gameMode.equals("Hard")){
            if(cardType.equals("freezePeaShooter")){
                return 30000;
            }
            if(cardType.equals("cherryBomb")){
                return 45000;
            }
        }
        return 0;
    }

}
