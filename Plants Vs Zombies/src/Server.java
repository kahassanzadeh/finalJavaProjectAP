import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class created for the server
 * @author Mohammad reza hassanzadhe
 */
public class Server {

    public static void main(String[] args){
        ExecutorService pool = Executors.newCachedThreadPool();
        int counter = 0;
        try(ServerSocket welcomingSocket = new ServerSocket(5050)) {
            while(counter < 1000){
                Socket connectionSocket = welcomingSocket.accept();
                counter++;
                pool.execute(new ClientHandler(connectionSocket,counter));
            }
            pool.shutdown();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}