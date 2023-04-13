package org.acabes.training.oop_exercise;

import org.acabes.training.Exercise;

import java.util.Scanner;

/**
 * - Input the game name from user
 * - Input player names from user
 * - Shuffle and distribute deck of cards on the four players
 * - Print the Game info:
 * Game Name
 * Player Names
 * The cards in with each player, including number and type
 */
public class OopExercise implements Exercise {
    Scanner scanner;

    OopExercise() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void solve() {
        // prompt info from the user and create game object.
        String gameName = promptGameName();
        Game game = new Game(gameName);
        promptPlayerNames(game);

        // shuffle & distribute deck.
        game.shuffleDeck();
        game.distributeDeck();

        // print game info.
        game.printName();
        game.printPlayerNames();
        game.printPlayerCards();
    }

    /**
     * Prompt names of 4 players and set them in the given game.
     */
    void promptPlayerNames(Game game) {
        for (int i = 0; i < 4; i++) {
            System.out.printf("Enter player (%s) name: ", i + 1);
            String playerName = scanner.next();
            game.getPlayer(i).setName(playerName);
        }
    }

    /**
     * Ask user to enter name of the game.
     * @return Game name entered  by the user.
     */
    String promptGameName() {
        System.out.print("Enter game name: ");
        return scanner.next();
    }
}
