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
                            System.out.println("INDEX:" + server.getClientList().indexOf(socket));
                            server.addNextMove(server.getClientList().indexOf(socket) + 1);
                            System.out.println("next:" + server.getNextMove() + " | " + server.getClientList().size());
                            server.setCurrentMoveSocket(server.getClientList().get(server.getNextMove()));
                            //System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + " -> CARD INFO:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
                            
                            cardMap.remove("yourturn");
                            for(Socket client : server.getClientList()) {
                                HashMap loopCardMap = cardMap;
                                loopCardMap.remove("yourturn");
                                if(server.getCurrentMoveSocket() == client) {
                                    loopCardMap.put("yourturn", 1);
                                }
                                try {
                                    final OutputStream outputStream = client.getOutputStream();
                                    final ObjectOutputStream mapOutputStream = new ObjectOutputStream(outputStream);
                                    mapOutputStream.writeObject(loopCardMap);
                                    
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