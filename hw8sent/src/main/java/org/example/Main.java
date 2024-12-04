package org.example;

import model.cards.Card;
import model.cards.CardLoader;
import model.grid.Grid;
import model.grid.GridLoader;
import model.managers.GameManager;
import players.Player;
import players.PlayerImpl;

import java.util.List;

/**
 * The entry point for the Three Trios game.
 * This class sets up the game components, distributes cards to players,
 * and manages the game flow.
 */
public class Main {

  /**
   * The main method initializes the grid, players, and game manager.
   * It also loads the cards and starts the game.
   *
   * @param args command-line arguments (not used in this game).
   */
  public static void main(String[] args) {

    Grid grid = GridLoader.loadGrid("src/main/resources/grids/grid_example.txt");

    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/cards_example.txt");

    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");

    distributeCards(cards, redPlayer, bluePlayer);

    GameManager gameManager = new GameManager(grid, redPlayer, bluePlayer);

    gameManager.startGame();

    gameManager.playTurn(redPlayer.getHand().get(0), 1, 1);
    gameManager.playTurn(bluePlayer.getHand().get(0), 0, 0);

    if (gameManager.isGameOver()) {
      System.out.println("Game Over!");
    }
  }

  /**
   * Distributes the cards equally between the two players.
   * Alternates cards between the two players as they are added from the list.
   *
   * @param cards the list of cards to be distributed.
   * @param red the red player.
   * @param blue the blue player.
   */
  private static void distributeCards(List<Card> cards, Player red, Player blue) {
    for (int i = 0; i < cards.size(); i++) {
      if (i % 2 == 0) {
        red.addCard(cards.get(i));
      } else {
        blue.addCard(cards.get(i));
      }
    }
  }
}
