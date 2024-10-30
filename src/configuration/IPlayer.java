package configuration;

import java.util.List;

import model.Card;

public interface IPlayer {

  /**
   * Returns the name of the player. Provides a way to retrieve the player's name, which is useful
   * for displaying information and tracking turns.
   *
   * @return the name of the player
   */
  String getName();

  /**
   * Returns the player's current hand of cards, allowing the game model to access and display the
   * player's cards.
   *
   * @return the player's hand of cards
   */
  List<Card> getHand();

  /**
   * Plays the given card from the player's hand, allowing the game model to update the game state
   * and enforce game rules.
   *
   * @param card the card to be played
   */
  void playCard(Card card);

  /**
   * Returns the player's score, which is the number of cards the player has on the grid and in
   * their hand. This is useful for determining the winner of the game.
   *
   * @return the player's score
   */
  int getScore();

  /**
   * Returns whether the player is a human or computer player. This is useful for determining how
   * the player's actions are handled (for when AI players are implemented).
   *
   * @return true if the player is a human player, false if the player is a computer player
   */
  boolean isHuman();
}
