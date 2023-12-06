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
        //setting up the cards
        int index = 0;
        Random rand = new Random();

        for (int value = 1; value <= 10; value++) {
            for (int i = 0; i < value; i++) {
                cards[index++] = new Card(value);
            }
        }
    }

    private void shuffleDeck() {
        //I swap cards to shuffle
        Random rand = new Random();
        for (int i = deckSize - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(cards, i, j);
        }
    }

    private void swap(Card[] arr, int i, int j) {
        //swap used for shuffling
        Card temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public Card drawCard() {
        if (deckSize == 0) {
            return null;
        }

        int randomIndex = new Random().nextInt(deckSize);
        Card drawnCard = cards[randomIndex];
        cards[randomIndex] = cards[deckSize - 1];
        cards[deckSize - 1] = null;
        deckSize--;

        return drawnCard;
    }

    public void discard(Card card) {
        discardPile[discardSize++] = card;
    }

    public Card[] getDiscardPile() {
        return Arrays.copyOf(discardPile, discardSize);
    }

    public int getDeckSize() {
        return deckSize;
    }

    public void DiscardToDeck() {
        //if you used all cards, you have to shuffle the discarded cards
        for (int i = 0; i < discardPile.length; i ++) {
            if (discardPile[i] != null) {
                cards[i] = discardPile[i];
            }
        }
        shuffleDeck();
    } 
}
