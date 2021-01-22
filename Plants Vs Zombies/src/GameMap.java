
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
import java.util.ArrayList;

/**
 * this class created for making the map Ui
 *
 * @author Mohammadreza Hassanzadeh
 * @since Jan 13 2020
 * @version 1.0
 */
public class GameMap extends JFrame implements MouseMotionListener {
    //background of the game
    private JLabel imageLabel;
    //the card that had been clicked
    private InsideCellType clickedCellType;

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombie>> allOfZombies;

    private ArrayList<ArrayList<Pea>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private Timer randomZombieGenerator ;

    private int sunScore = 100;

    private JLabel sunScoreLabel;

    private int x,y;


    private Timer updatingScreen;

    /**
     * constructor for setting some pictures and make the Game UI
     *
     * @throws IOException if the pictures Address Was Wrong
     */
    public GameMap() throws IOException {

        initFrame();
        initCardsAndLawns();







    }

    /**
     * showing the game map
     */
    public void showGameMap(){
        this.pack();
        this.setVisible(true);
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

        PlantCard sunflowerCard = new PlantCard(new ImageIcon(sunflowerPic).getImage());
        sunflowerCard.setLocation(110, 8);
        sunflowerCard.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.SunFlower;
            }
        });
        add(sunflowerCard);

        PlantCard peaShooterCard = new PlantCard(new ImageIcon(peaShooterPic).getImage());
        peaShooterCard.setLocation(175, 8);
        peaShooterCard.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.PeaShooter;
            }
        });
        add(peaShooterCard);

        PlantCard freezePeaShooterCard = new PlantCard(new ImageIcon(freezePeaShooterPic).getImage());
        freezePeaShooterCard.setLocation(240, 8);
        freezePeaShooterCard.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.FreezePeaShooter;
            }
        });
        add(freezePeaShooterCard);

        PlantCard wallNutCard = new PlantCard(new ImageIcon(wallNutPic).getImage());
        wallNutCard.setLocation(305, 8);
        wallNutCard.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.WallNut;
            }
        });
        add(wallNutCard);

        PlantCard cherryBombCard = new PlantCard(new ImageIcon(cherryBombPic).getImage());
        cherryBombCard.setLocation(370, 8);
        cherryBombCard.setAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.CherryBomb;
            }
        });
        add(cherryBombCard);

        for (int i = 1; i <= 5; i++) {
            LawnMower lw = new LawnMower(new ImageIcon(lawnMower).getImage());
            lw.setLocation(-25, 120 * i);
            add(lw);
        }
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j] = new CellInfo();
                allGameCells[i][j].setLocation(44 + (j*100),109 + (i*120));
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(j,i));
                getLayeredPane().add(allGameCells[i][j],1);
            }
        }
        add(imageLabel);

        updatingScreen = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        updatingScreen.start();
    }

    private void initFrame() throws IOException {

        this.setLocation(200,20);
        this.setSize(1000,752);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png"));
        imageLabel = new JLabel(new ImageIcon(image));

        sunScoreLabel = new JLabel(String.valueOf(sunScore));
        sunScoreLabel.setLocation(50,80);
        sunScoreLabel.setSize(60,20);
        sunScoreLabel.setFont(new Font("Times New Roman",Font.BOLD,17));
        this.add(sunScoreLabel);


    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(GameImages.getBackgroundImage(),0,0,null);

        for(int i = 0; i < 5;i++){
            for(int j = 0; j < 9; j++){
                if(allGameCells[i][j].getInCellPlant() instanceof PeaShooter){
                    g.drawImage(GameImages.getPeaShooterImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInCellPlant() instanceof FreezePeaShooter){
                    g.drawImage(GameImages.getFreezePeaShooterImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInCellPlant() instanceof SunFlower){
                    g.drawImage(GameImages.getSunFlowerImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInCellPlant() instanceof GiantWallNut){
                    g.drawImage(GameImages.getWallNutImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInCellPlant() instanceof CherryBomb){
                    g.drawImage(GameImages.getCherryBomb(),60 + (j * 100),129 + (i * 120),null);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    class ActionHandlerPlantingPlant implements ActionListener{

        private int row;

        private int column;

        public ActionHandlerPlantingPlant(int row,int column){
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(clickedCellType == InsideCellType.FreezePeaShooter){
                if(sunScore >= 175){
                    allGameCells[row][column].setInCellPlant(new FreezePeaShooter(GameMap.this,row,column,2000,100));
                    setSunScore(sunScore - 175);
                }
            }
            if(clickedCellType == InsideCellType.PeaShooter){
                if(sunScore >= 100){
                    allGameCells[row][column].setInCellPlant(new PeaShooter(GameMap.this,row,column,2000,100));
                    setSunScore(sunScore - 100);
                }
            }
            if(clickedCellType == InsideCellType.SunFlower){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new SunFlower(GameMap.this,row,column,2000,100));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.WallNut){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new GiantWallNut(GameMap.this,row,column,2000,100));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.CherryBomb){
                if(sunScore >= 150){
                    allGameCells[row][column].setInCellPlant(new CherryBomb(GameMap.this,row,column,2000,100));
                    setSunScore(sunScore - 150);
                }
            }
            clickedCellType = InsideCellType.Empty;
        }
    }


    public void setSunScore(int score){
        this.sunScore = score;
        sunScoreLabel.setText(String.valueOf(sunScore));
    }

    public CellInfo[][] getAllGameCells() {
        return allGameCells;
    }

    public ArrayList<ArrayList<Zombie>> getAllOfZombies() {
        return allOfZombies;
    }

    public ArrayList<ArrayList<Pea>> getAllOfPeas() {
        return allOfPeas;
    }

    public void addSuns(Sun sun){
        this.allOfSuns.add(sun);
        this.add(sun);
    }

    public ArrayList<Sun> getAllOfSuns() {
        return allOfSuns;
    }

    public int getSunScore() {
        return sunScore;
    }
}
