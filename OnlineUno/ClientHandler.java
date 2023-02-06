import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public boolean running = true;

    public ClientHandler(Socket socket) {
            this.socket = socket;
    }

    public void run() {
        try {
                System.out.println("Connection from " + socket + "!");
                
                while (running) {
                
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
                
                in.close();
                out.close();
                socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}