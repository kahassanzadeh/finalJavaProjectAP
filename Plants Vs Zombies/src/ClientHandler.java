import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler implements Runnable {

    private Socket connectionSocket;

    private int clientNumber;

    private static ArrayList<String>  clientList = new ArrayList<>();

    public ClientHandler(Socket connectionSocket,int clientNumber){
        this.connectionSocket = connectionSocket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try{
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[4096];
            int read = in.read(buffer);
            for(String s : clientList){
                out.write(s.getBytes());
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }finally {
            try{
                connectionSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
