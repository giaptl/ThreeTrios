package finalProviderCode.controller;

import finalProviderCode.model.Card;


/**
 * Represents a player listener for player actions in the game.
 * Allows the controller to communicate with the model when a player takes actions.
 */
public interface PlayerAction {

  /**
   * Called when a player selects a card in their hand.
   *
   * @param card The selected card.
   */
  void onHandCardSelected(Card card);

  /**
   * Called when a player wants to place a card onto the grid.
   *
   * @param card The card to be placed.
   * @param rows The row location.
   * @param cols The column location.
   */
  void onCardPlaced(Card card, int rows, int cols);

  /**
   * Called when a player ends their turn.
   */
  void onViewHand();

  /**
   * Called when a player requests to view the grid.
   */
  void onViewGrid();

  /**
   * Sets the player action listener to handle player interactions.
   *
   * @param listener The player action listener.
   */
  void setPlayerActionListener(PlayerAction listener);

  /**
   * Called when a player previews a move on the grid.
   *
   * @param card The card being previewed.
   * @param rows The row location for the preview.
   * @param cols The column location for the preview.
   */
  void onPreviewMove(Card card, int rows, int cols);
}
