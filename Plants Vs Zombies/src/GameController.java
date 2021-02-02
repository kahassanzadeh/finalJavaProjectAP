import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

public class GameController extends JLayeredPane implements Serializable {

    private CellInfo[][] allGameCells = new CellInfo[5][9];

    private ArrayList<ArrayList<Zombie>> allOfZombies;

    private ArrayList<ArrayList<Pea>> allOfPeas;

    private ArrayList<Sun> allOfSuns;

    private  Timer sunProducer;

    private  Timer advanceTimer;

    private  Timer updatingScreen;

    private int sunScore ;

    //the card that had been clicked
    private InsideCellType clickedCellType;

    private  JLabel sunScoreLabel = new JLabel();

    private ArrayList<LawnMower> allOfLawnMowers;

    private final String gameMode;

    private int stage;

    private boolean gameOver = false;

    private final GameMap gameMap;

    private int seconds = 0;

    private  Timer secondsCounter;

    private  Timer firstStageZombieGenerator;

    private  Timer secondStageZombieGenerator;

    private  Timer thirdStageZombieGenerator;

    private  Timer pauseSunProducerTimer;

    private  Timer firstStageZombieGeneratorPause;

    private  Timer secondStageZombieGeneratorPause;

    private  Timer thirdStageZombieGeneratorPause;

    private  InsideGameMenu pausePanel;

    private  InsideGameMenuPanel pauseMenu;

    private class PausePanelActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            updatingScreen.stop();
            advanceTimer.stop();
            sunProducer.stop();
            secondsCounter.stop();
            try{
                firstStageZombieGenerator.stop();
                secondStageZombieGenerator.stop();
                thirdStageZombieGenerator.stop();
                stopAllPlantsTimer();
            }catch(Exception ignored){
            }
            pauseMenu = new InsideGameMenuPanel(GameController.this);
            pauseMenu.showPausePanel();
        }
    }

    private class UpdatingScreenActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();

        }
    }

    private class AdvancerTimerActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            advance();
        }
    }

    private class SunProducerActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom= new SecureRandom();
            Sun temp = new Sun(GameController.this,secureRandom.nextInt(800) + 100,0,secureRandom.nextInt(300) + 200);
            allOfSuns.add(temp);
            add(temp,JLayeredPane.MODAL_LAYER);
        }
    }

    private class FirstStageZombieGeneratorActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(3);
            Zombie temp = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp);
        }
    }

    private class SecondsStageZombieGeneratorActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(3);
            Zombie temp1 = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp1);
            lane = secureRandom.nextInt(5);
            zombie = secureRandom.nextInt(3);
            Zombie temp2 = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp2);
        }
    }

    private class SecondsCounterActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            if(seconds >= 1 && !firstStageZombieGenerator.isRunning()){
                firstStageZombieGenerator.start();
            }else if(seconds >= 151 && !secondStageZombieGenerator.isRunning()){
                stage = 2;
                firstStageZombieGenerator.stop();
                secondStageZombieGenerator.start();
            }else if(seconds >= 331 && !thirdStageZombieGenerator.isRunning()){
                stage = 3;
                secondStageZombieGenerator.stop();
                thirdStageZombieGenerator.start();
            }else if(seconds >= 481){
                gameOver = false;
                JOptionPane.showMessageDialog(GameController.this,"Congratulations! You Won");
                gameMap.dispose();
                thirdStageZombieGenerator.stop();
                updatingScreen.stop();
                advanceTimer.stop();
                sunProducer.stop();
                secondsCounter.stop();
                gameMap.setGameStatus(GameStatus.Won);
                gameMap.getPerson().winingGame(gameMode);
            }
        }
    }





    public GameController(JLabel sunScoreLabel,int sunScore,String gameMode,GameMap gameMap) throws IOException {
        this.gameMap = gameMap;
        this.stage = 1;
        this.gameMode = gameMode;
        this.sunScore = sunScore;
        this.sunScoreLabel = sunScoreLabel;

        setSize(1000,752);
        setLayout(null);

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

        pausePanel = new InsideGameMenu(new ImageIcon("E:\\university\\5th term\\AP\\Final Project\\PVS Design Kit\\images\\MenuButton.png"));
        pausePanel.setLocation(900,5);
        pausePanel.setAction(new PausePanelActionListener());
        add(pausePanel,JLayeredPane.DEFAULT_LAYER);

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j] = new CellInfo();
                allGameCells[i][j].setLocation(44 + (j*100),109 + (i*120));
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(i,j));
                add(allGameCells[i][j],JLayeredPane.DEFAULT_LAYER);
            }
        }


        updatingScreen = new Timer(25,new UpdatingScreenActionListener());
        updatingScreen.start();

        advanceTimer = new Timer(10,new AdvancerTimerActionListener());
        advanceTimer.start();

        sunProducer = new Timer(getSunProducingTimer(), new SunProducerActionListener());
        sunProducer.start();

        firstStageZombieGenerator = new Timer(30000,new FirstStageZombieGeneratorActionListener());

        secondStageZombieGenerator = new Timer(30000,new SecondsCounterActionListener());

        thirdStageZombieGenerator = new Timer(25000, new SecondsCounterActionListener());

        secondsCounter = new Timer(1000,new SecondsCounterActionListener());
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
                   /* Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.ORANGE);
                    g2.fillRect(44 + ((allGameCells[i][j].getInCellPlant().getColumn() - 1) * 100),109 + ((allGameCells[i][j].getInCellPlant().getRow() - 1) * 120),300,360);*/
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

    public void GameOver() {
        gameOver = true;
        JOptionPane.showMessageDialog(this,"Zombies eat your brain !!!");
        gameMap.dispose();
        if(firstStageZombieGenerator.isRunning()){
            firstStageZombieGenerator.stop();
        }else if(firstStageZombieGeneratorPause.isRunning()){
            firstStageZombieGeneratorPause.stop();
        }else if(secondStageZombieGenerator.isRunning()){
            secondStageZombieGenerator.stop();
        }else if(secondStageZombieGeneratorPause.isRunning()){
            secondStageZombieGeneratorPause.stop();
        }else if(thirdStageZombieGenerator.isRunning()){
            thirdStageZombieGenerator.stop();
        }else if(thirdStageZombieGeneratorPause.isRunning()){
            thirdStageZombieGeneratorPause.stop();
        }
        secondsCounter.stop();
        updatingScreen.stop();
        advanceTimer.stop();
        sunProducer.stop();
        gameMap.setGameStatus(GameStatus.Lost);
        gameMap.getPerson().losingGame(gameMode);

    }


    class ActionHandlerPlantingPlant implements ActionListener,Serializable {

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
                    allGameCells[row][column].setInCellPlant(new FreezePeaShooter(GameController.this,row,column,1000,100,seconds * 1000));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 175);
                }
            }
            if(clickedCellType == InsideCellType.PeaShooter){
                if(sunScore >= 100){
                    allGameCells[row][column].setInCellPlant(new PeaShooter(GameController.this,row,column,1000,70,seconds * 1000));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 100);
                }
            }
            if(clickedCellType == InsideCellType.SunFlower){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new SunFlower(GameController.this,row,column,getSunFlowerProducingTimer(),50,seconds * 1000));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.WallNut){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new GiantWallNut(GameController.this,row,column,2000,150,seconds * 1000));
                    setSunScore(sunScore - 50);
                }
            }
            if(clickedCellType == InsideCellType.CherryBomb){
                if(sunScore >= 150){
                    allGameCells[row][column].setInCellPlant(new CherryBomb(GameController.this,row,column,2000,70,seconds * 1000));
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

    private void stopAllPlantsTimer(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                if(allGameCells[i][j].getInCellPlant() != null){
                    allGameCells[i][j].getInCellPlant().setStopingTimer(seconds * 1000);
                    allGameCells[i][j].getInCellPlant().getTaskTimer().stop();
                }
            }
        }
    }

    private void startAllPlantsTimer(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                if(allGameCells[i][j].getInCellPlant() != null){
                    if(allGameCells[i][j].getInCellPlant() instanceof SunFlower){
                        ((SunFlower) allGameCells[i][j].getInCellPlant()).startPausedTimer();
                    }else{
                        allGameCells[i][j].getInCellPlant().start();
                    }
                }
            }
        }
    }

    public void resumeGame(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(i,j));
//                add(allGameCells[i][j],JLayeredPane.DEFAULT_LAYER);
            }
        }
        updatingScreen.start();
        advanceTimer.start();
        secondsCounter.start();

        firstStageZombieGeneratorPause = new Timer(30000 - ((seconds * 1000) % 30000), new FirstStageZombieGeneratorActionListener());

        secondStageZombieGeneratorPause = new Timer(30000 - ((seconds * 1000) % 30000), new SecondsStageZombieGeneratorActionListener());

        thirdStageZombieGeneratorPause = new Timer(25000 - ((seconds * 1000) % 25000), new SecondsStageZombieGeneratorActionListener());

        if(stage == 1){
            firstStageZombieGeneratorPause.setRepeats(false);
            firstStageZombieGeneratorPause.start();
        }else if(stage == 2){
            secondStageZombieGeneratorPause.setRepeats(false);
            secondStageZombieGeneratorPause.start();
        }else if(stage == 3){
            thirdStageZombieGeneratorPause.setRepeats(false);
            thirdStageZombieGeneratorPause.start();
        }

        int delay = getSunProducingTimer() - ((seconds * 1000) % getSunProducingTimer());
        pauseSunProducerTimer = new Timer(delay, new SunProducerActionListener());
        pauseSunProducerTimer.setRepeats(false);
        pauseSunProducerTimer.start();
        sunProducer.setInitialDelay(getSunProducingTimer() + delay);
        sunProducer.start();
        startAllPlantsTimer();
    }

    public void setIsSaved(boolean isSaved){
        gameMap.setIsSaved(isSaved);
    }

    public boolean isSaved(){
        return gameMap.isSaved();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public int getSeconds() {
        return seconds;
    }

    public Timer getAdvanceTimer() {
        return advanceTimer;
    }

    public String getGameMode() {
        return gameMode;
    }

    public JLabel getSunScoreLabel() {
        return sunScoreLabel;
    }

    public Timer getSunProducer() {
        return sunProducer;
    }

    public int getStage() {
        return stage;
    }

    public Timer getFirstStageZombieGenerator() {
        return firstStageZombieGenerator;
    }

    public Timer getFirstStageZombieGeneratorPause() {
        return firstStageZombieGeneratorPause;
    }

    public Timer getPauseSunProducerTimer() {
        return pauseSunProducerTimer;
    }


    public Timer getSecondsCounter() {
        return secondsCounter;
    }

    public Timer getSecondStageZombieGenerator() {
        return secondStageZombieGenerator;
    }

    public Timer getSecondStageZombieGeneratorPause() {
        return secondStageZombieGeneratorPause;
    }

    public Timer getThirdStageZombieGenerator() {
        return thirdStageZombieGenerator;
    }

    public Timer getThirdStageZombieGeneratorPause() {
        return thirdStageZombieGeneratorPause;
    }

    public Timer getUpdatingScreen() {
        return updatingScreen;
    }

}
