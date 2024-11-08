package strategy;

import model.Player;
import model.ReadOnlyThreeTriosModel;

public interface Strategy {
  /**
   * Selects the best move (card + position) based on the current state of the game.
   *
   * @param player The player making the move.
   * @param model The current state of the game.
   * @return A Move object representing the selected card and position.
   */
  Move selectMove(Player player, ReadOnlyThreeTriosModel model);
}
