import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Card extends Actor
{
    private GreenfootImage image;
    private int colorindex;
    private int numberindex;
    private boolean specialcard;
    private boolean isEnabled;
    private Color color;
    
    public Card(/*GreenfootImage card_image,*/ int cardcolorindex, int cardnumberindex, boolean specialcard) {
        
        //this.image = card_image;
        this.colorindex = cardcolorindex;
        this.numberindex = cardnumberindex;
        this.specialcard = specialcard;
        
        if(specialcard) {
            color = Color.BLACK;
        } else {           
            if(colorindex == 0) {
                color = Color.RED;
            } else if(colorindex == 1) {
                color = Color.YELLOW;
            } else if(colorindex == 2) {
                color = Color.GREEN;
            } else {
                color = Color.BLUE;
            }
        }

        this.image = new GreenfootImage("" + this.numberindex, 100, Color.WHITE, color);
        this.image.scale(50, 75);
        setImage(this.image);
    }
    public void act()
    {
        if(Greenfoot.mouseClicked(this)) {
        
            PlayerCardArray pcatemp = getWorld().getObjects(PlayerCardArray.class).get(0);
            pcatemp.disableAll();
            setEnabled();
            
        }
    }
    
    
    public GreenfootImage getImage() {
        return this.image;
    }
    
    public void setEnabled() {
        this.image = new GreenfootImage("" + this.numberindex, 100, Color.WHITE, color);
        this.image.scale(55, 80);
        this.image.setColor(Color.WHITE);
        this.image.drawRect(-1,0,55,80);
        this.image.drawRect(0,0,55,80);
        this.image.drawRect(0,-1,55,80);
        setImage(this.image);
        this.isEnabled = true;
    }
    
    public void setDisabled() {
        
        this.image = new GreenfootImage("" + this.numberindex, 100, Color.WHITE, color);
        this.image.scale(50, 75);
        setImage(this.image);
        this.isEnabled = false;
    }
    
    
    public int getColorIndex() { return colorindex; }
    
    public int getNumberIndex() { return numberindex; }
    
    public boolean isSpecialCard() { return specialcard; }
    
    public boolean isEnabled() { return isEnabled; } 
    
}
