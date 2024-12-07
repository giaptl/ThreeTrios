package model;

import org.junit.Before;
import org.junit.Test;

import static model.CardValues.A;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import player.HumanPlayer;
import player.IPlayer;

public class BattleRuleStrategyTestLevel2 {
  private ThreeTriosModel model;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private Grid grid;
  private List<ICard> cards;

  @Before
  public void setUp() {
    // Create a 3x3 grid
    grid = new Grid(3, 3);

    // Initialize players
    redPlayer = new HumanPlayer("Red", new ArrayList<>());
    bluePlayer = new HumanPlayer("Blue", new ArrayList<>());

    // Create at least 10 cards (9 for grid + 1 extra as required)
    cards = new ArrayList<>();
    // Cards for Same rule test
    cards.add(new Card("CardA", 5, 4, 3, A.getValue())); // Card A for red
    cards.add(new Card("CardB", 2, 6, 8, 3));   // Card B for blue
    cards.add(new Card("CardC", 4, 7, 2, 1));   // Card C for red
    cards.add(new Card("CardD", 9, 5, 1, 4));   // Card D for blue
    cards.add(new Card("CardE", 6, 2, A.getValue(), 5)); // Card E for blue
    // Extra cards to meet minimum requirement
    cards.add(new Card("Card6", 3, 3, 3, 3));
    cards.add(new Card("Card7", 4, 4, 4, 4));
    cards.add(new Card("Card8", 5, 5, 5, 5));
    cards.add(new Card("Card9", 6, 6, 6, 6));
    cards.add(new Card("Card10", 7, 7, 7, 7));
  }

  @Test
  public void testSameStrategy() {
    // Initialize model with Same battle strategy
    model = new GameModel(new SameBattleRule());
    model.startGameWithConfig(grid, cards, false, redPlayer, bluePlayer);

    // Set up the board state as per example
    // Place Card B (BLUE)
    model.playCard(redPlayer, cards.get(1), 0, 1);
    // Place Card C (RED)
    model.playCard(bluePlayer, cards.get(2), 2, 1);
    // Place Card D (BLUE)
    model.playCard(redPlayer, cards.get(3), 1, 2);
    // Place Card E (BLUE)
    model.playCard(bluePlayer, cards.get(4), 1, 0);

    // Play Card A (RED) in center - this should trigger the Same rule
    model.playCard(bluePlayer, cards.get(0), 1, 1);

    // Verify Card E is flipped (due to Same rule)
    assertEquals(bluePlayer, (model.getGrid().getCell(1, 0)).getOwner());
    // Verify Card D is flipped (due to regular rules)
    assertEquals(bluePlayer, (model.getGrid().getCell(1, 2)).getOwner());
  }

  @Test
  public void testPlusStrategy() {
    // Create new set of cards for Plus rule test
    List<ICard> plusCards = new ArrayList<>(cards); // Copy base cards
    // Replace relevant cards with Plus rule specific values
    plusCards.set(1, new Card("CardB", 3, 5, 8, 2)); // Card B for blue (SOUTH=5)
    plusCards.set(2, new Card("CardC", 6, 7, 2, 1)); // Card C for red (NORTH=6)
    plusCards.set(3, new Card("CardD", 9, 5, 7, 4)); // Card D for red (WEST=7)
    plusCards.set(4, new Card("CardE", 6, 2, 1, 5)); // Card E for blue (EAST=1)

    // Initialize model with Plus battle strategy
    model = new GameModel(new PlusBattleRule());
    model.startGameWithConfig(grid, plusCards, false, redPlayer, bluePlayer);

    // Set up the board state as per example
    // Place Card B (BLUE)
    model.playCard(redPlayer, plusCards.get(1), 0, 1);
    // Place Card C (RED)
    model.playCard(bluePlayer, plusCards.get(2), 2, 1);
    // Place Card D (RED)
    model.playCard(redPlayer, plusCards.get(3), 1, 2);
    // Place Card E (BLUE)
    model.playCard(bluePlayer, plusCards.get(4), 1, 0);

    // Play Card A (RED) in center - this should trigger the Plus rule
    model.playCard(bluePlayer, plusCards.get(0), 1, 1);

    // Verify Card B is flipped (due to Plus rule - sum of 10)
    assertEquals(bluePlayer, (model.getGrid().getCell(0, 1)).getOwner());
    // Verify Card E is flipped (due to regular rules)
    assertEquals(bluePlayer, (model.getGrid().getCell(1, 0)).getOwner());
  }
}
