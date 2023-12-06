import java.util.Arrays;
import java.util.Scanner;


public class PiratePairs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        System.out.println("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        int losingScore = (60 / numPlayers) + 1;

        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player();
        }

        Deck deck = new Deck();
        int currentPlayerIndex = 0;
        int eachTurn = 1;   

        while (!done) {
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn");

            System.out.println("Player " + +(currentPlayerIndex + 1) + "'s card is " + Arrays.toString(Arrays.copyOfRange(currentPlayer.getHand(), 0, currentPlayer.getHandSize()))); // ADD 현재 카드 파악하기 위해서

            int choice = 1;

            if (currentPlayer.getHandSize() > 0) { 

                for (int i = 0; i < currentPlayer.getHand().length; i++) {
                    Card card = currentPlayer.getHand()[i];
                    if (card != null && card.getValue() >= losingScore - currentPlayer.getPoints()) {

                        int lowestCardValue = 10; 
                        for (Player player : players) {  
                            if (lowestCardValue > player.getLowestCardValue()) { 
                                lowestCardValue = player.getLowestCardValue();  
                            } 
                        } 
                        if (lowestCardValue < losingScore - currentPlayer.getPoints()) {
                            choice = 2;
                        }
                    }
                }
            }
            int totalCard = 0; 
            for (Player player : players) { 
                totalCard += player.getHandSize(); 
            }
            if (totalCard == 0) { 
                choice = 1; 
            }

            if (eachTurn <= numPlayers || currentPlayer.getHand().length == 0) {    
                choice = 1;     
            }                   

            if (choice == 1) {
                Card drawnCard = deck.drawCard();

                if (drawnCard != null) {
                    System.out.println("Player " + (currentPlayerIndex + 1) + " drew a card: " + drawnCard.getValue());

                    if (currentPlayer.hasPair(drawnCard)) {
                        int points = drawnCard.getValue();
                        System.out.println("Pair found! " + "Player " + (currentPlayerIndex + 1) + " gain " + points + " points.");
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
                    }
                } else {
                    System.out.println("There are no more cards left!");
                }
            } else {
                int lowestCardValue = 10; 
                int lowestCardPlayer = 0; 
                for (int i = 0; i < players.length; i++) {  
                    int lowerCardValue = players[i].getLowestCardValue();
                    if (lowerCardValue != 0 && lowestCardValue > lowerCardValue) { 
                        lowestCardValue = lowerCardValue;  
                        lowestCardPlayer = i;
                    } 
                } 
                Card deleteCard = players[lowestCardPlayer].deleteCard(lowestCardValue); 
                deck.discard(deleteCard);
                System.out.println("Player " + (currentPlayerIndex + 1) + " folded. " + "Player " + (currentPlayerIndex + 1) + " take the points of the lowest card in play: " + lowestCardValue);
                currentPlayer.addPoints(lowestCardValue);
                currentPlayer.clearHand();
            }
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s current score is " + currentPlayer.getPoints());
            int playerPoints = currentPlayer.getPoints();
            if (playerPoints > losingScore) {
                System.out.println("Player " + (currentPlayerIndex + 1) + " has exceeded the losing score. Game over!");
                done = true;
                break;
            }
            if (deck.getDeckSize() == 0) {
                System.out.println("There is no more card. Discard card to Deck"); // ADD
                deck.DiscardToDeck();
            } 
            eachTurn++;
            currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
        }
    }
}