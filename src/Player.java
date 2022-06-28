import java.util.ArrayList;

/**
 * This class contains fields and methods associated with a player
 */
public class Player {

    private String name;
    private int numberOfHands = 0;
    private int numberOfHandsWithTen = 0;

    public ArrayList<Card> getPlayersCardList() {
        return playersCardList;
    }

    public void setPlayersCardList(ArrayList<Card> playersCardList) {
        this.playersCardList = playersCardList;
    }

    public void addCard(Card c){
        playersCardList.add(c);
    }

    private ArrayList<Card> playersCardList;

    public Player(String name){
        this.name = name;
        playersCardList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfHands() {
        return numberOfHands;
    }

    public void setNumberOfHands(int numberOfHands) {
        this.numberOfHands = numberOfHands;
    }

    public int getNumberOfHandsWithTen() {
        return numberOfHandsWithTen;
    }

    public void setNumberOfHandsWithTen(int numberOfHandsWithTen) {
        this.numberOfHandsWithTen = numberOfHandsWithTen;
    }
}
