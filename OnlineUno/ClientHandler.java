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
                    if(socket == server.getCurrentMoveSocket()) {
                        try
                        {
                            cardMap = (HashMap) hashmapInputStream.readObject();
                            int nextmove = server.getClientList().indexOf(socket) + 1;
                            if(nextmove >= server.getClientList().size()) {
                                nextmove = 0;
                            }
                            System.out.println(nextmove);
                            server.setCurrentMoveSocket(server.getClientList().get(nextmove));
                            System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + " -> CARD INFO:\n color: " + cardMap.get("colorindex") + "; number: " + cardMap.get("numberindex") + "; specialcard:" + cardMap.get("specialcard"));
                            for(Socket client : server.getClientList()) {
                                if(server.getCurrentMoveSocket() == client) {
                                    cardMap.put("yourturn", 1);
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
                        }

                }
                socket.close();
               
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}