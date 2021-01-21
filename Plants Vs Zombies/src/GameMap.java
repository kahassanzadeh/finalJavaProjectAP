import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class GameMap extends JFrame{
    //background of the game
    private JLabel imageLabel;
    //the card that had been clicked
    private InsideCellType clickedCellType;

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombies>> allOfZombies;

    private ArrayList<ArrayList<Peas>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private Timer randomZombieGenerator;


    private Timer updatingScreen = new Timer(25, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    /**
     * constructor for setting some pictures and make the Game UI
     *
     * @throws IOException if the pictures Address Was Wrong
     */
    public GameMap() throws IOException {

        initFrame();
        initCardsAndLawns();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j] = new CellInfo();
                allGameCells[i][j].setLocation(44 + (j*100),109 + (i*120));
                getLayeredPane().add(allGameCells[i][j],1);
            }
        }






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
        sunflowerCard.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.SunFlower;
            }
        });
        add(sunflowerCard);

        PlantCard peaShooterCard = new PlantCard(new ImageIcon(peaShooterPic).getImage());
        peaShooterCard.setLocation(175, 8);
        peaShooterCard.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.PeaShooter;
            }
        });
        add(peaShooterCard);

        PlantCard freezePeaShooterCard = new PlantCard(new ImageIcon(freezePeaShooterPic).getImage());
        freezePeaShooterCard.setLocation(240, 8);
        freezePeaShooterCard.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.FreezePeaShooter;
            }
        });
        add(freezePeaShooterCard);

        PlantCard wallNutCard = new PlantCard(new ImageIcon(wallNutPic).getImage());
        wallNutCard.setLocation(305, 8);
        wallNutCard.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedCellType = InsideCellType.WallNut;
            }
        });
        add(wallNutCard);

        PlantCard cherryBombCard = new PlantCard(new ImageIcon(cherryBombPic).getImage());
        cherryBombCard.setLocation(370, 8);
        cherryBombCard.setActionListener(new ActionListener() {
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

        add(imageLabel);
    }

    private void initFrame() throws IOException {

        this.setLocation(200,20);
        this.setSize(1000,752);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = ImageIO.read(new File("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png"));
        imageLabel = new JLabel(new ImageIcon(image));


    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(GameImages.getBackgroundImage(),0,0,null);

        for(int i = 0; i < 5;i++){
            for(int j = 0; j < 9; j++){
                if(allGameCells[i][j].getInsideCellType() == InsideCellType.PeaShooter){
                    g.drawImage(GameImages.getPeaShooterImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInsideCellType() == InsideCellType.FreezePeaShooter){
                    g.drawImage(GameImages.getFreezePeaShooterImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInsideCellType() == InsideCellType.SunFlower){
                    g.drawImage(GameImages.getSunFlowerImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInsideCellType() == InsideCellType.WallNut){
                    g.drawImage(GameImages.getWallNutImage(),60 + (j * 100),129 + (i * 120),null);
                }
                else if(allGameCells[i][j].getInsideCellType() == InsideCellType.CherryBomb){
                    g.drawImage(GameImages.getCherryBomb(),60 + (j * 100),129 + (i * 120),null);
                }
            }
        }
    }



}
