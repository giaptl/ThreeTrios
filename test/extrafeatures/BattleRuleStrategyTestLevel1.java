package extrafeatures;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.Card;
import model.Direction;
import model.ICard;

import static model.CardValues.A;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleRuleStrategyTestLevel1 {
  private ICard cardA;
  private ICard cardB;
  private ICard cardC;


  @Before
  public void setUp() {
    cardA = new Card("A", 1, 1, 1, 1);
    // Card A:
    //       1
    // 1          1
    //       1
    cardB = new Card("B", 2, 2, 2, 2);
    // Card B:
    //       2
    // 2          2
    //       2
    cardC = new Card("C", A.getValue(),  A.getValue(),  A.getValue(),  A.getValue());
    // Card C:
    //       A
    // A          A
    //       A
  }

  @Test
  public void testNormalBattleRule() {
    BattleRuleStrategy normalRule = new NormalBattleRule();
    // NormalBattleRule: 1 vs 2
    assertFalse(normalRule.shouldFlipCard(cardA, cardB, Direction.NORTH));
    // NormalBattleRule: 2 vs 1
    assertTrue(normalRule.shouldFlipCard(cardB, cardA, Direction.NORTH));
    // NormalBattleRule: 1 vs A
    assertFalse(normalRule.shouldFlipCard(cardA, cardC, Direction.NORTH));
    // NormalBattleRule: A vs 1
    assertTrue(normalRule.shouldFlipCard(cardC, cardA, Direction.NORTH));
  }

  @Test
  public void testReverseBattleRule() {
    BattleRuleStrategy reverseRule = new ReverseBattleRule();
    // ReverseBattleRule: 1 vs 2
    assertTrue(reverseRule.shouldFlipCard(cardA, cardB, Direction.NORTH));
    // ReverseBattleRule: 2 vs 1
    assertFalse(reverseRule.shouldFlipCard(cardB, cardA, Direction.NORTH));
    // ReverseBattleRule: 1 vs A
    assertTrue(reverseRule.shouldFlipCard(cardA, cardC, Direction.NORTH));
    // ReverseBattleRule: A vs 1
    assertFalse(reverseRule.shouldFlipCard(cardC, cardA, Direction.NORTH));
  }

  @Test
  public void testFallenAceBattleRule() {
    BattleRuleStrategy fallenAceRule = new FallenAceBattleRule();
    // FallenAceBattleRule: 1 vs 2
    assertFalse(fallenAceRule.shouldFlipCard(cardA, cardB, Direction.NORTH));
    // FallenAceBattleRule: 2 vs 1
    assertTrue(fallenAceRule.shouldFlipCard(cardB, cardA, Direction.NORTH));
    // FallenAceBattleRule: 1 vs A
    assertTrue(fallenAceRule.shouldFlipCard(cardA, cardC, Direction.NORTH));
    // FallenAceBattleRule: A vs 1
    assertFalse(fallenAceRule.shouldFlipCard(cardC, cardA, Direction.NORTH));
  }

  @Test
  public void testCombinedBattleRule() {
    BattleRuleStrategy combinedRule = new CombinedBattleRule(
            List.of(new ReverseBattleRule(), new FallenAceBattleRule())
    );
    // AceLosesToTen: 1 vs A
    assertFalse(combinedRule.shouldFlipCard(cardA, cardC, Direction.NORTH));
    // TenLosesToAce: A vs 1
    assertTrue(combinedRule.shouldFlipCard(cardC, cardA, Direction.NORTH));
    // ReverseBattleRule: 1 vs 2
    assertTrue(combinedRule.shouldFlipCard(cardA, cardB, Direction.NORTH));
    // ReverseBattleRule: 2 vs 1
    assertFalse(combinedRule.shouldFlipCard(cardB, cardA, Direction.NORTH));
  }
}
