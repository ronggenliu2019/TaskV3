package com.test;

import java.util.Objects;

public class Card {
    private final int rank;
    private final Suit suit;

    public enum Suit
    {
        SPADES, HEARTS, DIAMONDS, CLUBS, JOKERS;
    }

    private final String[] RANKS = {null, "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(int rank, Suit suit) {
        if (suit == null) {
            throw new IllegalArgumentException("Invalid suit for card");
        }
        if ((suit == Suit.JOKERS && rank != 20) || (suit != Suit.JOKERS && (rank < 1 || rank > 13))) {
            throw new IllegalArgumentException("Invalid rank for card");
        }
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public static Suit[] getSuits()
    {
        return Suit.values();
    }

    public String getRankName() {
        return this.suit == Suit.JOKERS? "Joke" : RANKS[this.getRank()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }
}
