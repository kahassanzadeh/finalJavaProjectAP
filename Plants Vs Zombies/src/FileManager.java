import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

    private Person person;

    private String path;

    private WriteObjectToFile writeGameMapToFile = null;

    private ReadObjectFromFile readObjectFromFile = null;

    public FileManager(Person person){
        this.person = person;
        path = "./Persons/" + person.getUserName();
    }
    public FileManager(){
    }
    public void creatDirectory(){

        try{
            Files.createDirectory(Paths.get(path));
        }catch (Exception ignored){

        }
    }

    public void saveGameInTheDirectory(GameMap gameMap) throws IOException {
        String temp = path + "/" + gameMap.getCounterOfThisGame() + ".ser";
        writeGameMapToFile = new WriteObjectToFile(temp);
        writeGameMapToFile.writeToFile(gameMap);
        writeGameMapToFile.closeConnection();
    }

    public ArrayList<GameMap> updateRegisteredPersonGameMaps(int numberOfGames) throws IOException, ClassNotFoundException {
        ArrayList<GameMap> gm = new ArrayList<>();
        for(int i = 1; i <= numberOfGames; i++){
            String temp = path + "/" + i + ".ser";
            readObjectFromFile = new ReadObjectFromFile(temp);
            gm.add((GameMap) readObjectFromFile.readFromFile());
        }
        return gm;
    }

    public void savePerson(Person person) throws IOException {
        String temp = path + "/" + person.getUserName() + ".ser";
        writeGameMapToFile = new WriteObjectToFile(temp);
        writeGameMapToFile.writeToFile(person);
        writeGameMapToFile.closeConnection();
    }

    public ArrayList<Person> renewAllOfPlayers() throws IOException, ClassNotFoundException {
        readObjectFromFile = new ReadObjectFromFile("./PlayersInfo/allOfPlayers.ser");
        return (ArrayList<Person>) readObjectFromFile.readFromFile();
    }

    public void saveAllOFPlayersToFile() throws IOException {
        writeGameMapToFile = new WriteObjectToFile("./PlayersInfo/allOfPLayers.ser");
        writeGameMapToFile.writeToFile(PlayerController.getAllOfPLayers());
        writeGameMapToFile.closeConnection();
    }

    public void saveClientsListServer(ArrayList<String> clientList) throws IOException {
        writeGameMapToFile = new WriteObjectToFile("./PlayersInfo/clientsList.ser");
        writeGameMapToFile.writeToFile(clientList);
        writeGameMapToFile.closeConnection();
    }

    public ArrayList<String> renewClients() throws IOException, ClassNotFoundException {
        readObjectFromFile = new ReadObjectFromFile("./PlayersInfo/clientsList.ser");
        return (ArrayList<String>) readObjectFromFile.readFromFile();
    }

}
