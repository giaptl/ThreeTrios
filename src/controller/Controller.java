package controller;

import configuration.PlayerActions;
import model.Card;
import model.ModelFeatures;
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
public class Controller implements ControllerFeatures, ModelFeatures {
  private final ThreeTriosModel model;
  private final IGameView view;
  private Card selectedCard = null;
  private Player selectedPlayer = null;

  public Controller(ThreeTriosModel model, IGameView view) {
    this.model = model;
    this.view = view;
  }

  @Override
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


  @Override
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