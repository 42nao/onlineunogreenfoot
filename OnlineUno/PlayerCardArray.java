import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PlayerCardArray extends Actor
{
    private Card[] cards;
    private int startercardamount;
    private boolean row2;

    public PlayerCardArray(int startercardamount) {
        this.startercardamount = startercardamount;
        this.cards = new Card[100];  
        
        setImage(new GreenfootImage(10, 10));
    }
    
    public void act() { }
    
    public Card[] getCards() {
        return cards;
    }
    
    public void generateStarterCards() {
        for(int i = 0; i < startercardamount; i++) {
            cards[i] = Utils.getRandomCard();
            getWorld().addObject(cards[i],50 + i * 55, getWorld().getHeight() - 150);
        }
    }
    
    public void disableAll() {
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] == null) return;
            
            cards[i].setDisabled();
        }
    }
    
    public Card getEnabledCard() {
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] != null) { 
                if(cards[i].isEnabled()) {
                    return cards[i];
                }
            }
            
        }
        return null;
    }
    
    public void addCard(Card card) {
        
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] == null) {
                cards[i] = card;
                getWorld().addObject(cards[i],50 + i * 55, getWorld().getHeight() - 150);
                break;
            } 
        }
        System.out.println("added");
    }
    
    public void removeCard(Card card) {
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] == card) {
                cards[i] = null;
                break;
            } 
        }
        getWorld().removeObject(card);
        
        int x = 0;
        for(Card loopcard : cards) {
            if(loopcard != null) {
                x += 1;
            }
        }
        
        GameWorld world = (GameWorld) getWorld();
        
        System.out.println("x:" + x);
        if(x == 0) {
            //world.showText("Du hast gewonnen!", world.getWidth()/2 , 150);
            try
            {
                world.getClient().sendPlayerWon();
            }
            catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }
            Greenfoot.stop();
        }
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
}
