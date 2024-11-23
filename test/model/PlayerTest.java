package model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import player.HumanPlayer;
import player.IPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the Player class in model.
 */
public class PlayerTest {

  @Test
  public void testPlayerCreation() {
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );
    IPlayer player = new HumanPlayer("TestPlayer", hand);
    assertNotNull(player);
    assertEquals("TestPlayer", player.getName());
    assertEquals(hand, player.getHand());
  }

  @Test
  public void testGetName() {
    IPlayer player = new HumanPlayer("TestPlayer", List.of());
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  public void testGetHand() {
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );
    IPlayer player = new HumanPlayer("TestPlayer", hand);
    assertEquals(hand, player.getHand());
  }

  @Test
  public void testEqualsEqualObjects() {
    List<Card> hand = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand);
    IPlayer player2 = new HumanPlayer("Player1", hand);
    assertEquals(player1, player2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    List<Card> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<Card> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand1);
    IPlayer player2 = new HumanPlayer("Player2", hand2);
    assertNotEquals(player1, player2);
  }

  @Test
  public void testEqualsNull() {
    IPlayer player = new HumanPlayer("Player1", List.of(new Card("MysticEagle", 5, 10, 4, 2)));
    assertNotEquals(player, null);
  }

  @Test
  public void testHashCodeEqualObjects() {
    List<Card> hand = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand);
    IPlayer player2 = new HumanPlayer("Player1", hand);
    assertEquals(player1.hashCode(), player2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    List<Card> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<Card> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    IPlayer player1 = new HumanPlayer("Player1", hand1);
    IPlayer player2 = new HumanPlayer("Player2", hand2);
    assertNotEquals(player1.hashCode(), player2.hashCode());
  }
}
