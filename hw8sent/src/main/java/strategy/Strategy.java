package strategy;

import players.Player;

/**
 * The Strategy interface defines the method to be implemented
 * by all concrete strategy classes.
 */
public interface Strategy {
  Move executeStrategy(Player player);

  /**
   * Executes the specific strategy's logic.
   */
  void executeStrategy();

}
