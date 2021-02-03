import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class created for handling the Clients
 * @author Mohammadreza Hassanzadeh
 * @version 1.1
 */
public class ClientHandler implements Runnable {
    //connection socket
    private Socket connectionSocket;
    //client number
    private int clientNumber;
    //array list of all players
    private static ArrayList<String>  clientList = new ArrayList<>();

    /**
     * constructor for this class
     * @param connectionSocket
     * @param clientNumber
     */
    public ClientHandler(Socket connectionSocket,int clientNumber){
        this.connectionSocket = connectionSocket;
        this.clientNumber = clientNumber;
        FileManager fm = new FileManager();
        try{
            clientList = fm.renewClients();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ClientHandler(){
    }

    /**
     * because of threading i have implemented this run  method
     */
    @Override
    public void run() {
        try{
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[4096];
            while(true){
                try{
                    int read = in.read(buffer);
                    boolean flag = false;
                    String temp = new String(buffer,0,read);
                    for(int i = 0;i < clientList.size(); i++){
                        if(clientList.get(i).split("\\s+")[0].equals(temp.split("\\s+")[0])){
                            clientList.set(i,temp);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        clientList.add(temp);
                    }

                }catch(Exception e){
                    break;
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }finally {
            try{
                FileManager fm = new FileManager();
                fm.saveClientsListServer(clientList);
                connectionSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }



}
