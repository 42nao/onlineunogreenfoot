import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GetterCardStack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GetterCardStack extends Actor
{
    public GetterCardStack() {
        GreenfootImage img = new GreenfootImage("GET", 40, Color.WHITE, Color.BLACK);
        img.scale(75, 100);
        setImage(img);
    }
    
    public void act() {
        if(Greenfoot.mouseClicked(this)) {
            
            
            
            PlayerCardArray pcatemp = getWorld().getObjects(PlayerCardArray.class).get(0);
            GameWorld world = (GameWorld) getWorld();
            
            if(!(world.getClient().isYourTurn())) { return; }
            
            Card gcard = Utils.getRandomCard();
            pcatemp.addCard(gcard);
            
            Card currentcard = world.getCardStack().getCard();
            
            boolean t = false;
            
            
            for(Card card : pcatemp.getCards()) {
                if(card != null) {
                    if(card.isSpecialCard()) {
                        t = true;
                        break;
                    } else {
                        
                        if(card.getColorIndex() == currentcard.getColorIndex()) {
                            if(card.getNumberIndex() == currentcard.getNumberIndex()) {
                                t = true;
                                break;
                            }
                        }
                    }
                }
            }
            
            
            if(!t) {
                try
                {
                    world.getClient().sendCardPlaced(world.getCardStack().getCard());
                }
                catch (java.io.IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            
            
        }
    
    }
}
