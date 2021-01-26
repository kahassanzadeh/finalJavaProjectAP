import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.security.SecureRandom;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

public class GameController extends JLayeredPane implements MouseMotionListener {

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombie>> allOfZombies;

    private ArrayList<ArrayList<Pea>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private Timer randomZombieGenerator ;

    private Timer sunProducer;

    private Timer advanceTimer;

    private int sunScore ;

    private Timer  updatingScreen;

    //the card that had been clicked
    private InsideCellType clickedCellType;

    private JLabel sunScoreLabel = new JLabel();

    private ArrayList<LawnMower> allOfLawnMowers;



    public GameController(JLabel sunScoreLabel,int sunScore){
        this.sunScore = sunScore;
        this.sunScoreLabel = sunScoreLabel;

        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);

        allOfLawnMowers = new ArrayList<>();

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
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(i,j));
                add(allGameCells[i][j],JLayeredPane.DEFAULT_LAYER);
            }
        }

        updatingScreen = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        updatingScreen.start();

        advanceTimer = new Timer(10,(ActionEvent e) ->{
            try {
                advance();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        advanceTimer.start();

        sunProducer = new Timer(5000,(ActionEvent e) ->{
            SecureRandom secureRandom= new SecureRandom();
            Sun temp = new Sun(this,secureRandom.nextInt(800) + 100,0,secureRandom.nextInt(300) + 200);
            allOfSuns.add(temp);
            add(temp,JLayeredPane.MODAL_LAYER);
        });
        sunProducer.start();

        randomZombieGenerator = new Timer(2000,(ActionEvent e)->{
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            Zombie temp = zombieProducer("NormalZombie",this,lane,30);
            allOfZombies.get(lane).add(temp);
        });
        randomZombieGenerator.start();
    }
    public void advance() throws InterruptedException {
        for(ArrayList<Zombie> arrayZ : allOfZombies){
            for(Zombie z : arrayZ){
                z.onrush();
            }
        }
        for(ArrayList<Pea> arrayP : allOfPeas){
            Iterator<Pea> peaIterator = arrayP.iterator();
            while(peaIterator.hasNext()){
                Pea temp = peaIterator.next();
                if(temp.advance() != null){
                    peaIterator.remove();
                }
            }
        }


        for(int i = 0; i < allOfSuns.size();i++){
            allOfSuns.get(i).advance();
        }

        for(int i = 0; i < allOfLawnMowers.size();i++){
            if(allOfZombies.get(allOfLawnMowers.get(i).getLane()).size() > 0){
                allOfLawnMowers.get(i).start();
            }
        }



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

        for(ArrayList<Zombie> arrayZ : allOfZombies){
            for(Zombie z : arrayZ){
                if(z instanceof NormalZombie){
                    g.drawImage(GameImages.getNormalZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                }
                else if(z instanceof ConHeadZombie){
                    if(z.getHealth() <= 200){
                        g.drawImage(GameImages.getNormalZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    }
                    else{
                        g.drawImage(GameImages.getConeHeadZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    }
                }
                else if(z instanceof BucketHeadZombie){
                    if(z.getHealth() <= 200){
                        g.drawImage(GameImages.getNormalZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    }
                    else{
                        g.drawImage(GameImages.getBucketHeadZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    }
                }
            }
        }

        for(ArrayList<Pea> arrayP : allOfPeas){
            for(Pea p : arrayP){
                if(p instanceof FreezePea){
                    g.drawImage(GameImages.getFreezePeaImage(),p.getPosX(),130+(p.getMyLane() * 120),null);
                }
                else {
                    g.drawImage(GameImages.getPeaImage(),p.getPosX(),130+(p.getMyLane() * 120),null);
                }
            }
        }

        for(LawnMower lw : allOfLawnMowers){
            g.drawImage(lw.getImage(),lw.getxPosition(),130 + (lw.getLane() * 120),null);
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
                    allGameCells[row][column].setInCellPlant(new FreezePeaShooter(GameController.this,row,column,1000,200));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 175);
                }
            }
            if(clickedCellType == InsideCellType.PeaShooter){
                if(sunScore >= 100){
                    allGameCells[row][column].setInCellPlant(new PeaShooter(GameController.this,row,column,1000,200));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 100);
                }
            }
            if(clickedCellType == InsideCellType.SunFlower){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new SunFlower(GameController.this,row,column,5000,200));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.WallNut){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new GiantWallNut(GameController.this,row,column,2000,200));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.CherryBomb){
                if(sunScore >= 150){
                    allGameCells[row][column].setInCellPlant(new CherryBomb(GameController.this,row,column,2000,200));
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

    public Zombie zombieProducer(String type,GameController gp,int lane,int damage){
        Zombie temp = null;
        switch (type){
            case "NormalZombie":
                temp = new NormalZombie(gp,lane,damage);
                break;
            case "ConeHeadZombie":
                temp = new ConHeadZombie(gp,lane,damage);
                break;
            case "BucketHeadZombie":
                temp = new BucketHeadZombie(gp,lane,damage);
                break;
        }
        return temp;
    }

    public void removeSun(Sun temp){
        allOfSuns.remove(temp);
    }

    public ArrayList<LawnMower> getAllOfLawnMowers() {
        return allOfLawnMowers;
    }
}
