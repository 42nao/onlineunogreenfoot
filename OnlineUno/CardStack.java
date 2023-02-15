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
            GameWorld world = (GameWorld) getWorld();
            
            if(!(world.getClient().isYourTurn())) {
                System.out.println("Du bist nicht an der Reihe!");
                return;
            }
                 
            PlayerCardArray pcatemp = getWorld().getObjects(PlayerCardArray.class).get(0);
            Card targetcard = pcatemp.getEnabledCard();
            
            if(Utils.checkCardAllowed(this.card, targetcard)) {
                if(targetcard.isSpecialCard()) {
                    String input = Greenfoot.ask("Gebe eine Farbe an, die du haben willst");
                    System.out.println(input);
                    int colorindex = 0;
                    if(input.toLowerCase().equalsIgnoreCase("rot")) {
                        colorindex = 0;
                    } else if(input.toLowerCase().equalsIgnoreCase("gelb")) {
                        colorindex = 1;
                    } else if(input.toLowerCase().equalsIgnoreCase("grün")) {
                        colorindex = 2;
                    } else {
                        colorindex = 3;
                    }
                    System.out.println(colorindex);
                    targetcard.changeColor(colorindex);
                }
                try
                {
                    world.getClient().sendCardPlaced(targetcard);
                    pcatemp.removeCard(targetcard);
                }
                catch (java.io.IOException ioe)
                {
                    ioe.printStackTrace();
                }
                
            } else {
                System.out.println("Diese Kart darfst du nicht setzen!");
            }
        }
    }
    
    public Card getCard() { return this.card; }
    
   
}
