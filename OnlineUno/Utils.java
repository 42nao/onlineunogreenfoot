/**
 * Write a description of class Utils here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Utils  
{
    
    public Utils()
    {
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    public static Card getRandomCard() {
        Card _card;
        if(getRandomNumber(0, 10) == 3) {
            _card = new Card(0, getRandomNumber(0, 1), true);
        } else {       
            _card = new Card(getRandomNumber(0, 4), getRandomNumber(0, 12), false);
        }
        return _card;
        
    }
}
