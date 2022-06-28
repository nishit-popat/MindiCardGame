import java.util.ArrayList;

/**
 * This class contains list of cards and some operations associated with it
 */
public class DeckOfCards {
    public ArrayList<Card> cardsList;

    public DeckOfCards(){
        cardsList = new ArrayList<>();
    }

    public void generateDeckOfCards(){
        for(Suit s: Suit.values()){
            for(Rank r: Rank.values()){
                cardsList.add(new Card(s,r));
            }
        }
    }
}
