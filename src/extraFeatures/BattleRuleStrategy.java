package extraFeatures;

import model.Direction;
import model.ICard;

public interface BattleRuleStrategy {

  boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction);

}
