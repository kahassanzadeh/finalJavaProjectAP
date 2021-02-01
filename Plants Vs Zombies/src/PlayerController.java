import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class PlayerController implements Serializable {

    private static ArrayList<Person> allOfPLayers = new ArrayList<>();

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

    public static void checkUserName(String userName) throws Exception {
        for(Person p : allOfPLayers){
            if(p.getUserName().equals(userName)){
                throw new Exception("This username had been registered, Please try another username");
            }
        }
    }

    public static void addPerson(Person person){
        allOfPLayers.add(person);
    }

    public static void renewPlayers() throws IOException, ClassNotFoundException {
        FileManager fm = new FileManager();
        allOfPLayers = fm.renewAllOfPlayers();
    }

    public static ArrayList<Person> getAllOfPLayers() {
        return allOfPLayers;
    }
}
