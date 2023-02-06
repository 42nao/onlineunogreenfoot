import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class Server {

    private boolean running = true;
    private ServerSocket ss;
    private int[][] currentcardmatrix;
    private int[][] stackmatrix;
    private int currentplayers;
    private int maxplayers;
    private ArrayList<Socket> clientlist;
    

    public void launchServer(int players) throws IOException {
        this.maxplayers = players;
        this.currentplayers = 0;
        this.currentcardmatrix = new int[4][15];
        this.stackmatrix = new int[4][15];
        this.clientlist = new ArrayList<Socket>();
        this.ss = new ServerSocket(7777);
        
        System.out.println("ServerSocket awaiting connections...");


        while (running) {

            if(currentplayers > maxplayers) {
                return;
            }
            
            Socket socket = ss.accept();
            clientlist.add(socket);
            new ClientHandler(socket, this).start();
            System.out.println(clientlist.size());
            this.currentplayers += 1;
            
        }


        System.out.println("Closing sockets.");
        ss.close();
    }
    
    public boolean isRunning() {
        return running;
    }
    
    
    public void close() {
        running = false;
        System.out.println("Closing sockets.");
        try {
            ss.close();
        } catch (IOException | NullPointerException e) {
            return;
        }
    }
    

    public ArrayList<Socket> getClientList() { return clientlist; }
}




