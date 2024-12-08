import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import finalProviderCode.controller.ThreeTriosController;
import finalProviderCode.model.Card;
import finalProviderCode.model.Cell;
import model.grid.Grid;
import players.Player;

public class Main {
  public static void main(String[] args) {
    // Create players
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");

    Grid grid = new Grid(5, 5);

    // Create a deck of cells
    List<Cell> deck = new ArrayList<>();
    for (int i = 0; i < 25; i++) { // Assuming a 5x5 grid, adjust as necessary
      deck.add(new Cell()); // Add appropriate cells to the deck
    }

    // Create a board (5x5 example)
    Cell[][] board = new Cell[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        board[r][c] = new Cell(); // Initialize each cell
      }
    }


    // Create the model
    ThreeTriosModelImpl model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    // Start the game
    try {
      model.startGame(deck, board, true); // Shuffle set to true
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.err.println("Error starting the game: " + e.getMessage());
      return;
    }

    redPlayer.addCard(new Card("Card1", 2, 3, 1, 4));
    redPlayer.addCard(new Card("Card2", 5, 2, 3, 1));
    redPlayer.addCard(new Card("Card3", 4, 6, 5, 2));
    bluePlayer.addCard(new Card("Card4", 7, 8, 9, 3));
    bluePlayer.addCard(new Card("Card5", 6, 5, 4, 8));
    bluePlayer.addCard(new Card("Card6", 1, 2, 3, 4));


    // Launch the views for both players
    SwingUtilities.invokeLater(() -> {
      GameGUI redPlayerView = new GameGUI(model);
      GameGUI bluePlayerView = new GameGUI(model);

      // Create controllers
      ThreeTriosController redController = new ThreeTriosControllerImpl(model, redPlayer,
              redPlayerView);
      ThreeTriosController blueController = new ThreeTriosControllerImpl(model, bluePlayer,
              bluePlayerView);

      redPlayerView.setVisible(true);
      bluePlayerView.setVisible(true);
    });
  }
}
