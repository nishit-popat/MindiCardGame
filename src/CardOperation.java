import java.util.*;

/**
 * This class contains various operations that can be performed on cards
 */
public class CardOperation {

    /* Global variables of SIR and CURRENT_SUIT */
    public static Suit SIR = null;
    public static Suit CURRENT_SUIT = null;

    /**
     * This method randomly chooses the card and removes the card from the list
     *
     * @param cardsList list of cards
     * @return randomly picked card
     */
    public Card pickAndRemoveRandomCard(ArrayList<Card> cardsList) {
        Random random = new Random();
        int index = random.nextInt(cardsList.size());
        Card card = cardsList.get(index);
        cardsList.remove(index);
        return card;
    }

    /**
     * This method accepts the list of cards and
     * sends back the index of winning card among them
     *
     * @param cardsOnTheTable list of cards
     * @return index of winning card
     */
    public int getWinningCard(ArrayList<Card> cardsOnTheTable) {

        System.out.println("Cards on the table: ");
        System.out.println(cardsOnTheTable.toString());

        Card maxValueCard = cardsOnTheTable.get(0);
        int winningCardIndex = 0;

        if (SIR == null) {
            for (int i = 0; i < cardsOnTheTable.size(); i++) {
                // the suits of the card should match and then we have to check the rank */
                if (cardsOnTheTable.get(i).getSuit().getPriority() == maxValueCard.getSuit().getPriority() &&
                        cardsOnTheTable.get(i).getRank().getValue() > maxValueCard.getRank().getValue()) {
                    maxValueCard = cardsOnTheTable.get(i);
                    winningCardIndex = i;
                }
            }
            return winningCardIndex;
        } else {

            /* This linkedHashmap stores the card which is SIR and related index to it */
            Map<Card, Integer> cardWithSIR = new LinkedHashMap<>();

            for (int i = 0; i < cardsOnTheTable.size(); i++) {
                if (cardsOnTheTable.get(i).getSuit().equals(SIR)) {
                    cardWithSIR.put(cardsOnTheTable.get(i), i);
                }
            }

            if (!cardWithSIR.isEmpty()) {

                if (cardWithSIR.size() == 1) {
                    return cardWithSIR.entrySet().iterator().next().getValue();
                } else {

                    Map.Entry<Card, Integer> entry = cardWithSIR.entrySet().iterator().next();
                    maxValueCard = entry.getKey();
                    for (Card card : cardWithSIR.keySet()) {
                        if (card.getSuit().getPriority() == maxValueCard.getSuit().getPriority() &&
                                card.getRank().getValue() > maxValueCard.getRank().getValue()) {
                            maxValueCard = card;
                            winningCardIndex = cardWithSIR.get(card);
                        }
                    }
                    return winningCardIndex;
                }
            } else {
                for (int i = 0; i < cardsOnTheTable.size(); i++) {
                    if (cardsOnTheTable.get(i).getSuit().getPriority() == maxValueCard.getSuit().getPriority() &&
                            cardsOnTheTable.get(i).getRank().getValue() > maxValueCard.getRank().getValue()) {
                        maxValueCard = cardsOnTheTable.get(i);
                        winningCardIndex = i;
                    }
                }
                return winningCardIndex;
            }
        }

    }

}
