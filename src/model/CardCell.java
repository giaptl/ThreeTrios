package model;

import java.util.Objects;

/**
 * Represents a cell on the grid that contains a card.
 */
public class CardCell implements Cell {

  private Card card;
  private Player owner;

  public CardCell() {
    this.card = null;
  }

  public CardCell(Card card, Player owner) {
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
  public Player getOwner() {
    return owner;
  }

  /**
   * Sets the card in the cell.
   *
   * @param owner the owner to set for the CardCell
   */
  void setOwner(Player owner) {
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
