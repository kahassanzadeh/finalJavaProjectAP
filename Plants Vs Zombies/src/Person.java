import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Person implements Serializable {


    private String userName;

    private String password;

    private String name;

    private ArrayList<GameMap> allOfGames;

    private ArrayList<String> scoreList = new ArrayList<>();

    private int highScore;

    private transient UserPanel userPanel;

    private transient FileManager fm;




    public Person(String name,String userName,String password){

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
    }

    public void beginGame(String difficulty){
        try{
            GameMap gameMap = new GameMap(difficulty,this);
            gameMap.showGameMap();
        }catch(Exception ignored){

        }
    }


    public void writeGameMap(GameMap gameMap){

    }

    public void sendAndReceiveScoreToServer() {
        setHighScore();
        try(Socket client = new Socket("127.0.0.1",5050)){
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            byte[] buffer = new byte[4096];
            String temp = userName + " " + highScore;
            out.write(temp.getBytes());
            while(true){
                int read = in.read(buffer);

            }
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
}
