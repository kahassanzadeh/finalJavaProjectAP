import java.io.*;

/**
 * this class is created to write objects to file
 *
 * @author Mohammadreza Hassanzadeh
 * @since Dec 16 2020
 * @version 1.0
 */
public class WriteObjectToFile {
    //Object Stream for writing to file
    private ObjectOutputStream out;

    /**
     * constructor for writing file to the given address
     *
     * @param fileAddress address of file that we want to write
     * @throws FileNotFoundException if file not found
     * @throws IOException if writing is corrupted
     */
    public WriteObjectToFile(String fileAddress) throws FileNotFoundException, IOException {
        out = new ObjectOutputStream(new FileOutputStream(new File(fileAddress)));
    }

    /**
     * write an object to the file address , ObjectOutputStream
     * @param o object that we want to write in the file
     * @throws IOException if writing is corrupted
     */
    public void writeToFile(Object o) throws IOException {
        out.writeObject(o);
    }

    /**
     * this method will close the file
     * @throws IOException if writing is corrupted
     */
    public void closeConnection() throws IOException {
        out.close();
    }
}
