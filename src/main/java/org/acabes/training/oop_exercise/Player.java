package org.acabes.training.oop_exercise;

public class Player {
    private String name;
    private int score;
    private final Card[] cards;

    private static final int CARDS_PER_PLAYER = 13;

    Player() {
        cards = new Card[CARDS_PER_PLAYER];
    }

    void printCards() {
        for (Card card : cards) {
            System.out.println(card.toString());
        }
    }

    void addCard(int index, Card card) {
        cards[index] = card;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
