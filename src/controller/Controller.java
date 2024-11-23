// Controller.java
package controller;

import model.Card;
import model.IPlayer;
import model.ModelStatusListener;
import model.ThreeTriosModel;
import strategy.Move;
import view.IGameView;

public class Controller implements PlayerActionListener, ModelStatusListener {
  private final ThreeTriosModel model;
  private final IGameView view;
  private Card selectedCard = null;
  private final IPlayer player;

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
    this.player.addPlayerActionListener(this);
    this.model.addModelStatusListener(this);
  }

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
      view.showError("It's not your turn.");
      return;
    }

    // Show error message if grid cell is selected before selecting a card from the hand.
    if (selectedCard == null) {
      view.showError("Player " + player.getName() + ": You must select a card before selecting a grid cell.");
      return;
    }

    System.out.println("Grid cell clicked at row: " + row + ", col: " + col);

    if (selectedCard != null) {
      try {
        model.playCard(player, selectedCard, row, col);
        view.updateGridCell(row, col, selectedCard);
        view.removeCardFromHandPanel(player, selectedCard);
        view.refreshView();
        selectedCard = null; // Reset selectedCard after playing a card

        // Check if the game is over
        if (model.isGameOver()) {
          IPlayer winner = model.getWinner();
          if (winner == null) {
            gameOver(null);
          }
          gameOver(winner);
        }
      } catch (IllegalArgumentException e) {
        view.showError("Invalid move: " + e.getMessage());
      }
    }
  }

  @Override
  public void onMoveSelected(Move move) {
    if (!isPlayerTurn()) {
      view.showError("It's not your turn.");
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
    // If the current player is a computer, select and play a move automatically
    if (player.isComputer()) {
      Move move = player.getStrategy().selectMove(player, model);
      onGridCellSelected(move.getRow(), move.getCol());
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


  public void modelChanged() {
    view.refreshView(); // Refresh view when the model changes
  }
}