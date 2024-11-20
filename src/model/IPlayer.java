package model;

import java.util.List;

import controller.PlayerActionListener;

public interface IPlayer {

  void takeTurn(ThreeTriosModel model);
  void addPlayerActionListener(PlayerActionListener listener);
  void removePlayerActionListener(PlayerActionListener listener);

  /**
   * Public method which adds the card from a players hand.
   *
   * @param card represents the card that will be added.
   */
  void addCard(Card card);

  /**
   * Public method which removes the card from a players hand.
   *
   * @param card represents the card that will be removed.
   */
  void removeCard(Card card);

  /**
   * Gets the name of the player.
   * @return the name of the player
   */
  String getName();

  /**
   * Returns a copy of the current player's hand at any instance in the game.
   */
  List<Card> getHand();

}
