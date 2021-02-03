import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class created for controlling the players
 */
public class PlayerController implements Serializable {
    //all of the players
    private static ArrayList<Person> allOfPLayers = new ArrayList<>();

    /**
     * searching a player
     * @param userName user name of the player
     * @param password password of the player
     * @return Person that found
     * @throws Exception
     */
    public static Person searchPerson(String userName,String password) throws Exception {
        for(Person p : allOfPLayers){
            if(p.getUserName().equals(userName)){
                if(p.getPassword().equals(password)){
                    return p;
                }else{
                    throw new Exception("Incorrect Password");
                }
            }
        }
        return null;
    }

    /**
     * checking if the username is not created before
     * @param userName
     * @throws Exception
     */
    public static void checkUserName(String userName) throws Exception {
        for(Person p : allOfPLayers){
            if(p.getUserName().equals(userName)){
                throw new Exception("This username had been registered, Please try another username");
            }
        }
    }

    /**
     * adding person to the list
     * @param person Person obj
     */
    public static void addPerson(Person person){
        allOfPLayers.add(person);
    }

    /**
     * renew all of the players
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void renewPlayers() throws IOException, ClassNotFoundException {
        FileManager fm = new FileManager();
        allOfPLayers = fm.renewAllOfPlayers();
    }

    /**
     * players list
     * @return
     */
    public static ArrayList<Person> getAllOfPLayers() {
        return allOfPLayers;
    }
}
