import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class Server {

    private boolean running = true;
    private ServerSocket ss;
    private int currentplayers;
    private int maxplayers;
    private ArrayList<Socket> clientlist;
    private Socket currentmovesocket;
    private int nextmove;
    
    public void launchServer(int players) throws IOException {
        this.maxplayers = players;
        this.currentplayers = 0;
        this.clientlist = new ArrayList<Socket>();
        this.ss = new ServerSocket(7777);
        this.nextmove = 0;
        
        System.out.println("ServerSocket awaiting connections...");
        
        while (running) {

            if(currentplayers > maxplayers) {
                return;
            }
            
            Socket socket = ss.accept();
            clientlist.add(socket);
            new ClientHandler(socket, this).start();
            System.out.println(clientlist.size());
            
            if(currentplayers == 0) {
                currentmovesocket = socket;
            }
            
            this.currentplayers += 1;
            
            Card card = Utils.getRandomCard();
            
            
            for(Socket client : clientlist) {           
                try {
                    
                    final HashMap<String, Integer> cardMap = new HashMap<String, Integer>(); 
                    cardMap.put("colorindex", card.getColorIndex());
                    cardMap.put("numberindex", card.getNumberIndex());
                    cardMap.put("specialcard", card.isSpecialCard() ? 1 : 0);
                    if(currentmovesocket == client) {
                        cardMap.put("yourturn", 1);
                    }
                    
                    final OutputStream yourOutputStream = socket.getOutputStream();
                    final ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
                    mapOutputStream.writeObject(cardMap);
                                
                } catch(IOException e){ 
                    e.printStackTrace(); 
                }
            }
            
        }
        
        System.out.println("Closing sockets.");
        ss.close();
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
    
    public boolean isRunning() { return this.running; }
    
    public Socket getCurrentMoveSocket() { return this.currentmovesocket; }
    
    public ArrayList<Socket> getClientList() { return this.clientlist; }
    
    public int getNextMove() { return this.nextmove; }
    
    public void setCurrentMoveSocket(Socket currentmovesocket) { this.currentmovesocket = currentmovesocket; }

    public void setNextMove(int nextmove) { this.nextmove = nextmove; }

    public void addNextMove(int i) {
        this.nextmove += i;
        if(nextmove >= clientlist.size()) {
            nextmove = 0;
        }
    }
    
}




