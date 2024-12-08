package finalProviderCode.model;

/**
 * An interface to represent a card.
 */
public interface Card {

  /**
   * Gets the value associated with the north direction.
   *
   * @return the north value.
   */
  int getNorth();

  /**
   * Gets the value associated with the north direction.
   *
   * @return the east value.
   */
  int getEast();

  /**
   * Gets the value associated with the north direction.
   *
   * @return the south value.
   */
  int getSouth();

  /**
   * Gets the value associated with the north direction.
   *
   * @return the west value.
   */
  int getWest();

  /**
   * get information about the card, in form of a string.
   */
  String getInfo();
}
