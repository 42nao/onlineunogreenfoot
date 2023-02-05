import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Client {
    
    private static Socket socket;
    
    
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
    
    
    public Socket getSocket() {
        return socket;
    }
    
    
    
}
