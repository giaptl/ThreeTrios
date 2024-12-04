package model.utils;

import model.cards.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Hand} class represents a collection of cards held by a player.
 * It provides methods to add, remove, and retrieve cards in the player's hand.
 */
public class Hand {
  private final List<Card> cards;

  /**
   * Constructs an empty {@code Hand}.
   */
  public Hand() {
    this.cards = new ArrayList<>();
  }

  /**
   * Adds a card to the hand.
   *
   * @param card the {@code Card} to add
   */
  public void addCard(Card card) {
    cards.add(card);
  }

  /**
   * Removes a card from the hand.
   *
   * @param card the {@code Card} to remove
   */
  public void removeCard(Card card) {
    cards.remove(card);
  }

  /**
   * Returns a copy of the list of cards in the hand.
   * This ensures the original list is not modified externally.
   *
   * @return a new {@code List} containing the cards in the hand
   */
  public List<Card> getCards() {
    return new ArrayList<>(cards);
  }
}
