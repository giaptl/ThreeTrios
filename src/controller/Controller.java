// Controller.java
package controller;

import model.Card;
import player.IPlayer;
import model.ModelStatusListener;
import model.ThreeTriosModel;
import strategy.Move;
import view.IGameView;

/**
 * The Controller class is responsible for handling user interactions and updating the model and
 * view accordingly. It implements the PlayerActionListener and ModelStatusListener interfaces to
 * respond to player actions and model changes.
 */
public class Controller implements PlayerActionListener, ModelStatusListener {
  private final ThreeTriosModel model;
  private final IGameView view;
  private Card selectedCard = null;
  private final IPlayer player;
  private boolean gameOverCalled = false;

  /**
   * Constructs a Controller with the specified model, player, and view.
   *
   * @param model the game model
   * @param player the player associated with this controller
   * @param view the game view
   * @throws IllegalArgumentException if model, player, or view are null
   */
  public Controller(ThreeTriosModel model, IPlayer player, IGameView view) {
    if (model == null) {
      throw new IllegalArgumentException("Model must not be null");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View must not be null");
    }
    this.model = model;
    this.player = player;
    this.view = view;

    // Register as a listener for player actions and model status events
    this.model.addModelStatusListener(this);
    this.player.addPlayerActionListener(this);
    onPlayerTurn(model.getCurrentPlayer());
  }

  /**
   * Checks if it is the player's turn.
   *
   * @return true if it is the player's turn, false otherwise
   */
  private boolean isPlayerTurn() {
    return model.getCurrentPlayer().equals(player);
  }

  @Override
  public void onCardSelected(IPlayer player, int cardIndex) {
    if (!isPlayerTurn()) {
      view.showError("It's not your turn.");
      return;
    }

    if (!this.player.equals(player)) {
      view.showError("You cannot select cards from another player's hand.");
      return;
    }

    Card clickedCard = model.getPlayerHand(player).get(cardIndex);

    if (clickedCard.equals(selectedCard)) {
      // Deselect card if clicked again
      selectedCard = null;
      System.out.println("Deselected card from " + player.getName());
      view.updateCardSelection(player, null);
    } else {
      // Select new card
      selectedCard = clickedCard;
      System.out.println("Selected card " + selectedCard.getName()
              + " from " + player.getName() + " at index: " + cardIndex);
      view.updateCardSelection(player, selectedCard);
    }
  }

  @Override
  public void onGridCellSelected(int row, int col) {
    if (!isPlayerTurn()) {
      view.showError("It's not your turn.: onGridCellSelected");
      return;
    }

    if (!(player.isComputer())) {
      // Handle human player turn
      handleHumanPlayerTurn(row, col);
    } else {
      // Handle machine player turn
      handleMachinePlayerTurn();
    }
  }

  /**
   * Handles the turn for a machine player (automatic play).
   */
  private void handleMachinePlayerTurn() {
    new Thread(() -> {
      try {
        // Introduce a 500ms delay
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }

      System.out.println("Machine player " + player.getName() + " is taking its turn.");
      player.takeTurn(model);
      view.refreshView();

      if (model.isGameOver()) {
        IPlayer winner = model.getWinner();
        synchronized (this) {
          if (!gameOverCalled) {
            gameOverCalled = true;
            view.showGameOver(winner, model.getPlayerScore(winner));
          }
        }
      } else {
        // Set the next player and trigger their turn
        onPlayerTurn(model.getCurrentPlayer());
      }
    }).start();
  }

  /**
   * Handles the turn for a human player (manual play).
   *
   * @param row the row index of the selected grid cell
   * @param col the column index of the selected grid cell
   */
  private void handleHumanPlayerTurn(int row, int col) {
    // Show error message if grid cell is selected before selecting a card from the hand.
    if (selectedCard == null) {
      view.showError("Player " + player.getName() +
              ": You must select a card before selecting a grid cell.");
      return;
    }

    try {
      model.playCard(player, selectedCard, row, col);
      view.updateGridCell(row, col, selectedCard);
      view.removeCardFromHandPanel(player, selectedCard);
      view.refreshView();
      selectedCard = null; // Reset selectedCard after playing a card

      // Check if the game is over
      if (model.isGameOver()) {
        IPlayer winner = model.getWinner();
        gameOver(winner);
      }
    } catch (IllegalArgumentException e) {
      view.showError("Invalid move: " + e.getMessage());
    }
  }

  @Override
  public void onMoveSelected(Move move) {
    if (model.isGameOver()) {
      return;
    }
    if (!isPlayerTurn()) {
      view.showError("It's not your turn.: onMoveSelected");
      //System.out.println("Ignoring move: It's not " + player.getName() + "'s turn.");
      return;
    }
    try {
      // Apply the move to the model
      model.playCard(model.getCurrentPlayer(), move.getCard(), move.getRow(), move.getCol());
      // Update the view to reflect the changes
      view.updateGridCell(move.getRow(), move.getCol(), move.getCard());
      view.removeCardFromHandPanel(model.getCurrentPlayer(), move.getCard());
      view.refreshView();
    } catch (IllegalArgumentException e) {
      // Handle invalid move
      view.showError("Invalid move: " + e.getMessage());
    }
  }

  @Override
  public void onPlayerTurn(IPlayer player) {
    System.out.println("It's now " + player.getName() + "'s turn.");
    if (player.equals(this.player) && player.isComputer()) {
      handleMachinePlayerTurn();
    }
  }

  @Override
  public void gameOver(IPlayer winner) {
    if (winner == null) {
      int winningScore = model.getPlayerScore(player);
      view.showGameOver(null, winningScore);
    } else {
      int winningScore = model.getPlayerScore(winner);
      view.showGameOver(winner, winningScore);
    }
  }

  @Override
  public void onCardPlayed(IPlayer player, Card card, int row, int col) {
    view.updateGridCell(row, col, card);
    view.removeCardFromHandPanel(player, card);
    view.refreshView();
  }
}