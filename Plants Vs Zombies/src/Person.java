import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * this class created for saving the players of the game
 * @author mohammadreza hassanzadeh
 * @version 1.1
 */
public class Person implements Serializable {

    //username of the player
    private String userName;
    //password of the player
    private String password;
    //name of the player
    private String name;
    //game map array list
    private ArrayList<GameMap> allOfGames;
    //score of the player
    private int score;
    //lose games
    private int lose;
    //won games
    private int won;
    //counting for hard Games
    private int hardGames;
    //counting for normal games
    private int normalGames;

    //all of the players
    private transient ArrayList<String> otherPlayersGameStatus;
    //user panel
    private transient UserPanel userPanel;
    //file manager
    private transient FileManager fm;


    /**
     * constructor for this class
     * @param name name of the person
     * @param userName user name of the person
     * @param password password of the person
     */
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

    /**
     * begin a game for the person
     * @param difficulty difficulty of the game
     */
    public void beginGame(String difficulty){
        try{
            GameMap gameMap = new GameMap(difficulty,this);
            gameMap.showGameMap();
        }catch(Exception ignored){

        }
    }


    /**
     * sending the score to server
     */
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

    /**
     * getter for username
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * getter for the password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * showing the user panel
     */
    public void showUserPanel(){
        try{
            userPanel = new UserPanel(this);
        } catch (IOException ignored) {
        }
        userPanel.showUserPanel();
    }

    /**
     * saving the game into file
     * @param gameMap game map
     * @throws IOException
     */
    public void saveThisGame(GameMap gameMap) throws IOException {
        FileManager fm = new FileManager(this);
        if(!gameMap.isSaved()){
            allOfGames.add(gameMap);
            gameMap.setCounterOfThisGame(allOfGames.indexOf(gameMap) + 1);
        }
        fm.saveGameInTheDirectory(gameMap);
    }

    /**
     * getting all of the games
     * @return array list of gamMaps
     */
    public ArrayList<GameMap> getAllOfGames() {
        return allOfGames;
    }

    /**
     * finding the game map
     * @param selectedValue selected game map
     * @return game map
     */
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

    /**
     * loading the game
     * @param gameMap game map
     * @throws IOException
     */
    public void loadGame(GameMap gameMap) throws IOException {
        gameMap.resumeGame();
    }

    /**
     * updating all of the game players
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void updateGameMapsFromFile() throws IOException, ClassNotFoundException {
        FileManager fm = new FileManager(this);
        allOfGames = fm.updateRegisteredPersonGameMaps(allOfGames.size());
    }

    /**
     * saving the person to file
     * @throws IOException
     */
    public void savePerson() throws IOException {
        FileManager fm = new FileManager(this);
        fm.savePerson(this);
    }

    /**
     * if the player is losing
     * @param difficulty difficulty of the game
     */
    public void losingGame(String difficulty){
        this.lose++;
        if(difficulty.equals("Normal")){
            this.score -= 1;
        }else if(difficulty.equals("Hard")){
            this.score -= 3;
        }
        sendScoreToServer();
    }
    /**
     * if the player is wining
     * @param difficulty difficulty of the game
     */
    public void winingGame(String difficulty){
        this.won++;
        if(difficulty.equals("Normal")){
            this.score += 3;
        }else if(difficulty.equals("Hard")){
            this.score += 10;
        }
        sendScoreToServer();
    }

    /**
     * ++ the hard games
     */
    public void setHardGames() {
        this.hardGames++;
    }

    /**
     * ++ the normal games
     */
    public void setNormalGames() {
        this.normalGames++;
    }

    @Override
    public String toString() {
        return this.userName + "   " + "Loses : " + this.lose + "   " + "Wins : " + this.won + "   " + "Score : " + this.score + "   " + "HardGames : " + this.hardGames + "   " + "NormalGames : " + this.normalGames;
    }
}
