package model;

/**
 * Enum for card values. Used to represent the value of a card.
 */
public enum CardValues {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  A(10);

  private final int value;

  /**
   * Constructor of the enum class to assign values.
   */
  CardValues(int value) {
    this.value = value;
  }

  /**
   * Gets value of the card. Scoped since this is only used in testing.
   */
  public int getValue() {
    return value;
  }
}