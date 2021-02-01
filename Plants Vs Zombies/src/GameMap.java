
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;


/**
 * this class created for making the map Ui
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class GameMap extends JFrame implements Serializable {


    private GameController gameController;

    private  JLabel sunScoreLabel;

    private int sunScore = 300;

    private int score;

    private String gameMode ;

    private boolean isSaved = false;

    private Person person;

    private int counterOfThisGame;

    private  PlantCard sunflowerCard;

    private  PlantCard peaShooterCard;

    private  PlantCard freezePeaShooterCard;

    private  PlantCard wallNutCard;

    private  PlantCard cherryBombCard;

    private LawnMower[] lw = new LawnMower[5];



    public GameMap(String difficulty,Person person) throws IOException {
        this.person = person;
        this.gameMode = difficulty;
        initFrame();
        makingNewGameController();
        initCardsAndLawns();
        showGameMap();

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

        /*BufferedImage sunflowerPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_sunflower.png"));
        BufferedImage peaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_peashooter.png"));
        BufferedImage freezePeaShooterPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png"));
        BufferedImage wallNutPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_wallnut.png"));
        BufferedImage cherryBombPic = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png"));
        BufferedImage lawnMower = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Lawn_Mower.png"));*/

        sunflowerCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_sunflower.png"),coolDownSecondsGetter("SunFlower"));
        sunflowerCard.setLocation(110, 8);
        sunflowerCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.SunFlower);
        });
        getLayeredPane().add(sunflowerCard,JLayeredPane.MODAL_LAYER);

        peaShooterCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_peashooter.png"),coolDownSecondsGetter("peaShooter"));
        peaShooterCard.setLocation(175, 8);
        peaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.PeaShooter);
        });
        getLayeredPane().add(peaShooterCard,JLayeredPane.MODAL_LAYER);

        freezePeaShooterCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png"),coolDownSecondsGetter("freezePeaShooter"));
        freezePeaShooterCard.setLocation(240, 8);
        freezePeaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.FreezePeaShooter);
        });
        getLayeredPane().add(freezePeaShooterCard,JLayeredPane.MODAL_LAYER);

        wallNutCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_wallnut.png"),coolDownSecondsGetter("wallNut"));
        wallNutCard.setLocation(305, 8);
        wallNutCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.WallNut);
        });
        getLayeredPane().add(wallNutCard,JLayeredPane.MODAL_LAYER);

        cherryBombCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png"),coolDownSecondsGetter("cherryBomb"));
        cherryBombCard.setLocation(370, 8);
        cherryBombCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.CherryBomb);
        });
        getLayeredPane().add(cherryBombCard,JLayeredPane.MODAL_LAYER);


        for (int i = 1; i <= 5; i++) {
            lw[i - 1] = new LawnMower(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Lawn_Mower.png"),i - 1,gameController);
            gameController.getAllOfLawnMowers().add(lw[i - 1]);
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

    }

    public void setCardsAction(){
        sunflowerCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.SunFlower);
        });

        peaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.PeaShooter);
        });

        freezePeaShooterCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.FreezePeaShooter);
        });

        wallNutCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.WallNut);
        });

        cherryBombCard.setAction((ActionEvent e) -> {
            gameController.setClickedCellType(InsideCellType.CherryBomb);
        });
    }

    private void makingNewGameController() throws IOException {
        gameController = new GameController(sunScoreLabel,sunScore,gameMode,this);
        gameController.setLocation(0,0);
        getLayeredPane().add(gameController,JLayeredPane.DEFAULT_LAYER);
    }

    private void resumingGameController(){
        gameController.resumeGame();
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

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved){
        this.isSaved = isSaved;
    }

    public Person getPerson() {
        return person;
    }

    public void resumeGame() throws IOException {
        setCardsAction();
        resumingGameController();
        showGameMap();
    }


    @Override
    public String toString() {
        return "Game Number : " + counterOfThisGame + "    Seconds : " + gameController.getSeconds();
    }


    public int getCounterOfThisGame() {
        return counterOfThisGame;
    }

    public GameController getGameController() {
        return gameController;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setCounterOfThisGame(int counter){
        this.counterOfThisGame = counter;
    }
}
