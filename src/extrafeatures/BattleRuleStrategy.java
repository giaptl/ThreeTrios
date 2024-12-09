package extrafeatures;

import model.Direction;
import model.ICard;

/**
 * Represents a strategy for determining the winner of a battle.
 */
public interface BattleRuleStrategy {

  boolean shouldFlipCard(ICard cardA, ICard cardB, Direction direction);

}
