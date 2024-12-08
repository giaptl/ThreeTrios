package player;

import java.util.List;

import controller.PlayerActionListener;
import model.ICard;
import finalProviderCode.model.ThreeTriosModel;
import strategy.Strategy;

/**
 * The IPlayer interface defines the methods that a player in the game must implement.
 * It includes methods for taking a turn, managing the player's hand, and interacting with the game
 * model.
 */
public interface IPlayer {

  /**
   * Takes a turn in the game using the specified model.
   *
   * @param model the game model
   */
  void takeTurn(ThreeTriosModel model);

  /**
   * Adds a PlayerActionListener to this player.
   *
   * @param listener the listener to add
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Removes a PlayerActionListener from this player.
   *
   * @param listener the listener to remove
   */
  void removePlayerActionListener(PlayerActionListener listener);

  /**
   * Public method which adds the card from a players hand.
   *
   * @param card represents the card that will be added.
   */
  void addCard(ICard card);

  /**
   * Public method which removes the card from a players hand.
   *
   * @param card represents the card that will be removed.
   */
  void removeCard(ICard card);

  /**
   * Gets the name of the player.
   * @return the name of the player
   */
  String getName();

  /**
   * Returns a copy of the current player's hand at any instance in the game.
   */
  List<ICard> getHand();

  /**
   * Checks if the player is a computer.
   *
   * @return true if the player is a computer, false otherwise
   */
  boolean isComputer();

  /**
   * Gets the strategy used by the player.
   *
   * @return the strategy used by the player, or null if the player is human
   */
  Strategy getStrategy();

  /**
   * Sets the player's hand to the specified list of cards.
   *
   * @param hand the list of cards to set as the player's hand
   */
  void setHand(List<ICard> hand);
}
