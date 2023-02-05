import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CardStack extends Actor
{
    private Card card;
    
    public CardStack() {
        
        GreenfootImage img = new GreenfootImage("", 40, Color.WHITE, Color.GRAY);
        img.scale(75, 100);
        setImage(img);
    
    }
    
    public void setCurrentCard(Card card) {
        
        this.card = card;
        setImage(card.getImage());
        
    }
    
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this)) {
            PlayerCardArray pcatemp = getWorld().getObjects(PlayerCardArray.class).get(0);
            Card targetcard = pcatemp.getEnabledCard();
        
            GameWorld world = (GameWorld) getWorld();
            try
            {
                world.getClient().sendCardPlaced(targetcard);
            }
            catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
    }
}
