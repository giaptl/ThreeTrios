package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import extraFeatures.BattleRuleStrategy;
import extraFeatures.CombinedBattleRule;
import extraFeatures.FallenAceBattleRule;
import extraFeatures.PlusBattleRule;
import extraFeatures.ReverseBattleRule;
import extraFeatures.SameBattleRule;
import player.HumanPlayer;
import player.IPlayer;

import static model.CardValues.A;
import static org.junit.Assert.assertEquals;

public class BattleRuleStrategyTestLevel3 {
  private ThreeTriosModel combinedModel;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private List<ICard> cards;
  private Grid grid;

  @Before
  public void setUp() {
    // Create a 3x3 grid
    grid = new Grid(3, 3);

    // Initialize players
    redPlayer = new HumanPlayer("Red", new ArrayList<>());
    bluePlayer = new HumanPlayer("Blue", new ArrayList<>());

    // Create cards for testing (minimum 10 cards)
    cards = new ArrayList<>();

    // Red player cards (first half)
    cards.add(new Card("CardA", 1, 1, 1, 1)); // Red Card #1
    cards.add(new Card("CardB", 1, 1, 1, 1)); // Red Card #2
    cards.add(new Card("CardC", 4, 2, A.getValue(), 4)); // Red Card #3
    cards.add(new Card("CardD", 4, 3, 2, 6)); // Red Card #4
    cards.add(new Card("CardE", 5, A.getValue(), 7, 2)); // Red Card #5

    // Blue player cards (second half)
    cards.add(new Card("CardF", 2, 2, 2, 2)); // Blue Card #1
    cards.add(new Card("CardF", 2, 2, 2, 2)); // Blue Card #1
    cards.add(new Card("CardG", 3, 3, 3, 3)); // Blue Card #2
    cards.add(new Card("CardH", 2, 6, A.getValue(), 7)); // Blue Card #3
    cards.add(new Card("CardI", 9, 4, 4, 5)); // Blue Card #4
    cards.add(new Card("CardJ", 1, 6, 6, 8)); // Blue Card #5
  }

  @Test
  public void testReversePlusCombination() {
    // Combine Reverse (Level 1) with Plus (Level 2)
    List<BattleRuleStrategy> strategies = Arrays.asList(
            new PlusBattleRule(),
            new ReverseBattleRule()
    );
    combinedModel = new GameModel(new CombinedBattleRule(strategies));
    combinedModel.startGameWithConfig(grid, cards, false, redPlayer, bluePlayer);

    for (ICard card : combinedModel.getPlayerHand(redPlayer)) {
      System.out.println("RedPlayer: " + card.getName());
    }

    for (ICard card : combinedModel.getPlayerHand(bluePlayer)) {
      System.out.println("BlooPlayer: " + card.getName());
    }

    // Test that both Reverse and Plus rules are working

    // Play moves that trigger Reverse rule
    combinedModel.playCard(redPlayer, combinedModel.getPlayerHand(redPlayer).get(0), 2, 1);
    combinedModel.playCard(bluePlayer, combinedModel.getPlayerHand(bluePlayer).get(0), 2, 2);

    // Verify no cards flipped
    assertEquals(redPlayer, combinedModel.getGrid().getCell(2, 1).getOwner());
    assertEquals(bluePlayer, combinedModel.getGrid().getCell(2, 2).getOwner());

    // Play moves that trigger Reverse rule
    combinedModel.playCard(redPlayer, combinedModel.getPlayerHand(redPlayer).get(0), 1, 2);
    // Check that blue's card is flipped under reverse rule
    assertEquals(redPlayer, combinedModel.getGrid().getCell(2, 2).getOwner());

    // Play moves that trigger Plus rule
    combinedModel.playCard(bluePlayer, combinedModel.getPlayerHand(bluePlayer).get(0), 1, 1);
    // Verify Plus rule worked (all cards should be blue bc sums are 4 in 2 directions)
    assertEquals(bluePlayer, combinedModel.getGrid().getCell(1, 1).getOwner());
    assertEquals(bluePlayer, combinedModel.getGrid().getCell(1, 2).getOwner());
    assertEquals(bluePlayer, combinedModel.getGrid().getCell(2, 1).getOwner());
    assertEquals(bluePlayer, combinedModel.getGrid().getCell(2, 2).getOwner());
  }

  @Test
  public void testFallenAceSameCombination() {
    // Combine FallenAce (Level 1) with Same (Level 2)
    List<BattleRuleStrategy> strategies = Arrays.asList(
            new FallenAceBattleRule(),
            new SameBattleRule()
    );
    combinedModel = new GameModel(new CombinedBattleRule(strategies));
    combinedModel.startGameWithConfig(grid, cards, false, redPlayer, bluePlayer);

    // Test that both FallenAce and Same rules are working

    // Play moves that trigger FallenAce rule
    combinedModel.playCard(redPlayer, combinedModel.getPlayerHand(redPlayer).get(2), 1, 1);
    // Verify FallenAce rule worked (Ace should lose to 1)
    // assertEquals(bluePlayer, combinedModel.getGrid().getCell(1, 1).getOwner());

    // Play moves that trigger Same rule
    combinedModel.playCard(bluePlayer, combinedModel.getPlayerHand(bluePlayer).get(0), 0, 1);
    // Verify Same rule worked (matching values in opposing directions)
    // assertEquals(redPlayer, combinedModel.getGrid().getCell(0, 1).getOwner());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCombination() {
    // Try to combine Same and Plus rules (should throw exception)
    List<BattleRuleStrategy> strategies = Arrays.asList(
            new SameBattleRule(),
            new PlusBattleRule()
    );
    combinedModel = new GameModel(new CombinedBattleRule(strategies));
  }
}
