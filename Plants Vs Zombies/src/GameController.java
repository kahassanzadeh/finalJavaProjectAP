import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

public class GameController extends JLayeredPane implements MouseMotionListener {

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombie>> allOfZombies;

    private ArrayList<ArrayList<Pea>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private Timer randomZombieGenerator ;

    private final Timer sunProducer;

    private final Timer advanceTimer;

    private int sunScore ;

    private final Timer  updatingScreen;

    //the card that had been clicked
    private InsideCellType clickedCellType;

    private JLabel sunScoreLabel = new JLabel();

    private ArrayList<LawnMower> allOfLawnMowers;

    private final String gameMode;

    private final int stage;

    private boolean gameOver = false;

    private final GameMap gameMap;

    private int seconds = 0;

    private Timer secondsCounter;

    private final Timer firstStageZombieGenerator;

    private final Timer secondStageZombieGenerator;

    private final Timer thirdStageZombieGenerator;





    public GameController(JLabel sunScoreLabel,int sunScore,String gameMode,GameMap gameMap){
        this.gameMap = gameMap;
        this.stage = 1;
        this.gameMode = gameMode;
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
            advance();
        });
        advanceTimer.start();

        sunProducer = new Timer(getSunProducingTimer(),(ActionEvent e) ->{
            SecureRandom secureRandom= new SecureRandom();
            Sun temp = new Sun(this,secureRandom.nextInt(800) + 100,0,secureRandom.nextInt(300) + 200);
            allOfSuns.add(temp);
            add(temp,JLayeredPane.MODAL_LAYER);
        });
        sunProducer.start();

        firstStageZombieGenerator = new Timer(3000,(ActionEvent e)->{
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(3);
            Zombie temp = zombieProducer(zombie,this,lane);
            allOfZombies.get(lane).add(temp);
        });

        secondStageZombieGenerator = new Timer(30000,(ActionEvent e) -> {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(3);
            Zombie temp1 = zombieProducer(zombie,this,lane);
            allOfZombies.get(lane).add(temp1);
            lane = secureRandom.nextInt(5);
            zombie = secureRandom.nextInt(3);
            Zombie temp2 = zombieProducer(zombie,this,lane);
            allOfZombies.get(lane).add(temp2);
        });

        thirdStageZombieGenerator = new Timer(25000,(ActionEvent e) -> {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(3);
            Zombie temp1 = zombieProducer(zombie,this,lane);
            allOfZombies.get(lane).add(temp1);
            lane = secureRandom.nextInt(5);
            zombie = secureRandom.nextInt(3);
            Zombie temp2 = zombieProducer(zombie,this,lane);
            allOfZombies.get(lane).add(temp2);
        });

        secondsCounter = new Timer(1000,(ActionEvent e)->{
            seconds++;
            if(seconds == 1){
                firstStageZombieGenerator.start();
            }else if(seconds == 151){
                firstStageZombieGenerator.stop();
                secondStageZombieGenerator.start();
            }else if(seconds == 331){
                secondStageZombieGenerator.stop();
                thirdStageZombieGenerator.start();
            }else if(seconds == 481){
                gameOver = false;
                JOptionPane.showMessageDialog(this,"Congratulations! You Won");
                gameMap.dispose();
                thirdStageZombieGenerator.stop();
                updatingScreen.stop();
                advanceTimer.stop();
                sunProducer.stop();
            }
        });
        secondsCounter.start();


    }
    public void advance(){
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
            if(allOfLawnMowers.get(i).getShouldStart()){
                allOfLawnMowers.get(i).start();
            }else{
                allOfLawnMowers.get(i).advance();
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
                    g.drawImage(GameImages.getCherryBomb(),50 + (j * 100),110 + (i * 120),null);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.ORANGE);
                    g2.fillRect(44 + ((allGameCells[i][j].getInCellPlant().getColumn() - 1) * 100),109 + ((allGameCells[i][j].getInCellPlant().getRow() - 1) * 120),300,360);
                }
            }
        }

        for(ArrayList<Zombie> arrayZ : allOfZombies){
            for(Zombie z : arrayZ){
                if(z instanceof NormalZombie){
                    g.drawImage(GameImages.getNormalZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    /*Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.ORANGE);
                    g2.fillRect((int) z.posX,109 + z.lane * 120,400,120);*/

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

        if(seconds >= 151 && seconds <= 155){
            g.drawImage(GameImages.getSecondWavePic(),350,300,null );
        }else if(seconds >= 331 && seconds <= 335){
            g.drawImage(GameImages.getThirdWave(),400,362,null );
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

    public void GameOver() {
        gameOver = true;
        JOptionPane.showMessageDialog(this,"Zombies eat your brain !!!");
        gameMap.dispose();



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
                    allGameCells[row][column].setInCellPlant(new FreezePeaShooter(GameController.this,row,column,1000,100));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 175);
                }
            }
            if(clickedCellType == InsideCellType.PeaShooter){
                if(sunScore >= 100){
                    allGameCells[row][column].setInCellPlant(new PeaShooter(GameController.this,row,column,1000,70));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 100);
                }
            }
            if(clickedCellType == InsideCellType.SunFlower){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new SunFlower(GameController.this,row,column,getSunFlowerProducingTimer(),50));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.WallNut){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new GiantWallNut(GameController.this,row,column,2000,150));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.CherryBomb){
                if(sunScore >= 150){
                    allGameCells[row][column].setInCellPlant(new CherryBomb(GameController.this,row,column,2000,70));
                    allGameCells[row][column].getInCellPlant().start();
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

    public Zombie zombieProducer(int type,GameController gp,int lane){
        Zombie temp = null;
        switch (type){
            case 0:
                temp = new NormalZombie(gp,lane,5);
                break;
            case 1:
                temp = new ConHeadZombie(gp,lane,getDamageOfConeHeadZombies(),getZombieSpeed());
                break;
            case 2:
                temp = new BucketHeadZombie(gp,lane,getDamageOfBucketHeadZombies(),getZombieSpeed());
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

    public double getZombieSpeed(){
        switch(gameMode){
            case "Normal":
                return 0.45;
            case "Hard":
                return 0.5;
        }
        return 0;
    }

    public int getDamageOfConeHeadZombies(){
        switch(gameMode){
            case "Normal":
                return 10;
            case "Hard":
                return 15;
        }
        return 0;
    }

    public int getDamageOfBucketHeadZombies(){
        switch(gameMode){
            case "Normal":
                return 20;
            case "Hard":
                return 25;
        }
        return 0;
    }

    public int getSunProducingTimer(){
        switch(gameMode){
            case "Normal":
                return 25000;
            case "Hard":
                return 30000;
        }
        return 0;
    }

    public int getSunFlowerProducingTimer(){
        switch(gameMode){
            case "Normal":
                return 20000;
            case "Hard":
                return 25000;
        }
        return 0;
    }

    private int gettingStageTime(){
        switch (stage){
            case 1:
            case 3:
                return 150000;
            case 2:
                return 180000;
        }
        return 0;
    }

    public int getSeconds() {
        return seconds;
    }
}
