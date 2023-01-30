import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;

public class UnoWorld extends World
{
    
    private Server server;

    public UnoWorld()
    {    
        super(600, 400, 1); 
        
        
        server = new Server();
        Client client = new Client();
        
        
        Thread serverthread = new Thread() {
        
            public void run() {
                //Start Server
                try {
                    server.launchServer();
                    
                } catch (IOException e) { 
                    System.out.println("Fehler beim Verbinden! | " + e);
                }
            }
        };
        

        //serverthread.start();
        
        try
        {
            client.connect("localhost", 7777);
            client.send("this is a test");
           
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        
        
    }
    
    @Override
    public void stopped() {
        
        server.close();

    }
    
    
    
}
