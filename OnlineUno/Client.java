import java.io.*;
import java.net.*;
import java.io.ObjectOutputStream;
import java.util.HashMap;


public class Client {
    

    private static Socket socket;
    private Thread listenerThread;
    public boolean running = true;
    
    
    public static void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        
        System.out.println("Connected!");
    }
    
    public void close() {
        try {
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
            
    public void sendCardPlaced(Card card) throws IOException {
        final HashMap<String, Integer> cardMap = new HashMap<String, Integer>(); 
        cardMap.put("colorindex", card.getColorIndex());
        cardMap.put("numberindex", card.getNumberIndex());
        cardMap.put("specialcard", card.isSpecialCard() ? 1 : 0);
        
        final OutputStream yourOutputStream = socket.getOutputStream();
        final ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
        mapOutputStream.writeObject(cardMap);
        
        System.out.println("DEBUG: HashMap got send to Server");
        
    }
    
    public void listenForCardChange() throws IOException {
        listenerThread = new Thread() {
                public void run() {
                    //Start Server
                    try {
                        while (running) {
                
                            InputStream inputStream = socket.getInputStream();
                            ObjectInputStream hashmapInputStream = new ObjectInputStream(inputStream);
                            HashMap<String, Integer> cardMap;
                            try
                            {
                                cardMap = (HashMap) hashmapInputStream.readObject();
                                System.out.println("Client got Card. CARD:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
                            }
                            catch (ClassNotFoundException cnfe)
                            {
                                cnfe.printStackTrace();
                            }
                        }
                    } catch (IOException e) { 
                        System.out.println("ClientSide Fehler | " + e);
                    }
                }
        };
        
        
        
    
    }
    
    
    public Socket getSocket() {
        return socket;
    }
    
    
    
}
