package org.acabes.training.oop_exercise;

public class Card {
    private final int number;
    private final CardType type;

    Card(int number, CardType type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("number: %02d, type: %s", number, type);
    }
}
