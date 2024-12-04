package player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.ICard;
import strategy.CornerStrategy;
import strategy.FlipMaximizerStrategy;
import strategy.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Machine Player class in model. Just to note that the game keeps track of how
 * many players are added, and automatically appends a number at the end of the player's name to
 * signal they were added to the game in that order. For example, the 5th player added to the game
 * would have a name of "TestPlayer5". So, for the purposes of this test, since Before is used to
 * set up players before each test, we will test player names through its substring in order to test
 * that the original name (before that number is added). This is so that in case the tests run in a
 * different order for the grader it does not cause all our tests to fail.
 */
public class MachinePlayerTest {
  private MachinePlayer player;
  private List<ICard> initialHand;
  private Strategy mockStrategy;

  @Before
  public void setUp() {
    initialHand = new ArrayList<>();
    initialHand.add(new Card("Test Card 1", 1, 1, 1, 1));
    initialHand.add(new Card("Test Card 2", 2, 2, 2, 2));
    mockStrategy = new FlipMaximizerStrategy();
    player = new MachinePlayer("TestPlayer", initialHand, mockStrategy);
  }

  @Test
  public void testConstructor() {
    assertEquals("TestPlayer", player.getName().substring(0, 10));
    assertEquals(initialHand, player.getHand());
    assertEquals(mockStrategy, player.getStrategy());
  }

  @Test
  public void testConstructorWithNullName() {
    assertThrows(IllegalArgumentException.class, () -> new MachinePlayer(
            null, initialHand, mockStrategy));
  }

  @Test
  public void testConstructorWithNullHand() {
    assertThrows(IllegalArgumentException.class, () -> new MachinePlayer(
            "TestPlayer", null, mockStrategy));
  }

  @Test
  public void testConstructorWithNullStrategy() {
    assertThrows(IllegalArgumentException.class, () -> new MachinePlayer(
            "TestPlayer", initialHand, null));
  }

  @Test
  public void testGetName() {
    assertEquals("TestPlayer", player.getName().substring(0, 10));
  }

  @Test
  public void testGetHand() {
    List<ICard> hand = player.getHand();
    assertEquals(initialHand, hand);
    assertNotSame(initialHand, hand); // Ensure it's a copy
  }

  @Test
  public void testIsComputer() {
    assertTrue(player.isComputer());
  }

  @Test
  public void testGetStrategy() {
    assertEquals(mockStrategy, player.getStrategy());
  }

  @Test
  public void testSetHand() {
    List<ICard> newHand = new ArrayList<>();
    newHand.add(new Card("New Card", 3, 3, 3, 3));
    player.setHand(newHand);
    assertEquals(newHand, player.getHand());
  }

  @Test
  public void testSetHandWithNull() {
    assertThrows(NullPointerException.class, () -> player.setHand(null));
  }

  @Test
  public void testRemoveCard() {
    ICard cardToRemove = initialHand.get(0);
    player.removeCard(cardToRemove);
    assertFalse(player.getHand().contains(cardToRemove));
  }

  @Test
  public void testRemoveNonExistentCard() {
    Card nonExistentCard = new Card("Non-existent", 9, 9, 9, 9);
    player.removeCard(nonExistentCard);
    assertEquals(initialHand, player.getHand());
  }

  @Test
  public void testAddCard() {
    Card newCard = new Card("New Card", 3, 3, 3, 3);
    player.addCard(newCard);
    assertTrue(player.getHand().contains(newCard));
  }

  @Test
  public void testEqualsEqualObjects() {
    IPlayer player2 = this.player;
    assertEquals(player, player2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    List<ICard> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<ICard> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand1);
    IPlayer player2 = new MachinePlayer("Player2", hand2, new CornerStrategy());
    assertNotEquals(player1, player2);
  }

  @Test
  public void testEqualsNull() {
    IPlayer player = new MachinePlayer("Player1",
            List.of(new Card("MysticEagle", 5, 10, 4, 2)),
            new FlipMaximizerStrategy());
    assertNotEquals(player, null);
  }

  @Test
  public void testHashCodeEqualObjects() {
    List<ICard> hand = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand);
    IPlayer player2 = player1;
    assertEquals(player1.hashCode(), player2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    List<ICard> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<ICard> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    IPlayer player2 = new MachinePlayer("Player2", hand1, new FlipMaximizerStrategy());
    IPlayer player1 = new HumanPlayer("Player1", hand2);
    assertNotEquals(player1.hashCode(), player2.hashCode());
  }
}
