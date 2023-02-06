import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PlayerCardArray extends Actor
{
    private Card[] cards;
    private int startercardamount;
    
    public PlayerCardArray(int startercardamount) {
        this.startercardamount = startercardamount;
        this.cards = new Card[100];  
        
        setImage(new GreenfootImage(10, 10));
    }
    
    public void act() { }
    
    public void generateStarterCards() {
        for(int i = 0; i < startercardamount; i++) {
            cards[i] = Utils.getRandomCard();
            getWorld().addObject(cards[i],50 + i * 55, getWorld().getHeight() - 75);
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
}
