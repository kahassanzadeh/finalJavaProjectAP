import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * this class created for Managing files and saving ana loading them
 * @author Mohammadreza Hassanzadeh
 * @version 1.1
 */
public class FileManager {
    //person that we want to write
    private Person person;
    //path that we want to write or read from
    private String path;
    //write Object
    private WriteObjectToFile writeGameMapToFile = null;
    //readObject
    private ReadObjectFromFile readObjectFromFile = null;

    /**
     * constructor
     * @param person
     */
    public FileManager(Person person){
        this.person = person;
        path = "./Persons/" + person.getUserName();
    }
    public FileManager(){
    }

    /**
     * for each person this method creates a new folder
     */
    public void creatDirectory(){

        try{
            Files.createDirectory(Paths.get(path));
        }catch (Exception ignored){

        }
    }

    /**
     * this method will save the game into the directory that we want
     * @param gameMap game Map
     * @throws IOException if it can't write to file
     */
    public void saveGameInTheDirectory(GameMap gameMap) throws IOException {
        String temp = path + "/" + gameMap.getCounterOfThisGame() + ".ser";
        writeGameMapToFile = new WriteObjectToFile(temp);
        writeGameMapToFile.writeToFile(gameMap);
        writeGameMapToFile.closeConnection();
    }

    /**
     * this method will update the gameMaps of the player
     * @param numberOfGames number Of games that the person have
     * @return array list of gameMaps
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<GameMap> updateRegisteredPersonGameMaps(int numberOfGames) throws IOException, ClassNotFoundException {
        ArrayList<GameMap> gm = new ArrayList<>();
        for(int i = 1; i <= numberOfGames; i++){
            String temp = path + "/" + i + ".ser";
            readObjectFromFile = new ReadObjectFromFile(temp);
            gm.add((GameMap) readObjectFromFile.readFromFile());
        }
        return gm;
    }

    /**
     * this method will save person to the file
     * @param person person objects
     * @throws IOException
     */
    public void savePerson(Person person) throws IOException {
        String temp = path + "/" + person.getUserName() + ".ser";
        writeGameMapToFile = new WriteObjectToFile(temp);
        writeGameMapToFile.writeToFile(person);
        writeGameMapToFile.closeConnection();
    }

    /**
     * this method will renew all of the players list
     * @return array list of people
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<Person> renewAllOfPlayers() throws IOException, ClassNotFoundException {
        readObjectFromFile = new ReadObjectFromFile("./PlayersInfo/allOfPlayers.ser");
        return (ArrayList<Person>) readObjectFromFile.readFromFile();
    }

    /**
     * saving all of players into file
     * @throws IOException
     */
    public void saveAllOFPlayersToFile() throws IOException {
        writeGameMapToFile = new WriteObjectToFile("./PlayersInfo/allOfPlayers.ser");
        writeGameMapToFile.writeToFile(PlayerController.getAllOfPLayers());
        writeGameMapToFile.closeConnection();
    }

    /**
     * saving clients to file
     * @param clientList arraylist of string
     * @throws IOException
     */
    public void saveClientsListServer(ArrayList<String> clientList) throws IOException {
        writeGameMapToFile = new WriteObjectToFile("./PlayersInfo/clientsList.ser");
        writeGameMapToFile.writeToFile(clientList);
        writeGameMapToFile.closeConnection();
    }

    /**
     * renew all of the clients information
     * @return array list of string consists of clients information
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> renewClients() throws IOException, ClassNotFoundException {
        readObjectFromFile = new ReadObjectFromFile("./PlayersInfo/clientsList.ser");
        return (ArrayList<String>) readObjectFromFile.readFromFile();
    }

}
