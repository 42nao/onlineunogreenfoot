 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;

public class UnoWorld extends World
{
    
    private Server server;
    private Thread serverthread;
    private Client client;

    public UnoWorld()
    {    
        super(600, 400, 1); 
        
        
        
        server = new Server();
        client = new Client();
        /*
        serverthread = new Thread() {
                public void run() {
                    //Start Server
                    try {
                        server.launchServer(Integer.valueOf(4));
                    } catch (IOException e) { 
                        System.out.println("Fehler beim Verbinden! | " + e);
                    }
                }
        };
        
        Greenfoot.setWorld(new GameWorld(server, client, serverthread)); //DEBUG
        */
        
        this.addObject(new ButtonHostSelf(), getWidth()/2 - 150, getHeight()/2);
        this.addObject(new ButtonJoinOther(), getWidth()/2 + 150, getHeight()/2);
        //serverthread.start();
    }
    
    @Override
    public void stopped() {
        
        try {
            server.close();
            serverthread.stop();
            System.out.println(server.isRunning());
        } catch (NullPointerException e) {
            return;
        }
        
    }
    
    
    public Server getServer() {
        return server;
    }
    
    
    public Thread getServerThread() {
        return serverthread; 
    }
    
    public void setServerThread(Thread thread) {
        serverthread = thread;
    }
    
    public Client getClient() {
        return client;
    }
    
    
    
}
