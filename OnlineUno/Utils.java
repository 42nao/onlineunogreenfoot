import java.util.stream.*;

public class Utils  
{

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
    
    public static int indexOf(char[] arr, char val) {
        return IntStream.range(0, arr.length).filter(i -> arr[i] == val).findFirst().orElse(-1);
    }
    
    public static boolean checkCardAllowed(Card oldcard, Card newcard) {
        if(newcard.isSpecialCard()) {
            return true;
        } else if(oldcard.getColorIndex() == newcard.getColorIndex()) {
            return true;
        } else if(oldcard.getNumberIndex() == newcard.getNumberIndex()) {
            return true;
        }
        return false;
    }
}
