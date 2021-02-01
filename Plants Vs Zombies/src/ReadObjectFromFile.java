import java.io.*;
/**
 * this class is created to read objects from files
 *
 * @author Mohammadreza Hassanzadeh
 * @since Dec 16 2020
 * @version 1.0
 */
public class ReadObjectFromFile {

    //ObjectInputStream
    private ObjectInputStream in;

    /**
     * creating an ObjectInputStream with its file address
     * @param address address of the file that we want to creat the ObjectInputStream
     * @throws FileNotFoundException if file not found
     * @throws IOException if reading is corrupted
     */
    public ReadObjectFromFile(String address) throws FileNotFoundException, IOException {
        in = new ObjectInputStream(new FileInputStream(new File(address)));
    }

    /**
     *  this method will return objects that is read from the file
     * @return objects that is read from the file
     * @throws ClassNotFoundException if class doesn't existed
     * @throws IOException if reading is corrupted
     */
    public Object readFromFile() throws ClassNotFoundException, IOException {
        return in.readObject();
    }

    /**
     * this method will close the file
     * @throws IOException if reading is corrupted
     */
    public void closeConnection() throws IOException {
        in.close();
    }
}
