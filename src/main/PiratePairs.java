import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;

public class PiratePairs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        int losingScore = (60 / numPlayers) + 1;

        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player();
        }

        Deck deck = new Deck();

        int currentPlayerIndex = 0;
        while (true) {
            Player currentPlayer = players[currentPlayerIndex];

            System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn:");
            System.out.println("1. Draw a card");
            System.out.println("2. Fold");
            System.out.println("3. See all the cards discarded");

            int choice = scanner.nextInt();

            if (choice == 1) {
                Card drawnCard = deck.drawCard();

                if (drawnCard != null) {
                    System.out.println("Player " + (currentPlayerIndex + 1) + " drew a card: " + drawnCard.getValue());

                    if (currentPlayer.hasPair(drawnCard)) {
                        int points = drawnCard.getValue();
                        System.out.println("Pair found! You gain " + points + " points.");
                        currentPlayer.addPoints(points);
                        deck.discard(drawnCard);
                        for (Card card : currentPlayer.getHand()) {
                            if (card != null) {
                                deck.discard(card);
                            }
                        }
                        currentPlayer.clearHand();
                    } else {
                        currentPlayer.addToHand(drawnCard);
                        System.out.println("Your current score is " + currentPlayer.getPoints());
                    }
                } else {
                    System.out.println("There are no more cards left!");
                    
                }
            } else if (choice == 2) {
                int lowestCardValue = players[currentPlayerIndex].getLowestCardValue();
                System.out.println("You folded. You take the points of the lowest card in play: " + lowestCardValue);
                currentPlayer.addPoints(lowestCardValue);
                for (Card card : currentPlayer.getHand()) {
                    if (card != null) {
                        deck.discard(card);
                    }
                }
                currentPlayer.clearHand();
            } else if (choice == 3) {
                Card[] discardedCards = deck.getDiscardPile();
                System.out.println("Discarded Cards: " + Arrays.toString(discardedCards));
            }

            if (currentPlayer.getHand()[0] == null) {
                System.out.println("Player " + (currentPlayerIndex + 1) + " has no cards. Drawing a new card.");
                Card newCard = deck.drawCard();
                currentPlayer.addToHand(newCard);
                System.out.println("New Card: " + newCard.getValue());
            }

            int playerPoints = currentPlayer.getPoints();
            if (playerPoints > losingScore) {
                System.out.println("Player " + (currentPlayerIndex + 1) + " has exceeded the losing score. Game over!");
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
        }
    }
}

