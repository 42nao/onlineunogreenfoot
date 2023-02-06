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
        
        //INIT ATTRIBUTES
        this.server = new Server();
        this.client = new Client();
        
        //ADD OBJECTS TO WORLD
        this.addObject(new ButtonHostSelf(), getWidth()/2 - 150, getHeight()/2);
        this.addObject(new ButtonJoinOther(), getWidth()/2 + 150, getHeight()/2);
    }
    
    @Override
    public void stopped() {
        serverthread.stop();
        server.close();
        try
        {
            client.getSocket().close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        System.out.println("closing");
    }
    
    //GETTER
    public Server getServer() {
        return server;
    }
    
    public Thread getServerThread() {
        return serverthread; 
    }
    
    public Client getClient() {
        return client;
    }

    //SETTER
    public void setServerThread(Thread thread) {
        serverthread = thread;
    }
    
    
}
