import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.net.ConnectException;
import java.io.IOException;
/**
 * Write a description of class ButtonJoinOther here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonJoinOther extends Actor
{
    
     public ButtonJoinOther() {
        
        GreenfootImage img = new GreenfootImage("Join Game", 40, Color.WHITE, Color.BLUE);
        setImage(img);
    }
    
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            UnoWorld world = (UnoWorld) this.getWorld();
            String input = Greenfoot.ask("Gebe die IP Adresse des Hosts an:");
            
            try
            {
                world.getClient().connect(input.replace(" ", ""), 7777);
                Greenfoot.setWorld(new GameWorld(world.getServer(), world.getClient(), world.getServerThread()));
                
            }
            catch (IOException | NullPointerException e)
            {
                System.out.println("FEHLER: Konnte nicht mit dem Host verbinden.");
            }
        

            
        }
    }
    
}
