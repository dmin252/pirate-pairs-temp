import java.util.Arrays;

public class Player {
    private Card[] hand;
    private int handSize;
    private int points;

    public Player() {
        this.hand = new Card[55];
        this.handSize = 0;
        this.points = 0;
    }

    public void addToHand(Card card) {
        hand[handSize++] = card;
    }

    public Card[] getHand() {
        return hand;
    }

    public boolean hasPair(Card card) {
        for (int i = 0; i < handSize; i++) {
            if (hand[i] != null && hand[i].getValue() == card.getValue()) {
                return true;
            }
        }
        return false;
    }

    public int getLowestCardValue() {
        if (handSize == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < handSize; i++) {
            if (hand[i] != null && hand[i].getValue() < min) {
                min = hand[i].getValue();
            }
        }
        return min;
    }

    public void clearHand() {
        for (int i = 0; i < handSize; i++) {
            hand[i] = null;
        }
        handSize = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getHandSize() { 
        return handSize;
    }

    public Card deleteCard(int cardNum) { 
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null && hand[i].getValue() == cardNum) {
                Card temp = hand[i];
                hand[i] = null;
                handSize--;
                if (hand[i + 1] != null) {
                    for (int j = i + 1; j < hand.length; j++) {
                        hand[j - 1] = hand[j];
                    }
                }
                return temp;
            }
        }
        return null;
    }
}
