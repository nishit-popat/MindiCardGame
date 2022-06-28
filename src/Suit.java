/**
 * This enum contains suits and its priorities
 */
public enum Suit {
    CLUB(4),
    DIAMOND(3),
    HEART(2),
    SPADE(1);

    private int priority;

    Suit(int priority){
        this.priority = priority;
    }

    public void setPriority(int i){
        priority = i;
    }

    public int getPriority(){
        return priority;
    }
}
