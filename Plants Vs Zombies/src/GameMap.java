
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

    //game controller
    private GameController gameController;
    //sunscore label
    private  JLabel sunScoreLabel;
    //initial score
    private int sunScore = 300;
    //normal or hard
    private String gameMode ;
    // if the game has saved before
    private boolean isSaved = false;
    //person of the game
    private Person person;
    //counter of the game
    private int counterOfThisGame;
    //sunflower card
    private  PlantCard sunflowerCard;
    //peashooter card
    private  PlantCard peaShooterCard;
    //freeze peashooter card
    private  PlantCard freezePeaShooterCard;
    //wall nut card
    private  PlantCard wallNutCard;
    //cherry bomb card
    private  PlantCard cherryBombCard;
    //squash card
    private PlantCard squashCard;
    //all of the lawn mowers
    private LawnMower[] lw = new LawnMower[5];
//    game status
    private GameStatus gameStatus;


    /**
     * constructor for the game
     * @param difficulty normal or hard
     * @param person person that plays
     * @throws IOException
     */
    public GameMap(String difficulty,Person person) throws IOException {
        gameStatus = GameStatus.Running;
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

    /**
     * initializing the cards and lawns
     * @throws IOException
     */
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

        squashCard = new PlantCard(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\SquashSeedPacket.png"),coolDownSecondsGetter("squash"));
        squashCard.setLocation(435,8);
        squashCard.setAction((ActionEvent e) ->{
            gameController.setClickedCellType(InsideCellType.Squash);
        });
        getLayeredPane().add(squashCard,JLayeredPane.MODAL_LAYER);

        for (int i = 1; i <= 5; i++) {
            lw[i - 1] = new LawnMower(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\Lawn_Mower.png"),i - 1,gameController);
            gameController.getAllOfLawnMowers().add(lw[i - 1]);
        }
        getLayeredPane().add(sunScoreLabel,JLayeredPane.MODAL_LAYER);
    }

    /**
     * initializing the frame
     * @throws IOException
     */
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

    /**
     * renew the cards Action listener
     */
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

    /**
     * renew the gameController
     * @throws IOException
     */
    private void makingNewGameController() throws IOException {
        gameController = new GameController(sunScoreLabel,sunScore,gameMode,this);
        gameController.setLocation(0,0);
        getLayeredPane().add(gameController,JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * resuming the gameController
     */
    private void resumingGameController(){
        gameController.resumeGame();
    }
    public JLabel getSunScoreLabel() {
        return sunScoreLabel;
    }

    public int getSunScore() {
        return sunScore;
    }

    /**
     * setting all of the cooldowns for cards
     * @param cardType
     * @return
     */
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
            if(cardType.equals("squash")){
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
            if(cardType.equals("squash")){
                return 30000;
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

    /**
     * resuming the game map
     * @throws IOException
     */
    public void resumeGame() throws IOException {
        setCardsAction();
        resumingGameController();
        showGameMap();
    }

    /**
     * overriding to string method for game map
     * @return
     */
    @Override
    public String toString() {
        return "Game Number : " + counterOfThisGame + "    Seconds : " + gameController.getSeconds();
    }

    /**
     * getting th game number
     * @return
     */
    public int getCounterOfThisGame() {
        return counterOfThisGame;
    }

    public GameController getGameController() {
        return gameController;
    }

    public String getGameMode() {
        return gameMode;
    }

    /**
     * setting the couynter of the game
     * @param counter
     */
    public void setCounterOfThisGame(int counter){
        this.counterOfThisGame = counter;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * setting game status
     * @param gameStatus
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
