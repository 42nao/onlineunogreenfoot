import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ButtonHostSelf extends Actor
{
    
    public ButtonHostSelf() {
        GreenfootImage img = new GreenfootImage("hostgame.png");
        img.scale(200, 75);
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
            
            try
            {
                world.getClient().connect("localhost", 7777);
                Greenfoot.setWorld(new GameWorld(world.getServer(), world.getClient(), world.getServerThread()));
            }
            catch (IOException | NullPointerException e)
            {
                System.out.println("FEHLER: Konnte nicht mit dem Host verbinden.");
            }
        
            
        }
    }    
}
