import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * 
 * Write a description of class ButtonHostSelf here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonHostSelf extends Actor
{
    
    public ButtonHostSelf() {
        
        GreenfootImage img = new GreenfootImage("Host Game", 40, Color.WHITE, Color.GREEN);
        setImage(img);
    }
    
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            UnoWorld world = (UnoWorld) this.getWorld();
            String input = Greenfoot.ask("Gebe die Anzahl an Spielern an:");
            
            if (!(input.matches("[0-9]+") && Integer.valueOf(input) <= 5)) {
                System.out.println("Gebe eine Zahl zwischen 1 und 5 an.");
                return;
            }
            
            world.setServerThread(new Thread() {
            
                public void run() {
                    //Start Server
                    try {
                        world.getServer().launchServer(Integer.valueOf(input));
                        
                    } catch (IOException e) { 
                        System.out.println("Fehler beim Verbinden! | " + e);
                    }
                }
            });
            world.getServerThread().start();
            
            System.out.println("Der Server läuft jetzt über dein Gerät.\nDeine IP findest du mit Alt + Klick auf das WLAN Symbol auf deinem MacBook.");
            
        

            
        }
    }    
}
