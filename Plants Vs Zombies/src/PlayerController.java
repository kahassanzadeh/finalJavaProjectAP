import java.util.ArrayList;

public class PlayerController {

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

}
