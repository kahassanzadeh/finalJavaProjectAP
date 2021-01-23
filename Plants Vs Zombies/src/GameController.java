import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Time;
import java.util.ArrayList;

public class GameController extends JLayeredPane implements MouseMotionListener {

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombie>> allOfZombies;

    private ArrayList<ArrayList<Pea>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private Timer randomZombieGenerator ;

    private int sunScore ;

    private Timer  updatingScreen;

    //the card that had been clicked
    private InsideCellType clickedCellType;

    private JLabel sunScoreLabel = new JLabel();


    public GameController(JLabel sunScoreLabel,int sunScore){
        this.sunScore = sunScore;
        this.sunScoreLabel = sunScoreLabel;

        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);

        allOfSuns = new ArrayList<>();

        allOfPeas = new ArrayList<>();

        allOfPeas.add(new ArrayList<>());
        allOfPeas.add(new ArrayList<>());
        allOfPeas.add(new ArrayList<>());
        allOfPeas.add(new ArrayList<>());
        allOfPeas.add(new ArrayList<>());

        allOfZombies = new ArrayList<>();

        allOfZombies.add(new ArrayList<>());
        allOfZombies.add(new ArrayList<>());
        allOfZombies.add(new ArrayList<>());
        allOfZombies.add(new ArrayList<>());
        allOfZombies.add(new ArrayList<>());

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j] = new CellInfo();
                allGameCells[i][j].setLocation(44 + (j*100),109 + (i*120));
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(j,i));
                add(allGameCells[i][j],0);
            }
        }

        updatingScreen = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        updatingScreen.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\mainBG.png").getImage(),0,0,null);

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

    public InsideCellType getClickedCellType() {
        return clickedCellType;
    }

    public void setClickedCellType(InsideCellType clickedCellType) {
        this.clickedCellType = clickedCellType;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    class ActionHandlerPlantingPlant implements ActionListener {

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
                    allGameCells[row][column].setInCellPlant(new FreezePeaShooter(GameController.this,row,column,2000,100));
                    setSunScore(sunScore - 175);
                }
            }
            if(clickedCellType == InsideCellType.PeaShooter){
                if(sunScore >= 100){
                    allGameCells[row][column].setInCellPlant(new PeaShooter(GameController.this,row,column,2000,100));
                    setSunScore(sunScore - 100);
                }
            }
            if(clickedCellType == InsideCellType.SunFlower){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new SunFlower(GameController.this,row,column,2000,100));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.WallNut){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new GiantWallNut(GameController.this,row,column,2000,100));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.CherryBomb){
                if(sunScore >= 150){
                    allGameCells[row][column].setInCellPlant(new CherryBomb(GameController.this,row,column,2000,100));
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
        add(sun,1);
    }

    public ArrayList<Sun> getAllOfSuns() {
        return allOfSuns;
    }

    public int getSunScore() {
        return sunScore;
    }
}
