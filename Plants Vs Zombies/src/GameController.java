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

/**
 * this class created for controlling the game and timing
 * @author Mohammadreza Hassanzadeh
 * @version 1.1
 */
public class GameController extends JLayeredPane implements Serializable {
    //9*5 cell info
    private CellInfo[][] allGameCells = new CellInfo[5][9];
    //all of the zombies in the game
    private ArrayList<ArrayList<Zombie>> allOfZombies;
    //all of the peas in the game
    private ArrayList<ArrayList<Pea>> allOfPeas;
    //all of th esuns in the game
    private ArrayList<Sun> allOfSuns;
    //timer to produce sun
    private  Timer sunProducer;
    //timer to advance elements
    private  Timer advanceTimer;
    //repaint timer
    private  Timer updatingScreen;
    //sunScore
    private int sunScore ;

    //the card that had been clicked
    private InsideCellType clickedCellType;
    //sun score label
    private  JLabel sunScoreLabel = new JLabel();
    //all of the lawnMower of the game
    private ArrayList<LawnMower> allOfLawnMowers;
//    Normal or Hard
    private final String gameMode;
    //stages of the game
    private int stage;
    //if the person lose
    private boolean gameOver = false;
    //game map of the game
    private final GameMap gameMap;
    //seconds of the game
    private int seconds = 0;
    //counting the seconds
    private  Timer secondsCounter;
    //generating first stage zombies
    private  Timer firstStageZombieGenerator;
    //generating second stage zombies
    private  Timer secondStageZombieGenerator;
    //generating third stage zombies
    private  Timer thirdStageZombieGenerator;
    //for calculating the paused timer
    private  Timer pauseSunProducerTimer;
    //menu panel inside the game
    private  InsideGameMenu pausePanel;
    //menu panel inside the game
    private  InsideGameMenuPanel pauseMenu;
    //action listener for pausing panel
    private class PausePanelActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            updatingScreen.stop();
            advanceTimer.stop();
            sunProducer.stop();
            secondsCounter.stop();
            if(firstStageZombieGenerator.isRunning()){
                firstStageZombieGenerator.stop();
            }
            if(secondStageZombieGenerator.isRunning()){
                secondStageZombieGenerator.stop();
            }
            if(thirdStageZombieGenerator.isRunning()) {
                thirdStageZombieGenerator.stop();
            }
            /*try {
                if(firstStageZombieGeneratorPause.isRunning()){
                    firstStageZombieGeneratorPause.stop();
                }
                if(secondStageZombieGeneratorPause.isRunning()){
                secondStageZombieGeneratorPause.stop();
                }
                if(thirdStageZombieGeneratorPause.isRunning()){
                thirdStageZombieGeneratorPause.stop();
                }
                if(pauseSunProducerTimer.isRunning()){
                pauseSunProducerTimer.stop();
                }
            }catch(Exception ignored){

            }*/
            stopAllPlantsTimer();
            pauseMenu = new InsideGameMenuPanel(GameController.this);
            pauseMenu.showPausePanel();
        }
    }
    //action listener for repaint
    private class UpdatingScreenActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();

        }
    }
    //action listener for advance
    private class AdvancerTimerActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            advance();
        }
    }
    //action listener for sun producing
    private class SunProducerActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom= new SecureRandom();
            Sun temp = new Sun(GameController.this,secureRandom.nextInt(800) + 100,0,secureRandom.nextInt(300) + 200);
            allOfSuns.add(temp);
            add(temp,JLayeredPane.MODAL_LAYER);
        }
    }
    //first stage zombie generating action listener
    private class FirstStageZombieGeneratorActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(4);
            Zombie temp = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp);
        }
    }
    //second stage zombie generating action listener
    private class SecondsStageZombieGeneratorActionListener implements ActionListener,Serializable{

        @Override
        public void actionPerformed(ActionEvent e) {
            SecureRandom secureRandom = new SecureRandom();
            int lane = secureRandom.nextInt(5);
            int zombie = secureRandom.nextInt(4);
            Zombie temp1 = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp1);
            lane = secureRandom.nextInt(5);
            zombie = secureRandom.nextInt(4);
            Zombie temp2 = zombieProducer(zombie,GameController.this,lane);
            allOfZombies.get(lane).add(temp2);
        }
    }
    //timing the game action listener
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
                if(gameMode.equals("Normal")){
                    gameMap.getPerson().setNormalGames();
                }else if(gameMode.equals("Hard")){
                    gameMap.getPerson().setHardGames();
                }
                gameMap.getPerson().showUserPanel();
            }
        }
    }


    /**
     * constructor for the game control;er
     * @param sunScoreLabel label of the sun
     * @param sunScore sun
     * @param gameMode Normal or Hard
     * @param gameMap game map
     * @throws IOException
     */
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

        secondStageZombieGenerator = new Timer(30000,new SecondsStageZombieGeneratorActionListener());

        thirdStageZombieGenerator = new Timer(25000, new SecondsStageZombieGeneratorActionListener());

        secondsCounter = new Timer(1000,new SecondsCounterActionListener());
        secondsCounter.start();

    }

    /**
     * running the advance method for the elemnts
     */
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
                }else if(allGameCells[i][j].getInCellPlant() instanceof Squash){
                    g.drawImage(GameImages.getSquashImage(),50 + (j * 100),110 + (i * 120),null);
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
                }else if(z instanceof FootballZombie){
                    if(z.getHealth() <= 200){
                        g.drawImage(GameImages.getNormalZombieImage(),z.getPosX(),109+(z.getLane()*120),null);
                    }
                    else{
                        g.drawImage(GameImages.getFootballZombie(),z.getPosX(),109+(z.getLane()*120),null);
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

    /**
     * setting the cell plant type
     * @param clickedCellType
     */
    public void setClickedCellType(InsideCellType clickedCellType) {
        this.clickedCellType = clickedCellType;
    }

    /**
     * this method will create the needs for gameOver situation
     */
    public void GameOver() {
        gameOver = true;
        JOptionPane.showMessageDialog(this,"Zombies eat your brain !!!");
        gameMap.dispose();
        if(firstStageZombieGenerator.isRunning()){
            firstStageZombieGenerator.stop();
        }
        secondsCounter.stop();
        updatingScreen.stop();
        advanceTimer.stop();
        sunProducer.stop();
        gameMap.setGameStatus(GameStatus.Lost);
        gameMap.getPerson().losingGame(gameMode);
        gameMap.getPerson().showUserPanel();

    }

    /**
     * inner class for handling the cells
     */
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
            if(clickedCellType == InsideCellType.Squash){
                if(sunScore >= 50){
                    allGameCells[row][column].setInCellPlant(new Squash(GameController.this,row,column,2000,70,seconds * 1000));
                    allGameCells[row][column].getInCellPlant().start();
                    setSunScore(sunScore - 50);
                }
            }
            clickedCellType = InsideCellType.Empty;
        }
    }

    /**
     * setting the sun score
     * @param score
     */
    public void setSunScore(int score){
        this.sunScore = score;
        sunScoreLabel.setText(String.valueOf(sunScore));
    }

    /**
     * getting the cell info of the game
     * @return cell info
     */
    public CellInfo[][] getAllGameCells() {
        return allGameCells;
    }

    /**
     * getting all of the zombies in the game
     * @return
     */
    public ArrayList<ArrayList<Zombie>> getAllOfZombies() {
        return allOfZombies;
    }

    /**
     * getting all of the zombies in the game
     * @return
     */
    public ArrayList<ArrayList<Pea>> getAllOfPeas() {
        return allOfPeas;
    }

    /**
     * adding suns to the game
     * @param sun Sun object
     */
    public void addSuns(Sun sun){
        this.allOfSuns.add(sun);
        add(sun,1);
    }

    /**
     * getting all of the suns existin in the game
     * @return
     */
    public ArrayList<Sun> getAllOfSuns() {
        return allOfSuns;
    }

    /**
     * getting the sun score
     * @return
     */
    public int getSunScore() {
        return sunScore;
    }

    /**
     * creating zombies random
     * @param type type of the zombie
     * @param gp game Controller
     * @param lane lane of the zombie
     * @return zombie
     */
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
            case 3:
                temp = new FootballZombie(gp,lane,getDamageOfBucketHeadZombies(),0.6);
                break;
        }
        return temp;
    }

    /**
     * removing sun in the gameMap
     * @param temp
     */
    public void removeSun(Sun temp){
        allOfSuns.remove(temp);
    }

    /**
     * getting all of the lawnmowers
     * @return
     */
    public ArrayList<LawnMower> getAllOfLawnMowers() {
        return allOfLawnMowers;
    }

    /**
     * generating zombies speed
     * @return double speed
     */
    public double getZombieSpeed(){
        switch(gameMode){
            case "Normal":
                return 0.45;
            case "Hard":
                return 0.5;
        }
        return 0;
    }

    /**
     * generating damage of the zombies
     * @return damage
     */
    public int getDamageOfConeHeadZombies(){
        switch(gameMode){
            case "Normal":
                return 10;
            case "Hard":
                return 15;
        }
        return 0;
    }

    /**
     * generating damage of the bucket head Zombie
     * @return
     */
    public int getDamageOfBucketHeadZombies(){
        switch(gameMode){
            case "Normal":
                return 20;
            case "Hard":
                return 25;
        }
        return 0;
    }

    /**
     * getting the sun producing based on the game mode
     * @return
     */
    public int getSunProducingTimer(){
        switch(gameMode){
            case "Normal":
                return 25000;
            case "Hard":
                return 30000;
        }
        return 0;
    }

    /**
     * getting the sun flower based on the game mode
     * @return
     */
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

    /**
     * stoping all of the elements timers
     */
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

    /**
     * starting all of the elements timer
     */
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

    /**
     * this method will satisfy needs for resuming the game
     */
    public void resumeGame(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 9; j++){
                allGameCells[i][j].setAction(new ActionHandlerPlantingPlant(i,j));
            }
        }
        updatingScreen.start();
        advanceTimer.start();
        secondsCounter.start();


        int delay = getSunProducingTimer() - ((seconds * 1000) % getSunProducingTimer());
        pauseSunProducerTimer = new Timer(delay, new SunProducerActionListener());
        pauseSunProducerTimer.setRepeats(false);
        pauseSunProducerTimer.start();
        sunProducer.setInitialDelay(getSunProducingTimer() + delay);
        sunProducer.start();
        startAllPlantsTimer();
    }

    /**
     * set if the game has saved in file before
     * @param isSaved boolean
     */
    public void setIsSaved(boolean isSaved){
        gameMap.setIsSaved(isSaved);
    }

    public boolean isSaved(){
        return gameMap.isSaved();
    }

    /**
     * getting the gameMap
     * @return
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * getting the seconds of the game
     * @return
     */
    public int getSeconds() {
        return seconds;
    }


}
