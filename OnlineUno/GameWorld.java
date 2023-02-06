import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    private Server server;
    private Thread serverthread;
    private Client client;
    private PlayerCardArray pca;
    private CardStack cs; 
    private GetterCardStack gcs;
    
    public GameWorld(Server server, Client client, Thread serverthread)
    {    
        super(800, 500, 1); 
        
        this.server = server;
        this.client = client;
        this.serverthread = serverthread;
        
        pca = new PlayerCardArray(7);
        cs = new CardStack();
        gcs = new GetterCardStack();
        this.addObject(pca, -10, -10);
        this.addObject(cs, getWidth()/2 - 50, getHeight()/2);
        this.addObject(gcs, getWidth()/2 + 50, getHeight()/2);
        pca.generateStarterCards();
        
        try
        {
            client.listenForCardChange(this);
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    
    @Override
    public void stopped() {
        serverthread.stop();
        server.close();
        try
        {
            client.getSocket().close();
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
        System.out.println(server.isRunning());

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
