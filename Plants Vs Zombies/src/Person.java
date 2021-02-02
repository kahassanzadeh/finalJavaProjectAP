import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Person implements Serializable {


    private String userName;

    private String password;

    private String name;

    private ArrayList<GameMap> allOfGames;

    private int score;

    private int lose;

    private int won;

    private int hardGames;

    private int normalGames;

    private int highScore;

    private transient ArrayList<String> otherPlayersGameStatus;

    private transient UserPanel userPanel;

    private transient FileManager fm;




    public Person(String name,String userName,String password){

        this.hardGames = 0;
        this.normalGames = 0;
        this.score = 0;
        this.lose = 0;
        this.won = 0;
        this.name = name;
        this.userName = userName;
        this.password = password;
        try{
            userPanel = new UserPanel(this);
        } catch (IOException ignored) {

        }
        allOfGames = new ArrayList<>();

        fm = new FileManager(this);
        fm.creatDirectory();
        try{
            otherPlayersGameStatus = fm.renewClients();
        }catch (Exception ignored){

        }

        sendScoreToServer();
    }

    public void beginGame(String difficulty){
        try{
            GameMap gameMap = new GameMap(difficulty,this);
            gameMap.showGameMap();
        }catch(Exception ignored){

        }
    }



    public void sendScoreToServer() {
        try(Socket client = new Socket("127.0.0.1",5050)){
            OutputStream out = client.getOutputStream();
            String temp = this.toString();
            out.write(temp.getBytes());
        }
        catch(IOException ex){
            System.err.println(ex);
        }
    }

    public void setHighScore(){
        int max = 0;
        for(GameMap gm : allOfGames){
            if(gm.getScore() > max){
                max = gm.getScore();
            }
        }
        highScore = max;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void showUserPanel(){
        try{
            userPanel = new UserPanel(this);
        } catch (IOException ignored) {
        }
        userPanel.showUserPanel();
    }

    public void saveThisGame(GameMap gameMap) throws IOException {
        FileManager fm = new FileManager(this);
        if(!gameMap.isSaved()){
            allOfGames.add(gameMap);
            gameMap.setCounterOfThisGame(allOfGames.indexOf(gameMap) + 1);
        }
        fm.saveGameInTheDirectory(gameMap);
    }

    public ArrayList<GameMap> getAllOfGames() {
        return allOfGames;
    }

    public GameMap findGameMap(String selectedValue) {
        for(GameMap gm : allOfGames){
            if(selectedValue.equals(gm.toString())){
                return gm;
            }
        }
        return null;
    }

    public GameMap searchByGameID(int gameID){
        for(GameMap gm : allOfGames){
            if(gm.getCounterOfThisGame() == gameID){
                return gm;
            }
        }
        return null;
    }

    public void loadGame(GameMap gameMap) throws IOException {
        gameMap.resumeGame();
    }

    public void updateGameMapsFromFile() throws IOException, ClassNotFoundException {
        FileManager fm = new FileManager(this);
        allOfGames = fm.updateRegisteredPersonGameMaps(allOfGames.size());
    }

    public void savePerson() throws IOException {
        FileManager fm = new FileManager(this);
        fm.savePerson(this);
    }

    public void losingGame(String difficulty){
        this.lose++;
        if(difficulty.equals("Normal")){
            this.score -= 1;
        }else if(difficulty.equals("Hard")){
            this.score -= 3;
        }
        sendScoreToServer();
    }

    public void winingGame(String difficulty){
        this.won++;
        if(difficulty.equals("Normal")){
            this.score += 3;
        }else if(difficulty.equals("Hard")){
            this.score += 10;
        }
        sendScoreToServer();
    }

    @Override
    public String toString() {
        return this.userName + "   " + "Loses : " + this.lose + "   " + "Wins : " + this.won + "   " + "Score : " + this.score + "   " + "HardGames : " + this.hardGames + "   " + "NormalGames : " + this.normalGames;
    }
}
