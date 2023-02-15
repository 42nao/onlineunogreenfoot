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
                    System.out.println( server.getCurrentMoveSocket().getPort());                    
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream hashmapInputStream = new ObjectInputStream(inputStream);
                    HashMap<String, Integer> cardMap;
                    if(true/*socket == server.getCurrentMoveSocket()*/) {
                        try
                        {
                            cardMap = (HashMap) hashmapInputStream.readObject();
                            System.out.println("INDEX:" + server.getClientList().indexOf(socket));
                            
                            server.addNextMove(server.getClientList().indexOf(socket) + 1);
                            
                            System.out.println("next:" + server.getNextMove() + " | " + server.getClientList().size());
                            server.setCurrentMoveSocket(server.getClientList().get(server.getNextMove()));
                            System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + " -> CARD INFO:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
                            
                            System.out.println("CMS:" + server.getCurrentMoveSocket().getPort());
                            for(Socket client : server.getClientList()) {
                                HashMap loopCardMap = cardMap;
                                if(server.getCurrentMoveSocket() == client) {
                                    loopCardMap.put("yourturn", 1);
                                    System.out.println("tosend:" + loopCardMap.toString());
                                }
                                try {
                                    
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
                    } else {
                        System.out.println("blocked");
                    }

                }
                socket.close();
               
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}