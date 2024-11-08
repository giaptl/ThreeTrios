package strategy;

import model.Player;
import model.ReadOnlyThreeTriosModel;

public class CombinedStrategy implements Strategy {
  private final Strategy firstStrategy;
  private final Strategy fallbackStrategy;

  public CombinedStrategy(Strategy firstStrategy, Strategy fallbackStrategy) {
    this.firstStrategy = firstStrategy;
    this.fallbackStrategy = fallbackStrategy;
  }

  @Override
  public Move selectMove(Player player, ReadOnlyThreeTriosModel model) {
    Move firstAttempt = firstStrategy.selectMove(player, model);

    if (firstAttempt != null && isGoodEnough(firstAttempt)) {
      return firstAttempt;
    } else {
      return fallbackStrategy.selectMove(player, model);
    }
  }

  private boolean isGoodEnough(Move move) {
    // Define logic to determine if first attempt is good enough.
    return true;
  }
}
