import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


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
        
        //INIT ATTRIBUTES
        this.server = server;
        this.client = client;
        this.serverthread = serverthread;
        this.pca = new PlayerCardArray(7);
        this.cs = new CardStack();
        this.gcs = new GetterCardStack();
        
        
        //ADD OBJECTS TO WORLD
        this.addObject(pca, -10, -10);
        this.addObject(cs, getWidth()/2 - 50, getHeight()/2);
        this.addObject(gcs, getWidth()/2 + 50, getHeight()/2);
        
        //GENERATE STARTER CARDS
        pca.generateStarterCards();
        
        
        //LISTENER FOR CARDSTACK CHANGES
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
        System.out.println("closed");
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
    
    public CardStack getCardStack() { return this.cs; }
    
    //SETTER
    public void setServerThread(Thread thread) {
        serverthread = thread;
    }

}
