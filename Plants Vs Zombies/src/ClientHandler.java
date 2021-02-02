import java.io.File;
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
        FileManager fm = new FileManager();
        try{
            clientList = fm.renewClients();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ClientHandler(){

    }

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

    public static ArrayList<String> getClientList() {
        return clientList;
    }


}
