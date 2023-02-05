import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.io.ObjectInputStream;


public class Server {

    private boolean running = true;
    private ServerSocket ss;
    private int[][] currentcardmatrix;
    private int[][] stackmatrix;
    private int currentplayers;
    private int maxplayers;
    
    public void launchServer(int players) throws IOException {

        this.maxplayers = players;
        this.currentplayers = 0;
        this.currentcardmatrix = new int[4][15];
        this.stackmatrix = new int[4][15];
        this.ss = new ServerSocket(7777);
        
        System.out.println("ServerSocket awaiting connections...");


        while (running) {

            if(currentplayers > maxplayers) {
                return;
            }
            
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            this.currentplayers += 1;
            System.out.println("Connection from " + socket + "!");
            
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream hashmapInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Integer> cardMap;
            try
            {
                cardMap = (HashMap) hashmapInputStream.readObject();
                System.out.println("CARD INFO:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
            }
            catch (ClassNotFoundException cnfe)
            {
                cnfe.printStackTrace();
            }
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
}

