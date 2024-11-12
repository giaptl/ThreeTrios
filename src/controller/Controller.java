package controller;

import model.Card;
import model.Player;
import model.ThreeTriosModel;
import view.IGameView;


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
public class Controller {
  private final ThreeTriosModel model;
  private final IGameView view;
  private Card selectedCard = null;
  private Player selectedPlayer = null;

  public Controller(ThreeTriosModel model, IGameView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Handles the event when a card in a player's hand is clicked.
   * If the clicked card is already selected, it will be deselected.
   * Otherwise, the clicked card will be selected.
   *
   * @param player the player who clicked the card
   * @param cardIndex the index of the clicked card in the player's hand
   */
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


  /**
   * Handles the event when a grid cell is clicked.
   * If a card is selected, it attempts to play the card at the specified grid cell.
   * If the move is valid, the grid cell is updated, the card is removed from the player's hand,
   * and the view is refreshed. If the move is invalid, an error message is displayed.
   *
   * @param row the row index of the clicked grid cell
   * @param col the column index of the clicked grid cell
   */
  public void handleGridClick(int row, int col) {
    System.out.println("Grid cell clicked at row: " + row + ", col: " + col);

    if (selectedCard != null && selectedPlayer != null) {
      try {
        model.playCard(selectedPlayer, selectedCard, row, col);
        view.updateGridCell(row, col, selectedCard);
        view.removeCardFromHandPanel(selectedPlayer, selectedCard);
        view.refreshView();
        selectedCard = null;
        selectedPlayer = null;
      } catch (IllegalArgumentException e) {
        view.showError("Invalid move: " + e.getMessage());
      }
    }
  }
}