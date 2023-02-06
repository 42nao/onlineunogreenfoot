import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    public boolean running = true;

    public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
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
                        System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + " -> CARD INFO:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
                        for(Socket client : server.getClientList()) {
                            System.out.println(client.getPort());
                            try{
                                
                                final OutputStream outputStream = client.getOutputStream();
                                final ObjectOutputStream mapOutputStream = new ObjectOutputStream(outputStream);
                                mapOutputStream.writeObject(cardMap);
                                
                            } catch(IOException e){ 
                                e.printStackTrace(); 
                            }
                        }
                    }
                    catch (ClassNotFoundException cnfe)
                    {
                        cnfe.printStackTrace();
                    }
                }
                socket.close();
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}