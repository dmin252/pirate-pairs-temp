import java.util.Random;
import java.util.Arrays;

public class Deck {
    private Card[] cards;
    private Card[] discardPile;
    private int deckSize;
    private int discardSize;

    public Deck() {
        this.cards = new Card[55];
        this.discardPile = new Card[55];
        this.deckSize = 55;
        this.discardSize = 0;
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        int index = 0;
        Random rand = new Random();

        for (int value = 1; value <= 10; value++) {
            int numCards = value;
            for (int i = 0; i < numCards; i++) {
                cards[index++] = new Card(value);
            }
        }
    }

    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = deckSize - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(cards, i, j);
        }
    }

    private void swap(Card[] arr, int i, int j) {
        Card temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public Card drawCard() {
        if (deckSize == 0) {
            return null; // Deck is empty
        }

        int randomIndex = new Random().nextInt(deckSize);
        Card drawnCard = cards[randomIndex];
        cards[randomIndex] = cards[deckSize - 1];
        cards[deckSize - 1] = null; // Clear the drawn card from the deck
        deckSize--;

        return drawnCard;
    }

    public void discard(Card card) {
        discardPile[discardSize++] = card;
    }

    public Card[] getDiscardPile() {
        return Arrays.copyOf(discardPile, discardSize);
    }
}

