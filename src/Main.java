import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DeckOfCards deckOfCards = new DeckOfCards();
        CardOperation cardOperation = new CardOperation();
        deckOfCards.generateDeckOfCards();

        System.out.print("Enter number of players: ");
        int numberOfPlayers = scanner.nextInt();

        Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name of Player" + (i + 1));
            players[i] = new Player(scanner.next());
        }

        /*
         * Remove cards so that cards can be distributed among the players equally....
         */
        while (deckOfCards.cardsList.size() % numberOfPlayers != 0) {
            cardOperation.pickAndRemoveRandomCard(deckOfCards.cardsList);
        }

        /*
        Give cards to each player randomly...
         */
        while (deckOfCards.cardsList.size() > 0) {
            for (Player p : players) {
                p.addCard(cardOperation.pickAndRemoveRandomCard(deckOfCards.cardsList));
            }
        }

        /*
        While there is one card left, this loop should run...
         */
        while (players[0].getPlayersCardList().size() > 0) {

            ArrayList<Card> cardsOnTheTable = new ArrayList<>();

            /* This block adds the card of first player */
            System.out.println("Turn of " + players[0].getName());
            System.out.println("Choose index of card to pick-up from: ");

            for (int i = 0; i < players[0].getPlayersCardList().size(); i++) {
                System.out.println("Index: " + i +
                        " Card: " + players[0].getPlayersCardList().get(i));
            }

            int index = scanner.nextInt();

            /* Here main card indicates that this player has started this round.. */
            Card mainCard = players[0].getPlayersCardList().remove(index);

            System.out.println(players[0].getName() + " picked: " + mainCard);

            cardsOnTheTable.add(mainCard);

            /* The first player decides the suit of the hand... */
            CardOperation.CURRENT_SUIT = mainCard.getSuit();
            System.out.println("\nCurrent Suit: " + CardOperation.CURRENT_SUIT);

            /* This loop adds the cards for other remaining players */
            for (int i = 1; i < players.length; i++) {

                System.out.println("\nTurn of " + players[i].getName());
                System.out.println("Choose index of card to pick-up from: ");

                for (int j = 0; j < players[i].getPlayersCardList().size(); j++) {
                    System.out.println("Index: " + j +
                            "  Card: " + players[i].getPlayersCardList().get(j));
                }

                boolean loopVariable = false;
                while (!loopVariable) {

                    List<Card> availableCards = players[i].getPlayersCardList().stream()
                            .filter(card -> card.getSuit().getPriority() == mainCard.getSuit().getPriority())
                            .toList();

                    System.out.println("List of available cards for " + players[i].getName() + ": ");

                    if (availableCards.size() == 0) {

                        System.out.println("You can choose any card from above...");
                        index = scanner.nextInt();

                        /* loop for invalid card entries... */
                        while(index < 0 || index > availableCards.size()){
                            System.out.println("Invalid Input...  Pick again");
                            index = scanner.nextInt();
                        }

                        Card c = players[i].getPlayersCardList().remove(index);

                        /* This operation runs only one time,
                         * when SIR is null and user don't have card of current suit */
                        if (CardOperation.SIR == null) {
                            CardOperation.SIR = c.getSuit();
                        }
                        System.out.println(players[i].getName() + " picked: " + c);
                        System.out.println("\nSir: " + CardOperation.SIR + "\n");
                        cardsOnTheTable.add(c);
                        loopVariable = true;

                    } else {
                        System.out.println(availableCards);
                        index = scanner.nextInt();

                        /* loop for invalid card entries... */
                        while(index < 0 || index > availableCards.size()){
                            System.out.println("Invalid Input...  Pick again");
                            index = scanner.nextInt();
                        }

                        Card chosenCard = players[i].getPlayersCardList().get(index);
                        System.out.println("Chosen card: " + chosenCard);

                        for (Card card : availableCards) {

                            if (chosenCard.getSuit().getPriority() == card.getSuit().getPriority()) {
                                players[i].getPlayersCardList().remove(index);
                                cardsOnTheTable.add(chosenCard);
                                System.out.println(players[i].getName() + " picked: " + chosenCard);
                                loopVariable = true;
                                break;
                            } else {
                                System.out.println("Invalid choice.... Pick again");
                                index = scanner.nextInt();

                                /* loop for invalid card entries... */
                                while(index < 0 || index > availableCards.size()){
                                    System.out.println("Invalid Input...  Pick again");
                                    index = scanner.nextInt();
                                }

                                chosenCard = players[i].getPlayersCardList().get(index);
                                System.out.println(players[i].getName() + " picked: " + chosenCard);
                            }
                        }
                    }
                }
            }

            /* This block compares all the cards on the table */
            int winningPlayerIndex = cardOperation.getWinningCard(cardsOnTheTable);
            /* Increase number of hands of winning player */
            players[winningPlayerIndex].setNumberOfHands(players[winningPlayerIndex].getNumberOfHands() + 1);

            /* Increase number of hands of 10 for winning user, if this hand contains card with 10 */
            for (Card c : cardsOnTheTable) {
                if (c.getRank().getValue() == 10) {
                    players[winningPlayerIndex]
                            .setNumberOfHandsWithTen(players[winningPlayerIndex].getNumberOfHandsWithTen() + 1);
                }
            }

            /* Printing of winning player */
            System.out.println("\nThis hands is won by..." + players[winningPlayerIndex].getName() + "\n");

            /* Left rotate the players array so that winning player gets the first chance */
            leftRotate(players, winningPlayerIndex, players.length);

        }

        int indexOfPlayerWithMostNumberOfHands = 0;
        int indexOfPlayerWithMostNumberOfHandsWith10 = 0;

        /* Print the output */
        for (int i = 0; i < players.length; i++) {
            System.out.println("Player Name: " + players[i].getName());
            System.out.println("Winning Hands: " + players[i].getNumberOfHands());
            System.out.println("Winning Hands with 10: " + players[i].getNumberOfHandsWithTen());
            System.out.println();

            if (players[indexOfPlayerWithMostNumberOfHands].getNumberOfHands()
                    < players[i].getNumberOfHands()) {
                indexOfPlayerWithMostNumberOfHands = i;
            }

            if (players[indexOfPlayerWithMostNumberOfHandsWith10].getNumberOfHandsWithTen()
                    < players[i].getNumberOfHandsWithTen()) {
                indexOfPlayerWithMostNumberOfHandsWith10 = i;
            }
        }

        /* Print the winning players */
        System.out.println("\n\nPlayer with most number of hands of 10: " +
                players[indexOfPlayerWithMostNumberOfHandsWith10].getName());
        System.out.println("Player with most number of hands in general: " +
                players[indexOfPlayerWithMostNumberOfHands].getName());

    }

    /* helper method to rotate the array */
    static void leftRotate(Player arr[], int d, int n) {
        for (int i = 0; i < d; i++)
            leftRotatebyOne(arr, n);
    }

    /* helper method to rotate the array by one */
    static void leftRotatebyOne(Player arr[], int n) {
        int i;
        Player temp;
        temp = arr[0];
        for (i = 0; i < n - 1; i++)
            arr[i] = arr[i + 1];
        arr[i] = temp;
    }
}
