package players;

import model.cards.Card;
import java.util.List;

/**
 * Represents a player in the game with a specific color and a hand of cards.
 */
public interface Player {

  /**
   * Gets the color of the player (e.g., RED or BLUE).
   *
   * @return the color of the player as a string.
   */
  String getColor();

  /**
   * Retrieves the current hand of cards held by the player.
   *
   * @return a list of cards in the player's hand.
   */
  List<Card> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card the card to be added.
   */
  void addCard(Card card);

  /**
   * Removes a card from the player's hand.
   *
   * @param card the card to be removed.
   */
  void removeCard(Card card);

  String getName();


}
