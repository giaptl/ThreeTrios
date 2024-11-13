package strategy;

import model.Player;
import model.ReadOnlyThreeTriosModel;

public class CombinedStrategy implements Strategy {
  private final Strategy cornerStrategy;
  private final Strategy mainStrategy;
  private int moveCount;

  public CombinedStrategy(Strategy mainStrategy) {
    this.cornerStrategy = new CornerStrategy();
    this.mainStrategy = mainStrategy;
    this.moveCount = 0;
  }

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    moveCount++;
    if (moveCount <= 4) {
      return cornerStrategy.selectMove(player, model);
    } else {
      return mainStrategy.selectMove(player, model);
    }
  }
}
