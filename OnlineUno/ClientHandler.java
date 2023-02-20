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
            System.out.println("Neue Verbindung: " + socket);
            while (running) {                  
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream hashmapInputStream = new ObjectInputStream(inputStream);
                HashMap<String, Integer> cardMap;
                try {
                    cardMap = (HashMap) hashmapInputStream.readObject();
                    
                    if(cardMap.get("won") != null && cardMap.get("won") == 1) {
                        
                        HashMap<String, Integer> wonMap = new HashMap<String, Integer>();
                        wonMap.put("gameend", 1);
                        wonMap.put("winner", 0);
                        for(Socket client : server.getClientList()) {
                            HashMap loopCardMap = wonMap;
                            loopCardMap.remove("winner");
                            if(socket == client) {
                                 loopCardMap.put("winner", 1);
                            }
                            try {
                                final OutputStream outputStream = client.getOutputStream();
                                final ObjectOutputStream mapOutputStream = new ObjectOutputStream(outputStream);
                                mapOutputStream.writeObject(wonMap);
                            } catch(IOException e){ 
                                e.printStackTrace(); 
                            }
                        }
                        
                    } else {
                        server.addNextMove(server.getClientList().indexOf(socket) + 1);
                        server.setCurrentMoveSocket(server.getClientList().get(server.getNextMove())); 
                        
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
                    
                    
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
            socket.close();
               
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}