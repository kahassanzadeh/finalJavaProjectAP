import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Person {


    private String userName;

    private String password;

    private String name;

    private ArrayList<GameMap> allOfGames;

    private ArrayList<String> scoreList = new ArrayList<>();

    private int highScore;

    private UserPanel userPanel;

    public Person(String name,String userName,String password){
        this.name = name;
        this.userName = userName;
        this.password = password;
        try{
            userPanel = new UserPanel();
        } catch (IOException ignored) {
        }
        allOfGames = new ArrayList<>();
    }

    public void beginGame(String difficulty){
        try{
            GameMap gameMap = new GameMap(difficulty);
            gameMap.showGameMap();
        }catch(Exception ignored){

        }
    }

    public GameMap readGame(){
        return null;
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
        userPanel.showUserPanel();
    }
}
