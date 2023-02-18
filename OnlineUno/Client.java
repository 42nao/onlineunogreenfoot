import java.io.*;
import java.net.*;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import greenfoot.*;

public class Client {
    private static Socket socket;
    private Thread listenerThread;
    public boolean running = true;
    private boolean yourturn;

    public void connect(String host, int port) throws IOException {
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
        
        if(!yourturn) {
            return;
        }
        
        final HashMap<String, Integer> cardMap = new HashMap<String, Integer>(); 
        cardMap.put("colorindex", card.getColorIndex());
        cardMap.put("numberindex", card.getNumberIndex());
        cardMap.put("specialcard", card.isSpecialCard() ? 1 : 0);
        
        final OutputStream yourOutputStream = socket.getOutputStream();
        final ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
        mapOutputStream.writeObject(cardMap);
        
        System.out.println("CLIENT: Die Karte wurde an den Server geschickt.");
        yourturn = false;
        
    }
    
    public void sendPlayerWon() throws IOException {
        final HashMap<String, Integer> map = new HashMap<String, Integer>(); 
        map.put("won", 1);
        
        final OutputStream yourOutputStream = socket.getOutputStream();
        final ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
        mapOutputStream.writeObject(map);
        
        System.out.println("CLIENT: PlayerWon an Server gesendet");

    }
    
    public void listenForCardChange(World world) throws IOException {
        listenerThread = new Thread() {
            public void run() {
                //Start Server
                try {
                    while (running) {
                        InputStream inputStream = socket.getInputStream();
                        ObjectInputStream hashmapInputStream = new ObjectInputStream(inputStream);
                        HashMap<String, Integer> cardMap;
                        try {
                            cardMap = (HashMap) hashmapInputStream.readObject();
                            
                            if(cardMap.get("gameend") != null && cardMap.get("gameend") == 1) {
                                world.showText("Das Spiel ist vorbei.", world.getWidth()/2 , 100);
                                System.out.println(cardMap.get("winner") + " | " + socket.getPort());
                                if(cardMap.get("winner") != null && cardMap.get("winner") == 1) {
                                    world.showText("Du hast gewonnen!", world.getWidth()/2 , 150);
                                } else {
                                    world.showText("Du hast verloren!", world.getWidth()/2 , 150);
                                }
                            }
                            
                            
                            CardStack cs = world.getObjects(CardStack.class).get(0); 
                            cs.setCurrentCard(new Card(cardMap.get("colorindex"), cardMap.get("numberindex"), (cardMap.get("specialcard") == 1 ? true : false)));

                            if(cardMap.get("yourturn") != null) {
                                world.showText("Du bist an der Reihe!", world.getWidth()/2 , 100);
                                yourturn = true;
                            } else {
                              world.showText("Jemand anderes ist an der Reihe!", world.getWidth()/2 , 100);
                            }
                            
                        } catch (ClassNotFoundException cnfe) {
                            cnfe.printStackTrace();
                        }
                    }
                } catch (IOException e) { 
                    System.out.println("ClientSide Fehler | " + e);
                }
            }
        };
        listenerThread.start();
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public boolean isYourTurn() { return this.yourturn; }
    
}
