package controller;

import model.Card;
import model.Player;
import model.ThreeTriosModel;  // Change to ThreeTriosModel instead of ReadOnly
import view.IGameView;

public class Controller {
  private final ThreeTriosModel model;  // Change to ThreeTriosModel
  private final IGameView view;
  private Card selectedCard = null;
  private Player selectedPlayer = null;

  public Controller(ThreeTriosModel model, IGameView view) {
    this.model = model;
    this.view = view;
  }

  public void handleCardClick(Player player, int cardIndex) {
    Card clickedCard = model.getPlayerHand(player).get(cardIndex);

    if (selectedPlayer == player && clickedCard.equals(selectedCard)) {
      // Deselect card if clicked again
      selectedCard = null;
      selectedPlayer = null;
      System.out.println("Deselected card from " + player.getName());
      view.updateCardSelection(null, null);
    } else {
      // Select new card
      selectedCard = clickedCard;
      selectedPlayer = player;
      System.out.println("Selected card " + selectedCard.getName()
              + " from " + player.getName() + " at index: " + cardIndex);
      view.updateCardSelection(player, selectedCard);
    }
  }


  public void handleGridClick(int row, int col) {
    Card selectedCard = view.getSelectedCard();
    Player selectedPlayer = view.getSelectedPlayer();

    if (selectedCard != null && selectedPlayer != null) {
      try {
        model.playCard(selectedPlayer, selectedCard, row, col);
        view.updateGridCell(row, col, selectedCard);
        view.removeCardFromHandPanel(selectedPlayer, selectedCard);
        view.refreshView();
      } catch (IllegalArgumentException e) {
        view.showError("Invalid move: " + e.getMessage());
      }
    }
  }
}

/**
 * Game Setup and Flow:
 * 1. Load grid configuration from file
 * 2. Load card configuration from file
 * 3. Initialize game with grid and cards
 * 4. Distribute cards to players
 * 5. Red player starts the game
 * 6. Players take turns:
 *    a. Place a card on an empty cell
 *    b. Automatic battle phase for the placed card
 *    c. Switch to the other player
 * 7. Game ends when all card cells are filled
 * 8. Winner is determined by the most cards on the grid and in hand
 */