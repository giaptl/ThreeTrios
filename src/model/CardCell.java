package model;

import java.util.Objects;

import player.IPlayer;

/**
 * Represents a cell on the grid that contains a card.
 */
public class CardCell implements Cell {

  private Card card;
  private IPlayer owner;

  /**
   * Creates a new instance of the class with a null card.
   */
  public CardCell() {
    this.card = null;
  }

  /**
   * Create an instance of CardCell with a card and owner assigned to it.
   * Happens when a card is placed on the board.
   */
  public CardCell(Card card, IPlayer owner) {
    this.card = card;
    this.owner = owner;
  }

  @Override
  public boolean isHole() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return card == null;
  }

  /**
   * Checks if the cell is occupied.
   *
   * @return true if the cell is occupied, false otherwise.
   */
  public boolean isOccupied() {
    return card != null;
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public IPlayer getOwner() {
    return owner;
  }

  /**
   * Sets the card in the cell.
   *
   * @param owner the owner to set for the CardCell
   */
  void setOwner(IPlayer owner) {
    this.owner = owner;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CardCell cardCell = (CardCell) obj;
    return Objects.equals(card, cardCell.card) && Objects.equals(owner, cardCell.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(card, owner);
  }
}
