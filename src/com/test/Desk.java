package com.test;

public class Desk {

    private final Card[] cards;
    private int currentCard;
    public static int CARD_AMOUNT = 54;

    public Desk() {
        cards = new Card[CARD_AMOUNT];
        Card.Suit[] suits = Card.getSuits();

        int i = 0;
        for (Card.Suit suit : suits)
            if (suit == Card.Suit.JOKERS) {
                cards[i++] = new Card(20, suit);
                cards[i++] = new Card(20, suit);
            } else {
                for (int rank = 1; rank <= 13; rank++)
                    cards[i++] = new Card(rank, suit);
            }

        currentCard = 0;
    }

    public void shuffle() {
        synchronized (cards) {
            for (int i = CARD_AMOUNT - 1; i > 0; i--) {
                int j = (int) (CARD_AMOUNT * Math.random());
                Card tmp = cards[j];
                cards[j] = cards[i];
                cards[i] = tmp;
            }
        }
    }

    public Card deal() {
        synchronized (cards) {
            if (currentCard < CARD_AMOUNT) {
                Card card = cards[currentCard++];
                return card;
            } else {
                return (null);
            }
        }
    }
}
