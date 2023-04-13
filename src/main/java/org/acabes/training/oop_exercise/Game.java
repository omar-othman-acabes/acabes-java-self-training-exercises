package org.acabes.training.oop_exercise;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private final String name;
    private final Player[] players;
    private ArrayList<Card> deck;
    private static final int DECK_SIZE = 52;

    public Game(String gameName) {
        this.name = gameName;
        initializeDeck();
        players = new Player[4];

        for (int i = 0; i < 4; i++) {
            players[i] = new Player();
        }
    }

    String getName() {
        return name;
    }

    Player getPlayer(int index) {
        return players[index];
    }

    public void printName() {
        System.out.println("Game Name: " + getName());
    }

    public void printPlayerNames() {
        System.out.print("Player names: ");
        System.out.println(String.join(", ",
                players[0].getName(),
                players[1].getName(),
                players[2].getName(),
                players[3].getName())
        );
    }

    void printPlayerCards() {
        for (Player player : players) {
            System.out.printf("Cards of %s:%n", player.getName());
            player.printCards();
        }
    }

    private void initializeDeck() {
        deck = new ArrayList<>(DECK_SIZE);
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                Card card = new Card(j, CardType.values()[i]);
                deck.add(card);
            }
        }
    }

    void shuffleDeck() {
        Collections.shuffle(deck);
    }

    void distributeDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                players[i].addCard(j, deck.get(13 * i + j));
            }
        }
    }
}
