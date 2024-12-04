package model.grid;

import model.cards.Card;

/**
 * Represents a single cell on the game grid, which may contain a card owned by a player.
 * A cell can either be empty, filled with a card belonging to a specific owner, or be a hole.
 */
public class Cell {

  /** The card placed in this cell, or {@code null} if the cell is empty. */
  private Card card;

  /** The owner of the card in this cell, represented as a string (e.g., "RED" or "BLUE"). */
  private String owner;

  /** A flag to indicate if the cell is a hole or not. */
  private boolean isHole;

  /**
   * Constructor to initialize a cell.
   *
   * @param isHole whether the cell is a hole
   */
  public Cell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
    this.owner = null;
  }

  /**
   * Default constructor to initialize a cell as a non-hole (card cell).
   */
  public Cell() {
    this(false); // Default is not a hole
  }

  /**
   * Checks if the cell is empty (i.e., no card has been placed and it's not a hole).
   *
   * @return {@code true} if the cell is empty, {@code false} otherwise
   */
  public boolean isEmpty() {
    return !isHole && card == null;
  }

  /**
   * Sets a card into this cell and assigns an owner to the card.
   *
   * @param card  the card to be placed in the cell
   * @param owner the owner of the card, represented as a string (e.g., "RED" or "BLUE")
   * @throws IllegalStateException if the cell is a hole
   */
  public void setCard(Card card, String owner) {
    if (isHole) {
      throw new IllegalStateException("Cannot place a card in a hole.");
    }
    this.card = card;
    this.owner = owner;
    System.out.println("Card set: " + card + " with owner: " + owner);
  }




  /**
   * Clears the cell, removing the card and its owner.
   */
  public void clearCell() {
    this.card = null;
    this.owner = null;
  }

  /**
   * Retrieves the card placed in this cell.
   *
   * @return the {@link Card} in this cell, or {@code null} if the cell is empty
   */
  public Card getCard() {
    return card;
  }

  /**
   * Checks if the cell is a hole or a playable card cell.
   *
   * @return {@code true} if the cell is a hole, {@code false} otherwise
   */
  public boolean isHole() {
    return this.isHole;
  }

  /**
   * Retrieves the owner of the card in this cell.
   *
   * @return the owner of the card, represented as a string
   * (e.g., "RED" or "BLUE"), or {@code null} if no card is present
   */
  public String getOwner() {
    return this.owner; // Owner of the card in this cell
  }
  

  /**
   * Renders a string representation of the cell for display purposes.
   * If the cell is a hole, it returns "X".
   * If the cell is empty, it returns an underscore ("_").
   * If it contains a card, it returns the first character of the owner's name.
   *
   * @return a string representing the content of the cell
   */
  public String render() {
    if (isHole) {
      return "X"; // Hole representation
    }
    return isEmpty() ? "_" : String.valueOf(owner.charAt(0));
  }

  /**
   * Checks if the cell is occupied (i.e., contains a card).
   *
   * @return {@code true} if the cell contains a card, {@code false} otherwise
   */
  public boolean isOccupied() {
    return card != null;
  }
}


