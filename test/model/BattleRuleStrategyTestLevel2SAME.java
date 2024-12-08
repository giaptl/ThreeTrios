package model;

import org.junit.Before;
import org.junit.Test;

import static model.CardValues.A;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import extraFeatures.SameBattleRule;
import finalProviderCode.model.ThreeTriosModel;
import player.HumanPlayer;
import player.IPlayer;

public class BattleRuleStrategyTestLevel2SAME {
  private ThreeTriosModel sameRuleModel;
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

    // Create cards for testing (need at least 10 cards)
    cards = new ArrayList<>();

    // Cards for testing Same rule scenario
    cards.add(new Card("CardA", 5, 4, 3, A.getValue())); // Red Card #1
    cards.add(new Card("CardC", 4, 4, 4, 4)); // Red Card #2
    cards.add(new Card("Cardex1", 1, 1, 1, 1)); // Red Card #3
    cards.add(new Card("Cardex2", 1, 1, 1, 1)); // Red Card #4
    cards.add(new Card("Cardex3", 1, 1, 1, 1)); // Red Card #5

    // Cards for Blue player
    cards.add(new Card("CardD", 1, 1, 1, 1)); // Blue Card #1
    cards.add(new Card("CardD", 1, 1, 1, 1)); // Blue Card #1
    cards.add(new Card("CardB", 6, 6, 6, 6)); // Blue Card #2
    cards.add(new Card("CardE", A.getValue(), A.getValue(), A.getValue(), A.getValue())); // Blue Card #3
    cards.add(new Card("Cardex4", 1, 1, 1, 1)); // Blue Card #4
  }

  @Test
  public void testSameRuleGame() {
    sameRuleModel = new GameModel(new SameBattleRule());
    sameRuleModel.startGameWithConfig(grid, cards, false, redPlayer, bluePlayer);
    for (ICard card : sameRuleModel.getPlayerHand(redPlayer)) {
      System.out.println("RedPlayer: " + card.getName());
    }

    for (ICard card : sameRuleModel.getPlayerHand(bluePlayer)) {
      System.out.println("BlooPlayer: " + card.getName());
    }

    // Play out the Same rule scenario

    // ROUND 1
    // Red's turn: Place Card C
    // _ _ _
    // _ _ _
    // _ R _
    sameRuleModel.playCard(redPlayer, sameRuleModel.getPlayerHand(redPlayer).get(1), 2, 1);

    // ROUND 2
    // Blue's turn: Place Card D
    // _ _ _
    // _ _ B
    // _ R _
    System.out.println("Round 2:" + sameRuleModel.getPlayerHand(bluePlayer).get(0).getName());
    sameRuleModel.playCard(bluePlayer, sameRuleModel.getPlayerHand(bluePlayer).get(0), 1, 2);

    // ROUND 3
    // Red's turn: Place Cardex1
    // _ _ _
    // _ _ B
    // R R _
    sameRuleModel.playCard(redPlayer, sameRuleModel.getPlayerHand(redPlayer).get(1), 2, 0);

    // ROUND 4
    // Blue's turn: Place Card B
    // _ B _
    // _ _ B
    // R R _
    sameRuleModel.playCard(bluePlayer, sameRuleModel.getPlayerHand(bluePlayer).get(0), 0, 1);

    // ROUND 5
    // Red's turn: Place Cardex2
    // _ B _
    // _ _ B
    // R R R
    sameRuleModel.playCard(redPlayer, sameRuleModel.getPlayerHand(redPlayer).get(1), 2, 2);
    // Note that Card D (blue's card) should NOT be flipped because same rule ONLY applies if
    // adjacent numbers are same in at least 2 directions
    assertEquals(bluePlayer, sameRuleModel.getGrid().getCell(1, 2).getOwner());
    assertEquals(redPlayer, sameRuleModel.getGrid().getCell(2, 2).getOwner());

    // ROUND 6
    // Blue's turn: Place Card E
    // _ B _
    // B _ B
    // B R R
    System.out.println("Round 6:" + sameRuleModel.getPlayerHand(bluePlayer).get(0).getName());
    sameRuleModel.playCard(bluePlayer, sameRuleModel.getPlayerHand(bluePlayer).get(0), 1, 0);
    System.out.println(sameRuleModel.getGrid());

    // Note that Cardex1 (red's card) at (2,0) SHOULD be flipped bc blue's A is greater than red's 1
    assertEquals(bluePlayer, sameRuleModel.getGrid().getCell(1, 0).getOwner());
    assertEquals(bluePlayer, sameRuleModel.getGrid().getCell(2, 0).getOwner());

    // ROUND 7
    // Red's turn: Place Card A
    // _ B _
    // B R R
    // B R R
    sameRuleModel.playCard(redPlayer, sameRuleModel.getPlayerHand(redPlayer).get(0), 1, 1);

    // Verify Card E is flipped (due to Same rule)
    // Card E should have turned red since it's adjacent to Card A (A west = A east)
    System.out.println(sameRuleModel.getGrid().getCell(1, 0).getOwner().getName());
    assertEquals(redPlayer, sameRuleModel.getGrid().getCell(1, 0).getOwner());

    // Card C should be red as well since it's adjacent to Card A (4 south = 4 north)
    // Should already be red but just making sure
    assertEquals(redPlayer, sameRuleModel.getGrid().getCell(2, 1).getOwner());

    // Verify Card D is flipped (due to regular rules)
    // Due to regular game rules, Card D (blue's card) should also be red (3 east > 1 west)
    assertEquals(redPlayer, sameRuleModel.getGrid().getCell(1, 2).getOwner());

  }
}