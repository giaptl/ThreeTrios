package extrafeatures;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;


import model.Card;
import model.GameModel;
import model.Grid;
import model.ICard;
import model.ThreeTriosModel;
import player.HumanPlayer;
import player.IPlayer;

import static model.CardValues.A;

public class BattleRuleStrategyTestLevel2PLUS {

  private ThreeTriosModel plusRuleModel;
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

    // Red player cards (first half)
    cards.add(new Card("CardA", 5, 4, 3, A.getValue())); // Red Card #1 - Main card for Plus rule
    cards.add(new Card("CardC", 6, 7, 2, 1));           // Red Card #2 - South card (NORTH=6)
    cards.add(new Card("CardD", 9, 5, 7, 4));           // Red Card #3 - East card (WEST=7)
    cards.add(new Card("Cardex1", 1, 1, 1, 1));         // Red Card #4
    cards.add(new Card("Cardex2", 1, 1, 1, 1));         // Red Card #5

    // Blue player cards (second half)
    cards.add(new Card("CardB", 3, 5, 8, 2));           // Blue Card #1 - North card (SOUTH=5)
    cards.add(new Card("CardE", 6, 2, 1, 5));           // Blue Card #2 - West card (EAST=1)
    cards.add(new Card("Cardex3", 1, 1, 1, 1));         // Blue Card #3
    cards.add(new Card("Cardex4", 1, 1, 1, 1));         // Blue Card #4
    cards.add(new Card("Cardex5", 1, 1, 1, 1));         // Blue Card #5
  }

  @Test
  public void testPlusRuleGame() {
    plusRuleModel = new GameModel(new PlusBattleRule());
    plusRuleModel.startGameWithConfig(grid, cards, false, redPlayer, bluePlayer);


    // ROUND 1
    // Red's turn: Place Card C (NORTH=6)
    // _ _ _
    // _ _ _
    // _ R _
    plusRuleModel.playCard(redPlayer, plusRuleModel.getPlayerHand(redPlayer).get(1), 2, 1);

    // ROUND 2
    // Blue's turn: Place Card B (SOUTH=5)
    // _ B _
    // _ _ _
    // _ R _
    plusRuleModel.playCard(bluePlayer, plusRuleModel.getPlayerHand(bluePlayer).get(0), 0, 1);

    // ROUND 3
    // Red's turn: Place Card D (WEST=7)
    // _ B _
    // _ _ R
    // _ R _
    plusRuleModel.playCard(redPlayer, plusRuleModel.getPlayerHand(redPlayer).get(1), 1, 2);

    // ROUND 4
    // Blue's turn: Place Card E (EAST=1)
    // _ B _
    // B _ R
    // _ R _
    plusRuleModel.playCard(bluePlayer, plusRuleModel.getPlayerHand(bluePlayer).get(1), 1, 0);

    // ROUND 5
    // Red's turn: Place Card A in center - this should trigger Plus rule
    // _ R _
    // B R R
    // _ R _
    plusRuleModel.playCard(redPlayer, plusRuleModel.getPlayerHand(redPlayer).get(0), 1, 1);

    // Verify Card B is flipped (due to Plus rule - sum of 10)
    // Card B should be red since NORTH(A) + SOUTH(B) = 5 + 5 = 10
    assertEquals(redPlayer, plusRuleModel.getGrid().getCell(0, 1).getOwner());

    // Card E should be red due to regular battle rules (A's WEST > E's EAST)
    assertEquals(redPlayer, plusRuleModel.getGrid().getCell(1, 0).getOwner());

    // Card C and D should remain red as they were already owned by red
    assertEquals(redPlayer, plusRuleModel.getGrid().getCell(2, 1).getOwner());
    assertEquals(redPlayer, plusRuleModel.getGrid().getCell(1, 2).getOwner());
  }
}
